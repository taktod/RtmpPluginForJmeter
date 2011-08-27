package com.ttProject.jmeter.rtmp.sampler;

import java.util.List;
import java.util.Map;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IServiceCall;

import com.ttProject.jmeter.rtmp.RtmpData;
import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;
import com.ttProject.jmeter.rtmp.library.IRtmpClientEx;
import com.ttProject.jmeter.rtmp.library.RtmpClientEx;

@SuppressWarnings("unused")
public class RtmpConnectionSampler extends AbstractSampler implements TestBean {
	/** serialID */
	private static final long serialVersionUID = -3395716901195949497L;
	
	private boolean perThread;
	private String variableName = null;
	
//	private RtmpConnectConfig rtmpConnectConfig = null;
//	private String connectCode;
	@Override
	public SampleResult sample(Entry arg0) {
		SampleResult result = new SampleResult();
//		if(!preCheck(result)) {
//			return result;
//		}
//		doConnect(result);
		result.setSampleLabel("test");
		result.setSuccessful(true);
		return result;
	}
	
	// - 実処理 -------------- //
/*	private void setupResult(SampleResult result, String reason, boolean success) {
		result.sampleEnd();
		StringBuilder str = new StringBuilder();
		str.append(getName());
		str.append("[");
		str.append(Thread.currentThread().getName());
		str.append("]");
		result.setSampleLabel(str.toString());
		result.setSuccessful(success);
		result.setResponseData(reason.getBytes());
		result.setDataType(SampleResult.TEXT);
	}
	private boolean preCheck(SampleResult result) {
		result.sampleStart();
		JMeterVariables variables = JMeterContextService.getContext().getVariables();
		Object obj = variables.getObject(variableName);
		if(!(obj instanceof RtmpConnectConfig)) {
			setupResult(result, "variableName is invalid", false);
			return false;
		}
		rtmpConnectConfig = (RtmpConnectConfig)obj;
		if(!rtmpConnectConfig.isValid()) {
			setupResult(result, rtmpConnectConfig.getName() + "'s rtmpurl is invalid...", false);
			return false;
		}
		RtmpData rtmpData = rtmpConnectConfig.getRtmpData(perThread);
		if(rtmpData.getRtmpClient() != null) {
			// すでに接続が存在する。
			setupResult(result, "rtmpConnection is already established", true);
			return false;
		}
		return true;
	}
	private void doConnect(SampleResult result) {
		result.sampleStart();
/*		RtmpClientEx rtmpClient = new RtmpClientEx(
				rtmpConnectConfig.getServer(),
				rtmpConnectConfig.getPort(),
				rtmpConnectConfig.getApplication(),
				new ConnectEvent(Thread.currentThread()));* /
		setupResult(result, "ok", true);
	}
	private class ConnectEvent implements IRtmpClientEx {
		private Thread t;
		public ConnectEvent(Thread currentThread) {
			t = currentThread;
		}
		@Override
		public void onConnect(ObjectMap<String, Object> obj) {
			connectCode = (String)obj.get("code");
			t.interrupt();
		}
		@Override
		public void onDisconnect() {
		}
		@Override
		public void onCreateStream(Integer streamId) {
		}
		@Override
		public Object onInvoke(IServiceCall call) {
			return null;
		}
	}
	// - 変数 ------------- //
	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}
	/**
	 * @param variableName the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	/**
	 * @return the perThread
	 */
	public boolean isPerThread() {
		return perThread;
	}

	/**
	 * @param perThread the perThread to set
	 */
	public void setPerThread(boolean perThread) {
		this.perThread = perThread;
	}

}
