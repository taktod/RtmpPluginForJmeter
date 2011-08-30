package com.ttProject.jmeter.rtmp.sampler;

import java.beans.PropertyDescriptor;

import org.apache.jmeter.testbeans.BeanInfoSupport;

public class RtmpAbstractSamplerBeanInfo extends BeanInfoSupport {
	public RtmpAbstractSamplerBeanInfo() {
		super(RtmpAbstractSampler.class);
		
		createPropertyGroup("testSetting", new String[]{"variableName"});

		PropertyDescriptor p;
		p = property("variableName");
		p.setValue(NOT_UNDEFINED, Boolean.TRUE);
		p.setValue(DEFAULT, "rtmp");
	}
}
