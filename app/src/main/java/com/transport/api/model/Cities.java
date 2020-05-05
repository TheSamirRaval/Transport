package com.transport.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 28/3/20.
 */
public class Cities {
    @SerializedName("City")
    @Expose
    private List<City> city = null;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public static class City implements Parcelable {

        @SerializedName("CityId")
        @Expose
        private Integer cityId;
        @SerializedName("CityName")
        @Expose
        private String cityName;

        protected City(Parcel in) {
            if (in.readByte() == 0) {
                cityId = null;
            } else {
                cityId = in.readInt();
            }
            cityName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (cityId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(cityId);
            }
            dest.writeString(cityName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<City> CREATOR = new Creator<City>() {
            @Override
            public City createFromParcel(Parcel in) {
                return new City(in);
            }

            @Override
            public City[] newArray(int size) {
                return new City[size];
            }
        };

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public String toString() {
            return "City{" +
                    "cityId=" + cityId +
                    ", cityName='" + cityName + '\'' +
                    '}';
        }
    }
}
