package com.example.pda.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.pda.R;

import java.util.Calendar;

public class DailyAffairAddDialog extends AlertDialog {
    Context mContext;
    Button affairTimeButton;
    EditText affairContentInput;
    Button affairConfirmButton;
    TextView affairContentShow;

    public DailyAffairAddDialog(@NonNull Context context) {
        super(context);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.affair_add_dialog_layout, null);
        setView(view);
        affairTimeButton = view.findViewById(R.id.affairTimeButton);
        affairContentInput = view.findViewById(R.id.affairContentInput);
        affairConfirmButton = view.findViewById(R.id.affairConfirmButton);
        affairContentShow = view.findViewById(R.id.affairContentShow);

        affairTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                        2,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                                String str="";
                                str+=hourOfDay;
                                str+=":";
                                str+=minute;
                                affairContentShow.setText(str);
                            }
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true){
                    @Override
                    public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                        super.onTimeChanged(view, hourOfDay, minute);
                        String str="";
                        str+=hourOfDay;
                        str+=":";
                        str+=minute;
                        affairContentShow.setText(str);
                    }
                };
                timePickerDialog.show();
            }
        });
        affairConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AffairContentIsEmpty()){
                    Toast.makeText(view.getContext(), "请输入事务内容", Toast.LENGTH_SHORT).show();
                }
                else if(getAffairTime().isEmpty()){
                    Toast.makeText(view.getContext(), "请选择时间", Toast.LENGTH_SHORT).show();
                }
                else{
                    dismiss();
                }
            }
        });
        super.onCreate(savedInstanceState);
    }


    public String getAffairContent(){
        return affairContentInput.getText().toString();
    }

    public boolean AffairContentIsEmpty(){
        return affairContentInput.getText().toString().isEmpty();
    }

    public String getAffairTime(){
        return affairContentShow.getText().toString();
    }
}
