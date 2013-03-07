package com.zt.taobao;
/*
 * @(#)TopApiUtil.java    2012-3-12
 *
 * Copyright (c) 2012. All Rights Reserved.
 *
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.zt.lib.Reflector;

/**
 * TopApiUtil Top API 辅助工具
 * 
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TopApiUtil.java 2012-3-12 14:51:35 Exp $
 */
public abstract class TopApiUtil {
    
    private static final String CONTAINER_URL = "http://container.api.taobao.com/container?appkey=${appkey}";
    private static final String SESSION_PARAM_KEY = "top_session";
    private static final String LOGIN_URL = "http://login.taobao.com/member/login.jhtml";

    public static String getSessionKey(String appkey, String nick, String psw) {
    	HttpEntity entity = null;
        HttpClient httpClient = new DefaultHttpClient();
        String redirectURL = "";
        HttpPost postMethod = new HttpPost(LOGIN_URL);
        
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        nameValues.add(new BasicNameValuePair("TPL_username", "zxfrdas"));
        nameValues.add(new BasicNameValuePair("TPL_password", "zxfrdas989208"));
        nameValues.add(new BasicNameValuePair("action", "Authenticator"));
        nameValues.add(new BasicNameValuePair("TPL_redirect_url", ""));
        nameValues.add(new BasicNameValuePair("from", "tb"));
        nameValues.add(new BasicNameValuePair("event_submit_do_login", "anything"));
        nameValues.add(new BasicNameValuePair("loginType", "3"));
        try {
			postMethod.setEntity(new UrlEncodedFormEntity(nameValues, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
		redirectURL = httpResponse.getFirstHeader("Location").getValue();
		postMethod.releaseConnection();
		System.out.println("redirectURL: 1 " + redirectURL);
		
        String newUrl = CONTAINER_URL.replace("${appkey}", appkey);
        URI uri = null;
		try {
			uri = new URI(newUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		postMethod = new HttpPost(uri);
        try {
			httpResponse = httpClient.execute(postMethod);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		redirectURL = httpResponse.getAllHeaders()[8].getValue();
		postMethod.releaseConnection();
		System.out.println("redirectURL: 2 " + redirectURL);
		
		HttpGet httpGet = new HttpGet(redirectURL);
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpParams rsp = httpResponse.getParams();
		System.out.println(Reflector.toString(rsp));
        
        return extractSessionKey(httpGet.getMethod(), SESSION_PARAM_KEY);
    }
    
    private static String extractSessionKey(String rsp, String key) {
        if (rsp == null)
            return null;
        Map<String, String> nameValuePair = new HashMap<String, String>();
        String[] array = rsp.split("&");
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (s.indexOf("=") > 0) {
                String[] nameValues = s.split("=");
                if (nameValues.length == 2) {
                    nameValuePair.put(s.split("=")[0], s.split("=")[1]);
                }
            }
        }
        return nameValuePair.get(key);
    }

    public static void main(String[] args) {
        String sessionKey = TopApiUtil.getSessionKey("12887618", "11测试账号21", "taobao1234");
        System.out.println(sessionKey);
    }
}