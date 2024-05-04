package com.example.pda;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pda.fragment.AgendaDay;
import com.example.pda.fragment.AgendaMonth;
import com.example.pda.fragment.AgendaYear;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class agenda extends AppCompatActivity {

    //所有view的id
    ImageView backButtonFromAgenda;
    FragmentContainerView agendaContent;
    BottomNavigationView bottomNav;
    Context mContext = this;
    FragmentManager fm;
    FragmentTransaction ft;
    ArrayList<Fragment> fragments = new ArrayList<>();
    int lastFragment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        initView();
        initfragments();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backButtonFromAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.agendaYear){
                    switchFragment(lastFragment,0);
                    return true;
                }
                else if(id==R.id.agendaMonth){
                    switchFragment(lastFragment,1);
                    return true;
                }
                else if(id==R.id.agendaDay){
                    switchFragment(lastFragment,2);
                    return true;
                }
                return false;
            }
        });
    }

    void initView(){
        backButtonFromAgenda = findViewById(R.id.backButtonFromAgenda);
        agendaContent = findViewById(R.id.agendaContent);
        bottomNav = findViewById(R.id.bottomNav);
    }

    void initfragments(){
        AgendaYear agendaYear = new AgendaYear();
        AgendaMonth agendaMonth = new AgendaMonth();
        AgendaDay agendaDay = new AgendaDay();
        fragments.add(agendaYear);
        fragments.add(agendaMonth);
        fragments.add(agendaDay);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.agendaContent, agendaYear, "agendaYear").show(agendaYear).commit();
    }

    void switchFragment(int from,int to){
        if(from==to){
            return;
        }
        else{
            ft = fm.beginTransaction();
            ft.hide(fragments.get(from));
            //isadded方法表示这个fragment是否已经被添加到activity中
            if(!fragments.get(to).isAdded()){
                ft.add(R.id.agendaContent,fragments.get(to));
            }
            ft.show(fragments.get(to)).commit();
            lastFragment=to;
        }
    }

}