package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;

public class RtmpOnInvokeSampler extends RtmpTimeoutAbstractSampler implements TestBean {
	private static final long serialVersionUID = -2210763145979103988L;

	@Override
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
		if(!check(result)) {
			return result;
		}
		result.sampleStart();
		return result;
	}
}
