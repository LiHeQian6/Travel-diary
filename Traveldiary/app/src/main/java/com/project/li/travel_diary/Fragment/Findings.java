package com.project.li.travel_diary.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.*;
import com.project.li.travel_diary.AddMessageActivity;
import com.project.li.travel_diary.Login.LoginActivity;
import com.project.li.travel_diary.R;
import com.project.li.travel_diary.ShowMessageActivity;
import com.project.li.travel_diary.bean.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Findings extends Fragment {
    private TextureMapView mapView;
    private AMap map;
    private Marker clickMarker;
    private Button add;
    private List<Messages> list = new ArrayList();
    private AMapLocationClient mLocationClient;
    private LatLng latLng;
    private String date;
    private String address="火星QAQ";
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100 :
                    list.clear();
                    list.addAll((List) msg.obj);
                    Log.e("list",list.toString());
                    setMarker();
                    break;
                case 101 :
                    if (!((String)msg.obj).equals("0")){
                        messages.setId(Integer.parseInt((String) msg.obj));
                        list.add(messages);
                        String content = messages.getContent();
                        addMarker(new LatLng(messages.getLat(),messages.getLng()),messages.getTitle(), content);
                    }else{
                        Toast.makeText(getContext(),"发布失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private Messages messages;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.findings_layout, container, false);
        mapView=view.findViewById(R.id.mapView);
        add=view.findViewById(R.id.add);
        mapView.onCreate(savedInstanceState);
        if (map == null) {
            map = mapView.getMap();
        }
        //初始化地图状态
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);
        locationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        locationStyle.radiusFillColor(Color.argb(0,0,0,0));
        map.setMyLocationStyle(locationStyle);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setScaleControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.zoomTo(18));
        map.moveCamera(CameraUpdateFactory.changeTilt(60));
        //初始化定位监听
        mLocationClient = new AMapLocationClient(getContext());
        final AMapLocationClientOption mLocationClientOption = new AMapLocationClientOption();
        mLocationClientOption.setOnceLocation(true);
        mLocationClientOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                map.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                Log.e("坐标",aMapLocation.getLatitude()+","+aMapLocation.getLongitude());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date(aMapLocation.getTime());
                date=df.format(d);
                Log.e("date",date);
                address=aMapLocation.getAddress().equals("")?address:aMapLocation.getAddress();
                Log.e("address",address);
            }
        });
        //视角初始化
        mLocationClient.startLocation();
        //初始化数据
        getFootPrint();
        addMarker(new LatLng(34.341568, 108.940174),"西安","一起来啊！");
        //添加数据
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences sharedPreferences=getContext().getSharedPreferences("data",0);
                String name = sharedPreferences.getString("name", "");
                if(name.equals("")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                mLocationClient.startLocation();
                Messages messages = new Messages();
                Intent intent=new Intent(getContext(), AddMessageActivity.class);
                intent.putExtra("messages",messages);
                startActivityForResult(intent,100);
            }
        });
        //设置标记摘要点击空白消失
        map.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(clickMarker!=null){
                    clickMarker.hideInfoWindow();
                }
            }
        });
        map.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                clickMarker = marker;
                return true;
            }
        });
        map.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //位置限定
//                mLocationClient.startLocation();
//                if ( AMapUtils.calculateLineDistance(latLng,marker.getPosition())>100){
//                    Toast.makeText(getContext(),"距离太远了，我的千里眼都看不到了！快到那里亲眼看看吧！",Toast.LENGTH_LONG).show();
//                    return;
//                }
                Intent intent = new Intent(getContext(), ShowMessageActivity.class);
                intent.putExtra("location",marker.getPosition());
                startActivity(intent);
            }
        });
        return view;
    }

    private void setMarker() {
        for(int i=0;i<list.size();i++){
            Messages messages = list.get(i);
            addMarker(new LatLng(messages.getLat(),messages.getLng()),messages.getTitle(),messages.getContent());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&&resultCode==101){
            final SharedPreferences sharedPreferences=getContext().getSharedPreferences("data",0);
            String name = sharedPreferences.getString("name", "");
            messages= (Messages) data.getSerializableExtra("msg");
            messages.setDate(date);
            messages.setLat(latLng.latitude);
            messages.setLng(latLng.longitude);
            Log.e("message",address);
            messages.setAddress(address);
            messages.setUser(name);
            Log.e("message",messages.toString());
            //发送数据到后台
            addMessage(messages);
        }
    }

    private void addMessage(Messages msg) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",-1);
            jsonObject.put("title",msg.getTitle());
            jsonObject.put("content",msg.getContent());
            jsonObject.put("address",msg.getAddress());
            jsonObject.put("lng",msg.getLng());
            jsonObject.put("lat",msg.getLat());
            jsonObject.put("date",msg.getDate());
            jsonObject.put("user",msg.getUser());
            jsonObject.put("likenum",msg.getLikeNum());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String msgJSON = jsonObject.toString();
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/travel_diary/AddMessageServlet");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("charset", "utf-8");
                    OutputStream stream = conn.getOutputStream();
                    stream.write(msgJSON.getBytes());
                    stream.close();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String jsonString = reader.readLine();
                    Message message = new Message();
                    message.what = 101;
                    message.obj =jsonString;
                    Log.e("info",jsonString);
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void addMarker(LatLng lng, String title, String content){
        content = content.length()>10?content.substring(0,10)+"...":content;
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(lng);
        markerOption.title(title).snippet(content);
        //markerOption.draggable(true);//设置Marker可拖动
        //markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                //.decodeResource(getResources(),R.drawable.branch)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //markerOption.setFlat(true);//设置marker平贴地图效果
        final Marker marker = map.addMarker(markerOption);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        map=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    public void getFootPrint() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/travel_diary/FootPrintServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();Log.e("as",info);
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

}
