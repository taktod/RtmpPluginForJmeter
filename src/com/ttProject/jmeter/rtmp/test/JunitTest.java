package com.ttProject.jmeter.rtmp.test;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;
import com.ttProject.junit.TestEntry;
import com.ttProject.junit.library.MethodChecker;

public class JunitTest extends TestEntry {
	@Override
	public void setUp() throws Exception {
		JMeterVariables variables = new JMeterVariables();
		JMeterContext context = JMeterContextService.getContext();
		context.setVariables(variables);
		// RtmpConnectConfigはここで初期化しておく。
		variables.putObject("rtmp", new MethodChecker().getClassInstance(RtmpConnectConfig.class));
		
		setPackagePath("com.ttProject.jmeter.rtmp");
		super.setUp();
	}
	@Override
	public void doTest() throws Throwable {
		super.doTest();
	}
}
