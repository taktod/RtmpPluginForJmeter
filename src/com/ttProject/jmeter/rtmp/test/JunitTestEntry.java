package com.ttProject.jmeter.rtmp.test;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;
import com.ttProject.junit.TestEntry;
import com.ttProject.junit.library.MethodChecker;

public class JunitTestEntry extends TestEntry {
	@Override
	public void setUp() throws Exception {
		setPackagePath("com.ttProject.jmeter.rtmp");

		// (先にconnectionConfigをセットしておく。)
		MethodChecker mc = new MethodChecker();
		JMeterVariables variables = new JMeterVariables();
		JMeterContextService.getContext().setVariables(variables);
		variables.putObject("rtmp", mc.getClassInstance(RtmpConnectConfig.class));
		
		setData("sampleResult", new SampleResult());

		super.setUp();
	}
	@Override
	public boolean dump(Object obj) {
		if(obj instanceof SampleResult) {
			SampleResult result = (SampleResult) obj;
			System.out.println("[SampleResult]");
			System.out.println("success? : " + result.isSuccessful());
			System.out.println(result.getResponseDataAsString());
			return false;
		}
		return true;
	}
	@Override
	public boolean customCheck(String assume, Object ret) {
		if(ret instanceof SampleResult) {
			SampleResult result = (SampleResult)ret;
			if(result.isSuccessful()) {
				System.out.println(result.getResponseDataAsString());
				return true;
			}
			else {
				System.out.println(result.getResponseDataAsString());
				return false;
			}
		}
		return super.customCheck(assume, ret);
	}
}
