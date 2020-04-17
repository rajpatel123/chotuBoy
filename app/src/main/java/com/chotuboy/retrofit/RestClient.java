package com.chotuboy.retrofit;

import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.login.LoginWithOtpResponse;
import com.chotuboy.modelClass.otpVerify.VerifyOtpResponse;

import okhttp3.RequestBody;
import retrofit2.Callback;

public class RestClient {

    public static void logInWithOtpNewUser(RequestBody phone, Callback<LoginWithOtpResponse> callback) {
        RetrofitClient.getClient().loginWithOtp(phone).enqueue(callback);
    }

    public static void verifyOtpOtpNewUser(RequestBody phone, RequestBody otp, Callback<VerifyOtpResponse> callback) {
        RetrofitClient.getClient().verifyOtp(phone, otp).enqueue(callback);
    }

    public static void getAllOrederForOutLet(RequestBody outlet_id, Callback<OrderDetailsResponse> callback) {
        RetrofitClient.getClient().orderDetailsForOutLet(outlet_id).enqueue(callback);
    }

    public static void getAllOrederForDelivery(RequestBody delivery_id, Callback<OrderDetailsResponse> callback) {
        RetrofitClient.getClient().orderDetailsForDelivery(delivery_id).enqueue(callback);
    }


}



