package com.ttProject.jmeter.rtmp.library;

import org.apache.mina.core.session.IoSession;
import org.red5.server.net.rtmp.RTMPConnection;
import org.red5.server.net.rtmp.RTMPMinaIoHandler;
import org.red5.server.net.rtmp.codec.RTMP;

/**
 * IoHandler to use custom outboundHandshake
 * @author taktod
 *
 */
public class RTMPMinaIoHandlerEx extends RTMPMinaIoHandler {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		if(mode == RTMP.MODE_CLIENT) {
			// クライアント動作しかこないけど、クライアント動作する場合に外部宛のHandshake用オブジェクトをオリジナルのものに変更(Scheme1で認証します。)
			OutboundHandshakeEx o = new OutboundHandshakeEx();
			session.setAttribute(RTMPConnection.RTMP_HANDSHAKE, o);
		}
	}
}
