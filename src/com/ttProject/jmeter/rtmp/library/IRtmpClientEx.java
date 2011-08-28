package com.ttProject.jmeter.rtmp.library;

import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IServiceCall;

/**
 * Interface for RtmpClient for Red5
 * @author taktod
 */
public interface IRtmpClientEx {
	/**
	 * Connect Event
	 * @param obj objectMap
	 */
	public void onConnect(ObjectMap<String, Object> obj);
	/**
	 * Disconnect Event
	 */
	public void onDisconnect();
	/**
	 * CreateStream Event(for publish for play)
	 * @param streamId
	 */
	public void onCreateStream(Integer streamId);
	/**
	 * invoke function method.
	 * @param call
	 * @return
	 */
	public Object onInvoke(IServiceCall call);
}
