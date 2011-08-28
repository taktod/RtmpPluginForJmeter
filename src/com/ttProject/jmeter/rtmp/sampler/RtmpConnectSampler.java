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

/**
 * Rtmpのサーバーへの接続をサンプリングする。
 * @author taktod
 */
public class RtmpConnectSampler extends RtmpTimeoutAbstractSampler implements TestBean {
	/** シリアル番号 */
	private static final long serialVersionUID = -3395716901195949497L;
	/** スレッドごとの動作をするかフラグ */
	private boolean perThread;
	/** 接続後のサーバーからの応答コード */
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
		boolean success = false;
		// 動作前確認
		if(!check(result)) {
			return result;
		}
		// 実験スタート
		result.sampleStart();
		success = doConnect();
		setupResult(result, connectCode, success);
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
	private boolean doConnect() {
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
			connectCode = "ConnectionTimeout";
		}
		catch (Exception e) {
			System.out.println(connectCode);
		}
		// 接続成功時はスレッドとrtmpClientを関係つけておき、次のサンプラーで利用できるようにしておく。
		if("NetConnection.Connect.Success".equals(connectCode)) {
			getRtmpData().setRtmpClient(rtmpClient);
			return true;
		}
		else {
			rtmpClient.disconnect();
			return false;
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
	@Init({"init", "rtmp", "true", "5000"})
	private RtmpConnectSampler(RtmpConnectConfig config, String variableName, boolean perThread, long timeout) {
		super(config, timeout);
		// configが自動生成されている。
		setVariableName(variableName);
		setPerThread(perThread);
	}
}
