package com.project.li.travel_diary.MessageTree;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.project.li.travel_diary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class MessageTree extends AppCompatActivity {
    private List<Map<String,String>> dataSource;
    private ListView listView;
    private TreeAdapter treeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_tree);

        setStatusBar();


        dataSource = new ArrayList<>();
        for(int i=0;i<=4;i++){
            Map<String,String> item = new HashMap<>();
            item.put("title","我是标题"+i);
            item.put("leavedate","2019.10.2");
            item.put("location","拉斯维加斯");
            item.put("message","哈哈哈哈哈哈哈哈哈哈");
            item.put("finger","456");
            dataSource.add(item);
        }
        listView = findViewById(R.id.list_item);
        treeAdapter = new TreeAdapter(this,dataSource,R.layout.tree_item);
        listView.setAdapter(treeAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && resultCode == 200){
            dataSource = (List<Map<String,String>>)data.getSerializableExtra("dataSource");
            Log.e("title",dataSource.get(0).get("title"));
            dataSource.remove(dataSource.size()-1);
            listView = findViewById(R.id.list_item);
            treeAdapter = new TreeAdapter(this,dataSource,R.layout.tree_item);
            listView.setAdapter(treeAdapter);
        }
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }


}
