package com.ztz.asciidetector.structure.row;

public class RowFeatures {

	// row features
	private double  numericalCellRatio   = Float.NaN;
	private double  stringCellRatio      = Float.NaN;
	private boolean containsMetaDataWord = false;
	private boolean isEmptyLine          = false;
	private int     numberOfCells        = 0;

	public double getNumericalCellRatio() {
		return numericalCellRatio;
	}

	public void setNumericalCellRatio(double numericalCellRatio) {
		this.numericalCellRatio = numericalCellRatio;
	}

	public double getStringCellRatio() {
		return stringCellRatio;
	}

	public void setStringCellRatio(double stringCellRatio) {
		this.stringCellRatio = stringCellRatio;
	}

	public boolean isContainsMetaDataWord() {
		return containsMetaDataWord;
	}

	public void setContainsMetaDataWord(boolean containsMetaDataWord) {
		this.containsMetaDataWord = containsMetaDataWord;
	}

	public boolean isEmptyLine() {
		return isEmptyLine;
	}

	public void setEmptyLine(boolean emptyLine) {
		isEmptyLine = emptyLine;
	}

	public int getNumberOfCells() {
		return numberOfCells;
	}

	public void setNumberOfCells(int numberOfCells) {
		this.numberOfCells = numberOfCells;
	}
}
