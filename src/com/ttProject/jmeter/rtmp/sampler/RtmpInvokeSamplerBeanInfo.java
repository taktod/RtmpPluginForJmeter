package com.ttProject.jmeter.rtmp.sampler;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.gui.TableEditor;

public class RtmpInvokeSamplerBeanInfo extends BeanInfoSupport {
	public RtmpInvokeSamplerBeanInfo() {
		super(RtmpInvokeSampler.class);
		
		createPropertyGroup("hogehoge", new String[]{"test"});
		
		PropertyDescriptor p;
		p = property("test");
		p.setPropertyEditorClass(TableEditor.class);
		p.setValue(TableEditor.CLASSNAME, "java.lang.String");
		p.setValue(TableEditor.HEADERS, new String[]{"a", "b"});
		p.setValue(TableEditor.OBJECT_PROPERTIES, new String[]{TestData.data1, TestData.data2});
		p.setValue(NOT_UNDEFINED, Boolean.TRUE);
		p.setValue(DEFAULT, new ArrayList<Object>());
		p.setValue(MULTILINE, Boolean.TRUE);
	}
}
