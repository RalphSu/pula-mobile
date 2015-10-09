package com.pula.star.bean;

import java.util.Calendar;

public class UserInfoData {
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentCaption() {
		return parentCaption;
	}
	public void setParentCaption(String parentCaption) {
		this.parentCaption = parentCaption;
	}
	public int isGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(int updatedTime) {
		this.updatedTime = updatedTime;
	}
	public int getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(int createdTime) {
		this.createdTime = createdTime;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public long getBirthday() {
		return birthday;
	}
	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getGenderName() {
		return genderName;
	}
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
	
	public int getTypeRange() {
		return typeRange;
	}
	public void setTypeRange(int typeRange) {
		this.typeRange = typeRange;
	}
	
	public String getBarCode() {
        return barCode;
    }
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getGender() {
        return gender;
    }

    private String barCode;
	private String address;
	private String name;
	private int id;
	private boolean enabled;
	private String password;
	private String no;
	private String parentName;
	private String parentCaption;
	private int gender;
	private String genderName;
	private int updatedTime;
	private int createdTime;
	private int points;
   
    private long birthday;
    private int phone;
    private int zip;
    private String mobile;
    private String email;
    private int typeRange;
    

}
