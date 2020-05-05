package com.transport.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 29/3/20.
 */
public class Vehicles {


    @SerializedName("VehicleType")
    @Expose
    private List<VehicleType> vehicleType = null;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<VehicleType> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(List<VehicleType> vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class VehicleType {

        @SerializedName("VehicleTypeId")
        @Expose
        private Integer vehicleTypeId;
        @SerializedName("VehicleType")
        @Expose
        private String vehicleType;
        @SerializedName("Image")
        @Expose
        private String image;

        private boolean isSelected = false;

        public VehicleType(Integer vehicleTypeId, String vehicleType, String image) {
            this.vehicleTypeId = vehicleTypeId;
            this.vehicleType = vehicleType;
            this.image = image;
        }

        public Integer getVehicleTypeId() {
            return vehicleTypeId;
        }

        public void setVehicleTypeId(Integer vehicleTypeId) {
            this.vehicleTypeId = vehicleTypeId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
