package com.chotuboy.retrofit;

import com.chotuboy.modelClass.getDeliveryBoyInfo.GetDeliveryResp;
import com.chotuboy.modelClass.getOutLetInfo.GetOutLetInfoResp;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.login.LoginWithOtpResponse;
import com.chotuboy.modelClass.otpVerify.VerifyOtpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("http://chotu.thehighways.in/ApiData/SendOtp")
    Call<LoginWithOtpResponse> loginWithOtp(@Part("phone") RequestBody phone);

    @Multipart
    @POST("http://chotu.thehighways.in/ApiData/VerifyOtp")
    Call<VerifyOtpResponse> verifyOtp(@Part("phone") RequestBody phone,
                                      @Part("otp") RequestBody otp);

    @Multipart
    @POST("http://chotu.thehighways.in/ApiData/getAllOrder")
    Call<OrderDetailsResponse> orderDetailsForOutLet (@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("http://chotu.thehighways.in/ApiData/getAllOrder")
    Call<OrderDetailsResponse> orderDetailsForDelivery  (@Part("delivery_id") RequestBody delivery_id);

    @Multipart
    @POST("http://chotu.thehighways.in/ApiData/getDelivery")
    Call<GetDeliveryResp>GET_DELIVERY_RESP_CALL  (@Part("delivery_id") RequestBody delivery_id);

    @Multipart
    @POST("http://chotu.thehighways.in/ApiData/getOutlet")
    Call<GetOutLetInfoResp>GET_OUT_LET_INFO_RESP_CALL  (@Part("outlet_id") RequestBody outlet_id);



}
