package com.ztz;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.google.common.collect.Range;
import com.ztz.asciidetector.ASCIIStructureDescription;
import com.ztz.asciidetector.ASCIIStructureDetector;

/**
 * Unit tests for Detector
 */
public class DetectorTest {
	@Test
	public void simple() throws URISyntaxException {
		URL urlOfSimpleCSVFile = getClass().getClassLoader().getResource("com\\ztz\\asciidetector\\testdatasets\\simple\\simple.txt");
		File testFile = new File(urlOfSimpleCSVFile.toURI());
		ASCIIStructureDetector asciiStructureDetector = ASCIIStructureDetector.create();
		ASCIIStructureDescription asciiStructureDescription = asciiStructureDetector.detectStructure(testFile);
		assertEquals(';', asciiStructureDescription.getDelimiter());

		boolean hasMetaDataSection = asciiStructureDescription.isHasMetaDataSection();
		assertTrue(!hasMetaDataSection);

		Range<Integer> headerSection = asciiStructureDescription.getHeaderSection();
		assertTrue(headerSection.lowerEndpoint() == 0);
		assertTrue(headerSection.upperEndpoint() == 0);

		Range<Integer> dataSection = asciiStructureDescription.getDataSection();
		assertTrue(dataSection.lowerEndpoint() == 1);
	}
}
