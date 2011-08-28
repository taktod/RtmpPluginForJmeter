package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import com.ttProject.jmeter.rtmp.RtmpData;
import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;

public abstract class RtmpAbstractSampler extends AbstractSampler {
	private static final long serialVersionUID = 5240863586661986006L;
	private String variableName = null;
	
	private RtmpConnectConfig rtmpConnectConfig = null;
	private RtmpData rtmpData = null;

	public RtmpAbstractSampler() {
	}
	/**
	 * 結果の作成補助関数
	 * @param result resultオブジェクト
	 * @param reason 文字列の結果データ
	 * @param success 成功したかどうかフラグ
	 */
	protected void setupResult(SampleResult result, String reason, boolean success) {
		// サンプル動作完了
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
	/**
	 * 動作前状態確認
	 * @param result
	 * @return
	 */
	protected boolean check(SampleResult result) {
		if(!checkConfig(result)) {
			return false;
		}
		if(!checkRtmpData(result)) {
			return false;
		}
		return true;
	}
	protected boolean checkConfig(SampleResult result) {
		result.sampleStart();
		JMeterVariables variables = JMeterContextService.getContext().getVariables();
		Object obj = variables.getObject(getVariableName());
		if(!(obj instanceof RtmpConnectConfig)) {
			setupResult(result, "variableName is invalid" + getVariableName(), false);
			return false;
		}
		rtmpConnectConfig = (RtmpConnectConfig)obj;
		if(!rtmpConnectConfig.isValid()) {
			setupResult(result, rtmpConnectConfig.getName() + "'s rtmpurl is invalid...", false);
			return false;
		}
		return true;
	}
	protected boolean checkRtmpData(SampleResult result) {
		result.sampleStart();
		rtmpData = rtmpConnectConfig.getRtmpData();
		if(rtmpData.getRtmpClient() != null) {
			// すでに接続が存在する。
			setupResult(result, "rtmpConnection is not established yet...", false);
			return false;
		}
		return true;
	}
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
	 * @return the rtmpConnectConfig
	 */
	protected RtmpConnectConfig getRtmpConnectConfig() {
		return rtmpConnectConfig;
	}
	/**
	 * @return the rtmpData
	 */
	protected RtmpData getRtmpData() {
		return rtmpData;
	}





	protected RtmpAbstractSampler(RtmpConnectConfig config) {
		rtmpConnectConfig = config;
		rtmpData = rtmpConnectConfig.getRtmpData();
	}
}
