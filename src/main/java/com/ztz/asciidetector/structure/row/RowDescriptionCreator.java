package com.ztz.asciidetector.structure.row;

import java.lang.reflect.Field;
import java.util.Collection;

import com.ztz.asciidetector.structure.CharType;
import com.ztz.asciidetector.structure.DetectorSettings;

public class RowDescriptionCreator {

	private final DetectorSettings detectorSettings;

	public RowDescriptionCreator(DetectorSettings detectorSettings) {
		this.detectorSettings = detectorSettings;
	}

	public RowDescription create(int rowIndex, String row) {
		RowDescription rowDescription = new RowDescription(rowIndex, row);

		try {
			handleRow(row, rowDescription);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowDescription;
	}

	private void handleRow(String row, RowDescription rowDescription) throws NoSuchFieldException, IllegalAccessException {
		/* regarding https://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
		this is the fastes way to iterate through strings with more then 256 chars
		 */
		Field field = String.class.getDeclaredField("value");
		field.setAccessible(true);

		char[] chars = (char[]) field.get(row);
		int len = chars.length;
		for (int i = 0; i < Math.min(len, detectorSettings.getMaxNumberOfRowsToAnalyze()); i++) {
			char nextChar = chars[i];
			if (nextChar <= ' ') {
				throw new IllegalStateException("UNKNOWN char " + nextChar);
			} else {
				handleRowChar(nextChar, rowDescription);
			}
		}
	}

	private void handleRowChar(char nextChar, RowDescription rowDescription) {
		Collection<Character> possibleDelimiter = detectorSettings.getPossibleDelimiter();
		Collection<Character> possibleThousendsSeparators = detectorSettings.getPossibleThousendsSeparators();

		if (possibleDelimiter.contains(nextChar)) {
			rowDescription.addCharCount(CharType.DELIMITER, nextChar);
		} else if (possibleThousendsSeparators.contains(nextChar)) {
			rowDescription.addCharCount(CharType.DECIMAL_SEPARATOR, nextChar);
		} else if (Character.isLetter(nextChar)) {
			rowDescription.addCharCount(CharType.LETTER, nextChar);
		} else if (Character.isDigit(nextChar)) {
			rowDescription.addCharCount(CharType.NUMBER, nextChar);
		}
	}
}
