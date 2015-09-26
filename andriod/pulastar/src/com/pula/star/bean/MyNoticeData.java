package com.pula.star.bean;

import org.joda.time.DateTime;

public class MyNoticeData {

    private String noticeId;

    private String noticeName;

    private int buyCount;

    private int buyCost;

    private DateTime noticeDay;

    private DateTime buyDay;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public DateTime getNoticeDay() {
        return noticeDay;
    }

    public void setNoticeDay(DateTime noticeDay) {
        this.noticeDay = noticeDay;
    }

    public DateTime getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(DateTime buyDay) {
        this.buyDay = buyDay;
    }

}
