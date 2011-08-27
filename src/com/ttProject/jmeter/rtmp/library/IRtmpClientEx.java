package com.ttProject.jmeter.rtmp.library;

import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IServiceCall;

public interface IRtmpClientEx {
	public void onConnect(ObjectMap<String, Object> obj);
	public void onDisconnect();
	public void onCreateStream(Integer streamId);
	public Object onInvoke(IServiceCall call);
}
