package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.testelement.AbstractTestElement;

public class TestData extends AbstractTestElement {
	public static final String data1 = "data1";
	public static final String data2 = "data2";
	
	public TestData() {
		super();
	}
	public String getData1() {
		return getProperty(data1).getStringValue();
	}
	public void setData1(String str) {
		setProperty(data1, str);
	}
	public String getData2() {
		return getProperty(data2).getStringValue();
	}
	public void setData2(String str) {
		setProperty(data2, str);
	}
}
