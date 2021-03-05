package com.chotupartner.retrofit;

import com.chotupartner.modelClass.delivery.DeliveryPartnerResponse;
import com.chotupartner.modelClass.forOutLet.GetorderdetailbyidResp;
import com.chotupartner.modelClass.forOutLet.OutletLoginModel;
import com.chotupartner.modelClass.login.LoginWithOtpResponse;
import com.chotupartner.modelClass.orderlist.OrderListResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("ApiData/SendOtp")
    Call<LoginWithOtpResponse> loginWithOtp(@Part("phone") RequestBody phone, @Part("user_type") RequestBody user_type);

    @Multipart
    @POST("ApiData/VerifyOtp")
    Call<OutletLoginModel> verifyOtpOutLet(@Part("phone") RequestBody phone,
                                           @Part("otp") RequestBody otp,
                                           @Part("user_type") RequestBody user_type
    );


    @Multipart
    @POST("ApiData/VerifyOtp")
    Call<DeliveryPartnerResponse> verifyOtpDelivery(@Part("phone") RequestBody phone,
                                                  @Part("otp") RequestBody otp,
                                                  @Part("user_type") RequestBody user_type
    );


    @Multipart
    @POST("ApiData/getAllOrder")
    Call<OrderListResponse> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL(@Part("outlet_id") RequestBody outlet_id);

    @Multipart
    @POST("ApiData/getAllOrder")
    Call<OrderListResponse> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL_Delevery(@Part("delivery_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/get_order_outlet_detail_by_id")
    Call<GetorderdetailbyidResp> GetorderdetailbyidResp(@Part("outlet_id") RequestBody customer_id, @Part("order_id") RequestBody order_id);


    @Multipart
    @POST("ApiData/get_order_delivery_detail_by_id")
    Call<GetorderdetailbyidResp> getorderdetailbyidRespDelivery(@Part("delivery_id") RequestBody customer_id, @Part("order_id") RequestBody order_id);


    @Multipart
    @POST("ApiData/update_order")
    Call<ResponseBody> updateOrderStatus(@Part("order_id") RequestBody customer_id, @Part("order_status") RequestBody order_status, @Part("comment") RequestBody comment,@Part("otp") RequestBody otp);


//    @Multipart
//    @POST("ApiData/getAllOrder")
//    Call<GettingOrderResponseForOutLet> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL (@Part("outlet_id") RequestBody outlet_id);


}
