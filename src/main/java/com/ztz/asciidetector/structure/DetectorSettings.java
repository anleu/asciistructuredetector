package com.ztz.asciidetector.structure;

import java.util.Collection;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

public class DetectorSettings {

	private final int                           maxNumberOfRowsToAnalyze;
	private final Multimap<CharType, Character> specialCharacters;

	public DetectorSettings(int maxNumberOfRowsToAnalyze, Multimap<CharType, Character> specialCharacters ) {
		this.maxNumberOfRowsToAnalyze = maxNumberOfRowsToAnalyze;
		this.specialCharacters = specialCharacters;
	}

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
