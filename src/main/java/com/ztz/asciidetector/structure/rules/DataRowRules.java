package com.ztz.asciidetector.structure.rules;

import com.ztz.asciidetector.structure.row.RowFeatures;

public class DataRowRules {

	public static float match(RowFeatures features) {
		if (features.isEmptyLine()) {
			return 0;
		}
		if (features.isContainsMetaDataWord()) {
			return 0;
		}
		return (float) features.getNumericalCellRatio();
	}
}
