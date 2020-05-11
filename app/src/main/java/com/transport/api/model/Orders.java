package com.transport.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 16/3/20.
 */
public class Orders {

    @SerializedName("AllOrder")
    @Expose
    private List<AllOrder> allOrder = null;
    @SerializedName("AllOrders")
    @Expose
    private List<AllOrder> allOrders = null;
    @SerializedName("TotalDataCount")
    @Expose
    private Integer totalDataCount;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<AllOrder> getAllOrder() {
        return allOrder;
    }

    public void setAllOrder(List<AllOrder> allOrder) {
        this.allOrder = allOrder;
    }

    public List<AllOrder> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<AllOrder> allOrders) {
        this.allOrders = allOrders;
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

    public static class AllOrder {

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("OrderList")
        @Expose
        private List<OrderList> orderList = null;

        public static class OrderList implements Parcelable {

            @SerializedName("OrderSummaryId")
            @Expose
            private Integer orderSummaryId;
            @SerializedName("OrderNo")
            @Expose
            private String orderNo;
            @SerializedName("OrderTitle")
            @Expose
            private String orderTitle;
            @SerializedName("OrderDescription")
            @Expose
            private String orderDescription;
            @SerializedName("OrderDate")
            @Expose
            private String orderDate;
            @SerializedName("FromAddress")
            @Expose
            private String fromAddress;
            @SerializedName("FromLandmark")
            @Expose
            private String fromLandmark;
            @SerializedName("FromArea")
            @Expose
            private Integer fromArea;
            @SerializedName("FromCityId")
            @Expose
            private Integer fromCityId;
            @SerializedName("FromStateId")
            @Expose
            private Integer fromStateId;
            @SerializedName("FromLAT")
            @Expose
            private String fromLAT;
            @SerializedName("FromLONG")
            @Expose
            private String fromLONG;
            @SerializedName("ToAddress")
            @Expose
            private String toAddress;
            @SerializedName("ToLandmark")
            @Expose
            private String toLandmark;
            @SerializedName("ToArea")
            @Expose
            private Integer toArea;
            @SerializedName("ToCityId")
            @Expose
            private Integer toCityId;
            @SerializedName("ToStateId")
            @Expose
            private Integer toStateId;
            @SerializedName("ToLAT")
            @Expose
            private String toLAT;
            @SerializedName("ToLONG")
            @Expose
            private String toLONG;
            @SerializedName("IsShared")
            @Expose
            private Boolean isShared;
            @SerializedName("IsLoaded")
            @Expose
            private Boolean isLoaded;
            @SerializedName("ContactName")
            @Expose
            private String contactName;
            @SerializedName("ContactNumber")
            @Expose
            private String contactNumber;
            @SerializedName("ProposalCount")
            @Expose
            private Integer proposalCount;
            @SerializedName("UserId")
            @Expose
            private Integer userId;
            @SerializedName("Price")
            @Expose
            private Double price;
            @SerializedName("Status")
            @Expose
            private Integer status;
            @SerializedName("StatusName")
            @Expose
            private String statusName;
            @SerializedName("CreatedById")
            @Expose
            private Integer createdById;
            @SerializedName("ProductList")
            @Expose
            private List<ProductList> productList = null;
            @SerializedName("VehicleTypeId")
            @Expose
            private Integer vehicleTypeId;
            @SerializedName("VehicleType")
            @Expose
            private String vehicleType;
            @SerializedName("VehicalTypeImage")
            @Expose
            private String vehicalTypeImage;

            //region OwnerOrder
            @SerializedName("VehicleOrderSummaryId")
            @Expose
            private Integer vehicleOrderSummaryId;
            @SerializedName("VehicleOrderNo")
            @Expose
            private String vehicleOrderNo;
            @SerializedName("VehicleOrderTitle")
            @Expose
            private String vehicleOrderTitle;
            @SerializedName("VehicleOrderDescription")
            @Expose
            private String vehicleOrderDescription;
            @SerializedName("VehicleOrderDate")
            @Expose
            private String vehicleOrderDate;
            @SerializedName("VehicleId")
            @Expose
            private Integer vehicleId;
            @SerializedName("Size")
            @Expose
            private String size;
            @SerializedName("Qty")
            @Expose
            private Integer qty;
            @SerializedName("UoM")
            @Expose
            private String uoM;
            @SerializedName("ProductState")
            @Expose
            private Integer productState;
            //endregion

            public static class ProductList implements Parcelable {

                @SerializedName("OrderDetailId")
                @Expose
                private Integer orderDetailId;
                @SerializedName("ProductName")
                @Expose
                private String productName;
                @SerializedName("ProductState")
                @Expose
                private Integer productState;
                @SerializedName("Qty")
                @Expose
                private Integer qty;
                @SerializedName("UoM")
                @Expose
                private String uoM;
                @SerializedName("Size")
                @Expose
                private String size;
                @SerializedName("SizeType")
                @Expose
                private String sizeType;
                @SerializedName("SizeWidth")
                @Expose
                private String sizeWidth;
                @SerializedName("SizeHeight")
                @Expose
                private String sizeHeight;
                @SerializedName("Price")
                @Expose
                private Double price;
                @SerializedName("MaterialName")
                @Expose
                private String materialName;
                @SerializedName("MaterialImage")
                @Expose
                private String materialImage;
                @SerializedName("MaterialId")
                @Expose
                private Integer materialId;


                protected ProductList(Parcel in) {
                    if (in.readByte() == 0) {
                        orderDetailId = null;
                    } else {
                        orderDetailId = in.readInt();
                    }
                    productName = in.readString();
                    if (in.readByte() == 0) {
                        productState = null;
                    } else {
                        productState = in.readInt();
                    }
                    if (in.readByte() == 0) {
                        qty = null;
                    } else {
                        qty = in.readInt();
                    }
                    uoM = in.readString();
                    size = in.readString();
                    sizeType = in.readString();
                    sizeWidth = in.readString();
                    sizeHeight = in.readString();
                    if (in.readByte() == 0) {
                        price = null;
                    } else {
                        price = in.readDouble();
                    }
                    materialName = in.readString();
                    materialImage = in.readString();
                    if (in.readByte() == 0) {
                        materialId = null;
                    } else {
                        materialId = in.readInt();
                    }
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    if (orderDetailId == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(orderDetailId);
                    }
                    dest.writeString(productName);
                    if (productState == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(productState);
                    }
                    if (qty == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(qty);
                    }
                    dest.writeString(uoM);
                    dest.writeString(size);
                    dest.writeString(sizeType);
                    dest.writeString(sizeWidth);
                    dest.writeString(sizeHeight);
                    if (price == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeDouble(price);
                    }
                    dest.writeString(materialName);
                    dest.writeString(materialImage);
                    if (materialId == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(materialId);
                    }
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
                    @Override
                    public ProductList createFromParcel(Parcel in) {
                        return new ProductList(in);
                    }

                    @Override
                    public ProductList[] newArray(int size) {
                        return new ProductList[size];
                    }
                };

                public Integer getOrderDetailId() {
                    return orderDetailId;
                }

                public void setOrderDetailId(Integer orderDetailId) {
                    this.orderDetailId = orderDetailId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public Integer getProductState() {
                    return productState;
                }

                public void setProductState(Integer productState) {
                    this.productState = productState;
                }

                public Integer getQty() {
                    return qty;
                }

                public void setQty(Integer qty) {
                    this.qty = qty;
                }

                public String getUoM() {
                    return uoM;
                }

                public void setUoM(String uoM) {
                    this.uoM = uoM;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
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

                public Double getPrice() {
                    return price;
                }

                public void setPrice(Double price) {
                    this.price = price;
                }

                public String getMaterialName() {
                    return materialName;
                }

                public void setMaterialName(String materialName) {
                    this.materialName = materialName;
                }

                public String getMaterialImage() {
                    return materialImage;
                }

                public void setMaterialImage(String materialImage) {
                    this.materialImage = materialImage;
                }

                public Integer getMaterialId() {
                    return materialId;
                }

                public void setMaterialId(Integer materialId) {
                    this.materialId = materialId;
                }
            }


            protected OrderList(Parcel in) {
                if (in.readByte() == 0) {
                    orderSummaryId = null;
                } else {
                    orderSummaryId = in.readInt();
                }
                orderNo = in.readString();
                orderTitle = in.readString();
                orderDescription = in.readString();
                orderDate = in.readString();
                fromAddress = in.readString();
                fromLandmark = in.readString();
                if (in.readByte() == 0) {
                    fromArea = null;
                } else {
                    fromArea = in.readInt();
                }
                if (in.readByte() == 0) {
                    fromCityId = null;
                } else {
                    fromCityId = in.readInt();
                }
                if (in.readByte() == 0) {
                    fromStateId = null;
                } else {
                    fromStateId = in.readInt();
                }
                fromLAT = in.readString();
                fromLONG = in.readString();
                toAddress = in.readString();
                toLandmark = in.readString();
                if (in.readByte() == 0) {
                    toArea = null;
                } else {
                    toArea = in.readInt();
                }
                if (in.readByte() == 0) {
                    toCityId = null;
                } else {
                    toCityId = in.readInt();
                }
                if (in.readByte() == 0) {
                    toStateId = null;
                } else {
                    toStateId = in.readInt();
                }
                toLAT = in.readString();
                toLONG = in.readString();
                byte tmpIsShared = in.readByte();
                isShared = tmpIsShared == 0 ? null : tmpIsShared == 1;
                byte tmpIsLoaded = in.readByte();
                isLoaded = tmpIsLoaded == 0 ? null : tmpIsLoaded == 1;
                contactName = in.readString();
                contactNumber = in.readString();
                if (in.readByte() == 0) {
                    proposalCount = null;
                } else {
                    proposalCount = in.readInt();
                }
                if (in.readByte() == 0) {
                    userId = null;
                } else {
                    userId = in.readInt();
                }
                if (in.readByte() == 0) {
                    price = null;
                } else {
                    price = in.readDouble();
                }
                if (in.readByte() == 0) {
                    status = null;
                } else {
                    status = in.readInt();
                }
                statusName = in.readString();
                if (in.readByte() == 0) {
                    createdById = null;
                } else {
                    createdById = in.readInt();
                }
                productList = in.createTypedArrayList(ProductList.CREATOR);
                if (in.readByte() == 0) {
                    vehicleTypeId = null;
                } else {
                    vehicleTypeId = in.readInt();
                }
                vehicleType = in.readString();
                vehicalTypeImage = in.readString();
                if (in.readByte() == 0) {
                    vehicleOrderSummaryId = null;
                } else {
                    vehicleOrderSummaryId = in.readInt();
                }
                vehicleOrderNo = in.readString();
                vehicleOrderTitle = in.readString();
                vehicleOrderDescription = in.readString();
                vehicleOrderDate = in.readString();
                if (in.readByte() == 0) {
                    vehicleId = null;
                } else {
                    vehicleId = in.readInt();
                }
                size = in.readString();
                if (in.readByte() == 0) {
                    qty = null;
                } else {
                    qty = in.readInt();
                }
                uoM = in.readString();
                if (in.readByte() == 0) {
                    productState = null;
                } else {
                    productState = in.readInt();
                }
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                if (orderSummaryId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(orderSummaryId);
                }
                dest.writeString(orderNo);
                dest.writeString(orderTitle);
                dest.writeString(orderDescription);
                dest.writeString(orderDate);
                dest.writeString(fromAddress);
                dest.writeString(fromLandmark);
                if (fromArea == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(fromArea);
                }
                if (fromCityId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(fromCityId);
                }
                if (fromStateId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(fromStateId);
                }
                dest.writeString(fromLAT);
                dest.writeString(fromLONG);
                dest.writeString(toAddress);
                dest.writeString(toLandmark);
                if (toArea == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(toArea);
                }
                if (toCityId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(toCityId);
                }
                if (toStateId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(toStateId);
                }
                dest.writeString(toLAT);
                dest.writeString(toLONG);
                dest.writeByte((byte) (isShared == null ? 0 : isShared ? 1 : 2));
                dest.writeByte((byte) (isLoaded == null ? 0 : isLoaded ? 1 : 2));
                dest.writeString(contactName);
                dest.writeString(contactNumber);
                if (proposalCount == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(proposalCount);
                }
                if (userId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(userId);
                }
                if (price == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeDouble(price);
                }
                if (status == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(status);
                }
                dest.writeString(statusName);
                if (createdById == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(createdById);
                }
                dest.writeTypedList(productList);
                if (vehicleTypeId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(vehicleTypeId);
                }
                dest.writeString(vehicleType);
                dest.writeString(vehicalTypeImage);
                if (vehicleOrderSummaryId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(vehicleOrderSummaryId);
                }
                dest.writeString(vehicleOrderNo);
                dest.writeString(vehicleOrderTitle);
                dest.writeString(vehicleOrderDescription);
                dest.writeString(vehicleOrderDate);
                if (vehicleId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(vehicleId);
                }
                dest.writeString(size);
                if (qty == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(qty);
                }
                dest.writeString(uoM);
                if (productState == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(productState);
                }
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<OrderList> CREATOR = new Creator<OrderList>() {
                @Override
                public OrderList createFromParcel(Parcel in) {
                    return new OrderList(in);
                }

                @Override
                public OrderList[] newArray(int size) {
                    return new OrderList[size];
                }
            };

            public Integer getOrderSummaryId() {
                return orderSummaryId;
            }

            public void setOrderSummaryId(Integer orderSummaryId) {
                this.orderSummaryId = orderSummaryId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getOrderTitle() {
                return orderTitle;
            }

            public void setOrderTitle(String orderTitle) {
                this.orderTitle = orderTitle;
            }

            public String getOrderDescription() {
                return orderDescription;
            }

            public void setOrderDescription(String orderDescription) {
                this.orderDescription = orderDescription;
            }

            public String getOrderDate() {
                return orderDate;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
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

            public Integer getFromArea() {
                return fromArea;
            }

            public void setFromArea(Integer fromArea) {
                this.fromArea = fromArea;
            }

            public Integer getFromCityId() {
                return fromCityId;
            }

            public void setFromCityId(Integer fromCityId) {
                this.fromCityId = fromCityId;
            }

            public Integer getFromStateId() {
                return fromStateId;
            }

            public void setFromStateId(Integer fromStateId) {
                this.fromStateId = fromStateId;
            }

            public String getFromLAT() {
                return fromLAT;
            }

            public void setFromLAT(String fromLAT) {
                this.fromLAT = fromLAT;
            }

            public String getFromLONG() {
                return fromLONG;
            }

            public void setFromLONG(String fromLONG) {
                this.fromLONG = fromLONG;
            }

            public String getToAddress() {
                return toAddress;
            }

            public void setToAddress(String toAddress) {
                this.toAddress = toAddress;
            }

            public String getToLandmark() {
                return toLandmark;
            }

            public void setToLandmark(String toLandmark) {
                this.toLandmark = toLandmark;
            }

            public Integer getToArea() {
                return toArea;
            }

            public void setToArea(Integer toArea) {
                this.toArea = toArea;
            }

            public Integer getToCityId() {
                return toCityId;
            }

            public void setToCityId(Integer toCityId) {
                this.toCityId = toCityId;
            }

            public Integer getToStateId() {
                return toStateId;
            }

            public void setToStateId(Integer toStateId) {
                this.toStateId = toStateId;
            }

            public String getToLAT() {
                return toLAT;
            }

            public void setToLAT(String toLAT) {
                this.toLAT = toLAT;
            }

            public String getToLONG() {
                return toLONG;
            }

            public void setToLONG(String toLONG) {
                this.toLONG = toLONG;
            }

            public Boolean getShared() {
                return isShared;
            }

            public void setShared(Boolean shared) {
                isShared = shared;
            }

            public Boolean getLoaded() {
                return isLoaded;
            }

            public void setLoaded(Boolean loaded) {
                isLoaded = loaded;
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

            public Integer getProposalCount() {
                return proposalCount == null ? 0 : proposalCount;
            }

            public void setProposalCount(Integer proposalCount) {
                this.proposalCount = proposalCount;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public Integer getCreatedById() {
                return createdById;
            }

            public void setCreatedById(Integer createdById) {
                this.createdById = createdById;
            }

            public List<ProductList> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductList> productList) {
                this.productList = productList;
            }

            public Integer getVehicleTypeId() {
                return vehicleTypeId;
            }

            public void setVehicleTypeId(Integer vehicleTypeId) {
                this.vehicleTypeId = vehicleTypeId;
            }

            public String getVehicleType() {
                return vehicleType == null ? "null" : vehicleType;
            }

            public void setVehicleType(String vehicleType) {
                this.vehicleType = vehicleType;
            }

            public String getVehicalTypeImage() {
                return vehicalTypeImage;
            }

            public void setVehicalTypeImage(String vehicalTypeImage) {
                this.vehicalTypeImage = vehicalTypeImage;
            }

            public Integer getVehicleOrderSummaryId() {
                return vehicleOrderSummaryId;
            }

            public void setVehicleOrderSummaryId(Integer vehicleOrderSummaryId) {
                this.vehicleOrderSummaryId = vehicleOrderSummaryId;
            }

            public String getVehicleOrderNo() {
                return vehicleOrderNo;
            }

            public void setVehicleOrderNo(String vehicleOrderNo) {
                this.vehicleOrderNo = vehicleOrderNo;
            }

            public String getVehicleOrderTitle() {
                return vehicleOrderTitle;
            }

            public void setVehicleOrderTitle(String vehicleOrderTitle) {
                this.vehicleOrderTitle = vehicleOrderTitle;
            }

            public String getVehicleOrderDescription() {
                return vehicleOrderDescription;
            }

            public void setVehicleOrderDescription(String vehicleOrderDescription) {
                this.vehicleOrderDescription = vehicleOrderDescription;
            }

            public String getVehicleOrderDate() {
                return vehicleOrderDate;
            }

            public void setVehicleOrderDate(String vehicleOrderDate) {
                this.vehicleOrderDate = vehicleOrderDate;
            }

            public Integer getVehicleId() {
                return vehicleId;
            }

            public void setVehicleId(Integer vehicleId) {
                this.vehicleId = vehicleId;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public Integer getQty() {
                return qty;
            }

            public void setQty(Integer qty) {
                this.qty = qty;
            }

            public String getUoM() {
                return uoM;
            }

            public void setUoM(String uoM) {
                this.uoM = uoM;
            }

            public Integer getProductState() {
                return productState;
            }

            public void setProductState(Integer productState) {
                this.productState = productState;
            }

            @Override
            public String toString() {
                return "OrderList{" +
                        "orderSummaryId=" + orderSummaryId +
                        ", orderNo='" + orderNo + '\'' +
                        ", orderTitle='" + orderTitle + '\'' +
                        ", orderDescription='" + orderDescription + '\'' +
                        ", orderDate='" + orderDate + '\'' +
                        ", fromAddress='" + fromAddress + '\'' +
                        ", fromLandmark='" + fromLandmark + '\'' +
                        ", fromArea=" + fromArea +
                        ", fromCityId=" + fromCityId +
                        ", fromStateId=" + fromStateId +
                        ", fromLAT='" + fromLAT + '\'' +
                        ", fromLONG='" + fromLONG + '\'' +
                        ", toAddress='" + toAddress + '\'' +
                        ", toLandmark='" + toLandmark + '\'' +
                        ", toArea=" + toArea +
                        ", toCityId=" + toCityId +
                        ", toStateId=" + toStateId +
                        ", toLAT='" + toLAT + '\'' +
                        ", toLONG='" + toLONG + '\'' +
                        ", isShared=" + isShared +
                        ", isLoaded=" + isLoaded +
                        ", contactName='" + contactName + '\'' +
                        ", contactNumber='" + contactNumber + '\'' +
                        ", proposalCount=" + proposalCount +
                        ", userId=" + userId +
                        ", price=" + price +
                        ", status=" + status +
                        ", statusName='" + statusName + '\'' +
                        ", createdById=" + createdById +
                        ", productList=" + productList +
                        ", vehicleTypeId=" + vehicleTypeId +
                        ", vehicleType='" + vehicleType + '\'' +
                        ", vehicalTypeImage='" + vehicalTypeImage + '\'' +
                        ", vehicleOrderSummaryId=" + vehicleOrderSummaryId +
                        ", vehicleOrderNo='" + vehicleOrderNo + '\'' +
                        ", vehicleOrderTitle='" + vehicleOrderTitle + '\'' +
                        ", vehicleOrderDescription='" + vehicleOrderDescription + '\'' +
                        ", vehicleOrderDate='" + vehicleOrderDate + '\'' +
                        ", vehicleId=" + vehicleId +
                        ", size='" + size + '\'' +
                        ", qty=" + qty +
                        ", uoM='" + uoM + '\'' +
                        ", productState=" + productState +
                        '}';
            }
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<OrderList> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderList> orderList) {
            this.orderList = orderList;
        }

        @Override
        public String toString() {
            return "AllOrder{" +
                    "date='" + date + '\'' +
                    ", orderList=" + orderList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Orders{" +
                "allOrder=" + allOrder +
                ", allOrders=" + allOrders +
                ", totalDataCount=" + totalDataCount +
                ", status=" + status +
                '}';
    }
}
