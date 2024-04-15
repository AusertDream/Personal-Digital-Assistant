package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.adapter.TallyExpenseAdapter;
import com.example.pda.adapter.TallyIncomeAdapter;
import com.example.pda.entity.TallyRecord;
import com.example.pda.utils.FormatedTime;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class TallyBook extends AppCompatActivity {

    //所有组件对象
    ImageView backButton;//返回按钮
    LinearLayout changeTimeGroup; //收支查询调整时间按钮
    TextView timeText; //时间文本显示
    SwitchCompat startAutoTallySwitch; //自动记账开关
    ImageView addButton; //手动添加账单按钮
    RecyclerView incomeRecyclerView; //收入列表
    RecyclerView expenseRecyclerView; //支出列表
    ImageView alertButton; //警告按钮
    LinearLayout tally_item_layout; //账单列表
    TextView tv_income; //收入总额
    TextView tv_expense; //支出总额
    TextView tv_balance; //结余
    TextView tv_comment;//Ray评
    EditText tallyTitleInput; //手动添加记录标题输入框
    EditText tallyAmountInput; //手动添加记录金额输入框
    EditText tallyTimeInput; //手动添加记录时间输入框
    TextView addRecordHelp; //添加记录辅助文本
    Button addCancelButton; //添加记录取消按钮
    Button addVerifyButton; //添加记录确认按钮

    //TallyRecord
    ArrayList<TallyRecord> incomeRecordList = new ArrayList<>();
    ArrayList<TallyRecord> expenseRecordList = new ArrayList<>();

    double income = 0;//总收入
    double expense = 0;//总支出
    double balance = 0;//结余

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tallybook);
        initView();
        initTallyRecord();
        initRecyclerView();
    }

    protected void onStart() {
        super.onStart();

    }

    protected void onResume(){
        super.onResume();
        //返回按钮
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //开启自动记账警告
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TallyBook.this);
                builder.setTitle("警告");
                builder.setMessage("开启自动记账功能后，将会监听您的通知栏消息，可能会获取到您的隐私信息，如果不放心请不要开启此功能。");
                builder.setPositiveButton("确定",null);
                builder.show();
            }
        });
        //开启自动记账功能
        startAutoTallySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TallyBook.this);
                    builder.setTitle("警告");
                    builder.setMessage("开启自动记账功能后，将会监听您的通知栏消息，可能会获取到您的隐私信息，是否确认开启？");
                    builder.setPositiveButton("确定",(dialog, which) -> {
                        setAutoTally(true);
                    });
                    builder.setNegativeButton("取消",(dialog, which) -> {
                        startAutoTallySwitch.setChecked(false);
                        setAutoTally(false);
                    });
                    builder.show();
                }
                else{
                    setAutoTally(false);
                }
            }
        });
        //手动添加
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTallyRecord();
            }
        });
    }



    void initView(){
        backButton = findViewById(R.id.backButton);
        changeTimeGroup = findViewById(R.id.changeTimeButton);
        timeText = findViewById(R.id.timeText);
        startAutoTallySwitch = findViewById(R.id.startAutoTallySwitch);
        addButton = findViewById(R.id.addButton);
        incomeRecyclerView = findViewById(R.id.incomeRecyclerView);
        expenseRecyclerView = findViewById(R.id.expenseRecyclerView);
        alertButton = findViewById(R.id.alertButton);
        tally_item_layout = findViewById(R.id.tally_item_layout);
        tv_income = findViewById(R.id.tv_income);
        tv_expense = findViewById(R.id.tv_expense);
        tv_balance = findViewById(R.id.tv_balance);
        tv_comment = findViewById(R.id.tv_comment);

    }
    void setAutoTally(boolean isAutoTally){
        //TODO:设置自动记账功能

    }
    void initRecyclerView(){
        //初始化incomeRecyclerView和expenseRecyclerView
        LinearLayoutManager layoutManagerLeft = new LinearLayoutManager(this);
        incomeRecyclerView.setLayoutManager(layoutManagerLeft);
        LinearLayoutManager layoutManagerRight = new LinearLayoutManager(this);
        expenseRecyclerView.setLayoutManager(layoutManagerRight);
        TallyIncomeAdapter incomeAdapter = new TallyIncomeAdapter(incomeRecordList);
        incomeRecyclerView.setAdapter(incomeAdapter);
        TallyExpenseAdapter expenseAdapter = new TallyExpenseAdapter(expenseRecordList);
        expenseRecyclerView.setAdapter(expenseAdapter);
    }
    void initTallyRecord(){
        //TODO 从数据库获取数据


        //TODO 计算当前数组内存放的所有记录的支出情况
        calIncomeAndExpense();
    }

    void calIncomeAndExpense(){
        //TODO 计算当前数组内存放的所有记录的支出情况

        String incomeStr = "收入 ￥ "+income;
        String expenseStr = "支出 ￥ "+expense;
        String balanceStr = "结余 ￥ "+balance;
        tv_income.setText(incomeStr);
        tv_expense.setText(expenseStr);
        tv_balance.setText(balanceStr);
        if(balance<0&&balance>-1000){
            tv_comment.setText("有点小透支了");
        }
        else if(balance<=-1000) {
            tv_comment.setText("不是哥么，您这么花钱的？？");
        }
        else if(balance>=0&&balance<1000){
            tv_comment.setText("海星");
        }
        else{
            tv_comment.setText("不是哥么，这么能省钱的？？");
        }
    }

    void showAddTallyRecord(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加一条记录");
        View view = LayoutInflater.from(this).inflate(R.layout.add_tally_record_dialog_layout,null);
        builder.setView(view);

        tallyTitleInput = view.findViewById(R.id.tallyTitleInput);
        tallyAmountInput = view.findViewById(R.id.tallyAmountInput);
        tallyTimeInput = view.findViewById(R.id.tallyTimeInput);
        addRecordHelp = view.findViewById(R.id.help);
        addCancelButton = view.findViewById(R.id.addCancelButton);
        addVerifyButton = view.findViewById(R.id.addVerifyButton);
        //TODO 继承AlertDialog，重写一个新的dialog类
        builder.setPositiveButton("确定",(dialog, which) -> {
            Editable title = tallyTitleInput.getText();
            Editable amount = tallyAmountInput.getText();
            Editable time = tallyTimeInput.getText();
            //合法性检查
            if(checkTallyRecordInput(title,amount,time)){

            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("错误");
                alert.setMessage("输入不合法，请检查输入除了时间外是否为空，或者金额是否合法");
                alert.setPositiveButton("确定",null);
                alert.show();
            }
        });
        builder.setNegativeButton("取消",null);
        AlertDialog dialog = builder.create();
        dialog.show();
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
            if(amountStr.charAt(i)!='-'||amountStr.charAt(i)!='.'||!(amountStr.charAt(i)>='0'&&amountStr.charAt(i)<='9')){
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
        }
        return amountIsLegal;
    }
}