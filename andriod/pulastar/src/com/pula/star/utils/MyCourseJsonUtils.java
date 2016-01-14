package com.pula.star.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pula.star.bean.MyCourseJsonTools;

public class MyCourseJsonUtils {
	
	private List<MyCourseJsonTools> list;
	private MyCourseJsonTools tools;

	
	public List<MyCourseJsonTools> PutData(String str) {
		list = new ArrayList<MyCourseJsonTools>();
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			JSONArray arr = obj.getJSONArray("records");
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
				    
				    int paidCount = 0;
				    int gongFangCount = 0;
				    int huoDongCount = 0;
				    int specCount = 0;
				    
				    int paidUsedCount = 0;
				    int gongFangUsedCount = 0;
				    int huoDongUsedCount = 0;
				    int specUsedCount = 0;
				    JSONObject order_json;
				    
				    for(int j = 0; j < order.length(); j++)
				    {
				    order_json = order.getJSONObject(j);
				    
				    paidCount += order_json.getInt("paiedCount");
				    gongFangCount += order_json.getInt("gongfangCount");
				    huoDongCount += order_json.getInt("huodongCount");
				    specCount += order_json.getInt("specialCourseCount");
				    paidUsedCount += order_json.getInt("usedCount");
				    gongFangUsedCount += order_json.getInt("usedGongFangCount");
				    huoDongUsedCount += order_json.getInt("usedHuodongCount");
				    specUsedCount += order_json.getInt("usedSpecialCourseCount");
				    tools.setLevel(order.getJSONObject(0).getInt("level")); 
				    }
				    
				    tools.setPaid_count(paidCount);
				    tools.setGongfang_count(gongFangCount);
					tools.setHuodong_count(huoDongCount);
					tools.setSpec_count(specCount);
					
					tools.setUsed_count(paidUsedCount);						
					tools.setUsed_gongfang_count(gongFangUsedCount);
					tools.setUsed_huodong_count(huoDongUsedCount);
				    tools.setUsed_spec_count(specUsedCount);
				    
					list.add(tools);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
