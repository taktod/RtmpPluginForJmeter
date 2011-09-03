package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.testelement.AbstractTestElement;

public class TestData extends AbstractTestElement {
	private static final long serialVersionUID = 5449733340752469732L;
	private static final String param = "parameter";
	private static final String note = "note";
	public void setParam(String data) {
		setProperty(param, data);
	}
	public String getParam() {
		return getProperty(param).getStringValue();
	}
	public void setNote(String data) {
		setProperty(note, data);
	}
	public String getNote() {
		return getProperty(note).getStringValue();
	}
}
