package com.ztz.asciidetector.structure.row;

import java.util.EnumMap;
import java.util.Map;

import com.google.common.util.concurrent.AtomicLongMap;
import com.ztz.asciidetector.structure.CharType;

public class CharTypesCounts {

	private final Map<CharType, AtomicLongMap<Character>> charTypesCounts;

	CharTypesCounts() {
		charTypesCounts = new EnumMap<>(CharType.class);
		for (CharType charType : CharType.values()) {
			charTypesCounts.put(charType, AtomicLongMap.create());
		}
	}

	void increase(CharType charType, char character) {
		charTypesCounts.get(charType).getAndIncrement(character);
	}

	public Map<Character, Long> getCountsFor(CharType charType) {
		return charTypesCounts.get(charType).asMap();
	}
}
