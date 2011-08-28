package com.ttProject.jmeter.rtmp.config;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.config.ConfigElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import com.ttProject.jmeter.rtmp.RtmpData;
import com.ttProject.junit.annotation.Init;

public class RtmpConnectConfig extends AbstractTestElement 
	implements TestBean, ConfigElement, TestListener {
	/** シリアルバージョンID */
	private static final long serialVersionUID = -6893264509399917662L;

	// 設定項目
	private String variableName = null;
	private String rtmpUrl = null;
	private String pageUrl = null;
	private String swfUrl = null;

	private Map<Thread, RtmpData> rtmpData = null;
	private RtmpData rtmpDat = null;
	private String server = null;
	private Integer port = null;
	private String application = null;

	public RtmpConnectConfig() {
		rtmpData = new ConcurrentHashMap<Thread, RtmpData>();
	}
	@SuppressWarnings("unused")
	@Init({"rtmp", "rtmp://49.212.39.17/avatarChat/135", "http://localhost/test.html", "http://localhost/test.swf"})
	private RtmpConnectConfig(
			String variableName,
			String rtmpUrl,
			String pageUrl,
			String swfUrl) {
		setVariableName(variableName);
		setRtmpUrl(rtmpUrl);
		setPageUrl(pageUrl);
		setSwfUrl(swfUrl);
		rtmpData = new ConcurrentHashMap<Thread, RtmpData>();
	}
	public boolean isValid() {
		return (server != null && port != null && application != null);
	}
	public RtmpData getRtmpData() {
		return getRtmpData(null);
	}
	public RtmpData getRtmpData(Boolean perThread) {
		if(perThread == null) {
			// threadごとの指定がない場合はある方を応答する。
			if(rtmpDat != null) {
				return rtmpDat;
			}
			return this.rtmpData.get(Thread.currentThread());
		}
		if(!perThread) {
			if(rtmpDat == null) {
				rtmpDat = new RtmpData();
			}
			return rtmpDat;
		}
		RtmpData rtmpData = this.rtmpData.get(Thread.currentThread());
		if(rtmpData == null) {
			rtmpData = new RtmpData();
			this.rtmpData.put(Thread.currentThread(), rtmpData);
		}
		return rtmpData;
	}
	@Override
	public void addConfigElement(ConfigElement paramConfigElement) {
	}
	@Override
	public boolean expectsModification() {
		return false;
	}
	@Override
	public void testEnded() {
		System.out.println("test end...");
		// テストが終了したときによけいなコネクションがのこっている場合はすべて破棄する。
		for(Entry<Thread, RtmpData> entry : rtmpData.entrySet()) {
			try {
				entry.getValue().getRtmpClient().disconnect();
			}
			catch (Exception e) {
			}
		}
		try {
			rtmpDat.getRtmpClient().disconnect();
		}
		catch (Exception e) {
		}
	}
	@Override
	public void testEnded(String arg0) {
	}
	@Override
	public void testIterationStart(LoopIterationEvent arg0) {
	}
	@Override
	public void testStarted() {
		System.out.println("test start...");
		JMeterVariables variables = JMeterContextService.getContext().getVariables();
		if(variableName != null && !variableName.equals("")) {
			variables.putObject(variableName, this);
		}
	}
	@Override
	public void testStarted(String arg0) {
	}

	// - データ ----------------------------------------------- /
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
	 * @return the rtmpUrl
	 */
	public String getRtmpUrl() {
		return rtmpUrl;
	}
	/**
	 * @param rtmpUrl the rtmpUrl to set
	 */
	public void setRtmpUrl(String rtmpUrl) {
		this.rtmpUrl = rtmpUrl;
		try {
			// rtmp://server:port/room or else
			String[] paths = rtmpUrl.split("/", 4);
			String[] addresses = paths[2].split(":");
			server = addresses[0];
			if(addresses.length == 1) {
				port = 1935;
			}
			else {
				port = Integer.parseInt(addresses[1]);
			}
			application = paths[3];
		}
		catch (Exception e) {
			server = null;
			port = null;
			application = null;
		}
	}
	/**
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * @param pageUrl the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	/**
	 * @return the swfUrl
	 */
	public String getSwfUrl() {
		return swfUrl;
	}
	/**
	 * @param swfUrl the swfUrl to set
	 */
	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}
	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}
	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}
}
