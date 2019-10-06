package com.project.li.travel_diary.bean;

import android.support.annotation.NonNull;
import com.amap.api.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * @author li
 * @date 2019/10/4
 * @time 上午10:59
 */
public class Messages implements Serializable{
    private int id;
    private String title;
    private String content;
    private Double lng;
    private Double lat;
    private String date;
    private String user;
    private String address;
    private int likeNum;

    public Messages() {
    }

    public Messages(int id, String title, String content, Double lng, Double lat, String date, String user, String address, int likeNum) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lng = lng;
        this.lat = lat;
        this.date = date;
        this.user = user;
        this.address = address;
        this.likeNum = likeNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                ", address='" + address + '\'' +
                ", likeNum=" + likeNum +
                '}';
    }
}
