package com.transport.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 31/3/20.
 */
public class AddUpdateOrder {

    @SerializedName("toLandmark")
    @Expose
    private String toLandmark;
    @SerializedName("orderNo")
    @Expose
    private Integer orderNo;
    @SerializedName("toLONG")
    @Expose
    private Double toLONG;
    @SerializedName("contactName")
    @Expose
    private String contactName;
    @SerializedName("fromLAT")
    @Expose
    private Double fromLAT;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("fromLONG")
    @Expose
    private Double fromLONG;
    @SerializedName("toAddress")
    @Expose
    private String toAddress;
    @SerializedName("isLoaded")
    @Expose
    private Boolean isLoaded;
    @SerializedName("toLAT")
    @Expose
    private Double toLAT;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("orderSummaryId")
    @Expose
    private Integer orderSummaryId;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("fromAddress")
    @Expose
    private String fromAddress;
    @SerializedName("fromLandmark")
    @Expose
    private String fromLandmark;
    @SerializedName("VehicleTypeId")
    @Expose
    private Integer vehicleId;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("isShared")
    @Expose
    private Boolean isShared;
    @SerializedName("productList")
    @Expose
    private List<ProductList> productList = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fromCityId")
    @Expose
    private String fromCityId;
    @SerializedName("fromStateId")
    @Expose
    private String fromStateId;


    public AddUpdateOrder(String toLandmark, Integer orderNo, Double toLONG, String contactName, Double fromLAT,
                          String userId, Double fromLONG, String toAddress, Boolean isLoaded, Double toLAT,
                          String price, Integer orderSummaryId, String contactNumber, String fromAddress,
                          String fromLandmark, Integer vehicleId, String orderDate, Boolean isShared,
                          List<ProductList> productList, Integer status, String fromCityId, String fromStateId) {
        this.toLandmark = toLandmark;
        this.orderNo = orderNo;
        this.toLONG = toLONG;
        this.contactName = contactName;
        this.fromLAT = fromLAT;
        this.userId = userId;
        this.fromLONG = fromLONG;
        this.toAddress = toAddress;
        this.isLoaded = isLoaded;
        this.toLAT = toLAT;
        this.price = price;
        this.orderSummaryId = orderSummaryId;
        this.contactNumber = contactNumber;
        this.fromAddress = fromAddress;
        this.fromLandmark = fromLandmark;
        this.vehicleId = vehicleId;
        this.orderDate = orderDate;
        this.isShared = isShared;
        this.productList = productList;
        this.status = status;
        this.fromCityId = fromCityId;
        this.fromStateId = fromStateId;
    }

    public String getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(String fromCityId) {
        this.fromCityId = fromCityId;
    }

    public String getFromStateId() {
        return fromStateId;
    }

    public void setFromStateId(String fromStateId) {
        this.fromStateId = fromStateId;
    }

    public String getToLandmark() {
        return toLandmark;
    }

    public void setToLandmark(String toLandmark) {
        this.toLandmark = toLandmark;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Double getToLONG() {
        return toLONG;
    }

    public void setToLONG(Double toLONG) {
        this.toLONG = toLONG;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Double getFromLAT() {
        return fromLAT;
    }

    public void setFromLAT(Double fromLAT) {
        this.fromLAT = fromLAT;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getFromLONG() {
        return fromLONG;
    }

    public void setFromLONG(Double fromLONG) {
        this.fromLONG = fromLONG;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Boolean getLoaded() {
        return isLoaded;
    }

    public void setLoaded(Boolean loaded) {
        isLoaded = loaded;
    }

    public Double getToLAT() {
        return toLAT;
    }

    public void setToLAT(Double toLAT) {
        this.toLAT = toLAT;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getOrderSummaryId() {
        return orderSummaryId;
    }

    public void setOrderSummaryId(Integer orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromLandmark() {
        return fromLandmark;
    }

    public void setFromLandmark(String fromLandmark) {
        this.fromLandmark = fromLandmark;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public List<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList> productList) {
        this.productList = productList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static class ProductList {

        @SerializedName("productState")
        @Expose
        private Integer productState;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("orderDetailId")
        @Expose
        private Integer orderDetailId;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("uoM")
        @Expose
        private String uoM;
        @SerializedName("sizeType")
        @Expose
        private String sizeType;
        @SerializedName("sizeWidth")
        @Expose
        private String sizeWidth;
        @SerializedName("sizeHeight")
        @Expose
        private String sizeHeight;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("materialId")
        @Expose
        private String materialId;
        @SerializedName("size")
        @Expose
        private String size;

        public ProductList(Integer productState, String productName, Integer orderDetailId, String qty, String uoM, String sizeType, String sizeWidth,
                           String sizeHeight, String price, String materialId, String size) {
            this.productState = productState;
            this.productName = productName;
            this.orderDetailId = orderDetailId;
            this.qty = qty;
            this.uoM = uoM;
            this.sizeType = sizeType;
            this.sizeWidth = sizeWidth;
            this.sizeHeight = sizeHeight;
            this.price = price;
            this.materialId = materialId;
            this.size = size;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Integer getProductState() {
            return productState;
        }

        public void setProductState(Integer productState) {
            this.productState = productState;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getOrderDetailId() {
            return orderDetailId;
        }

        public void setOrderDetailId(Integer orderDetailId) {
            this.orderDetailId = orderDetailId;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getUoM() {
            return uoM;
        }

        public void setUoM(String uoM) {
            this.uoM = uoM;
        }

        public String getSizeType() {
            return sizeType;
        }

        public void setSizeType(String sizeType) {
            this.sizeType = sizeType;
        }

        public String getSizeWidth() {
            return sizeWidth;
        }

        public void setSizeWidth(String sizeWidth) {
            this.sizeWidth = sizeWidth;
        }

        public String getSizeHeight() {
            return sizeHeight;
        }

        public void setSizeHeight(String sizeHeight) {
            this.sizeHeight = sizeHeight;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }
    }
}
