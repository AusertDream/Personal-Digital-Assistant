package com.example.pda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

import com.example.pda.R;
import com.example.pda.entity.Message_WithBot;


//适配器类继承自RecyclerView.Adapter，用于个性化显示
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    //消息记录
    ArrayList<Message_WithBot> messageList ;
    Context mContext;
    //chatAdapter构造方法
    public ChatAdapter(ArrayList<Message_WithBot> messageList,Context mContext){
        this.messageList = messageList;
        this.mContext = mContext;
    }

    //自定义ViewHolder用于存放所有的组件ID和渲染的view
    static class ViewHolder extends RecyclerView.ViewHolder {
        //所有组件类
        LinearLayout leftLayout;
        TextView tv_left;
        TextView left_time;
        TextView right_time;
        LinearLayout chatLayout;
        LinearLayout rightLayout;
        TextView tv_right;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout =  itemView.findViewById(R.id.leftLayout);
            tv_left = itemView.findViewById(R.id.tv_left);
            left_time = itemView.findViewById(R.id.left_time);
            right_time = itemView.findViewById(R.id.right_time);
            chatLayout = itemView.findViewById(R.id.chatLayout);
            rightLayout = itemView.findViewById(R.id.rightLayout);
            tv_right = itemView.findViewById(R.id.tv_right);
        }
    };


    //View的初始渲染
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_layout,parent,false);
        return new ViewHolder(view);
    }

    //在BindViewHolder中绑定数据
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message_WithBot message = messageList.get(position);

       //如果消息是bot发送的则让左对齐的布局显示
        if(Objects.equals(message.getSender(), "bot")){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            //设置时间
            holder.left_time.setText(message.getTime());
            holder.tv_left.setText("" + message.getContent());
        }
        //是用户发送的则让右对齐的布局显示出来
        else{
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.right_time.setText(message.getTime());
            holder.tv_right.setText("" + message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

