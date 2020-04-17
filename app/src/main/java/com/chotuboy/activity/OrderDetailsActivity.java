package com.chotuboy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.adapter.OrderProductDetailsAdapter;
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

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView orderDtlsRecyc;
    private Spinner spinner;
    OrderProductDetailsAdapter orderProductDetailsAdapter;
    private ImageView back;
    List<OrderProduct> orderProductList = new ArrayList<>();
    private OrderDetailsResponse orderDetailsResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderDtlsRecyc = findViewById(R.id.OrderDetailsRecyc);
        spinner = findViewById(R.id.StateSpinner);
        back = findViewById(R.id.back);

        showRecycler();
        getOrderDetails();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                        orderProductDetailsAdapter.setData(orderDetailsResponse);
                        orderProductDetailsAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Show product order details failure!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void showRecycler() {
        orderProductDetailsAdapter = new OrderProductDetailsAdapter(this, orderProductList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        orderDtlsRecyc.setLayoutManager(layoutManager);
        orderDtlsRecyc.setItemAnimator(new DefaultItemAnimator());
        orderDtlsRecyc.setAdapter(orderProductDetailsAdapter);
    }
}
