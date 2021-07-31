package com.ztz.asciidetector.structure;

import java.util.Map;

import com.google.common.util.concurrent.AtomicLongMap;

public class RowDescription {

	private final int                      rowIndex;
	private final AtomicLongMap<Character> charCount = AtomicLongMap.create();

	RowDescription(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	void addCharCount(char character) {
		charCount.getAndIncrement(character);
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public Map<Character, Long> getCharCount() {
		return charCount.asMap();
	}
}
