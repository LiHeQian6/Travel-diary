package com.project.li.travel_diary.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.*;
import com.project.li.travel_diary.AddMessageActivity;
import com.project.li.travel_diary.R;
import com.project.li.travel_diary.ShowMessageActivity;
import com.project.li.travel_diary.bean.Messages;

import java.io.Serializable;
import java.util.Date;

public class Findings extends Fragment {
    private TextureMapView mapView;
    private AMap map;
    private Marker clickMarker;
    private Button add;
    private AMapLocationClient mLocationClient;
    private LatLng latLng;
    private Date date;
    private String address;

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
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                map.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                Log.e("坐标",aMapLocation.getLatitude()+","+aMapLocation.getLongitude());
                date = new Date(aMapLocation.getTime());
                Log.e("date",date.toString());
                address=aMapLocation.getAddress();
                Log.e("address",address);
            }
        });
        //视角初始化
        mLocationClient.startLocation();
        //初始化数据
        addMarker(new LatLng(34.341568, 108.940174),"西安","一起来啊！");
        //添加数据
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(getContext(), ShowMessageActivity.class);
                intent.putExtra("location",marker.getPosition());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&&resultCode==101){
            Messages messages= (Messages) data.getSerializableExtra("msg");
            messages.setDate(date);
            messages.setLng(latLng);
            messages.setAddress(address);
            //发送数据到后台
            String content = messages.getContent();
            addMarker(messages.getLng(),messages.getTitle(), content.length()>10?content.substring(0,10)+"...":content);
        }
    }

    private void addMarker(LatLng lng, String title, String content){
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
}
