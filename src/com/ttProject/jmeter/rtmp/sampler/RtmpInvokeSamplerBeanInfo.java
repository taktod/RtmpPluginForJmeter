package com.ttProject.jmeter.rtmp.sampler;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.gui.TableEditor;

import com.ttProject.jmeter.rtmp.data.InvokeParameterData;

public class RtmpInvokeSamplerBeanInfo extends BeanInfoSupport {
	public RtmpInvokeSamplerBeanInfo() {
		super(RtmpInvokeSampler.class);
		
		createPropertyGroup("Setting", new String[]{
				"timeOut",
				"invokeFunc",
				"parameters"});
		
		PropertyDescriptor p;
		p = property("invokeFunc");
		p.setValue(NOT_UNDEFINED, Boolean.TRUE);
		p.setValue(DEFAULT, "");

		p = property("parameters");
		p.setPropertyEditorClass(TableEditor.class);
		p.setValue(TableEditor.CLASSNAME, InvokeParameterData.class.getName());
		p.setValue(TableEditor.HEADERS, new String[]{"parameter", "note"});
		p.setValue(TableEditor.OBJECT_PROPERTIES, new String[]{InvokeParameterData.param, InvokeParameterData.note});
		p.setValue(NOT_UNDEFINED, Boolean.TRUE);
		p.setValue(DEFAULT, new ArrayList<InvokeParameterData>());
	}
}
