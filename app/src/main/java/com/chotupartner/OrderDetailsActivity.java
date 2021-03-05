package com.chotupartner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chotupartner.adapter.OrderDetailsAdapter;
import com.chotupartner.databinding.OrderDetailsBinding;
import com.chotupartner.modelClass.forOutLet.GetorderdetailbyidResp;
import com.chotupartner.modelClass.orderDetailsModel.OrderProduct;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Constants;
import com.chotupartner.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {


    OrderDetailsBinding mBinding;
    int orderID;
String selectedOrderStatus;
    private String comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.order_details);

        Intent intent = getIntent();

        orderID = intent.getIntExtra("orderID",0);
        mBinding.layoutTop.txt.setText("Orders Details");

        mBinding.orderStatusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments = mBinding.commentEdt.getText().toString();
                updateOrderstatus();
            }
        });
       mBinding.orderStatusSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              final String[] values = getResources().getStringArray(R.array.order_statusfor_outlet);
              selectedOrderStatus= values[position];
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
        mBinding.layoutTop.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(OrderDetailsActivity.this, "outletData"))) {
            get_order_list();
        }else{
            get_order_listDelevery();

        }
    }

    private void updateOrderstatus() {

        if (TextUtils.isEmpty(selectedOrderStatus)){
            Toast.makeText(OrderDetailsActivity.this, "Please select order status", Toast.LENGTH_SHORT).show();
            return;
        }



        if (!TextUtils.isEmpty(selectedOrderStatus) && selectedOrderStatus.equalsIgnoreCase("Select Order Status")){
            Toast.makeText(OrderDetailsActivity.this, "Please select order status", Toast.LENGTH_SHORT).show();
            return;
        }


   if (TextUtils.isEmpty(comments)){
            Toast.makeText(OrderDetailsActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
            return;
        }


      if (Utils.isInternetConnected(OrderDetailsActivity.this)){
          Utils.showProgressDialog(OrderDetailsActivity.this);
          RequestBody status = RequestBody.create(MediaType.parse("text/plain"),selectedOrderStatus);
          RequestBody orderID1 = RequestBody.create(MediaType.parse("text/plain"), ""+orderID);
          RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), ""+comments);


          RestClient.updateOrderStatus(orderID1, status, comment, new Callback<ResponseBody>() {
              @Override
              public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();
                  if (response.code()==200){
                      Toast.makeText(OrderDetailsActivity.this, "Order updated", Toast.LENGTH_LONG).show();
                  }
              }

              @Override
              public void onFailure(Call<ResponseBody> call, Throwable t) {
                  Utils.dismissProgressDialog();
              }
          });
      }










    }


    ArrayList<OrderProduct> orderInfos = new ArrayList<>();

    private void get_order_list() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(this,"outlet_id"));
        RequestBody orderID1 = RequestBody.create(MediaType.parse("text/plain"), ""+orderID);

        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(homeActivity);
            RestClient.GetorderdetailbyidResp(customeridRequest, orderID1, new Callback<GetorderdetailbyidResp>() {
                @Override
                public void onResponse(Call<GetorderdetailbyidResp> call, Response<GetorderdetailbyidResp> response) {
                    Utils.dismissProgressDialog();
                    try {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            orderInfos.clear();
                                GetorderdetailbyidResp getorderdetailbyidResp = response.body();


                                mBinding.recyclerCartView.setVisibility(View.VISIBLE);
                                orderInfos.addAll(getorderdetailbyidResp.getOrderInfo().getOrderProduct());
                                mBinding.recyclerCartView.setVisibility(View.VISIBLE);
                                OrderDetailsAdapter horizontalAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, orderInfos);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartView.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartView.setAdapter(horizontalAdapter);

                                mBinding.tvCustName.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getFirstname() + " " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getLastname());
                                mBinding.tvCustMobile.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getPhone());
                                mBinding.tvPaidOnline.setText("Payment Status " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getPaymentType());
                                mBinding.outLetName.setText("Delivery By " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getPaymentType());




                                //  mBinding.tvCustName.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus());


//                                Calendar cl = Calendar.getInstance();
//                                cl.setTimeInMillis(Long.parseLong(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getSlotBook())*1000);  //here your time in miliseconds
//                                String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);
//                                String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);


                                mBinding.Time.setText("Placed on " + Utils.startTimeFormat(Long.parseLong(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getSlotBook())*1000));
                                mBinding.tvAddress.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getAddress());

                                mBinding.lltop.setVisibility(View.VISIBLE);

                                int tamount = (int) Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getAmount());
                                int tDiscount = (int) Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDiscount());
                                mBinding.tvPrice.setText("Rs " +tamount);
                                mBinding.tvDiscount.setText("Rs " +tDiscount);

                                mBinding.tvDeliveryChar.setText("Rs " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDeliveryCharge());

                                int total = (int) Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getAmount());
                                mBinding.tvFinalPaidAmount.setText("Rs " + total);
                                if (Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDiscount())>0){
                                    mBinding.totalSavingAmount.setText("Rs " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDiscount());
                                    mBinding.savingRl.setVisibility(View.VISIBLE);
                                }


                                if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Pending")) {

                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);

                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv4);
                                } else if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Processing")) {
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);
                                    mBinding.iv1Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv4);
                                } else if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Processed")) {

                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);
                                    mBinding.iv1Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    mBinding.iv2Line.setBackgroundColor(getResources().getColor(R.color.green_color));


                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv4);
                                } else if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Complete")) {
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv4);
                                    mBinding.iv1Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    mBinding.iv2Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    mBinding.iv3Line.setBackgroundColor(getResources().getColor(R.color.green_color));

                                } else {
                                    mBinding.llOutLetText.setVisibility(View.GONE);

                                    mBinding.llImage.setVisibility(View.GONE);
                                    mBinding.tvDeliverd.setVisibility(View.VISIBLE);
                                }



                        } else {
                            mBinding.recyclerCartView.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetorderdetailbyidResp> call, Throwable t) {
                    mBinding.recyclerCartView.setVisibility(View.GONE);

                    Log.d("tag", "onFailure: ");
                    //   Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Utils.dismissProgressDialog();
            Toast.makeText(OrderDetailsActivity.this, "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }
    private void get_order_listDelevery() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(this,"delivery_id"));
        RequestBody orderID1 = RequestBody.create(MediaType.parse("text/plain"), ""+orderID);

        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(homeActivity);
            RestClient.getorderdetailbyidRespDelivery(customeridRequest, orderID1, new Callback<GetorderdetailbyidResp>() {
                @Override
                public void onResponse(Call<GetorderdetailbyidResp> call, Response<GetorderdetailbyidResp> response) {
                    Utils.dismissProgressDialog();
                    try {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            orderInfos.clear();
                                GetorderdetailbyidResp getorderdetailbyidResp = response.body();


                                mBinding.recyclerCartView.setVisibility(View.VISIBLE);
                                orderInfos.addAll(getorderdetailbyidResp.getOrderInfo().getOrderProduct());
                                mBinding.recyclerCartView.setVisibility(View.VISIBLE);
                                OrderDetailsAdapter horizontalAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, orderInfos);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartView.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartView.setAdapter(horizontalAdapter);

                                mBinding.tvCustName.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getFirstname() + " " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getLastname());
                                mBinding.tvCustMobile.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getPhone());
                                mBinding.tvPaidOnline.setText("Payment Status " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getPaymentType());
                                mBinding.outLetName.setText("Delivery By " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getPaymentType());




                                //  mBinding.tvCustName.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus());


//                                Calendar cl = Calendar.getInstance();
//                                cl.setTimeInMillis(Long.parseLong(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getSlotBook())*1000);  //here your time in miliseconds
//                                String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);
//                                String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);


                                mBinding.Time.setText("Placed on " + Utils.startTimeFormat(Long.parseLong(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getSlotBook())*1000));
                                mBinding.tvAddress.setText(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getAddress());

                                mBinding.lltop.setVisibility(View.VISIBLE);

                                int tamount = (int) Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getAmount());
                                int tDiscount = (int) Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDiscount());
                                mBinding.tvPrice.setText("Rs " +tamount);
                                mBinding.tvDiscount.setText("Rs " +tDiscount);

                                mBinding.tvDeliveryChar.setText("Rs " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDeliveryCharge());

                                int total = (int) Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getAmount());
                                mBinding.tvFinalPaidAmount.setText("Rs " + total);
                                if (Float.parseFloat(getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDiscount())>0){
                                    mBinding.totalSavingAmount.setText("Rs " + getorderdetailbyidResp.getOrderInfo().getOrderInfo().getDiscount());
                                    mBinding.savingRl.setVisibility(View.VISIBLE);
                                }


                                if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Pending")) {

                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);

                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv4);
                                } else if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Processing")) {
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);
                                    mBinding.iv1Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv4);
                                } else if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Processed")) {

                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);
                                    mBinding.iv1Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    mBinding.iv2Line.setBackgroundColor(getResources().getColor(R.color.green_color));


                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.ring).into(mBinding.iv4);
                                } else if (getorderdetailbyidResp.getOrderInfo().getOrderInfo().getOrderStatus().equalsIgnoreCase("Complete")) {
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv1);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv2);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv3);
                                    Picasso.get().load(R.drawable.circle).into(mBinding.iv4);
                                    mBinding.iv1Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    mBinding.iv2Line.setBackgroundColor(getResources().getColor(R.color.green_color));
                                    mBinding.iv3Line.setBackgroundColor(getResources().getColor(R.color.green_color));

                                } else {
                                    mBinding.llOutLetText.setVisibility(View.GONE);

                                    mBinding.llImage.setVisibility(View.GONE);
                                    mBinding.tvDeliverd.setVisibility(View.VISIBLE);
                                }



                        } else {
                            mBinding.recyclerCartView.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetorderdetailbyidResp> call, Throwable t) {
                    mBinding.recyclerCartView.setVisibility(View.GONE);

                    Log.d("tag", "onFailure: ");
                    //   Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Utils.dismissProgressDialog();
            Toast.makeText(OrderDetailsActivity.this, "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }


}

