package com.example.pda.fragment;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pda.R;
import com.example.pda.adapter.AgendaMonthAdapter;
import com.example.pda.adapter.AgendaYearAdapter;
import com.example.pda.database.PDADataHelper;
import com.example.pda.entity.MonthAffair;
import com.example.pda.entity.YearAffair;
import com.example.pda.utils.AffairAddDialog;
import com.example.pda.utils.MonthlyAffairDialog;

import java.util.ArrayList;


public class AgendaMonth extends Fragment {

    View view;
    Button addAgendaButton_month;
    SwitchCompat delAgendaButton_month;
    RecyclerView agendaYearRecyclerView_month;
    AgendaMonthAdapter agendaMonthAdapter;
    ArrayList<MonthAffair> agendaDetails_month = new ArrayList<>();

    PDADataHelper mHelper = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda_month, container, false);
        initView();
        initData();
        initRecyclerView();
        return view;
    }

    public void onResume(){
        super.onResume();
        //添加按钮的点击事件
        addAgendaButton_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthlyAffairDialog affairAddDialog = new MonthlyAffairDialog(view.getContext());
                affairAddDialog.show();
                affairAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        int month,day;
                        if(affairAddDialog.getAffairContent().equals("")){
                            Toast.makeText(view.getContext(),"事项内容不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!affairAddDialog.getAffairTime().equals("")) {
                            month = Integer.parseInt(affairAddDialog.getAffairTime().split(" ")[0]);
                            day = Integer.parseInt(affairAddDialog.getAffairTime().split(" ")[1]);
                            MonthAffair monthAffair = new MonthAffair(month, day, affairAddDialog.getAffairContent());
                            int pos = getInsertPosition(agendaDetails_month, monthAffair);
                            if (pos == agendaDetails_month.size()|| agendaDetails_month.isEmpty()) {
                                agendaDetails_month.add(monthAffair);
                            } else {
                                agendaDetails_month.add(pos, monthAffair);
                            }
                            mHelper.insertMonthAffair(monthAffair);
                            agendaMonthAdapter.notifyItemInserted(pos);
                        }
                        affairAddDialog.dismiss();
                    }
                });

                agendaMonthAdapter.setOnItemClickListener(new AgendaMonthAdapter.OnItemClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        if(delAgendaButton_month.isChecked()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle("警告");
                            builder.setMessage("确认删除该事项吗？");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mHelper.deleteMonthAffair(agendaDetails_month.get(position));
                                    agendaDetails_month.remove(position);
                                    agendaMonthAdapter.notifyItemRemoved(position);
                                }
                            });
                            builder.setNegativeButton("取消",null);
                            builder.show();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle("信息");
                            builder.setMessage("想要删除先打开删除开关哦");
                            builder.setPositiveButton("确认",null);
                            builder.show();
                        }
                    }
                });
            }
        });
    }

    public void onStop(){
        super.onStop();
        mHelper.closeWriteLink();
        mHelper.closeReadLink();
    }

    void initView(){
        addAgendaButton_month = view.findViewById(R.id.addAgendaButton_month);
        delAgendaButton_month = view.findViewById(R.id.delAgendaButton_month);
        agendaYearRecyclerView_month = view.findViewById(R.id.agendaYearRecyclerView_month);
    }

    void initData(){
        mHelper=PDADataHelper.getInstance(view.getContext());
        SQLiteDatabase dbRead = mHelper.openReadLink();
        SQLiteDatabase dbWrite = mHelper.openWriteLink();
        agendaDetails_month = mHelper.selectMonthAffairAll();
    }

    void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        agendaYearRecyclerView_month.setLayoutManager(linearLayoutManager);
        agendaMonthAdapter = new AgendaMonthAdapter(agendaDetails_month);
        agendaYearRecyclerView_month.setAdapter(agendaMonthAdapter);
    }


    int getInsertPosition(@NonNull ArrayList<MonthAffair> data, MonthAffair monthAffair){
        int pos = data.size()-1;
        for(int i=0;i<data.size();i++){
            int month = data.get(i).getMonth();
            int day = data.get(i).getDay();
            if(monthAffair.getMonth()>month){
                if(i==data.size()-1){
                    pos=data.size();
                    break;
                }
            }
            else if(monthAffair.getMonth()==month){
                if(monthAffair.getDay()>=day){
                    if(i==data.size()-1){
                        pos=data.size();
                        break;
                    }
                    else{
                        continue;
                    }
                }
                else{
                    pos=i;
                    break;
                }
            }
            else{
                pos=i;
                break;
            }
        }
        return pos;
    }
}