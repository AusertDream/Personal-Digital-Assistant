package com.example.pda.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.R;
import com.example.pda.entity.YearAffair;
import com.example.pda.fragment.AgendaYear;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AgendaYearAdapter extends RecyclerView.Adapter<AgendaYearAdapter.AgendaYearViewHolder>{
    private ArrayList<YearAffair> agendaDetails;

    public AgendaYearAdapter(ArrayList<YearAffair> agendaDetails) {
        this.agendaDetails = agendaDetails;
    }

    @NonNull
    @Override
    public AgendaYearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_item_layout, parent, false);
        return new AgendaYearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaYearViewHolder holder, int position) {
        YearAffair yearAffair = agendaDetails.get(position);
        String time="";
        time+=yearAffair.getYear();
        time+=".";
        time+=yearAffair.getMonth();
        holder.agendaShowTime.setText(time);
        holder.agendaDetail.setText(yearAffair.getAffair());
    }

    @Override
    public int getItemCount() {
        return agendaDetails.size();
    }

    public static class AgendaYearViewHolder extends RecyclerView.ViewHolder {
        TextView agendaShowTime;
        TextView agendaDetail;

        public AgendaYearViewHolder(@NonNull View itemView) {
            super(itemView);
            agendaShowTime = itemView.findViewById(R.id.agendaShowTime);
            agendaDetail = itemView.findViewById(R.id.agendaDetail);
        }
    }
}
