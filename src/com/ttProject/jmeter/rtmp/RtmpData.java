package com.ttProject.jmeter.rtmp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ttProject.jmeter.rtmp.library.RtmpClientEx;

public class RtmpData {
	private RtmpClientEx rtmpClient;
	private Map<String, Object> values = new ConcurrentHashMap<String, Object>();

	/**
	 * @return the rtmpClient
	 */
	public RtmpClientEx getRtmpClient() {
		return rtmpClient;
	}

	/**
	 * @param rtmpClient the rtmpClient to set
	 */
	public void setRtmpClient(RtmpClientEx rtmpClient) {
		this.rtmpClient = rtmpClient;
	}
}
