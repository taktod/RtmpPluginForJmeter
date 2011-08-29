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
}
