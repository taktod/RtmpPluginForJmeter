package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;

public class RtmpDisconnectSampler extends AbstractSampler implements TestBean {

	/** シリアルID */
	private static final long serialVersionUID = 4432585069468458531L;

	@Override
	public SampleResult sample(Entry entry) {
		SampleResult result = new SampleResult();
//		if(!preCheck(result)) {
//			return result;
//		}
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
}
