package com.ztz.asciidetector.structure.row;

import com.ztz.asciidetector.structure.CharType;

public class RowDescription {

	private final int             rowIndex;
	private final String          row;
	private final CharTypesCounts charTypesCounts = new CharTypesCounts();

	RowDescription(int rowIndex, String row) {
		this.rowIndex = rowIndex;
		this.row = row;
	}

	void addCharCount(CharType charType, char character) {
		charTypesCounts.increase(charType, character);
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public CharTypesCounts getCharCount() {
		return charTypesCounts;
	}
}
