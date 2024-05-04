package com.example.pda.entity;

public class YearAffair {
    private int year;
    private int month;
    private String affair;
    public YearAffair(int year, int month, String affair) {
        this.year = year;
        this.month = month;
        this.affair = affair;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setAffair(String affair) {
        this.affair = affair;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public String getAffair() {
        return affair;
    }
}
