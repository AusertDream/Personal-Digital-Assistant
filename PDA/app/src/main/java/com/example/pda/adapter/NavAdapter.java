package com.example.pda.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pda.R;

import java.util.ArrayList;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder>{

    ArrayList<String> navList;

    public NavAdapter(ArrayList<String> navList){
        this.navList = navList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout nav_item_layout;
        Button nav_item_button;
        public ViewHolder(@NonNull View parent) {
            super(parent);
            nav_item_layout = parent.findViewById(R.id.nav_item_layout);
            nav_item_button = parent.findViewById(R.id.nav_item_button);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        //按钮点击事件
        viewHolder.nav_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedContent = viewHolder.nav_item_button.getText().toString();
                if(clickedContent.equals("手账")){
                    //跳转到手账页面
                    Intent intent = new Intent(v.getContext(), com.example.pda.TallyBook.class);
                    v.getContext().startActivity(intent);
                }
                else if(clickedContent.equals("日程表")){
                    //跳转到日程表界面
                    Intent intent = new Intent(v.getContext(),com.example.pda.agenda.class);
                    v.getContext().startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(), "这个还没做（", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String navButtonContent = navList.get(position);
        holder.nav_item_button.setText(navButtonContent);
    }

    @Override
    public int getItemCount() {
        return navList.size();
    }
}
