package shahzaib.com.traffeee_01.Models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by shahzaib on 8/19/2018.
 */

public class Status_Model {
    public String name,textmessage,imageUrl,address;
    long time;
    latlng latLng;

    public Status_Model(String name, String textmessage, String imageUrl, String address, long time, latlng latLng) {
        this.name = name;
        this.textmessage = textmessage;
        this.imageUrl = imageUrl;
        this.address = address;
        this.time = time;
        this.latLng = latLng;
    }

    public latlng getLatLng() {
        return latLng;
    }

    public void setLatLng(latlng latLng) {
        this.latLng = latLng;
    }

    Status_Model(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextmessage() {
        return textmessage;
    }

    public void setTextmessage(String textmessage) {
        this.textmessage = textmessage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
