package com.dong;

import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class MyThread implements Callable<String> {

	@Override
	public String call() throws Exception {
		return "success...";
	}

	public static void main(String[] args) throws Exception {

		URL url = new URL("https://api-ros.meizu.com");
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.connect();
		for (Certificate certificate : connection.getServerCertificates()) {
			// 第一个就是服务器本身证书，后续的是证书链上的其他证书
			X509Certificate x509Certificate = (X509Certificate) certificate;
			System.out.println(x509Certificate.getSubjectDN());
			System.out.println(x509Certificate.getNotBefore());// 有效期开始时间
			System.out.println(x509Certificate.getNotAfter());// 有效期结束时间
		}
		connection.disconnect();

	}

}
