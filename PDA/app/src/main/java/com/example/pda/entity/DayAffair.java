package com.example.pda.entity;

public class DayAffair {
    private int hour;
    private int minute;
    private String affair;
    public DayAffair(int hour, int minute, String affair) {
        this.hour = hour;
        this.minute = minute;
        this.affair = affair;
    }
    public void setAffair(String affair) {
        this.affair = affair;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public String getAffair() {
        return affair;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
}
