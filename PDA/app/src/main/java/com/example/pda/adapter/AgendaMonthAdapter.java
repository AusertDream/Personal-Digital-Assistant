package com.example.pda.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.R;
import com.example.pda.entity.MonthAffair;

import java.util.ArrayList;

public class AgendaMonthAdapter extends RecyclerView.Adapter<AgendaMonthAdapter.ViewHolder> {

    ArrayList<MonthAffair> agendaDetails_month = new ArrayList<>();
    OnItemClickListener onItemClickListener;

    public AgendaMonthAdapter(ArrayList<MonthAffair> agendaDetails_month){
        this.agendaDetails_month=agendaDetails_month;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonthAffair monthAffair = agendaDetails_month.get(position);
        String time="";
        String month= String.valueOf(monthAffair.getMonth());
        String day= String.valueOf(monthAffair.getDay());
        if(month.length()==1){
            month="0"+month;
        }
        if(day.length()==1){
            day="0"+day;
        }
        time=month+"."+day;
        holder.agendaShowTime.setText(time);
        holder.agendaDetail.setText(monthAffair.getAffair());
        holder.affairDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    int position = holder.getAdapterPosition();
                    onItemClickListener.onDeleteClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return agendaDetails_month.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView agendaShowTime;
        TextView agendaDetail;
        ImageView affairDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agendaShowTime = itemView.findViewById(R.id.agendaShowTime);
            agendaDetail = itemView.findViewById(R.id.agendaDetail);
            affairDeleteButton = itemView.findViewById(R.id.affairDeleteButton);
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
