package com.ztz.asciidetector.structure.cell;

public class CellDescription {

	private int rowPos;
	private int colPos;

	// Cell features
	private int     valueLength        = Integer.MIN_VALUE;
	private boolean hasDerivedKeywords = false;
	private boolean isEmptyRowBefore   = false;
	private boolean isEmptyRowAfter    = false;

	public int getRowPos() {
		return rowPos;
	}

	public void setRowPos(int rowPos) {
		this.rowPos = rowPos;
	}

	public int getColPos() {
		return colPos;
	}

	public void setColPos(int colPos) {
		this.colPos = colPos;
	}

	public int getValueLength() {
		return valueLength;
	}

	public void setValueLength(int valueLength) {
		this.valueLength = valueLength;
	}

	public boolean isHasDerivedKeywords() {
		return hasDerivedKeywords;
	}

	public void setHasDerivedKeywords(boolean hasDerivedKeywords) {
		this.hasDerivedKeywords = hasDerivedKeywords;
	}

	public boolean isEmptyRowBefore() {
		return isEmptyRowBefore;
	}

	public void setEmptyRowBefore(boolean emptyRowBefore) {
		isEmptyRowBefore = emptyRowBefore;
	}

	public boolean isEmptyRowAfter() {
		return isEmptyRowAfter;
	}

	public void setEmptyRowAfter(boolean emptyRowAfter) {
		isEmptyRowAfter = emptyRowAfter;
	}
}


