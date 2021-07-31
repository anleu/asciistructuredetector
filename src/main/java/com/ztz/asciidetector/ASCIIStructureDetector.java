package com.ztz.asciidetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ztz.asciidetector.structure.DetectorSettings;
import com.ztz.asciidetector.structure.RowDescription;
import com.ztz.asciidetector.structure.RowDescriptionCreator;

public class ASCIIStructureDetector {

	private final DetectorSettings detectorSettings;

	// Create this via builder
	ASCIIStructureDetector(DetectorSettings detectorSettings) {
		this.detectorSettings = detectorSettings;
	}

	public ASCIIStructureDescription detectStructure(File file) {
		buildRowDescriptions(file);

		return null;
	}

	private void buildRowDescriptions(File file) {
		List<RowDescription> rowDescriptions = new ArrayList<>();
		RowDescriptionCreator rowDescriptionCreator = new RowDescriptionCreator(detectorSettings);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			int rowIndex = 0;
			String line;
			while ((line = br.readLine()) != null) {
				rowDescriptions.add(rowDescriptionCreator.create(rowIndex, line));
				rowIndex++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
