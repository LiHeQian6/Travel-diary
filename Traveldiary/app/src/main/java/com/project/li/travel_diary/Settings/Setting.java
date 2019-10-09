package com.project.li.travel_diary.Settings;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.project.li.travel_diary.R;

import java.util.Set;

public class Setting extends AppCompatActivity {
    private String[] itemName = {"昵称","密码","皮肤","更多设置"};
    private ListView settingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setStatusBar();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,itemName,R.layout.setting_item);
        settingList = findViewById(R.id.list_view);
        settingList.setAdapter(arrayAdapter);

        settingOnClickEvent();


    }

    //设置设置每一项点击事件
    private void settingOnClickEvent() {
        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent();
                    intent.setClass(Setting.this,SetName.class);
                    startActivity(intent);
                }else if(position == 1){
                    Intent intentCP = new Intent();
                    intentCP.setClass(Setting.this,ChangePassword.class);
                    startActivity(intentCP);
                }else {
                    Toast.makeText(Setting.this,"功能尚未开发，敬请期待...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayout settingImageLayout = findViewById(R.id.setting_image_layout);
        settingImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting.this,"功能尚未开发，敬请期待...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }
}
