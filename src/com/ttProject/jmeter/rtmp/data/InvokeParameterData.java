package com.ttProject.jmeter.rtmp.data;

import org.apache.jmeter.testelement.AbstractTestElement;

public class InvokeParameterData extends AbstractTestElement {
	private static final long serialVersionUID = -1L;
	public static final String param = "parameter";
	public static final String note = "note";
	public void setParam(String data) {
		setProperty(param, data);
	}
	public String getParam() {
		return getPropertyAsString(param);
	}
	public void setNote(String data) {
		setProperty(note, data);
	}
	public String getNote() {
		return getPropertyAsString(note);
	}
}
