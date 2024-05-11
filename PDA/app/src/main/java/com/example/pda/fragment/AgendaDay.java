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
import com.example.pda.adapter.AgendaYearAdapter;
import com.example.pda.database.PDADataHelper;
import com.example.pda.entity.DayAffair;
import com.example.pda.adapter.AgendaDayAdapter;
import com.example.pda.entity.YearAffair;
import com.example.pda.utils.DailyAffairAddDialog;

import java.util.ArrayList;


public class AgendaDay extends Fragment {

    View view;
    Button addAgendaButton_day;
    SwitchCompat delAgendaButton_day;
    RecyclerView agendaYearRecyclerView_day;
    AgendaDayAdapter agendaDayAdapter;

    ArrayList<DayAffair> agendaDetails_day = new ArrayList<>();
    PDADataHelper mHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda_day, container, false);
        initView();
        initData();
        initRecycleView();
        return view;
    }

    public void onResume(){
        super.onResume();
        addAgendaButton_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyAffairAddDialog dailyAffairAddDialog = new DailyAffairAddDialog(view.getContext());
                dailyAffairAddDialog.show();
                dailyAffairAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        int hour, minute;
                        if(dailyAffairAddDialog.getAffairContent().equals("")){
                            Toast.makeText(view.getContext(),"事项内容不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!dailyAffairAddDialog.getAffairTime().equals("")) {
                            hour = Integer.parseInt(dailyAffairAddDialog.getAffairTime().split(":")[0]);
                            minute = Integer.parseInt(dailyAffairAddDialog.getAffairTime().split(":")[1]);
                            DayAffair dayAffair = new DayAffair(hour, minute, dailyAffairAddDialog.getAffairContent());
                            int pos = getInsertPosition(agendaDetails_day,dayAffair);
                            if(pos==agendaDetails_day.size()||agendaDetails_day.isEmpty()){
                                agendaDetails_day.add(dayAffair);
                            }
                            else {
                                agendaDetails_day.add(pos, dayAffair);
                            }
                            mHelper.insertDayAffair(dayAffair);
                            agendaDayAdapter.notifyItemInserted(pos);
                        }
                    }
                });
            }
        });
        agendaDayAdapter.setOnItemClickListener(new AgendaYearAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                if(delAgendaButton_day.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("警告");
                    builder.setMessage("确认删除该事项吗？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mHelper.deleteDayAffair(agendaDetails_day.get(position));
                            agendaDetails_day.remove(position);
                            agendaDayAdapter.notifyItemRemoved(position);
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

    public void onStop(){
        super.onStop();
        mHelper.closeReadLink();
        mHelper.closeWriteLink();
    }

    void initView(){
        addAgendaButton_day = view.findViewById(R.id.addAgendaButton_day);
        delAgendaButton_day = view.findViewById(R.id.delAgendaButton_day);
        agendaYearRecyclerView_day = view.findViewById(R.id.agendaYearRecyclerView_day);
    }

    void initData(){
        mHelper = PDADataHelper.getInstance(view.getContext());
        SQLiteDatabase dbRead = mHelper.openReadLink();
        SQLiteDatabase dbWrite = mHelper.openWriteLink();
        agendaDetails_day = mHelper.selectDayAffairAll();
    }

    void initRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        agendaYearRecyclerView_day.setLayoutManager(linearLayoutManager);
        agendaDayAdapter = new AgendaDayAdapter(agendaDetails_day);
        agendaYearRecyclerView_day.setAdapter(agendaDayAdapter);
    }

    int getInsertPosition(@NonNull ArrayList<DayAffair> data, DayAffair dayAffair){
        int pos = data.size()-1;
        for(int i=0;i<data.size();i++){
            int hour = data.get(i).getHour();
            int minute = data.get(i).getMinute();
            if(dayAffair.getHour()>hour){
                if(i==data.size()-1){
                    pos=data.size();
                    break;
                }
            }
            else if(dayAffair.getHour()==hour){
                if(dayAffair.getMinute()>=minute){
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