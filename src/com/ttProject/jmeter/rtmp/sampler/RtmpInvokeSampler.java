package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCall;

import com.ttProject.jmeter.rtmp.library.IRtmpClientEx;
import com.ttProject.jmeter.rtmp.library.RtmpClientEx;
import com.ttProject.junit.annotation.Init;
import com.ttProject.junit.annotation.Junit;
import com.ttProject.junit.annotation.Test;
import com.ttProject.junit.library.MethodChecker;

/**
 * Rtmpサーバー宛にメッセージを実行する。
 * @author taktod
 *
 */
public class RtmpInvokeSampler extends RtmpTimeoutAbstractSampler implements TestBean {
	/** シリアル番号 */
	private static final long serialVersionUID = -7937880449517409758L;
	private String invokeResult = null;

	public RtmpInvokeSampler() {
	}
	@Override
	@Junit({
		@Test(init={"rt", "4000", "true"}, value={"null"}, assume="@custom[]")
	})
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
		result.sampleStart();
		boolean success = false;
		if(!check(result)) {
			return result;
		}
		success = doInvoke();
		setupResult(result, invokeResult, success);
		return result;
	}
	private boolean doInvoke() {
		InvokeEvent event = new InvokeEvent(Thread.currentThread());
		RtmpClientEx rtmpClient = getRtmpData().getRtmpClient();
		rtmpClient.setListener(event);
		rtmpClient.invoke("testCall", new Object[]{"test", "hogehoge"}, event);
		try {
			Thread.sleep(getTimeOutVal());
			invokeResult = "invoke Timeout";
			return false;
		}
		catch (InterruptedException e) {
			;
		}
		catch (Exception e) {
			invokeResult = e.getMessage();
			return false;
		}
		return true;
	}
	private class InvokeEvent implements IRtmpClientEx, IPendingServiceCallback {
		private Thread t;
		public InvokeEvent(Thread currentThread) {
			t = currentThread;
		}
		@Override
		public void onConnect(ObjectMap<String, Object> obj) {
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
		@Override
		public void resultReceived(IPendingServiceCall call) {
			invokeResult = call.getResult().toString();
			t.interrupt();
		}
	}

	@SuppressWarnings("unused")
	@Init({"rtmp", "4000", "testCall"})
	private RtmpInvokeSampler(String variableName, String timeOut, String methodName) throws Exception {
		RtmpConnectSampler connect = (RtmpConnectSampler)new MethodChecker().getClassInstance(RtmpConnectSampler.class);
		setVariableName(variableName);
		setTimeOut(timeOut);
		connect.sample(null);
	}
}
