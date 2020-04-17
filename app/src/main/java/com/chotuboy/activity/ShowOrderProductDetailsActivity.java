package com.chotuboy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.adapter.ShowOrderProductDetailsAdapter;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.orderDetailsModel.OrderProduct;
import com.chotuboy.retrofit.RestClient;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.chotuboy.utils.Constants;
import com.chotuboy.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowOrderProductDetailsActivity extends AppCompatActivity {

    private RecyclerView orderDtlsRecyc;
    private Spinner spinner;
    ShowOrderProductDetailsAdapter showOrderProductDetailsAdapter;
    private ImageView back;
    List<OrderProduct> orderProductList = new ArrayList<>();
    private OrderDetailsResponse orderDetailsResponse;
    private TextView stateTv, orderedIdTv;
    private String userType,mobileNo,selectedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderDtlsRecyc = findViewById(R.id.OrderDetailsRecyc);
        stateTv = findViewById(R.id.StateTv);
        orderedIdTv =findViewById(R.id.OrderedIdTv);
        back = findViewById(R.id.back);

        userType = ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE);
        mobileNo = ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE);
        selectedId = ChotuBoyPrefs.getString(getApplicationContext(), Constants.UserTypeSelectedID);

        getOrdrProductdetails();  //  goToOrderDetailsActivity, sending  data previously t

        showRecycler();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getOrdrProductdetails() {
        Bundle bundle = getIntent().getExtras();
        String OrderId = bundle.getString("OrderId");
        String CustomerId = bundle.getString("CustomerId");
        String ShippingId = bundle.getString("ShippingId");
        String OutletId = bundle.getString("OutletId");
        String DeliveryId = bundle.getString("DeliveryId");
        String RazorpayOrderId = bundle.getString("RazorpayOrderId");
        String Amount = bundle.getString("Amount");
        String Discount = bundle.getString("Discount");
        String PaymentStatus = bundle.getString("PaymentStatus");
        String OrderStatus = bundle.getString("OrderStatus");
        String Firstname = bundle.getString("Firstname");
        String Lastname = bundle.getString("Lastname");
        String Email = bundle.getString("Email");
        String Lhone = bundle.getString("Lhone");
        String Address = bundle.getString("Address");
        String City = bundle.getString("City");
        String Postcode = bundle.getString("Postcode");
        String StateId = bundle.getString("StateId");
        String DeliveryCharge = bundle.getString("DeliveryCharge");
        String PaymentType = bundle.getString("PaymentType");

        String DateAdded = bundle.getString("DateAdded");
        String OrderProductId = bundle.getString("OrderProductId");
        String ProductId = bundle.getString("ProductId");
        String PriceId = bundle.getString("PriceId");
        String Quantity = bundle.getString("Quantity");

        orderedIdTv.setText(OrderId);
        stateTv.setText(StateId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetails();
    }

    private void getOrderDetails() {

        RequestBody userType = RequestBody.create(MediaType.parse("text/plain"),
                ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE));
        RequestBody outlet_id = RequestBody.create(MediaType.parse("text/plain"),
                ChotuBoyPrefs.getString(getApplicationContext(), Constants.UserTypeSelectedID));

        Utils.showProgressDialog(getApplicationContext());
        RestClient.getAllOrederForOutLet(outlet_id, new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus() == true) {

                        orderDetailsResponse = response.body();
                        //orderDetailsResponse = (OrderDetailsResponse) response.body().getOrderInfo();
                        showOrderProductDetailsAdapter.setData(orderDetailsResponse);
                        showOrderProductDetailsAdapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(ShowOrderProductDetailsActivity.this, "massage"+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ShowOrderProductDetailsActivity.this, "response"+response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Show product order details failure!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showRecycler() {
        if (orderProductList != null && orderProductList.size() > 0) {
            showOrderProductDetailsAdapter = new ShowOrderProductDetailsAdapter(this, orderProductList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            orderDtlsRecyc.setLayoutManager(layoutManager);
            orderDtlsRecyc.setItemAnimator(new DefaultItemAnimator());
            orderDtlsRecyc.setAdapter(showOrderProductDetailsAdapter);
        }else {
            Toast.makeText(this, "Opps!", Toast.LENGTH_SHORT).show();
        }
    }




    // onBacked pressed registration
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
