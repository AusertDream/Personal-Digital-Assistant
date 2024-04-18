package com.example.pda;

import static android.os.SystemClock.sleep;

import static java.lang.Math.abs;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.pda.utils.TallyInfoDialog;
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

    //TallyRecord
    ArrayList<TallyRecord> incomeRecordList = new ArrayList<>();
    ArrayList<TallyRecord> expenseRecordList = new ArrayList<>();

    double income = 0;//总收入
    double expense = 0;//总支出
    double balance = 0;//结余
    TallyIncomeAdapter incomeAdapter;//收入适配器
    TallyExpenseAdapter expenseAdapter;//支出适配器
    Context mContext = this;

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
        incomeAdapter = new TallyIncomeAdapter(incomeRecordList);
        incomeRecyclerView.setAdapter(incomeAdapter);
        incomeAdapter.setOnItemClickListener(new TallyIncomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("警告");
                builder.setMessage("确定删除这条记录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TallyRecord tmp = incomeRecordList.get(position);
                        showIncomeAndExpense(tmp.getAmount(),3);
                        incomeRecordList.remove(position);
                        incomeAdapter.notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
        expenseAdapter = new TallyExpenseAdapter(expenseRecordList);
        expenseRecyclerView.setAdapter(expenseAdapter);
        expenseAdapter.setOnItemClickListener(new TallyExpenseAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("警告");
                builder.setMessage("确定删除这条记录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TallyRecord tmp = expenseRecordList.get(position);
                        showIncomeAndExpense(tmp.getAmount(),4);
                        expenseRecordList.remove(position);
                        expenseAdapter.notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
    }
    void initTallyRecord(){
        //TODO 从数据库获取数据

        //初始化总收支
        initIncomeAndExpense();
    }

    void initIncomeAndExpense(){
        //初始化总收支
        for(TallyRecord record:incomeRecordList){
            income+=record.getAmount();
        }
        for(TallyRecord record:expenseRecordList){
            expense+=record.getAmount();
        }
        balance = income+expense;
        showIncomeAndExpense(0,0);

    }
    /*
        mode=0:用于初始化的时候显示总收支
        mode=1:用于添加收入时显示总收支
        mode=2:用于添加支出时显示总收支
        mode=3:用于删除收入时显示总收支
        mode=4:用于删除支出时显示总收支
     */
    void showIncomeAndExpense(double amount,int mode){
        if(mode==0){
            //do nothing
        }
        else if(mode==1){
            income+=amount;
            balance+=amount;
        }
        else if(mode==2){
            expense+=amount;
            balance+=amount;
        }
        else if(mode==3){
            income-=amount;
            balance-=amount;
        }
        else if(mode==4){
            expense-=amount;
            balance-=amount;
        }

        String incomeStr = "收入 ￥ "+income;
        String expenseStr = "支出 ￥ "+abs(expense);
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
        TallyInfoDialog mydialog = new TallyInfoDialog(this);
        mydialog.show();
        TallyRecord record = new TallyRecord();
        //设置监听器当窗口关闭的时候获取其中的内容
        mydialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(!mydialog.isEmpty()) {
                    //获取内容
                     record.setTitle(mydialog.getTitle());
                     record.setAmount(mydialog.getAmount());

                     if(mydialog.getTime().isEmpty()){
                         record.setTime("Unknown Time");
                     }
                     else{
                         record.setTime(mydialog.getTime());
                     }
                     //分类处理
                     if (record.getAmount() > 0) {
                         incomeRecordList.add(record);
                         incomeAdapter.notifyItemInserted(incomeRecordList.size() - 1);
                         incomeRecyclerView.scrollToPosition(incomeRecordList.size() - 1);
                         showIncomeAndExpense(record.getAmount(),1);
                     } else if (record.getAmount() < 0) {
                         expenseRecordList.add(record);
                         expenseAdapter.notifyItemInserted(expenseRecordList.size() - 1);
                         expenseRecyclerView.scrollToPosition(expenseRecordList.size() - 1);
                         showIncomeAndExpense(record.getAmount(),2);
                     } else {
                         Toast.makeText(TallyBook.this, "金额不能为0", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });

    }


}