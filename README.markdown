# RtmpPluginForJmeter

## 概要
JMeterによるRtmpサーバーへの接続動作を確認するためのプラグイン

## 使い方(開発編)

* ライブラリを準備します。必要なものは以下
   * red5由来(同じものをjmeterのlib/extに設置が必須)
	   <pre>red5.jar 1.0.0 (RC2dev推奨)
	   bcprov-jdk16-145.jar
	   com.springsource.slf4j.api-1.6.1.jar
	   commons-beanutil-1..2.jar
	   ehcache-2.2.0.jar
	   log4j-over-slf4j-1.6.1.jar
	   logback-classic-0.9.26.jar
	   logback-core-0.9.26.jar
	   mina-core-2.0.3.jar
	   org.springframework.beans-3.0.5.RELEASE.jar
	   org.springframework.context-3.0.5.RELEASE.jar
	   org.springframework.core-3.0.5.RELEASE.jar
	   </pre>
   * jmeter由来
	   <pre>ApacheJMeter_core.jar (lib/extより)
	   logkit-2.0.jar (junit動作に必要)
	   jorphan.jar (junit動作に必要)
	   oro-2.0.8.jar (junit動作に必要)
	   avalon-framework-4.1.4.jar (junit動作に必要)
	   </pre>
   * junitTest由来
	   <pre>jnit4 (eclipseのシステムライブラリ)
	   junitTest.jar (僕の別プロジェクト)
	   </pre>

## ライセンス
一応LGPLということにしておきます。なにか問題がでた場合は適宜変更する予定

## 履歴
* 2011/08/27プログラムをリポジトリに登録してみた。
* 2011/08/21プログラム作成開始

## やることメモ
* とりあえず、Rtmpで接続、切断、関数の呼び出し、関数の受け取り等を実行できるようにしておく。(関数の呼び出し条件の追加が必要。あとはできあがり。)
* 試行スレッドグループを複数にして実行すると、どうやらRtmpDataCacheまわりが暴走するっぽい。まったく関係ないサーバーに接続しにいっているのに、動作がおかしくなる。
* 試行頻度があがりすぎると、動作がおかしくなる。２０スレッド以上たてると不具合が発生する？(Red5のバグと思われ)
* Red5サーバーが相手だとこの状態でサーバー側の動作もおかしくなる。(Red5のバグと思われ)
* 保持データが蒸発する不具合があるっぽいので(RtmpClientが次の動作に反映されない。)対処しておく。
	どうやら動作途中に成功も失敗もしないエラーが発生しておわるっぽい。
	19:47:47.255 [?X???b?h?O???[?v 1-18] WARN  o.a.m.util.DefaultExceptionMonitor - Unexpected exception.
java.lang.NullPointerException: null
	at org.apache.mina.transport.socket.nio.NioSocketConnector.close(NioSocketConnector.java:218) ~[mina-core-2.0.3.jar:na]
	at org.apache.mina.transport.socket.nio.NioSocketConnector.close(NioSocketConnector.java:48) ~[mina-core-2.0.3.jar:na]
	at org.apache.mina.core.polling.AbstractPollingIoConnector.connect0(AbstractPollingIoConnector.java:335) ~[mina-core-2.0.3.jar:na]
	at org.apache.mina.core.service.AbstractIoConnector.connect(AbstractIoConnector.java:262) [mina-core-2.0.3.jar:na]
	at org.apache.mina.core.service.AbstractIoConnector.connect(AbstractIoConnector.java:172) [mina-core-2.0.3.jar:na]
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.startConnector(RtmpClientEx.java:221) [rtmpSampler.jar:na]
	at org.red5.server.net.rtmp.BaseRTMPClientHandler.connect(BaseRTMPClientHandler.java:242) [red5.jar:na]
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.connect(RtmpClientEx.java:195) [rtmpSampler.jar:na]
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.connect(RtmpClientEx.java:165) [rtmpSampler.jar:na]
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.doConnect(RtmpConnectSampler.java:95) [rtmpSampler.jar:na]
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.sample(RtmpConnectSampler.java:50) [rtmpSampler.jar:na]
	at org.apache.jmeter.threads.JMeterThread.process_sampler(JMeterThread.java:381) [ApacheJMeter_core.jar:2.5 r1158837]
	at org.apache.jmeter.threads.JMeterThread.run(JMeterThread.java:274) [ApacheJMeter_core.jar:2.5 r1158837]
	at java.lang.Thread.run(Thread.java:680) [na:1.6.0_26]
org.apache.mina.core.RuntimeIoException: Failed to get the session.
	at org.apache.mina.core.future.DefaultConnectFuture.getSession(DefaultConnectFuture.java:60)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx$1.operationComplete(RtmpClientEx.java:225)
	at org.apache.mina.core.future.DefaultIoFuture.notifyListener(DefaultIoFuture.java:377)
	at org.apache.mina.core.future.DefaultIoFuture.addListener(DefaultIoFuture.java:327)
	at org.apache.mina.core.future.DefaultConnectFuture.addListener(DefaultConnectFuture.java:116)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.startConnector(RtmpClientEx.java:222)
	at org.red5.server.net.rtmp.BaseRTMPClientHandler.connect(BaseRTMPClientHandler.java:242)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.connect(RtmpClientEx.java:195)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.connect(RtmpClientEx.java:165)
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.doConnect(RtmpConnectSampler.java:95)
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.sample(RtmpConnectSampler.java:50)
	at org.apache.jmeter.threads.JMeterThread.process_sampler(JMeterThread.java:381)
	at org.apache.jmeter.threads.JMeterThread.run(JMeterThread.java:274)
	at java.lang.Thread.run(Thread.java:680)
Caused by: java.nio.channels.ClosedByInterruptException
	at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:184)
	at sun.nio.ch.SocketChannelImpl.connect(SocketChannelImpl.java:511)
	at org.apache.mina.transport.socket.nio.NioSocketConnector.connect(NioSocketConnector.java:190)
	at org.apache.mina.transport.socket.nio.NioSocketConnector.connect(NioSocketConnector.java:48)
	at org.apache.mina.core.polling.AbstractPollingIoConnector.connect0(AbstractPollingIoConnector.java:319)
	at org.apache.mina.core.service.AbstractIoConnector.connect(AbstractIoConnector.java:262)
	at org.apache.mina.core.service.AbstractIoConnector.connect(AbstractIoConnector.java:172)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.startConnector(RtmpClientEx.java:221)
	... 8 more
