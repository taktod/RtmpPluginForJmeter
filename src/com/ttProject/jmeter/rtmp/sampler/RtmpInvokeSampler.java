package com.ttProject.jmeter.rtmp.sampler;

import java.util.ArrayList;
import java.util.List;

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
	private List<TestData> params;

	public RtmpInvokeSampler() {
	}
	@Override
	@Junit({
		@Test(init={"rt", "4000", "true"}, value={"null"}, assume="@custom[variableName is invalid]")
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
		List<String> params = new ArrayList<String>();
//		for(TestData data : params) {
//			invokeResult += data.getParam() + ":" + data.getNote();
//		}
		for(int i = 1; i < 6; i ++) {
			String param = getPropertyAsString("param" + i);
			if(param != null && !param.equals("")) {
				params.add(param);
			}
		}
		rtmpClient.invoke(getPropertyAsString("invokeFunc"), params.toArray(), event);
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
	/**
	 * @return the invokeFunc
	 */
	public String getInvokeFunc() {
		return getPropertyAsString("invokeFunc");
	}
	/**
	 * @param invokeFunc the invokeFunc to set
	 */
	public void setInvokeFunc(String invokeFunc) {
		setProperty("invokeFunc", invokeFunc);
	}
	/**
	 * @return the test
	 */
	public List<TestData> getParameters() {
		System.out.println("getParameters is called...");
		return params;
	}
	/**
	 * @param test the test to set
	 */
	public void setParameters(List<TestData> params) {
		System.out.println("setParameters is called....");
		this.params = params;
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
