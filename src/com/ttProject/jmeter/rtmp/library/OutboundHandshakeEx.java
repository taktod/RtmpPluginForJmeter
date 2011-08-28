package com.ttProject.jmeter.rtmp.library;

import org.apache.mina.core.buffer.IoBuffer;
import org.red5.server.net.rtmp.OutboundHandshake;
import org.red5.server.net.rtmp.RTMPConnection;
import org.red5.server.net.rtmp.RTMPHandshake;
import org.red5.server.net.rtmp.message.Constants;

/**
 * Handshake support for red5 RtmpClient
 * @author taktod
 */
public class OutboundHandshakeEx extends OutboundHandshake {
	/** client constant value */
	private static final byte[] CLIENT_CONST = "Genuine Adobe Flash Player 001".getBytes();
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IoBuffer generateClientRequest1() {
		byte[] outgoingDigest;

		log.debug("generateClientRequest1");
		IoBuffer request = IoBuffer.allocate(Constants.HANDSHAKE_SIZE/* 1536 */ + 1);
		request.put(RTMPConnection.RTMP_NON_ENCRYPTED /* 3 */);

		log.info("Creating client handshake part 1 for encryption");
		IoBuffer buf = IoBuffer.allocate(Constants.HANDSHAKE_SIZE);
		buf.put(handshakeBytes);
		buf.flip();// handShake bytesをコピー
		// Scheme1のValidationは772バイト目から４バイトのデータが認証キーデータの設置場所となります。
		byte[] digestPointer = getFourBytesFrom(buf, 772);
		// 利用オフセットは772 + 4(設置場所指定) = 776バイト目から728桁のバイトデータを元にどのようにSHA256暗号化するか決定します。
		// 計算はもともとOutboundHandshakeがscheme0によるhandshakeをrtmpe用に実行していたので、そこでつかっていた関数を極力使い回すことにしました。
		int digestOffset = calculateOffset(digestPointer, 728, 776);
		buf.rewind();
		int messageLength = Constants.HANDSHAKE_SIZE - RTMPHandshake.DIGEST_LENGTH;
		byte[] message = new byte[messageLength];
		buf.get(message, 0, digestOffset);
		int afterDigestOffset = digestOffset + RTMPHandshake.DIGEST_LENGTH;
		buf.position(afterDigestOffset);
		buf.get(message, digestOffset, Constants.HANDSHAKE_SIZE - afterDigestOffset);
		// 埋め込み用暗号化キーが完成
		outgoingDigest = calculateHMAC_SHA256(message, CLIENT_CONST);
		// キー追加
		buf.position(digestOffset);
		buf.put(outgoingDigest);
		buf.rewind();
		// つくったデータを送信データに設置
		request.put(buf);
		request.flip();
		return request;
	}
	/**
	 * addByte
	 * @param bytes
	 * @return
	 */
	private int addBytes(byte[] bytes) {
		if (bytes.length != 4) {
			throw new RuntimeException("Unexpected byte array size: " + bytes.length);
		}
		int result = 0;
		for (int i = 0; i < bytes.length; i++) {
			result += bytes[i] & 0xff;
		}
		return result;
	}
	/**
	 * calculateOffset size
	 * @param pointer
	 * @param modulus
	 * @param increment
	 * @return
	 */
	private int calculateOffset(byte[] pointer, int modulus, int increment) {
		int offset = addBytes(pointer);
		offset %= modulus;
		offset += increment;
		return offset;
	}

}
