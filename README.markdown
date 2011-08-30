# RtmpPluginForJmeter

## 概要
JMeterによるRtmpサーバーへの接続動作を確認するためのプラグイン

## 使い方(開発編)
1. ライブラリを準備します。必要なものは以下
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
* とりあえず、Rtmpで接続、切断、関数の呼び出し、関数の受け取り等を実行できるようにしておく。
* 保持データが蒸発する不具合があるっぽいので(RtmpClientが次の動作に反映されない。)対処しておく。
* swfUrl pageUrl等きちんと動作させる。
* GUIをなんとかする。