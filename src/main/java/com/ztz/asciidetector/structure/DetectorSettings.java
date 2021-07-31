package com.ztz.asciidetector.structure;

import java.util.Collection;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

public class DetectorSettings {

	private final int                           maxNumberOfRowsToAnalyze = 500;
	private       Multimap<CharType, Character> specialCharacters        = MultimapBuilder.enumKeys(CharType.class).arrayListValues().build();

	public Collection<Character> getPossibleDelimiter() {
		return specialCharacters.get(CharType.DELIMITER);
	}

	public Collection<Character> getPossibleThousendsSeparators() {
		return specialCharacters.get(CharType.DECIMAL_SEPARATOR);
	}

	public int getMaxNumberOfRowsToAnalyze() {
		return maxNumberOfRowsToAnalyze;
	}
}
