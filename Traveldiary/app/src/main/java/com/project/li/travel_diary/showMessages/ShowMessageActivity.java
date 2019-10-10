package com.project.li.travel_diary.showMessages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.amap.api.maps.model.LatLng;
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

public class ShowMessageActivity extends AppCompatActivity {
    private List<Messages> list = new ArrayList();
    private ListView listView;
    private LatLng location;
    public static String liked="";
    public static AppCompatActivity a;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    list.clear();
                    list.addAll((List) msg.obj);
                    adapter = new MessageAdapter(a, list, R.layout.messages_page);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        a=this;
        setStatusBar();
        location =(LatLng) getIntent().getParcelableExtra("location");
        getPositionMessages();
        listView=findViewById(R.id.list_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 110 && resultCode == 200) {
            List<Map<String, String>> dataSource = (List<Map<String, String>>) data.getSerializableExtra("dataSource");
            for(int i=0;i<list.size();i++){
                if(list.get(i).getId()==Integer.parseInt(dataSource.get(0).get("id"))){
                    list.get(i).setTitle(dataSource.get(0).get("title"));
                    list.get(i).setContent(dataSource.get(0).get("message"));
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void getPositionMessages() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/travel_diary/PositionMessageServlet?lat="+location.latitude+"&lng="+location.longitude);
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
                            messages.setId(jsonObject.getInt("id"));
                            messages.setLikeNum(jsonObject.getInt("likeNum"));
                            messages.setTitle(jsonObject.getString("title"));
                            messages.setContent(jsonObject.getString("content"));
                            messages.setAddress(jsonObject.getString("address"));
                            messages.setLng(jsonObject.getDouble("lng"));
                            messages.setLat(jsonObject.getDouble("lat"));
                            messages.setDate(jsonObject.getString("date"));
                            messages.setUser(jsonObject.getString("user"));
                            messages.setLiked(jsonObject.getString("liked"));
                            list.add(messages);
                        }
                        Message message = new Message();
                        message.what = 100;
                        message.obj = list;
                        handler.sendMessage(message);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        final SharedPreferences sharedPreferences=getSharedPreferences("data",0);
        final String name = sharedPreferences.getString("name", "");
        if(!"".equals(liked)){
            new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://" +getResources().getString(R.string.IP) + ":8080/travel_diary/LikeMessageServlet?id="+liked+"&user="+name);
                        URLConnection conn = url.openConnection();
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String info = reader.readLine();
                        if(info.equals("true")){
                            liked="";
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }



}
