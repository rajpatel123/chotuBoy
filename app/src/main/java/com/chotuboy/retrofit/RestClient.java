package com.chotuboy.retrofit;

import com.chotuboy.modelClass.forOutLet.GetorderdetailbyidResp;
import com.chotuboy.modelClass.forOutLet.GettingOrderResponseForOutLet;
import com.chotuboy.modelClass.forOutLet.OutletLoginModel;
import com.chotuboy.modelClass.login.LoginWithOtpResponse;
import com.chotuboy.modelClass.orderlist.OrderListResponse;
import com.chotuboy.modelClass.otpVerify.VerifyOtpResponse;

import okhttp3.RequestBody;
import retrofit2.Callback;

public class RestClient {

  public static void logInWithOtpNewUser(RequestBody phone,RequestBody loginAs, Callback<LoginWithOtpResponse> callback) {
        RetrofitClient.getClient().loginWithOtp(phone,loginAs).enqueue(callback);
    }

      public static void verifyOtpOutLet(RequestBody phone, RequestBody otp, RequestBody userType, Callback<OutletLoginModel> callback) {
        RetrofitClient.getClient().verifyOtpOutLet(phone, otp,userType).enqueue(callback);
    }

  public static void getAllOrederForOutLet(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL(outlet_id).enqueue(callback);
  }



  public static void GetorderdetailbyidResp(RequestBody customer_id,RequestBody order_id, Callback<GetorderdetailbyidResp> callback) {
    RetrofitClient.getClient().GetorderdetailbyidResp(customer_id,order_id).enqueue(callback);
  }


}



