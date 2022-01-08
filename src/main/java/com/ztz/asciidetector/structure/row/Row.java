package com.ztz.asciidetector.structure.row;

import java.lang.reflect.Field;
import java.util.Collection;

import com.ztz.asciidetector.structure.CharType;
import com.ztz.asciidetector.DetectorSettings;

public class Row {

	private final DetectorSettings detectorSettings;

	private final int             rowIndex;
	private final String          row;
	private final CharTypesCounts charTypesCounts = new CharTypesCounts();

	public Row(DetectorSettings detectorSettings, int rowIndex, String row) throws NoSuchFieldException, IllegalAccessException {
		this.detectorSettings = detectorSettings;
		this.rowIndex = rowIndex;
		this.row = row;
		fillDescription();
	}

	private void fillDescription() throws NoSuchFieldException, IllegalAccessException {
		/* regarding https://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
		this is the fastes way to iterate through strings with more then 256 chars
		 */
		Field field = String.class.getDeclaredField("value");
		field.setAccessible(true);
		char[] chars = (char[]) field.get(row);
		int len = chars.length;
		for (int i = 0; i < Math.min(len, detectorSettings.getMaxNumberOfRowsToAnalyze()); i++) {
			char nextChar = chars[i];
			handleRowChar(nextChar);
		}
	}

	private void handleRowChar(char nextChar) {
		Collection<Character> possibleDelimiter = detectorSettings.getPossibleDelimiter();
		Collection<Character> possibleThousendsSeparators = detectorSettings.getPossibleThousendsSeparators();

		if (possibleDelimiter.contains(nextChar)) {
			addCharCount(CharType.DELIMITER, nextChar);
		} else if (possibleThousendsSeparators.contains(nextChar)) {
			addCharCount(CharType.DECIMAL_SEPARATOR, nextChar);
		} else if (Character.isLetter(nextChar)) {
			addCharCount(CharType.LETTER, nextChar);
		} else if (Character.isDigit(nextChar)) {
			addCharCount(CharType.NUMBER, nextChar);
		}
	}

	private void addCharCount(CharType charType, char character) {
		charTypesCounts.increase(charType, character);
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public CharTypesCounts getCharTypeCounts() {
		return charTypesCounts;
	}

	public String getRowString() {
		return row;
	}
}
