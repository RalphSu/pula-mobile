package com.pula.star.bean;

public class MyCourseJsonTools {

	private String start_time; //开始时间
	private String end_time;//结束时间
	private String start_weekday;//星期几
	private String start_hour;//开始的小时
	private String start_minute;//开始的分钟
	private String duration_minute;//课时
	private String name;//课程名字
	private String branch_name;//分部地址
	private String teacher_pic_url;//老师图片的获取地址，可以通过老师的ID来转化	
	private String cousr_no;//课程 的编号
	private int used_count;
	private int paid_count;
	
	private String create_time;
	
	private int gongfang_count;
	private int used_gongfang_count;
	
	private int huodong_count;
	private int used_huodong_count;
	
	public int getUsed_count() {
		return used_count;
	}

	public void setUsed_count(int used_count) {
		this.used_count = used_count;
	}

	public int getPaid_count() {
		return paid_count;
	}

	public void setPaid_count(int paid_count) {
		this.paid_count = paid_count;
	}

	public int getGongfang_count() {
		return gongfang_count;
	}

	public void setGongfang_count(int gongfang_count) {
		this.gongfang_count = gongfang_count;
	}

	public int getUsed_gongfang_count() {
		return used_gongfang_count;
	}

	public void setUsed_gongfang_count(int used_gongfang_count) {
		this.used_gongfang_count = used_gongfang_count;
	}

	public int getHuodong_count() {
		return huodong_count;
	}

	public void setHuodong_count(int huodong_count) {
		this.huodong_count = huodong_count;
	}

	public int getUsed_huodong_count() {
		return used_huodong_count;
	}

	public void setUsed_huodong_count(int used_huodong_count) {
		this.used_huodong_count = used_huodong_count;
	}


	
	public String getStart_weekday() {
		return start_weekday;
	}

	public void setStart_weekday(String start_weekday) {
		this.start_weekday = start_weekday;
	}

	
	public String getCreate_time() {
	  return create_time;
    }

	public void setCreate_time(String create_time) {
	   this.create_time = create_time;
	}
	
	public String getCourse_no() {
		return cousr_no;
	}

	public void setCourse_no(String cousr_no) {
		this.cousr_no = cousr_no;
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




