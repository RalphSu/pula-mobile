package com.yuhj.ontheway.utils;

public class JSONTools {

	private String start_time; //开始时间
	private String end_time;//结束时间
	private String start_weekday;//星期几
	private String start_hour;//开始的小时
	private String start_minute;//开始的分钟
	private String duration_minute;//课时
	private String name;//课程名字
	private String branch_name;//分部地址
	private String teacher_pic_url;//老师图片的获取地址，可以通过老师的ID来转化
	
	public String getStart_weekday() {
		return start_weekday;
	}

	public void setStart_weekday(String start_weekday) {
		this.start_weekday = start_weekday;
	}

	public String getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}

	public String getStart_minute() {
		return start_minute;
	}

	public void setStart_minute(String start_minute) {
		this.start_minute = start_minute;
	}
	
	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getDuration_minute() {
		return duration_minute;
	}

	public void setDuration_minute(String duration_minute) {
		this.duration_minute = duration_minute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getTeacher_pic_url() {
		return teacher_pic_url;
	}

	public void setTeacher_pic_url(String teacher_pic_url) {
		this.teacher_pic_url = teacher_pic_url;
	}
	
}
