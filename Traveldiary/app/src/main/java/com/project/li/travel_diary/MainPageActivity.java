package com.project.li.travel_diary;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.project.li.travel_diary.Fragment.Findings;
import com.project.li.travel_diary.Fragment.Footprint;
import com.project.li.travel_diary.Fragment.Message;
import pub.devrel.easypermissions.EasyPermissions;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        fragmentTabHost.addTab(tabSpec3, Message.class,null);

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
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(MainPageActivity.this, "APP将申请以下权限，请问是否允许？", 100,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
        EasyPermissions.PermissionCallbacks permissionCallbacks = new EasyPermissions.PermissionCallbacks() {
            @Override
            public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
            }

            @Override
            public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

            }

            @Override
            public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_PHONE_STATE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionCallbacks.onPermissionsDenied(100,permissions);
    }
}
