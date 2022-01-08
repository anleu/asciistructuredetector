package com.ztz.asciidetector.structure.row;

import java.util.Arrays;
import java.util.Set;

import com.google.common.base.Predicate;
import com.ztz.asciidetector.structure.CharType;

public final class RowCalculations {

	public static double calcNumericRowRatio(Row row) {
		return calcCellRatio(CharType.NUMBER, row);
	}

	public static double calcStringRowRatio(Row row) {
		return calcCellRatio(CharType.LETTER, row);
	}

	private static double calcCellRatio(CharType charType, Row row) {
		CharTypesCounts charTypeCounts = row.getCharTypeCounts();
		int rowLength = row.getRowString().length();

		long numberOfCharType = getNumberOfCharType(charTypeCounts, charType);
		long numberOfDelimiterChars = getNumberOfCharType(charTypeCounts, CharType.DELIMITER);

		return numberOfCharType * 1.0 / (rowLength - numberOfDelimiterChars);
	}

	private static long getNumberOfCharType(CharTypesCounts charTypeCounts, CharType charType) {
		return charTypeCounts.getCountsFor(charType)
				.values()
				.stream()
				.mapToLong(l -> l)
				.sum();
	}

	public static boolean containsMetaDataWord(String rowString, Set<String> metaDataWords, char delimiter) {
		String[] split = rowString.split(String.valueOf(delimiter));
		return Arrays.stream(split).anyMatch((Predicate<String>) metaDataWords::contains);
	}

	public static int getNumberOfCells(String rowString, char delimiter) {
		return rowString.split(String.valueOf(delimiter)).length;
	}
}
