package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;

public class RtmpDisconnectSampler extends RtmpAbstractSampler implements TestBean {

	/** シリアルID */
	private static final long serialVersionUID = 4432585069468458531L;

	@Override
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
//		if(!preCheck(result)) {
//			return result;
//		}
		return result;
	}
}
