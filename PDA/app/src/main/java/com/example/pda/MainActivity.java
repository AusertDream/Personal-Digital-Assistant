package com.example.pda;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.pda.adapter.ChatAdapter;
import com.example.pda.adapter.NavAdapter;
import com.example.pda.entity.Message_WithBot;

public class MainActivity extends AppCompatActivity {

    //消息记录
    ArrayList<Message_WithBot> messageList = new ArrayList<>();
    //当前上下文
    Context mContext = this;
    //所有的组件对象
    Button linkStart; //linkStart按钮
    TextView introTitle; //顶部title文字
    ImageView botImage; //bot图片
    DrawerLayout drawerLayout;//侧滑栏
    ImageView button_more; //更多按钮
    LinearLayout navigation_bar_background; //导航栏背景
    TextView introInfo; //更多按钮提示语
    LinearLayout talkLayout; //对话布局
    RecyclerView chatView; //聊天框
    Button sendButton_WithBot; //发送按钮
    TextView inputText_WithBot; //输入框
    ChatAdapter chatadapter;//与bot的聊天适配器
    LinearLayout nav_item_layout; //导航栏布局
    RecyclerView nav_recycler_view; //导航栏列表布局




    //onCreate执行一些数据初始化的工作
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                    //默认的初始化
        setContentView(R.layout.activity_main);
        initView();//初始化所有组件对象
        //初始化聊天记录
        initChatRecord();
        //初始化导航栏内容
        initNavigation();
    }

    //start执行一些用户可见，但是不可交互的工作
    @Override
    protected void onStart(){
        super.onStart();

    }

    //resume执行一些用户可见，可交互的工作
    public void onResume(){
        super.onResume();

        //处理linkstart按钮的点击事件
        linkStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放进入语音
                playWelcomeAudio();
                //初始化进入布局
                initEnterLayout();

            }
        });

        //处理更多按钮的点击事件
        button_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //打开侧滑栏
                drawerLayout.open();
            }
        });

        //处理发送按钮的点击事件
        sendButton_WithBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的内容
                String inputText = inputText_WithBot.getText().toString();
                //获取当前时间
                Date curDate = new Date();
                String curTime = formatTime(curDate);
                if(!inputText.equals("")) {
                    //将消息添加进messageList中
                    Message_WithBot new_message = new Message_WithBot(inputText, curTime, "user");
                    messageList.add(new_message);
                    chatadapter.notifyItemInserted(messageList.size() - 1);
                    chatView.scrollToPosition(messageList.size() - 1);
                    //调用GPT接口，获得回复
                    String botResponse = getChatResponse(inputText);
                    //将回复添加进messageList中
                    curDate=new Date();
                    curTime = formatTime(curDate);
                    Message_WithBot bot_message = new Message_WithBot(botResponse, curTime, "bot");
                    messageList.add(bot_message);
                    chatadapter.notifyItemInserted(messageList.size() - 1);
                    chatView.scrollToPosition(messageList.size() - 1);
                }
                //清空输入框内容
                inputText_WithBot.setText("");
            }
        });
    }
    //播放进入语音
    public void playWelcomeAudio(){
        //建立监听器，监听是否播放完毕，如果完毕，释放资源
        MediaPlayer welcomeAudio = MediaPlayer.create(this,R.raw.duyanjuren_welcome);
        if(welcomeAudio!=null){
            welcomeAudio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    welcomeAudio.release();
                }
            });
            //播放音频
            try {
                welcomeAudio.start();
            } catch (IllegalStateException e){
                Log.e("MediaPlayError","welcome audio display failed");
            }
        }
        else{
            Log.e("MediaPlayObjectError","MediaPlayer Object Create failed");
        }

    }
    //格式化时间
    public String formatTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //时间增加8小时UTF+8
        date.setTime(date.getTime()+8*60*60*1000);
        return sdf.format(date);
    }

    public void initEnterLayout(){
        //顶部title文字消失
        introTitle.setVisibility(View.GONE);
        //导航栏出现
        navigation_bar_background.setVisibility(View.VISIBLE);
        //更多按钮提示语出现
        introInfo.setVisibility(View.VISIBLE);
        //bot图片向上移动
        botImage.animate().translationY(-400).setDuration(1000).start();
        //linkStart按钮消失
        linkStart.setVisibility(View.GONE);
        //更多按钮出现
        button_more.setVisibility(View.VISIBLE);
        //对话框出现
        talkLayout.setVisibility(View.VISIBLE);
    }
    public void initChatRecord(){
        //bot初始信息添加进messagelist中去
        Date curDate = new Date();
        String curTime = formatTime(curDate);
        Message_WithBot default_message = new Message_WithBot("你好，我叫bot，有什么可以帮助你的吗？",curTime,"bot");
        messageList.add(default_message);
        //设置聊天框的布局管理器,设置itemlayout上下文是当前布局，从而显示内容。
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        chatView.setLayoutManager(layoutManager);
        //创建适配器，用于给聊天框存放初始数据
        chatadapter = new ChatAdapter(messageList,mContext);
        chatView.setAdapter(chatadapter);
    }
    @NonNull
    public String getChatResponse(String inputText) {
        //TODO 调用GPT接口，获得回复
        String response = "当前未接入接口，此功能暂时不能用捏QAQ";

        return response;
    }

    public void initView(){
        //所有的组件对象
        linkStart = findViewById(R.id.linkStart); //linkStart按钮
        introTitle = findViewById(R.id.intro_title); //顶部title文字
        botImage = findViewById(R.id.botImage); //bot图片
        drawerLayout = findViewById(R.id.drawerLayout);//侧滑栏
        button_more = findViewById(R.id.button_more); //更多按钮
        navigation_bar_background = findViewById(R.id.navigation_bar_background); //导航栏背景
        introInfo = findViewById(R.id.introInfo); //更多按钮提示语
        talkLayout = findViewById(R.id.talkLayout); //对话布局
        chatView = findViewById(R.id.chatView_WithBot); //聊天框
        sendButton_WithBot = findViewById(R.id.sendButton_WithBot); //发送按钮
        inputText_WithBot = findViewById(R.id.multilineEditText_WithBot); //输入框
        nav_item_layout = findViewById(R.id.nav_item_layout); //导航栏item布局
        nav_recycler_view = findViewById(R.id.nav_recycler_view); //导航栏列表布局
    }
    public void initNavigation(){
        //列表内容
        ArrayList<String> nav_item_list = new ArrayList<>();
        nav_item_list.add("主页");
        nav_item_list.add("日程表");
        nav_item_list.add("手账");
        nav_item_list.add("个人日志");
        nav_item_list.add("拍照识别");
        nav_item_list.add("定位");
        nav_item_list.add("设置");
        nav_item_list.add("关于");
        //设置导航栏布局管理器
        LinearLayoutManager nav_item_layout_manager = new LinearLayoutManager(mContext);
        nav_recycler_view.setLayoutManager(nav_item_layout_manager);

        NavAdapter navAdapter = new NavAdapter(nav_item_list);
        nav_recycler_view.setAdapter(navAdapter);

    }
}




