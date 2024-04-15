package com.example.pda.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.entity.TallyRecord;
import com.example.pda.R;

import java.util.ArrayList;
import com.example.pda.entity.TallyRecord;


public class TallyIncomeAdapter extends RecyclerView.Adapter<TallyIncomeAdapter.ViewHolder>{

    ArrayList<TallyRecord> recordList = new ArrayList<>();
    public TallyIncomeAdapter(ArrayList<TallyRecord> recordList){
        this.recordList = recordList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,amount,time;
        LinearLayout tally_item_layout;
        public ViewHolder(@NonNull View parent) {
            super(parent);
            title = parent.findViewById(R.id.title);
            amount = parent.findViewById(R.id.amount);
            time = parent.findViewById(R.id.time);
            tally_item_layout = parent.findViewById(R.id.tally_item_layout);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tally_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TallyRecord record = recordList.get(position);
        double money = record.getAmount();
        String res = "+" + money;
        holder.title.setText(record.getTitle());
        holder.amount.setText(res);
        holder.time.setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }
}