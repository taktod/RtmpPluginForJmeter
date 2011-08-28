package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.red5.io.utils.ObjectMap;
import org.red5.server.api.service.IServiceCall;
import org.red5.server.net.rtmp.ClientExceptionHandler;

import com.ttProject.jmeter.rtmp.config.RtmpConnectConfig;
import com.ttProject.jmeter.rtmp.library.IRtmpClientEx;
import com.ttProject.jmeter.rtmp.library.RtmpClientEx;
import com.ttProject.junit.annotation.Init;
import com.ttProject.junit.annotation.Junit;
import com.ttProject.junit.annotation.Test;

public class RtmpConnectSampler extends RtmpTimeoutAbstractSampler implements TestBean {
	/** serialID */
	private static final long serialVersionUID = -3395716901195949497L;
	
	private boolean perThread;
	private String connectCode;
	/**
	 * 通常のコンストラクタ
	 */
	public RtmpConnectSampler() {
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
		if(!check(result)) {
			return result;
		}
		// 実験スタート
		result.sampleStart();
		doConnect();
		setupResult(result, connectCode, true);
		return result;
	}
	/**
	 * 実行前動作確認(エラー時は動作確認にかかった時間をレポートする。)
	 * @param result
	 * @return true:問題なし。 false:問題あり、サンプリング強制終了
	 */
	@Override
	protected boolean check(SampleResult result) {
		if(!checkConfig(result)) {
			return false;
		}
		// rtmpClientが接続しているか確認する。
		if(getRtmpData().getRtmpClient() != null) {
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
				getRtmpConnectConfig().getServer(),
				getRtmpConnectConfig().getPort(),
				getRtmpConnectConfig().getApplication(),
				event);
		rtmpClient.setExceptionHandler(event);
		rtmpClient.connect();
		try {
			Thread.sleep(getTimeOutVal());
		}
		catch (Exception e) {
			System.out.println(connectCode);
		}
		// 接続成功時はスレッドとrtmpClientを関係つけておき、次のサンプラーで利用できるようにしておく。
		if("NetConnection.Connect.Success".equals(connectCode)) {
			getRtmpData().setRtmpClient(rtmpClient);
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
	private RtmpConnectSampler(RtmpConnectConfig config, String variableName, boolean perThread) {
		super(config);
		// configが自動生成されている。
		setVariableName(variableName);
		setPerThread(perThread);
	}
}
