package com.yuhj.ontheway.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	/**
	 * ��װ�˽���json�ķ���������list<JSONTOOLS>
	 */
	private List<JSONTools>list;
	private JSONTools tools;
	//����list�ڵ�����
	public List<JSONTools> PutData(String str){
		list=new ArrayList<JSONTools>();
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			JSONArray arr=obj.getJSONArray("data");
			for(int i=0;i<arr.length();i++){
				tools=new JSONTools();
				JSONObject json=arr.getJSONObject(i);
      				
				tools.setStart_weekday(json.getJSONObject("course").getString("startWeekDay"));
				tools.setStart_minute(json.getJSONObject("course").getString("startMinute"));
				tools.setStart_hour(json.getJSONObject("course").getString("startHour"));
				tools.setStart_time(json.getJSONObject("course").getString("startTime"));
				tools.setEnd_time(json.getJSONObject("course").getString("endTime"));
				tools.setName(json.getJSONObject("course").getString("name"));
				tools.setBranch_name(json.getJSONObject("course").getString("branchName"));
				tools.setDuration_minute(json.getJSONObject("course").getString("durationMinute"));
				tools.setCourse_no(json.getJSONObject("course").getString("no"));
				list.add(tools);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	} 
 }
