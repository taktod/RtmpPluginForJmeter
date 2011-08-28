package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IServiceCall;
import org.red5.server.net.rtmp.ClientExceptionHandler;

import com.ttProject.jmeter.rtmp.RtmpData;
import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;
import com.ttProject.jmeter.rtmp.library.IRtmpClientEx;
import com.ttProject.jmeter.rtmp.library.RtmpClientEx;
import com.ttProject.junit.annotation.Init;
import com.ttProject.junit.annotation.Junit;
import com.ttProject.junit.annotation.Test;

public class RtmpConnectionSampler extends AbstractSampler implements TestBean {
	/** serialID */
	private static final long serialVersionUID = -3395716901195949497L;
	
	private boolean perThread;
	private String variableName = null;
	
	private RtmpConnectConfig rtmpConnectConfig = null;
	private RtmpData rtmpData = null;
	private String connectCode;
	/**
	 * 通常のコンストラクタ
	 */
	public RtmpConnectionSampler() {
	}
	/**
	 * {@inheritDoc}
	 * @param entry
	 * @return
	 */
	@Override
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
		// 動作前確認
		if(!preCheck(result)) {
			return result;
		}
		// 実験スタート
		result.sampleStart();
		doConnect();
		setupResult(result, connectCode, true);
		return result;
	}
	/**
	 * 結果の作成補助関数
	 * @param result resultオブジェクト
	 * @param reason 文字列の結果データ
	 * @param success 成功したかどうかフラグ
	 */
	private void setupResult(SampleResult result, String reason, boolean success) {
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
	 * 実行前動作確認(エラー時は動作確認にかかった時間をレポートする。)
	 * @param result
	 * @return true:問題なし。 false:問題あり、サンプリング強制終了
	 */
	private boolean preCheck(SampleResult result) {
		result.sampleStart();
		JMeterVariables variables = JMeterContextService.getContext().getVariables();
		Object obj = variables.getObject(variableName);
		if(!(obj instanceof RtmpConnectConfig)) {
			setupResult(result, "variableName is invalid", false);
			return false;
		}
		rtmpConnectConfig = (RtmpConnectConfig)obj;
		if(!rtmpConnectConfig.isValid()) {
			setupResult(result, rtmpConnectConfig.getName() + "'s rtmpurl is invalid...", false);
			return false;
		}
		rtmpData = rtmpConnectConfig.getRtmpData(perThread);
		if(rtmpData.getRtmpClient() != null) {
			// すでに接続が存在する。
			setupResult(result, "rtmpConnection is already established", true);
			return false;
		}
		return true;
	}
	/**
	 * 接続動作
	 * @param result
	 */
	@Junit({
		@Test({})
	})
	private void doConnect() {
		ConnectEvent event = new ConnectEvent(Thread.currentThread());
		RtmpClientEx rtmpClient = new RtmpClientEx(
				rtmpConnectConfig.getServer(),
				rtmpConnectConfig.getPort(),
				rtmpConnectConfig.getApplication(),
				event);
		rtmpClient.setExceptionHandler(event);
		rtmpClient.connect();
		try {
			Thread.sleep(10000);
		}
		catch (Exception e) {
			System.out.println(connectCode);
		}
		// 接続成功時はスレッドとrtmpClientを関係つけておき、次のサンプラーで利用できるようにしておく。
		if("NetConnection.Connect.Success".equals(connectCode)) {
			rtmpData.setRtmpClient(rtmpClient);
		}
	}
	/**
	 * コネクト時のコールバック関数
	 * @author taktod
	 */
	private class ConnectEvent implements IRtmpClientEx, ClientExceptionHandler {
		private Thread t;
		public ConnectEvent(Thread currentThread) {
			t = currentThread;
		}
		@Override
		public void onConnect(ObjectMap<String, Object> obj) {
			connectCode = (String)obj.get("code");
			t.interrupt();
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
		/**
		 * 何らかの例外が発生した場合、動作をとめる。(相手のサーバーがみつからないとき等。)
		 */
		@Override
		public void handleException(Throwable ex) {
			connectCode = ex.getMessage();
			t.interrupt();
		}
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
	 * @return the perThread
	 */
	public boolean isPerThread() {
		return perThread;
	}
	/**
	 * @param perThread the perThread to set
	 */
	public void setPerThread(boolean perThread) {
		this.perThread = perThread;
	}





	/**
	 * Junitテスト用のデフォルトデータ入りコンストラクタ
	 * @param config
	 * @param variableName
	 * @param perThread
	 */
	@SuppressWarnings("unused")
	@Init({"init", "rtmp", "true"})
	private RtmpConnectionSampler(RtmpConnectConfig config, String variableName, boolean perThread) {
		// configが自動生成されている。
		setVariableName(variableName);
		setPerThread(perThread);
		rtmpConnectConfig = config;
		rtmpData = rtmpConnectConfig.getRtmpData(perThread);
	}
}
