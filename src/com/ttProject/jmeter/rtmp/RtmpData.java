package com.ttProject.jmeter.rtmp;

import com.ttProject.jmeter.rtmp.library.RtmpClientEx;

public class RtmpData {
	private RtmpClientEx rtmpClient;

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
