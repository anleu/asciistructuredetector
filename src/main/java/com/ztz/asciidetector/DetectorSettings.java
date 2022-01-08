package com.ztz.asciidetector;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.ztz.asciidetector.structure.CharType;

public class DetectorSettings {

	private final int                           maxNumberOfRowsToAnalyze;
	private final Multimap<CharType, Character> specialCharacters;
	private final Set<String>                   metaDataWords;
	private final double                        dataRangeThreshold = 0.25;

	public DetectorSettings(int maxNumberOfRowsToAnalyze, Multimap<CharType, Character> specialCharacters, Set<String> metaDataWords) {
		this.maxNumberOfRowsToAnalyze = maxNumberOfRowsToAnalyze;
		this.specialCharacters = specialCharacters;
		this.metaDataWords = metaDataWords;
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

	public Set<String> getMetaDataWords() {
		return metaDataWords;
	}

	public double getDataRangeThreshold() {
		return dataRangeThreshold;
	}
}
