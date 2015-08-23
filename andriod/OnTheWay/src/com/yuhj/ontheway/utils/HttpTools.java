package com.yuhj.ontheway.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpTools {
	public static InputStream getInputStream(String path){
		try {
			//��ȡ��������
			Log.i("info", "url:"+path);
			URL url=new URL(path);
			//��URL����
			HttpURLConnection urlconn=(HttpURLConnection) url.openConnection();
			//�������ӳ�ʱ��Ӧʱ��
			urlconn.setConnectTimeout(5*1000);
			//���ö�ȡ��Ӧ��ʱ
			urlconn.setReadTimeout(5*1000);
			//�������ӷ�ʽ
			urlconn.setRequestMethod("GET");
			//����
			urlconn.connect();
			if(urlconn.getResponseCode()==HttpURLConnection.HTTP_OK){
				return urlconn.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	public static String GetStringByInputStream(InputStream stream){
		if(stream!=null){
			BufferedReader buffer=new BufferedReader(new InputStreamReader(stream));
			StringBuffer sb=new StringBuffer();
			String str=null;
			try {
				while((str=buffer.readLine())!=null){
					sb.append(str);
				}
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
