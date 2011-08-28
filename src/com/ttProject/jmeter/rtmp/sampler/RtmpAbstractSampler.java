package com.ttProject.jmeter.rtmp.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.SampleResult;

public abstract class RtmpAbstractSampler extends AbstractSampler {
	/** これ、あるのはおかしいかも */
	private static final long serialVersionUID = 5240863586661986006L;

	/**
	 * 結果の作成補助関数
	 * @param result resultオブジェクト
	 * @param reason 文字列の結果データ
	 * @param success 成功したかどうかフラグ
	 */
	protected void setupResult(SampleResult result, String reason, boolean success) {
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
