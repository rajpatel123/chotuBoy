package com.chotuboy.retrofit;

import com.chotuboy.modelClass.forOutLet.GettingOrderResponseForOutLet;
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
    Call<GettingOrderResponseForOutLet> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL (@Part("outlet_id") RequestBody outlet_id);


}
