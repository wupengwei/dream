package com.wpw.dream.test.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SerializableObject {
	
	public static void sendObject(String requestUrl, int connTimeoutMills,
			  int readTimeoutMills, Object serializedObject) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setRequestProperty("Content-Type","application/x-java-serialized-object");
			//httpUrlConn.setConnectTimeout(connTimeoutMills);
			//httpUrlConn.setReadTimeout(readTimeoutMills);
			// 设置是否向httpUrlConn输出，因为是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false
			httpUrlConn.setDoOutput(true);
			// 设置是否从httpUrlConn读入，默认情况下是true
			httpUrlConn.setDoInput(true);
			// 不使用缓存   
			httpUrlConn.setUseCaches(false);

			// 设置请求方式，默认是GET
			httpUrlConn.setRequestMethod("POST");
			
			httpUrlConn.connect();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(httpUrlConn.getOutputStream());
			objectOutputStream.writeObject(serializedObject);
			
			objectOutputStream.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream()));

			System.out.println(br.readLine());
			br.close();
			
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setId(1L);
		user.setName("wpw");
		SerializableObject.sendObject("http://127.0.0.1:8090/dream-web/testSerializable.do", 5000, 5000, user);
		
	}

}
