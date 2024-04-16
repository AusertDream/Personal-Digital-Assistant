package com.example.pda.utils;



import static android.os.SystemClock.sleep;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.pda.R;

public class TallyInfoDialog extends AlertDialog {
    Context mContext;
    EditText tallyTitleInput; //手动添加记录标题输入框
    EditText tallyAmountInput; //手动添加记录金额输入框
    EditText tallyTimeInput; //手动添加记录时间输入框
    TextView addRecordHelp; //添加记录辅助文本
    Button addCancelButton;//取消按钮
    Button addVerifyButton;//确认按钮

    String title,time;
    double amount;
    public TallyInfoDialog(@NonNull Context context) {
        super(context);
        mContext=context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_tally_record_dialog_layout, null);
        setView(view);
        //初始化组件
        tallyTitleInput = view.findViewById(R.id.tallyTitleInput);
        tallyAmountInput = view.findViewById(R.id.tallyAmountInput);
        tallyTimeInput = view.findViewById(R.id.tallyTimeInput);
        addRecordHelp = view.findViewById(R.id.help);
        addCancelButton = view.findViewById(R.id.addCancelButton);
        addVerifyButton = view.findViewById(R.id.addVerifyButton);
        addCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable title = tallyTitleInput.getText();
                Editable amount = tallyAmountInput.getText();
                Editable time = tallyTimeInput.getText();
                addRecordHelp.setVisibility(View.GONE);
                //合法性检查
                if(checkTallyRecordInput(title,amount,time)){
                    addRecordHelp.setVisibility(View.GONE);
                    TallyInfoDialog.this.title = title.toString();
                    TallyInfoDialog.this.amount = Double.parseDouble(amount.toString());
                    TallyInfoDialog.this.time = time.toString();
                    dismiss();
                }
                else{
                    //如果已经有文字了，抖动一下
                    if(addRecordHelp.getVisibility()==View.VISIBLE){
                        
                    }
                    else {
                        addRecordHelp.setText("输入不合法，请检查输入除了时间外是否为空，或者金额是否合法");
                        addRecordHelp.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        super.onCreate(savedInstanceState);
    }


    boolean checkTallyRecordInput(Editable title, Editable amount, Editable time){
        //检查是否非空
        if(title.length()==0||amount.length()==0){
            return false;
        }
        String amountStr = amount.toString();
        //检查金额是否合法
        boolean amountIsLegal = true;
        int cntDot = 0;
        for(int i=0;i<amountStr.length();i++){
            if(amountStr.charAt(i)=='.'){
                cntDot++;
            }
            if(amountStr.charAt(i)!='-'&&amountStr.charAt(i)!='.'&&!(amountStr.charAt(i)>='0'&&amountStr.charAt(i)<='9')){
                amountIsLegal = false;
                break;
            }
            if(cntDot>1){
                amountIsLegal = false;
                break;
            }
            if(amountStr.charAt(i)=='-'&&i!=0){
                amountIsLegal = false;
                break;
            }
            if(amountStr.charAt(i)=='.'&&i==0){
                amountIsLegal = false;
                break;
            }
            if(amountStr.charAt(i)=='.'&&i==amountStr.length()-1){
                amountIsLegal = false;
                break;
            }
        }
        return amountIsLegal;
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

    public boolean isEmpty(){
        return title==null||amount==0.0;
    }
}
