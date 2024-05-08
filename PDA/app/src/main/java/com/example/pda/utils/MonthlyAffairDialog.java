package com.example.pda.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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

public class MonthlyAffairDialog extends AlertDialog {

    Context mContext;
    Button affairTimeButton;
    EditText affairContentInput;
    Button affairConfirmButton;
    TextView affairContentShow;

    public MonthlyAffairDialog(@NonNull Context context) {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                String str="";
                                str+=month+1;
                                str+=" ";
                                str+=dayOfMonth;
                                affairContentShow.setText(str);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)){
                    @Override
                    public void onDateChanged(android.widget.DatePicker view, int year, int month, int day) {
                        super.onDateChanged(view, year, month, day);
                        String str="";
                        str+=month+1;
                        str+=" ";
                        str+=day;
                        affairContentShow.setText(str);
                    }
                };
                datePickerDialog.show();
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