org.apache.mina.core.RuntimeIoException: Failed to get the session.
	at org.apache.mina.core.future.DefaultConnectFuture.getSession(DefaultConnectFuture.java:60)
	at org.red5.server.net.rtmp.RTMPClient.disconnect(RTMPClient.java:103)
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.doConnect(RtmpConnectSampler.java:114)
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.sample(RtmpConnectSampler.java:50)
	at org.apache.jmeter.threads.JMeterThread.process_sampler(JMeterThread.java:381)
	at org.apache.jmeter.threads.JMeterThread.run(JMeterThread.java:274)
	at java.lang.Thread.run(Thread.java:680)
Caused by: java.nio.channels.ClosedByInterruptException
	at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:184)
	at sun.nio.ch.SocketChannelImpl.connect(SocketChannelImpl.java:511)
	at org.apache.mina.transport.socket.nio.NioSocketConnector.connect(NioSocketConnector.java:190)
	at org.apache.mina.transport.socket.nio.NioSocketConnector.connect(NioSocketConnector.java:48)
	at org.apache.mina.core.polling.AbstractPollingIoConnector.connect0(AbstractPollingIoConnector.java:319)
	at org.apache.mina.core.service.AbstractIoConnector.connect(AbstractIoConnector.java:262)
	at org.apache.mina.core.service.AbstractIoConnector.connect(AbstractIoConnector.java:172)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.startConnector(RtmpClientEx.java:221)
	at org.red5.server.net.rtmp.BaseRTMPClientHandler.connect(BaseRTMPClientHandler.java:242)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.connect(RtmpClientEx.java:195)
	at com.ttProject.jmeter.rtmp.library.RtmpClientEx.connect(RtmpClientEx.java:165)
	at com.ttProject.jmeter.rtmp.sampler.RtmpConnectSampler.doConnect(RtmpConnectSampler.java:95)
	... 4 more
	
* swfUrl pageUrl等きちんと動作させる。(済み)
* GUIをなんとかする。