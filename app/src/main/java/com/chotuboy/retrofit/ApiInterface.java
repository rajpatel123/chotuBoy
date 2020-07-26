package com.chotuboy.retrofit;

import com.chotuboy.modelClass.forOutLet.GetorderdetailbyidResp;
import com.chotuboy.modelClass.forOutLet.GettingOrderResponseForOutLet;
import com.chotuboy.modelClass.forOutLet.OutletLoginModel;
import com.chotuboy.modelClass.login.LoginWithOtpResponse;
import com.chotuboy.modelClass.orderlist.OrderListResponse;
import com.chotuboy.modelClass.otpVerify.VerifyOtpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("ApiData/SendOtp")
    Call<LoginWithOtpResponse> loginWithOtp(@Part("phone") RequestBody phone,@Part("user_type") RequestBody user_type);

    @Multipart
    @POST("ApiData/VerifyOtp")
    Call<OutletLoginModel> verifyOtpOutLet(@Part("phone") RequestBody phone,
                                     @Part("otp") RequestBody otp,
                                     @Part("user_type") RequestBody user_type
    );


    @Multipart
    @POST("ApiData/VerifyOtp")
    Call<OutletLoginModel> verifyOtpDelivery(@Part("phone") RequestBody phone,
                                     @Part("otp") RequestBody otp,
                                     @Part("user_type") RequestBody user_type
    );

    @Multipart
    @POST("ApiData/getAllOrder")
    Call<OrderListResponse> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL (@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/get_order_detail_by_id")
    Call<GetorderdetailbyidResp> GetorderdetailbyidResp(@Part("customer_id") RequestBody customer_id, @Part("order_id") RequestBody order_id);



//    @Multipart
//    @POST("ApiData/getAllOrder")
//    Call<GettingOrderResponseForOutLet> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL (@Part("outlet_id") RequestBody outlet_id);


}
