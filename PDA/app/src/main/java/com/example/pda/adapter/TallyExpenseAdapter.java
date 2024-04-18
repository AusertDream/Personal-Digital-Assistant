package com.example.pda.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.entity.TallyRecord;
import com.example.pda.R;

import java.util.ArrayList;
import com.example.pda.entity.TallyRecord;


public class TallyExpenseAdapter extends RecyclerView.Adapter<TallyExpenseAdapter.ViewHolder>{

    ArrayList<TallyRecord> recordList = new ArrayList<>();
    OnItemClickListener onItemClickListener;
    public TallyExpenseAdapter(ArrayList<TallyRecord> recordList){
        this.recordList = recordList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,amount,time;
        ImageView deleteButton;
        public ViewHolder(@NonNull View parent) {
            super(parent);
            title = parent.findViewById(R.id.title);
            amount = parent.findViewById(R.id.amount);
            time = parent.findViewById(R.id.time);
            deleteButton = parent.findViewById(R.id.deleteButton);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tally_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    int position = viewHolder.getAdapterPosition();
                    onItemClickListener.onDeleteClick(position);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TallyRecord record = recordList.get(position);
        double money = record.getAmount();
        String res = String.valueOf(money);
        holder.title.setText(record.getTitle());
        holder.amount.setText(res);
        holder.time.setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}