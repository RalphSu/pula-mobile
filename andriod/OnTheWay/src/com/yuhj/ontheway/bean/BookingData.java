/**
 * 
 */
package com.yuhj.ontheway.bean;

import java.util.Date;

/**
 * @author Liangfei
 *
 */
public class BookingData {

    private int id;
    private String studentName;
    private String parentName;
    private String branchName;
    private String content;
    private String resultName;
    private String plan1;
    private String plan2;
    private String plan3;
    private String plan4;
    private String plan5;
    private Date createdTime;
    private String phone;

    private String resultId;
    private String comments;
    private String ownerName;
    private String age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getPlan1() {
        return plan1;
    }

    public void setPlan1(String plan1) {
        this.plan1 = plan1;
    }

    public String getPlan2() {
        return plan2;
    }

    public void setPlan2(String plan2) {
        this.plan2 = plan2;
    }

    public String getPlan3() {
        return plan3;
    }

    public void setPlan3(String plan3) {
        this.plan3 = plan3;
    }

    public String getPlan4() {
        return plan4;
    }

    public void setPlan4(String plan4) {
        this.plan4 = plan4;
    }

    public String getPlan5() {
        return plan5;
    }

    public void setPlan5(String plan5) {
        this.plan5 = plan5;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getComments() {
        return comments;
    }
    

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
