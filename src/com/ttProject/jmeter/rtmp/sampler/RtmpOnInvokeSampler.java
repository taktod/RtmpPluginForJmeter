package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IServiceCall;

import com.ttProject.jmeter.rtmp.library.IRtmpClientEx;
import com.ttProject.jmeter.rtmp.library.RtmpClientEx;
import com.ttProject.junit.library.MethodChecker;

public class RtmpOnInvokeSampler extends RtmpTimeoutAbstractSampler implements TestBean {
	private static final long serialVersionUID = -2210763145979103988L;
	private String methodName = null;
	private String onInvokeResult = null;
	/**
	 * コンストラクタ
	 */
	public RtmpOnInvokeSampler() {
	}
	@Override
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
		result.sampleStart();
		boolean success = false;
		if(!check(result)) {
			return result;
		}
		success = doOnInvoke();
		setupResult(result, onInvokeResult, success);
		return result;
	}
	private boolean doOnInvoke() {
		RtmpClientEx rtmpClient = getRtmpData().getRtmpClient();
		Object[] ret = rtmpClient.getOnInvokeData(methodName);
		if(ret == null) {
			// メッセージが設置されていない場合はtimeoutまでデータを待つ。
			rtmpClient.setListener(new OnInvokeEvent(Thread.currentThread()));
			try {
				Thread.sleep(getTimeOutVal());
				onInvokeResult = "onInvoke Timeout";
				return false;
			}
			catch (InterruptedException e) {
				;
			}
			catch (Exception e) {
				onInvokeResult = e.getMessage();
				return false;
			}
			ret = rtmpClient.getOnInvokeData(methodName);
		}
		onInvokeResult = "";
		for(Object obj : ret) {
			onInvokeResult += obj.toString() + "\r\n";
		}
		return true;
	}
	private class OnInvokeEvent implements IRtmpClientEx {
		private Thread t;
		public OnInvokeEvent(Thread currentThread) {
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
			if(call.getServiceMethodName().equals(methodName)) {
				t.interrupt();
			}
			return null; // 応答する文字列が思い浮かばないのでとりあえずnullを返しておく。
		}
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@SuppressWarnings("unused")
	private RtmpOnInvokeSampler(String variableName, String timeOut, String methodName) throws Exception {
		RtmpConnectSampler connect = (RtmpConnectSampler)new MethodChecker().getClassInstance(RtmpConnectSampler.class);
		setVariableName(variableName);
		setTimeOut(timeOut);
		setMethodName(methodName);
		connect.sample(null);
	}
}
