package shahzaib.com.traffeee_01.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by shahzaib on 7/25/2017.
 */

public  class latlng implements Parcelable {

    double longitite,latitude;
    LatLng latLng;
    ArrayList<latlng> latlngs;

   public latlng(ArrayList<latlng> l){
       this.latlngs = l;
   }
    public latlng() {
    }
    public latlng(LatLng l){
        this.latLng = l;
    }
    public latlng(double longitite, double latitude) {
        this.longitite = longitite;
        this.latitude = latitude;
    }

    protected latlng(Parcel in) {
        longitite = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<latlng> CREATOR = new Creator<latlng>() {
        @Override
        public latlng createFromParcel(Parcel in) {
            return new latlng(in);
        }

        @Override
        public latlng[] newArray(int size) {
            return new latlng[size];
        }
    };

    public double getLongitite() {
        return longitite;
    }

    public void setLongitite(double longitite) {
        this.longitite = longitite;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(longitite);
        parcel.writeDouble(latitude);
    }
}
