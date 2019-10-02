package com.project.li.travel_diary.MessageTree;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.project.li.travel_diary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,String> item = new HashMap<>();
        item.put("time","2019.10.2");
        item.put("location","拉斯维加斯");
        item.put("message","哈哈哈哈哈哈哈哈哈哈");
        dataSource.add(item);
        listView = findViewById(R.id.list_item);
        treeAdapter = new TreeAdapter(this,dataSource,R.layout.tree_item);
        listView.setAdapter(treeAdapter);

    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }
}
