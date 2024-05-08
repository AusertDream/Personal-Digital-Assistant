package com.example.pda.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.R;
import com.example.pda.entity.DayAffair;

import java.util.ArrayList;

public class AgendaDayAdapter extends RecyclerView.Adapter<AgendaDayAdapter.ViewHolder> {

    ArrayList<DayAffair> agendaDetails_day = new ArrayList<>();

    private AgendaYearAdapter.OnItemClickListener onItemClickListener;

    public AgendaDayAdapter(ArrayList<DayAffair> agendaDetails_day){
        this.agendaDetails_day=agendaDetails_day;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaDayAdapter.ViewHolder holder, int position) {
        DayAffair dayAffair = agendaDetails_day.get(position);
        String time="";
        String hour= String.valueOf(dayAffair.getHour());
        String minute= String.valueOf(dayAffair.getMinute());
        if(hour.length()==1){
            hour="0"+hour;
        }
        if(minute.length()==1){
            minute="0"+minute;
        }
        time=hour+":"+minute;
        holder.agendaShowTime.setText(time);
        holder.agendaDetail.setText(dayAffair.getAffair());
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
        return agendaDetails_day.size();
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

    public void setOnItemClickListener(AgendaYearAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
