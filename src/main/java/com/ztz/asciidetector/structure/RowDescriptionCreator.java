package com.ztz.asciidetector.structure;

public class RowDescriptionCreator {

	private final DetectorSettings detectorSettings;

	public RowDescriptionCreator(DetectorSettings detectorSettings) {

		this.detectorSettings = detectorSettings;
	}

	public RowDescription create(int rowIndex, String row) {
		RowDescription rowDescription = new RowDescription(rowIndex);


		return rowDescription;
	}


}
