package com.ttProject.jmeter.rtmp.sampler;

import java.beans.PropertyDescriptor;

import org.apache.jmeter.testbeans.BeanInfoSupport;

@SuppressWarnings("unused")
public class RtmpConnectionSamplerBeanInfo extends BeanInfoSupport {
	public RtmpConnectionSamplerBeanInfo() {
		super(RtmpConnectionSampler.class);
		
//		createPropertyGroup("test", new String[]{"variableName", "perThread"});
//		PropertyDescriptor p;
//		p = property("perThread");
//		p.setValue(NOT_UNDEFINED, Boolean.TRUE);
	}
}
