package com.ztz.asciidetector;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.AtomicLongMap;
import com.ztz.asciidetector.structure.CharType;
import com.ztz.asciidetector.structure.row.CharTypesCounts;
import com.ztz.asciidetector.structure.row.Row;
import com.ztz.asciidetector.structure.row.RowCalculations;
import com.ztz.asciidetector.structure.row.RowFeatures;
import com.ztz.asciidetector.structure.rules.DataRowRules;

public class ASCIIStructureDetector {

    private final DetectorSettings detectorSettings;

    // Todo Create this via builder
    public static ASCIIStructureDetector create() {
        // create with default settings
        Multimap<CharType, Character> specialCharacters = MultimapBuilder.enumKeys(CharType.class).arrayListValues().build();
        specialCharacters.put(CharType.DELIMITER, ';');
        specialCharacters.put(CharType.DELIMITER, ',');
        specialCharacters.put(CharType.DELIMITER, '\t');
        specialCharacters.put(CharType.DECIMAL_SEPARATOR, ',');
        specialCharacters.put(CharType.DECIMAL_SEPARATOR, '.');

        Set<String> metaDataWords = Collections.EMPTY_SET;

        return new ASCIIStructureDetector(new DetectorSettings(500, specialCharacters, metaDataWords));
    }

    ASCIIStructureDetector(DetectorSettings detectorSettings) {
        this.detectorSettings = detectorSettings;
    }

    public ASCIIStructureDescription detectStructure(File file) {

        ASCIIStructureDescription asciiStructureDescription = new ASCIIStructureDescription();
        List<Row> rows = buildRows(file);
        Character delimiter = detectDelimiter(rows);
        asciiStructureDescription.setDelimiter(delimiter);
        List<RowFeatures> rowFeaturesList = buildRowFeatureList(rows, delimiter);

        int dataRowsThreshold = (int) (detectorSettings.getDataRangeThreshold() * rows.size());
        int numOfHeaderRows = 0;
        int numOfDataRows = 0;
        boolean headerRowsDetected = false;

        for (int rowIndex = 0; rowIndex < rowFeaturesList.size(); rowIndex++) {
            RowFeatures rowFeatures = rowFeaturesList.get(rowIndex);

            // ToDo handle meta

            boolean isDataRow = isDataRow(rowFeatures);
            if (!isDataRow && !headerRowsDetected) {
                headerRowsDetected = true;
                numOfHeaderRows++;
            }
            if (!isDataRow && headerRowsDetected && numOfDataRows > 0) {
                // Case: there is a row after some header rows and data rows are detected
                // theoretically possible if there are some meta data at the end of the file and
                // the detection range is long enough
                // ToDo How to handle this case?
            }

            if (isDataRow) {
                if (headerRowsDetected) {
                    asciiStructureDescription.setHeaderSection(Range.closed(0, numOfHeaderRows - 1));
                }
                numOfDataRows++;
                if (numOfDataRows > dataRowsThreshold) {
                    asciiStructureDescription.setDataSection(Range.closed(numOfHeaderRows, rows.size()));
                    break;
                }
            }
        }
        return asciiStructureDescription;
    }

    private boolean isDataRow(RowFeatures rowFeatures) {
        float match = DataRowRules.match(rowFeatures);
        return match > 0.85f;
    }

    private List<RowFeatures> buildRowFeatureList(List<Row> rows, char delimiter) {
        List<RowFeatures> rowFeaturesList = new ArrayList<>();
        for (Row row : rows) {
            RowFeatures rowFeatures = new RowFeatures();
            rowFeatures.setNumericalCellRatio(RowCalculations.calcNumericRowRatio(row));
            rowFeatures.setStringCellRatio(RowCalculations.calcStringRowRatio(row));
            rowFeatures.setContainsMetaDataWord(RowCalculations.containsMetaDataWord(row.getRowString(), detectorSettings.getMetaDataWords(), delimiter));
            rowFeatures.setEmptyLine(row.getRowString().isEmpty());
            rowFeatures.setNumberOfCells(RowCalculations.getNumberOfCells(row.getRowString(), delimiter));
            rowFeaturesList.add(rowFeatures);
        }
        return rowFeaturesList;
    }

    private Character detectDelimiter(List<Row> rows) {
        // First solution: the most counted delimiter is the correct one
        AtomicLongMap<Character> delimiterCounts = AtomicLongMap.create();
        for (Row row : rows) {
            CharTypesCounts charTypeCounts = row.getCharTypeCounts();
            Map<Character, Long> countsFor = charTypeCounts.getCountsFor(CharType.DELIMITER);
            for (Entry<Character, Long> characterCount : countsFor.entrySet()) {
                delimiterCounts.getAndAdd(characterCount.getKey(), characterCount.getValue());
            }
        }
        List<Map.Entry<Character, Long>> sorted = new ArrayList<>(delimiterCounts.asMap().entrySet());
        Collections.sort(sorted, Collections.reverseOrder(Map.Entry.comparingByValue()));
        return sorted.get(0).getKey();
    }

    private List<Row> buildRows(File file) {
        List<Row> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int rowIndex = 0;
            String line;
            while (rowIndex < detectorSettings.getMaxNumberOfRowsToAnalyze() && (line = br.readLine()) != null) {
                Row row = new Row(detectorSettings, rowIndex, line);
                rows.add(row);
                rowIndex++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
