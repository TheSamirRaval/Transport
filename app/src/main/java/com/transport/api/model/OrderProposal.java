package com.transport.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 5/5/2020.
 */
public class OrderProposal {

    @SerializedName("VehicleProposal")
    @Expose
    private List<VehicleProposal> vehicleProposal = null;
    @SerializedName("TotalDataCount")
    @Expose
    private Integer totalDataCount;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<VehicleProposal> getVehicleProposal() {
        return vehicleProposal;
    }

    public void setVehicleProposal(List<VehicleProposal> vehicleProposal) {
        this.vehicleProposal = vehicleProposal;
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

    public static class VehicleProposal implements Parcelable {

        @SerializedName("VehicleProposalId")
        @Expose
        private Integer vehicleProposalId;
        @SerializedName("VehicleId")
        @Expose
        private Integer vehicleId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("VehicleTypeId")
        @Expose
        private Integer vehicleTypeId;
        @SerializedName("VehicalType")
        @Expose
        private String vehicalType;
        @SerializedName("VehicleNumber")
        @Expose
        private String vehicleNumber;
        @SerializedName("SerialNumber")
        @Expose
        private String serialNumber;
        @SerializedName("MaxCapacity")
        @Expose
        private Integer maxCapacity;
        @SerializedName("VehicalOrderNo")
        @Expose
        private String vehicalOrderNo;
        @SerializedName("VehicalOrderDate")
        @Expose
        private String vehicalOrderDate;
        @SerializedName("IsAdminApprove")
        @Expose
        private Boolean isAdminApprove;
        @SerializedName("ContactName")
        @Expose
        private String contactName;
        @SerializedName("ContactNumber")
        @Expose
        private String contactNumber;
        @SerializedName("OrderSummaryId")
        @Expose
        private Integer orderSummaryId;
        @SerializedName("Qty")
        @Expose
        private Integer qty;
        @SerializedName("Price")
        @Expose
        private Double price;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("ProposalDateTime")
        @Expose
        private String proposalDateTime;
        @SerializedName("Status")
        @Expose
        private Boolean status;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("EmailId")
        @Expose
        private String emailId;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("Address1")
        @Expose
        private String address1;
        @SerializedName("Address2")
        @Expose
        private String address2;
        @SerializedName("BirthDate")
        @Expose
        private String birthDate;
        @SerializedName("StateId")
        @Expose
        private Integer stateId;
        @SerializedName("CountryId")
        @Expose
        private Integer countryId;
        @SerializedName("PinCode")
        @Expose
        private Integer pinCode;
        @SerializedName("DeviceInfo")
        @Expose
        private String deviceInfo;
        @SerializedName("Image")
        @Expose
        private String image;

        protected VehicleProposal(Parcel in) {
            if (in.readByte() == 0) {
                vehicleProposalId = null;
            } else {
                vehicleProposalId = in.readInt();
            }
            if (in.readByte() == 0) {
                vehicleId = null;
            } else {
                vehicleId = in.readInt();
            }
            if (in.readByte() == 0) {
                userId = null;
            } else {
                userId = in.readInt();
            }
            if (in.readByte() == 0) {
                vehicleTypeId = null;
            } else {
                vehicleTypeId = in.readInt();
            }
            vehicalType = in.readString();
            vehicleNumber = in.readString();
            serialNumber = in.readString();
            if (in.readByte() == 0) {
                maxCapacity = null;
            } else {
                maxCapacity = in.readInt();
            }
            vehicalOrderNo = in.readString();
            vehicalOrderDate = in.readString();
            byte tmpIsAdminApprove = in.readByte();
            isAdminApprove = tmpIsAdminApprove == 0 ? null : tmpIsAdminApprove == 1;
            contactName = in.readString();
            contactNumber = in.readString();
            if (in.readByte() == 0) {
                orderSummaryId = null;
            } else {
                orderSummaryId = in.readInt();
            }
            if (in.readByte() == 0) {
                qty = null;
            } else {
                qty = in.readInt();
            }
            if (in.readByte() == 0) {
                price = null;
            } else {
                price = in.readDouble();
            }
            description = in.readString();
            proposalDateTime = in.readString();
            byte tmpStatus = in.readByte();
            status = tmpStatus == 0 ? null : tmpStatus == 1;
            if (in.readByte() == 0) {
                createdBy = null;
            } else {
                createdBy = in.readInt();
            }
            createdDate = in.readString();
            userName = in.readString();
            emailId = in.readString();
            phone = in.readString();
            address1 = in.readString();
            address2 = in.readString();
            birthDate = in.readString();
            if (in.readByte() == 0) {
                stateId = null;
            } else {
                stateId = in.readInt();
            }
            if (in.readByte() == 0) {
                countryId = null;
            } else {
                countryId = in.readInt();
            }
            if (in.readByte() == 0) {
                pinCode = null;
            } else {
                pinCode = in.readInt();
            }
            deviceInfo = in.readString();
            image = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (vehicleProposalId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(vehicleProposalId);
            }
            if (vehicleId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(vehicleId);
            }
            if (userId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(userId);
            }
            if (vehicleTypeId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(vehicleTypeId);
            }
            dest.writeString(vehicalType);
            dest.writeString(vehicleNumber);
            dest.writeString(serialNumber);
            if (maxCapacity == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(maxCapacity);
            }
            dest.writeString(vehicalOrderNo);
            dest.writeString(vehicalOrderDate);
            dest.writeByte((byte) (isAdminApprove == null ? 0 : isAdminApprove ? 1 : 2));
            dest.writeString(contactName);
            dest.writeString(contactNumber);
            if (orderSummaryId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(orderSummaryId);
            }
            if (qty == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(qty);
            }
            if (price == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(price);
            }
            dest.writeString(description);
            dest.writeString(proposalDateTime);
            dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
            if (createdBy == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(createdBy);
            }
            dest.writeString(createdDate);
            dest.writeString(userName);
            dest.writeString(emailId);
            dest.writeString(phone);
            dest.writeString(address1);
            dest.writeString(address2);
            dest.writeString(birthDate);
            if (stateId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(stateId);
            }
            if (countryId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(countryId);
            }
            if (pinCode == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(pinCode);
            }
            dest.writeString(deviceInfo);
            dest.writeString(image);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<VehicleProposal> CREATOR = new Creator<VehicleProposal>() {
            @Override
            public VehicleProposal createFromParcel(Parcel in) {
                return new VehicleProposal(in);
            }

            @Override
            public VehicleProposal[] newArray(int size) {
                return new VehicleProposal[size];
            }
        };

        public Integer getVehicleProposalId() {
            return vehicleProposalId;
        }

        public void setVehicleProposalId(Integer vehicleProposalId) {
            this.vehicleProposalId = vehicleProposalId;
        }

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

        public String getVehicalType() {
            return vehicalType;
        }

        public void setVehicalType(String vehicalType) {
            this.vehicalType = vehicalType;
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

        public Integer getMaxCapacity() {
            return maxCapacity;
        }

        public void setMaxCapacity(Integer maxCapacity) {
            this.maxCapacity = maxCapacity;
        }

        public String getVehicalOrderNo() {
            return vehicalOrderNo;
        }

        public void setVehicalOrderNo(String vehicalOrderNo) {
            this.vehicalOrderNo = vehicalOrderNo;
        }

        public String getVehicalOrderDate() {
            return vehicalOrderDate;
        }

        public void setVehicalOrderDate(String vehicalOrderDate) {
            this.vehicalOrderDate = vehicalOrderDate;
        }

        public Boolean getAdminApprove() {
            return isAdminApprove;
        }

        public void setAdminApprove(Boolean adminApprove) {
            isAdminApprove = adminApprove;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public Integer getOrderSummaryId() {
            return orderSummaryId;
        }

        public void setOrderSummaryId(Integer orderSummaryId) {
            this.orderSummaryId = orderSummaryId;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProposalDateTime() {
            return proposalDateTime;
        }

        public void setProposalDateTime(String proposalDateTime) {
            this.proposalDateTime = proposalDateTime;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getPhone() {
            return phone;
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

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getPinCode() {
            return pinCode;
        }

        public void setPinCode(Integer pinCode) {
            this.pinCode = pinCode;
        }

        public String getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(String deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
