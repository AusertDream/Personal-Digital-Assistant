package com.example.pda.entity;

public class MonthAffair {
    int month;
    int day;
    String affair;
    public MonthAffair(int month, int day, String affair) {
        this.month = month;
        this.day = day;
        this.affair = affair;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public String getAffair() {
        return affair;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public void setAffair(String affair) {
        this.affair = affair;
    }
}
