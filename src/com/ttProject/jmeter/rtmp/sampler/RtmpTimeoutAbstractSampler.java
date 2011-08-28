package com.ttProject.jmeter.rtmp.sampler;

import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;

public abstract class RtmpTimeoutAbstractSampler extends RtmpAbstractSampler {
	private static final long serialVersionUID = 15383998085000665L;
	private String timeOut = null;
	private Long timeOutVal;
	private final long defaultTimeOutVal = 1000L;

	/**
	 * @return the timeout
	 */
	public String getTimeOut() {
		return timeOut;
	}
	public Long getTimeOutVal() {
		if(timeOutVal == null) {
			return defaultTimeOutVal;
		}
		return timeOutVal;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeOut(String timeOut) {
		try {
			this.timeOutVal = Long.parseLong(timeOut);
			this.timeOut = timeOut;
		}
		catch (Exception e) {
			this.timeOut = "";
			timeOutVal = null;
		}
	}
	public RtmpTimeoutAbstractSampler() {
	}
	protected RtmpTimeoutAbstractSampler(RtmpConnectConfig config) {
		super(config);
	}
}
