package com.project.li.travel_diary.MessageTree;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.project.li.travel_diary.R;
import com.project.li.travel_diary.bean.Messages;

import org.json.JSONArray;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageTree extends AppCompatActivity {
    private List<Map<String,String>> dataSource;
    private Context context = this;
    private ListView listView;
    private TreeAdapter treeAdapter;
    public final static String IPaddress="47.94.247.44";
    private Handler handlerInquery= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List list = (List)msg.obj;
            dataSource = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                Map<String,String> item = new HashMap<>();
                Messages messages = (Messages)list.get(i);
                item.put("id",messages.getId()+"");
                item.put("title",messages.getTitle()+"");
                item.put("leavedate",messages.getDate());
                item.put("location",messages.getAddress());
                item.put("message",messages.getContent());
                item.put("finger",messages.getLikeNum()+"");
                item.put("lng",messages.getLng()+"");
                item.put("lat",messages.getLat()+"");
                dataSource.add(item);
            }
            listView = findViewById(R.id.list_item);
            treeAdapter = new TreeAdapter(context,dataSource,R.layout.tree_item);
            listView.setAdapter(treeAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_tree);

        getMyLeaveMessageHistory();

        setStatusBar();


    }

    //向服务器发送数据,返回用户的所有留言
    public void getMyLeaveMessageHistory() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.IP) +
                            ":8080/travel_diary/MyFootPrintServlet?user="+getSharedPreferences("data",MODE_PRIVATE).getString("name",""));
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    List list = new ArrayList();
                    try {
                        JSONArray array = new JSONArray(info);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            Messages messages = new Messages();
                            messages.setId(jsonObject.getInt("id"));                //id
                            messages.setLikeNum(jsonObject.getInt("likeNum"));      //赞数
                            messages.setTitle(jsonObject.getString("title"));       //标题
                            messages.setContent(jsonObject.getString("content"));   //留言内容
                            messages.setAddress(jsonObject.getString("address"));   //地址
                            messages.setDate(jsonObject.getString("date"));         //留言日期
                            messages.setLat(jsonObject.getDouble("lat"));
                            messages.setLng(jsonObject.getDouble("lng"));
                            list.add(messages);
                        }
                        Message message = new Message();
                        message.what = 100;
                        message.obj = list;
                        handlerInquery.sendMessage(message);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //跳转的返回方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && resultCode == 200){
            dataSource = (List<Map<String,String>>)data.getSerializableExtra("dataSource");
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
