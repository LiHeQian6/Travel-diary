package com.project.li.travel_diary;

import android.net.wifi.aware.DiscoverySession;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.amap.api.maps.model.LatLng;
import com.project.li.travel_diary.bean.Messages;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowMessageActivity extends AppCompatActivity {
    private List<Messages> list = new ArrayList();
    private ListView listView;
    private LatLng location;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    list.clear();
                    list.addAll((List) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        location =(LatLng) getIntent().getParcelableExtra("location");
        getPositionMessages();
        listView=findViewById(R.id.list_view);
        listView.setAdapter(new MessageAdapter());
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
}
