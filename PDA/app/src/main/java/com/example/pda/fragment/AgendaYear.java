package com.example.pda.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.pda.entity.YearAffair;
import com.example.pda.utils.AffairAddDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;


public class AgendaYear extends Fragment {
    Button addAgendaButton;
    SwitchCompat delAgendaButton;
    RecyclerView agendaYearRecyclerView;
    AgendaYearAdapter agendaYearAdapter;
    ArrayList<YearAffair> agendaDetails = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda_year, container, false);
        initView();
        initData();
        initRecyclerView();
        return view;
    }

    public void onResume(){
        super.onResume();
        //添加按钮的点击事件
        addAgendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AffairAddDialog affairAddDialog = new AffairAddDialog(view.getContext());
                affairAddDialog.show();
                affairAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                            int year,month;
                            if(!affairAddDialog.getAffairTime().equals("")) {
                                year = Integer.parseInt(affairAddDialog.getAffairTime().split(" ")[0]);
                                month = Integer.parseInt(affairAddDialog.getAffairTime().split(" ")[1]);
                                YearAffair yearAffair = new YearAffair(year, month, affairAddDialog.getAffairContent());
                                int pos = getInsertPosition(agendaDetails, yearAffair);
                                if (pos == agendaDetails.size()) {
                                    agendaDetails.add(yearAffair);
                                } else {
                                    agendaDetails.add(pos, yearAffair);
                                }
                                agendaYearAdapter.notifyItemInserted(pos);
                            }
                            affairAddDialog.dismiss();
                    }
                });
            }
        });

        //删除按钮的点击事件
        agendaYearAdapter.setOnItemClickListener(new AgendaYearAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                if(delAgendaButton.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("警告");
                    builder.setMessage("确认删除该事项吗？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            agendaDetails.remove(position);
                            agendaYearAdapter.notifyItemRemoved(position);
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

    void initData(){
        //TODO 从数据库中获取数据

        agendaDetails.add(new YearAffair(2021, 1, "2021年1月的事情"));
        agendaDetails.add(new YearAffair(2021, 2, "2021年2月的事情"));
        agendaDetails.add(new YearAffair(2021, 3, "2021年3月的事情"));
    }
    void initView(){
        addAgendaButton = view.findViewById(R.id.addAgendaButton);
        delAgendaButton = view.findViewById(R.id.delAgendaButton);
        agendaYearRecyclerView = view.findViewById(R.id.agendaYearRecyclerView);
    }
    void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        agendaYearRecyclerView.setLayoutManager(linearLayoutManager);
        agendaYearAdapter = new AgendaYearAdapter(agendaDetails);
        agendaYearRecyclerView.setAdapter(agendaYearAdapter);
    }

    int getInsertPosition(@NonNull ArrayList<YearAffair> data, YearAffair yearAffair){
        int pos = data.size()-1;
        for(int i=0;i<data.size();i++){
            int year = data.get(i).getYear();
            int month = data.get(i).getMonth();
            if(yearAffair.getYear()>=year){
                if(yearAffair.getMonth()>=month){
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