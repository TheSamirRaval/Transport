package com.transport.api.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SAM on 12/3/20.
 */
public class User {

    @SerializedName("UserData")
    @Expose
    private UserData userData;
    @SerializedName("Status")
    @Expose
    private Status status;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class UserData implements Parcelable{

        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("EmailId")
        @Expose
        private String emailId;
        @SerializedName("Username")
        @Expose
        private String username;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("Address1")
        @Expose
        private String address1;
        @SerializedName("Address2")
        @Expose
        private String address2;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("BirthDate")
        @Expose
        private String birthDate;
        @SerializedName("Language")
        @Expose
        private String language;
        @SerializedName("CountryId")
        @Expose
        private Integer countryId;
        @SerializedName("CountryName")
        @Expose
        private String countryName;
        @SerializedName("StateId")
        @Expose
        private Integer stateId;
        @SerializedName("StateIdName")
        @Expose
        private String stateIdName;
        @SerializedName("CityId")
        @Expose
        private Integer cityId;
        @SerializedName("CityName")
        @Expose
        private String cityName;
        @SerializedName("PinCode")
        @Expose
        private String pinCode;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;
        @SerializedName("RoleListJson")
        @Expose
        private String roleListJson;
        @SerializedName("AuthToken")
        @Expose
        private String authToken;

        public UserData() {
        }

        protected UserData(Parcel in) {
            if (in.readByte() == 0) {
                userId = null;
            } else {
                userId = in.readInt();
            }
            emailId = in.readString();
            username = in.readString();
            phone = in.readString();
            address1 = in.readString();
            address2 = in.readString();
            image = in.readString();
            birthDate = in.readString();
            language = in.readString();
            if (in.readByte() == 0) {
                countryId = null;
            } else {
                countryId = in.readInt();
            }
            countryName = in.readString();
            if (in.readByte() == 0) {
                stateId = null;
            } else {
                stateId = in.readInt();
            }
            stateIdName = in.readString();
            if (in.readByte() == 0) {
                cityId = null;
            } else {
                cityId = in.readInt();
            }
            cityName = in.readString();
            pinCode = in.readString();
            latitude = in.readString();
            longitude = in.readString();
            roleListJson = in.readString();
            authToken = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (userId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(userId);
            }
            dest.writeString(emailId);
            dest.writeString(username);
            dest.writeString(phone);
            dest.writeString(address1);
            dest.writeString(address2);
            dest.writeString(image);
            dest.writeString(birthDate);
            dest.writeString(language);
            if (countryId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(countryId);
            }
            dest.writeString(countryName);
            if (stateId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(stateId);
            }
            dest.writeString(stateIdName);
            if (cityId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(cityId);
            }
            dest.writeString(cityName);
            dest.writeString(pinCode);
            dest.writeString(latitude);
            dest.writeString(longitude);
            dest.writeString(roleListJson);
            dest.writeString(authToken);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<UserData> CREATOR = new Creator<UserData>() {
            @Override
            public UserData createFromParcel(Parcel in) {
                return new UserData(in);
            }

            @Override
            public UserData[] newArray(int size) {
                return new UserData[size];
            }
        };

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone==null?"":phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getStateIdName() {
            return stateIdName;
        }

        public void setStateIdName(String stateIdName) {
            this.stateIdName = stateIdName;
        }

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

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRoleListJson() {
            return roleListJson;
        }

        public void setRoleListJson(String roleListJson) {
            this.roleListJson = roleListJson;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }
    }
}
