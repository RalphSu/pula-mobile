package com.pula.star.bean;

public class MyCourseData {
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public int getPaidCount() {
		return paidCount;
	}

	public void setPaidCount(int paidCount) {
		this.paidCount = paidCount;
	}

	public int getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(int usedCount) {
		this.usedCount = usedCount;
	}

	public int getSpecialCourseCount() {
		return specialCourseCount;
	}

	public void setSpecialCourseCount(int specialCourseCount) {
		this.specialCourseCount = specialCourseCount;
	}

	public int getUsedSpecialCourseCount() {
		return usedSpecialCourseCount;
	}

	public void setUsedSpecialCourseCount(int usedSpecialCourseCount) {
		this.usedSpecialCourseCount = usedSpecialCourseCount;
	}

	public int getHuodongCount() {
		return huodongCount;
	}

	public void setHuodongCount(int huodongCount) {
		this.huodongCount = huodongCount;
	}

	public int getUsedHuodongCount() {
		return usedHuodongCount;
	}

	public void setUsedHuodongCount(int usedHuodongCount) {
		this.usedHuodongCount = usedHuodongCount;
	}

	public int getGongfangCount() {
		return gongfangCount;
	}

	public void setGongfangCount(int gongfangCount) {
		this.gongfangCount = gongfangCount;
	}

	public int getUsedGongFangCount() {
		return usedGongFangCount;
	}

	public void setUsedGongFangCount(int usedGongFangCount) {
		this.usedGongFangCount = usedGongFangCount;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	private String createTime;
	private String updateTime;
	private String courseTime;
	
	private int paidCount;
	private int usedCount;
	
	private int specialCourseCount;
	private int usedSpecialCourseCount;
	
	private int huodongCount;
	private int usedHuodongCount;
	
	private int gongfangCount;
	private int usedGongFangCount;
	
    private int level;
}
