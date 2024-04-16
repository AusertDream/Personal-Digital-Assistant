package com.example.pda.entity;

public class TallyRecord {
    String title,time;
    double amount;
    public TallyRecord(String title, double amount, String time){
        this.title = title;
        this.amount = amount;
        this.time = time;
    }
    public TallyRecord(TallyRecord record){
        this.title = record.title;
        this.amount = record.amount;
        this.time = record.time;
    }
    public TallyRecord(){
        this.title = "";
        this.amount = 0.0;
        this.time = "";
    }
    public String getTitle() {
        return title;
    }
    public double getAmount() {
        return amount;
    }
    public String getTime() {
        return time;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEmpty(){
        return title.equals("")||amount==0.0;
    }
}

