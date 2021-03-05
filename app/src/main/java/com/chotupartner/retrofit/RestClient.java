package com.chotupartner.retrofit;

import com.chotupartner.modelClass.delivery.DeliveryPartnerResponse;
import com.chotupartner.modelClass.forOutLet.GetorderdetailbyidResp;
import com.chotupartner.modelClass.forOutLet.OutletLoginModel;
import com.chotupartner.modelClass.login.LoginWithOtpResponse;
import com.chotupartner.modelClass.orderlist.OrderListResponse;
import com.chotupartner.modelClass.otpVerify.VerifyOtpResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class RestClient {

  public static void logInWithOtpNewUser(RequestBody phone,RequestBody loginAs, Callback<LoginWithOtpResponse> callback) {
        RetrofitClient.getClient().loginWithOtp(phone,loginAs).enqueue(callback);
    }

      public static void verifyOtpOutLet(RequestBody phone, RequestBody otp, RequestBody userType, Callback<OutletLoginModel> callback) {
        RetrofitClient.getClient().verifyOtpOutLet(phone, otp,userType).enqueue(callback);
    }



    public static void verifyOtpDelivery(RequestBody phone, RequestBody otp, RequestBody userType, Callback<DeliveryPartnerResponse> callback) {
        RetrofitClient.getClient().verifyOtpDelivery(phone, otp,userType).enqueue(callback);
    }

  public static void getAllOrederForOutLet(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL(outlet_id).enqueue(callback);
  }


  public static void getAllOrederForOutLetDelivery(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL_Delevery(outlet_id).enqueue(callback);
  }



  public static void updateOrderStatus(RequestBody orderId,RequestBody order_status, RequestBody comment, Callback<ResponseBody> callback) {
    RetrofitClient.getClient().updateOrderStatus(orderId,order_status,comment).enqueue(callback);
  }



  public static void GetorderdetailbyidResp(RequestBody customer_id,RequestBody order_id, Callback<GetorderdetailbyidResp> callback) {
    RetrofitClient.getClient().GetorderdetailbyidResp(customer_id,order_id).enqueue(callback);
  }



  public static void getorderdetailbyidRespDelivery(RequestBody customer_id,RequestBody order_id, Callback<GetorderdetailbyidResp> callback) {
    RetrofitClient.getClient().getorderdetailbyidRespDelivery(customer_id,order_id).enqueue(callback);
  }


}



