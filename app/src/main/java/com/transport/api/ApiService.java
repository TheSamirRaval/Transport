package com.transport.api;


import com.transport.api.model.AddUpdateOrder;
import com.transport.api.model.Cities;
import com.transport.api.model.OrderProposal;
import com.transport.api.model.Orders;
import com.transport.api.model.OwnerVehicle;
import com.transport.api.model.RowMaterials;
import com.transport.api.model.States;
import com.transport.api.model.Status;
import com.transport.api.model.StatusObjectHolder;
import com.transport.api.model.User;
import com.transport.api.model.Vehicles;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SAM on 8/3/20.
 */
public interface ApiService {

    @POST("User/Register")
    Observable<User> register(@Body Map<String, Object> map);

    @POST("User/Login")
    Observable<User> login(@Body Map<String, Object> map);

    @POST("User/Logout/{id}")
    Observable<StatusObjectHolder> logout(@Header("AuthToken") String authorization, @Path("id") String id);

    @POST("State/GetState")
    Observable<States> getState(@Body Map<String, Object> map);

    @POST("City/GetCity")
    Observable<Cities> getCity(@Body Map<String, Object> map);

    @GET("Order/GetProductMaterial")
    Observable<RowMaterials> getProductMaterial(@HeaderMap Map<String, Object> headers);

    @GET("Vehicle/GetVehicleType")
    Observable<Vehicles> getVehicleType(@HeaderMap Map<String, Object> headers);

    @POST("Order/AddUpdateOrder")
    Observable<Status> addUpdateOrder(@HeaderMap Map<String, Object> headers, @Body Map<String, Object> map);

    @POST("Order/AddUpdateOrder")
    Observable<StatusObjectHolder> addUpdateOrder(@Header("AuthToken") String authorization, @Body AddUpdateOrder addUpdateOrder);

    @POST("VehicleOrder/AddUpdateVehicleOrder")
    Observable<StatusObjectHolder> addUpdateVehicleOrder(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("VehicleOrder/GetVehicleOrderByUserId")
    Observable<Orders> getVehicleOrderByUserId(@Header("AuthToken") String authorization, @Body Map<String, Object> map);


    @POST("Order/GetOrderByUserId")
    Observable<Orders> getOrderByUserId(@Header("AuthToken") String authorization, @Body Map<String, Object> map);


    @POST("Order/GetOrder")
    Observable<Orders> getOrder(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("Order/GetFeedForVehicle")
    Observable<Orders> getFeedForVehicle(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("VehicleOrder/GetFeedForUser")
    Observable<Orders> getFeedForUser(@Header("AuthToken") String authorization, @Body Map<String, Object> map);


    @POST("Vehicle/GetVehicleByUserId")
    Observable<OwnerVehicle> getVehicleByUserId(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("VehicleProposal/AddVehicleProposal")
    Observable<StatusObjectHolder> addVehicleProposal(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("VehicleProposal/GetVehicleProposalByUserId")
    Observable<String> getVehicleProposalByUserId(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("VehicleProposal/GetVehicleProposalByOrderId")
    Observable<OrderProposal> getVehicleProposalByOrderId(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

    @POST("Order/ConfirmProposalInOrder")
    Observable<StatusObjectHolder> confirmProposalInOrder(@Header("AuthToken") String authorization, @Body Map<String, Object> map);

}
