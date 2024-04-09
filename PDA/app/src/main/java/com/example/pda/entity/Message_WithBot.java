package com.example.pda.entity;

//基本消息类
public class Message_WithBot{
    private String content;
    private String time;
    private String sender;

    //默认构造方法
    public Message_WithBot(){
        content="";
        time=null;
        sender="";
    }
    //含参构造方法
    public Message_WithBot(String content, String time, String sender){
        this.content=content;
        this.time=time;
        this.sender=sender;
    }
    //get方法
    public String getContent(){
        return content;
    }
    public String getTime(){
        return time;
    }
    public String getSender(){
        return sender;
    }
    //set方法
    public void setContent(String content){
        this.content=content;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setSender(String sender){
        this.sender=sender;
    }
}
