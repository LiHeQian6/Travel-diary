package com.project.li.travel_diary.showMessages;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.project.li.travel_diary.Fragment.Findings;
import com.project.li.travel_diary.Fragment.Footprint;
import com.project.li.travel_diary.Fragment.Myself;
import com.project.li.travel_diary.R;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends AppCompatActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDynamicPermission();
        setStatusBar();
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.mipmap.footg,"足迹"));
        fragmentTabHost.addTab(tabSpec1, Footprint.class,null);

        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.mipmap.fangda,"发现"));

        fragmentTabHost.addTab(tabSpec2, Findings.class,null);

        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.mipmap.myselfg,"个人"));
        fragmentTabHost.addTab(tabSpec3, Myself.class,null);

        fragmentTabHost.setCurrentTab(1);
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.colorMyBlue));

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                textViewMap.get(tabId).setTextColor(getResources().getColor(R.color.colorMyBlue));
                switch(tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.foot);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.fangdag);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.myselfg);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.colorMyGray));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.colorMyGray));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.footg);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.fangda);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.myselfg);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.colorMyGray));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.colorMyGray));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.footg);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.fangdag);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.myself);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.colorMyGray));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.colorMyGray));
                        break;
                }
            }
        });



    }

    public View getTabSpecView(String tag, int imageResId, String title){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_layout,null);

        ImageView imageView = view.findViewById(R.id.item_image);
        imageView.setImageResource(imageResId);

        TextView textView = view.findViewById(R.id.item_text);
        textView.setText(title);

        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);

        return view;
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色
        }
    }
    private void getDynamicPermission() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(MainPageActivity.this,100,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION).setRationale("APP将申请以下权限，请允许以下申请的权限，否则app功能无法正常使用！")
                    .setNegativeButtonText("取消")
                    .setPositiveButtonText("确定")
                    .build());
        }
    }
}
