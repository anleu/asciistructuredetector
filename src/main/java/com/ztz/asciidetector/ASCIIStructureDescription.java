package com.ztz.asciidetector;

import java.nio.charset.Charset;

import com.google.common.collect.Range;

public class ASCIIStructureDescription {

	private Charset        charset;
	private char           delimiter;
	private boolean        hasMetaDataSection;
	private Range<Integer> metaSection;
	private Range<Integer> headerSection;
	private Range<Integer> dataSection;

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	public void setHasMetaDataSection(boolean hasMetaDataSection) {
		this.hasMetaDataSection = hasMetaDataSection;
	}

	public void setMetaSection(Range<Integer> metaSection) {
		this.metaSection = metaSection;
	}

	public void setHeaderSection(Range<Integer> headerSection) {
		this.headerSection = headerSection;
	}

	public void setDataSection(Range<Integer> dataSection) {
		this.dataSection = dataSection;
	}

	public Charset getCharset() {
		return charset;
	}

	public char getDelimiter() {
		return delimiter;
	}

	public boolean isHasMetaDataSection() {
		return hasMetaDataSection;
	}

	public Range<Integer> getMetaSection() {
		return metaSection;
	}

	public Range<Integer> getHeaderSection() {
		return headerSection;
	}

	public Range<Integer> getDataSection() {
		return dataSection;
	}
}
