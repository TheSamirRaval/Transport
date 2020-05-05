package com.transport.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OwnerVehicle {

    @SerializedName("Vehicle")
    @Expose
    private List<Vehicle> vehicle = null;
    @SerializedName("TotalDataCount")
    @Expose
    private Integer totalDataCount;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getTotalDataCount() {
        return totalDataCount;
    }

    public void setTotalDataCount(Integer totalDataCount) {
        this.totalDataCount = totalDataCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Vehicle {

        @SerializedName("VehicleId")
        @Expose
        private Integer vehicleId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("VehicleTypeId")
        @Expose
        private Integer vehicleTypeId;
        @SerializedName("VehicleType")
        @Expose
        private String vehicleType;
        @SerializedName("VehicleNumber")
        @Expose
        private String vehicleNumber;
        @SerializedName("SerialNumber")
        @Expose
        private String serialNumber;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("MaxCapacity")
        @Expose
        private Integer maxCapacity;
        @SerializedName("IsAdminApprove")
        @Expose
        private boolean isAdminApprove;
        @SerializedName("IsActive")
        @Expose
        private boolean isActive;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;

        private boolean isSelected = false;

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getMaxCapacity() {
            return maxCapacity;
        }

        public void setMaxCapacity(Integer maxCapacity) {
            this.maxCapacity = maxCapacity;
        }

        public boolean isAdminApprove() {
            return isAdminApprove;
        }

        public void setAdminApprove(boolean adminApprove) {
            isAdminApprove = adminApprove;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
