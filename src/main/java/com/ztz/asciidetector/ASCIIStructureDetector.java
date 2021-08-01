package com.ztz.asciidetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.util.concurrent.AtomicLongMap;
import com.ztz.asciidetector.structure.CharType;
import com.ztz.asciidetector.structure.DetectorSettings;
import com.ztz.asciidetector.structure.row.CharTypesCounts;
import com.ztz.asciidetector.structure.row.RowDescription;
import com.ztz.asciidetector.structure.row.RowDescriptionCreator;

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
		return new ASCIIStructureDetector(new DetectorSettings(500, specialCharacters));
	}

	ASCIIStructureDetector(DetectorSettings detectorSettings) {
		this.detectorSettings = detectorSettings;
	}

	public ASCIIStructureDescription detectStructure(File file) {

		ASCIIStructureDescription asciiStructureDescription = new ASCIIStructureDescription();

		List<RowDescription> rowDescriptions = buildRowDescriptions(file);
		asciiStructureDescription.setDelimiter(detectDelimiter(rowDescriptions));

		return asciiStructureDescription;
	}

	private Character detectDelimiter(List<RowDescription> rowDescriptions) {
		// First solution: the most counted delimiter is the correct one
		AtomicLongMap<Character> delimiterCounts = AtomicLongMap.create();
		for (RowDescription rowDescription : rowDescriptions) {
			CharTypesCounts charTypeCounts = rowDescription.getCharTypeCounts();
			Map<Character, Long> countsFor = charTypeCounts.getCountsFor(CharType.DELIMITER);
			for (Entry<Character, Long> characterCount : countsFor.entrySet()) {
				delimiterCounts.getAndAdd(characterCount.getKey(), characterCount.getValue());
			}
		}
		List<Map.Entry<Character, Long>> sorted = new ArrayList<>(delimiterCounts.asMap().entrySet());
		Collections.sort(sorted, Collections.reverseOrder(Map.Entry.comparingByValue()));
		return sorted.get(0).getKey();
	}

	private List<RowDescription> buildRowDescriptions(File file) {
		List<RowDescription> rowDescriptions = new ArrayList<>();
		RowDescriptionCreator rowDescriptionCreator = new RowDescriptionCreator(detectorSettings);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			int rowIndex = 0;
			String line;
			while (rowIndex < detectorSettings.getMaxNumberOfRowsToAnalyze() && (line = br.readLine()) != null) {
				rowDescriptions.add(rowDescriptionCreator.create(rowIndex, line));
				rowIndex++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowDescriptions;
	}
}
