package com.yuhj.ontheway.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yuhj.ontheway.bean.MyCourseJsonTools;

public class MyCourseJsonUtils {
	
	private List<MyCourseJsonTools> list;
	private MyCourseJsonTools tools;

	
	public List<MyCourseJsonTools> PutData(String str) {
		list = new ArrayList<MyCourseJsonTools>();
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			JSONArray arr = obj.getJSONArray("data");
			for (int i = 0; i < arr.length(); i++) {
				tools = new MyCourseJsonTools();

				JSONObject json = arr.getJSONObject(i);

				if (json.getString("course") != "null") {
					tools.setStart_weekday(json.getJSONObject("course")
							.getString("startWeekDay"));
					tools.setStart_minute(json.getJSONObject("course")
							.getString("startMinute"));
					tools.setStart_hour(json.getJSONObject("course").getString(
							"startHour"));
					tools.setStart_time(json.getJSONObject("course").getString(
							"startTime"));
					tools.setEnd_time(json.getJSONObject("course").getString(
							"endTime"));
					tools.setName(json.getJSONObject("course")
							.getString("name"));
					tools.setBranch_name(json.getJSONObject("course")
							.getString("branchName"));
					tools.setDuration_minute(json.getJSONObject("course")
							.getString("durationMinute"));
					tools.setCourse_no(json.getJSONObject("course").getString(
							"no"));
					tools.setCreate_time(json.getJSONObject("course").getString("createTime"));
					
				    JSONArray order = json.getJSONArray("orders");
				    JSONObject order_json = order.getJSONObject(0);
				    tools.setPaid_count(order_json.getInt("paiedCount"));
				    tools.setGongfang_count(order_json.getInt("gongfangCount"));
					tools.setHuodong_count(order_json.getInt("huodongCount"));
					
					JSONArray order_usage = json.getJSONArray("orderUsages");
					JSONObject order_usages_json = order_usage.getJSONObject(0);
					
					tools.setUsed_count(order_usages_json.getInt("usedCount"));						
					tools.setUsed_gongfang_count(order_usages_json.getInt("usedGongfangCount"));
					tools.setUsed_huodong_count(order_usages_json.getInt("usedHuodongCount"));
				    
				    
			
					list.add(tools);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
