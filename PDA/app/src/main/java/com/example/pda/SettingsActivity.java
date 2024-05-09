package com.example.pda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    ImageView backButton;
    SwitchCompat autoPlayWelcomeAudioSwitch;

    //Setting values;
    String settingName;
    boolean isAutoPlayWelcomeAudioEnabled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initView();
        initSettingValues();
    }




    protected void onResume() {
        super.onResume();
        backButton.setOnClickListener(v -> {
            finish();
        });
        autoPlayWelcomeAudioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAutoPlayWelcomeAudioEnabled = isChecked;
                SharedPreferences sharedPreferences = getSharedPreferences(settingName,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isAutoPlayWelcomeAudioEnabled",isChecked);
                editor.apply();
            }
        });
    }

    protected void onStop(){
        super.onStop();

    }

    void initView() {
        backButton = findViewById(R.id.backButtonFromSettings);
        autoPlayWelcomeAudioSwitch = findViewById(R.id.autoPlayWelcomeAudioSwitch);
    }
    private void initSettingValues() {
        settingName = "settings";
        SharedPreferences sharedPreferences = getSharedPreferences(settingName,MODE_PRIVATE);
        isAutoPlayWelcomeAudioEnabled = sharedPreferences.getBoolean("isAutoPlayWelcomeAudioEnabled",false);
        autoPlayWelcomeAudioSwitch.setChecked(isAutoPlayWelcomeAudioEnabled);
    }

}
