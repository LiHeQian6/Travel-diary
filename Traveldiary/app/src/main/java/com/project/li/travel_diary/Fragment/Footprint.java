package com.project.li.travel_diary.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.*;
import com.amap.api.maps.model.*;
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
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Footprint extends SupportMapFragment {
    private TextureMapView mapView;
    private AMap map;
    private Marker clickMarker;
    private List<Messages> list = new ArrayList();
    private AMapLocationClient mLocationClient;
    private SharedPreferences pref;
    private Handler handler=new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message msg) {
            list.clear();
            list.addAll((List) msg.obj);
            //设置标记点
            setMarker();
            //足迹初始化
            setFootPrint();
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.footprint_layout, container, false);
        mapView=view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        if (map == null) {
            map = mapView.getMap();
        }
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);
        locationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        locationStyle.radiusFillColor(Color.argb(0,0,0,0));
        map.setMyLocationStyle(locationStyle);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setScaleControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.changeTilt(60));
        //数据初始化
        getMyFootPrint();
        //初始化定位监听
        mLocationClient = new AMapLocationClient(getContext());
        AMapLocationClientOption mLocationClientOption = new AMapLocationClientOption();
        mLocationClientOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //将视角移到当前位置
                map.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude())));
            }
        });
        //视角初始化
        mLocationClient.startLocation();
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
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setFootPrint() {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        for(int i=0;i<list.size();i++) {
            latLngs.add(new LatLng(list.get(i).getLat(),list.get(i).getLng()));
        }
        map.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(getResources().getColor(R.color.registerBtnFrontCorlor,null)));
    }

    private void setMarker() {
        for(int i=0;i<list.size();i++){
            Messages messages = list.get(i);
            addMarker(new LatLng(messages.getLat(),messages.getLng()),messages.getTitle(),messages.getContent());
        }
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

    public void getMyFootPrint() {
        new Thread() {
            @Override
            public void run() {
                try {
                    pref = getContext().getSharedPreferences("data", MODE_PRIVATE);
                    URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/travel_diary/MyFootPrintServlet?user="+pref.getString("name",""));
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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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
}
