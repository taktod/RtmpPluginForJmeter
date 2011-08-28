package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;

import com.ttProject.jmeter.rtmp.library.RtmpClientEx;

public class RtmpDisconnectSampler extends RtmpAbstractSampler implements TestBean {

	/** シリアルID */
	private static final long serialVersionUID = 4432585069468458531L;
	@Override
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
		if(!check(result)) {
			return result;
		}
		result.sampleStart();
		doDisconnect();
		setupResult(result, "disconnected", true);
		return result;
	}
	private void doDisconnect() {
		RtmpClientEx rtmpClient = getRtmpData().getRtmpClient();
		rtmpClient.disconnect();
		getRtmpData().setRtmpClient(null);
	}
}
