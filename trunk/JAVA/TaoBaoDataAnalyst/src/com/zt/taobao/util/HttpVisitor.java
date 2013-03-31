package com.zt.taobao.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import com.zt.lib.io.StreamHelper;

public class HttpVisitor {

	private static final int TIME_OUT = 10 * 1000;
	
	public HttpVisitor()
	{
	}
	
	/**
	 * 获取指定网络地址的HTML页面内容
	 * @param url 网址
	 * @return HTML页面内容
	 */
	public String getHtml(String url)
	{
		StringBuffer sb = new StringBuffer();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, TIME_OUT);
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				InputStream inputStream = response.getEntity().getContent();
				sb.append(StreamHelper.toString(inputStream, Charset.forName("GBK")));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return sb.toString();
	}
	
}
