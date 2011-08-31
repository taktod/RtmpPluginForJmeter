package com.ttProject.jmeter.rtmp.sampler;

import java.beans.PropertyDescriptor;

import org.apache.jmeter.testbeans.BeanInfoSupport;

/**
 * 接続サンプリングの詳細設定
 * @author taktod
 */
public class RtmpConnectSamplerBeanInfo extends BeanInfoSupport {
	/**
	 * コンストラクタ
	 */
	public RtmpConnectSamplerBeanInfo() {
		super(RtmpConnectSampler.class);
		
		createPropertyGroup("testSetting", new String[]{"perThread"});
		PropertyDescriptor p;
		p = property("perThread");
		p.setValue(NOT_UNDEFINED, Boolean.TRUE);
		p.setValue(DEFAULT, Boolean.TRUE);
	}
}
