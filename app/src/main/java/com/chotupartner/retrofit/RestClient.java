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
    RetrofitClient.getClient().getPendingOrders(outlet_id).enqueue(callback);
  }


  public static void getConfirmedOrders(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().getConfirmedOrders(outlet_id).enqueue(callback);
  }


  public static void getDeliveryNewOrders(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().getDeliveryNewOrders(outlet_id).enqueue(callback);
  }


  public static void getDeliveryOngoingOrders(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().getDeliveryOngoingOrders(outlet_id).enqueue(callback);
  }

  public static void getAllOrderDelivery(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().getAllOrderDelivery(outlet_id).enqueue(callback);
  }

  public static void getAllOrderOutlet(RequestBody outlet_id, Callback<OrderListResponse> callback) {
    RetrofitClient.getClient().getAllOrderOutlet(outlet_id).enqueue(callback);
  }



  public static void update_order(RequestBody orderId,RequestBody order_status,RequestBody otp,RequestBody comment, Callback<ResponseBody> callback) {
    RetrofitClient.getClient().update_order(orderId,order_status,otp,comment).enqueue(callback);
  }


  public static void updateOrderStatus(RequestBody outlet_id,RequestBody orderId,RequestBody order_status, Callback<ResponseBody> callback) {
    RetrofitClient.getClient().updateOrderStatus(outlet_id,orderId,order_status).enqueue(callback);
  }



  public static void GetorderdetailbyidResp(RequestBody customer_id,RequestBody order_id, Callback<GetorderdetailbyidResp> callback) {
    RetrofitClient.getClient().GetorderdetailbyidResp(customer_id,order_id).enqueue(callback);
  }



  public static void getorderdetailbyidRespDelivery(RequestBody customer_id,RequestBody order_id, Callback<GetorderdetailbyidResp> callback) {
    RetrofitClient.getClient().getorderdetailbyidRespDelivery(customer_id,order_id).enqueue(callback);
  }


}



