package com.example.pda.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatedTime {
    Date date = new Date();

    public FormatedTime(){
        //时间增加8小时UTF+8
        //+5000校准时间
        date.setTime(date.getTime()+8*60*60*1000+5000);
    }
    private String formatTime(Date date,int mode){
        String time;
        SimpleDateFormat sdf;
        //模式1:yyyy-MM-dd HH:mm:ss
        if(mode==1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        //模式2：yyyy-MM-dd HH:mm
        else if (mode == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        //模式3：yyyy-MM-dd
        else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        time  = sdf.format(date);
        return time;
    }

    public String getFormatedTime(int mode){
        return formatTime(date,mode);
    }
}
