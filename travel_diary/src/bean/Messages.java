package bean;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * @author li
 * @date 2019/10/4
 * @time 涓婂崍10:59
 */
public class Messages implements Serializable{
    private int id;
    private String title;
    private String content;
    private Double lng;
    private Double lat;
    private String date;
    private String user;
    private String address="火星QAQ";
    private String liked="";
    private int likeNum;
    private String PWD = "";

    public Messages() {
    }

    

    
	
	    /**
	     * 创建一个新的实例 Messages
	     *
	     * @param id
	     * @param title
	     * @param content
	     * @param lng
	     * @param lat
	     * @param date
	     * @param user
	     * @param address
	     * @param liked
	     * @param likeNum
	     */
	    
	public Messages(int id, String title, String content, Double lng, Double lat, String date, String user,
			String address, String liked, int likeNum) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.lng = lng;
		this.lat = lat;
		this.date = date;
		this.user = user;
		this.address = address;
		this.liked = liked;
		this.likeNum = likeNum;
	}




	/**
	 * @return liked
	 */
	
	public String getLiked() {
		return liked;
	}



	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setLiked(String liked) {
		this.liked = liked;
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
    
	/**
	 * @return iP
	 */
	
	public String getPWD() {
		return PWD;
	}





	
	    /* (非 Javadoc)
	     * 
	     * 
	     * @return
	     * @see java.lang.Object#toString()
	     */
	    
	@Override
	public String toString() {
		return "Messages [id=" + id + ", title=" + title + ", content=" + content + ", lng=" + lng + ", lat=" + lat
				+ ", date=" + date + ", user=" + user + ", address=" + address + ", liked=" + liked + ", likeNum="
				+ likeNum + "]";
	}

}
