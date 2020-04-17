package com.chotuboy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.adapter.OrderProductDetailsAdapter;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView orderDtlsRecyc;
    private Spinner spinner;
    OrderProductDetailsAdapter orderProductDetailsAdapter;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderDtlsRecyc = findViewById(R.id.OrderDetailsRecyc);
        spinner =findViewById(R.id.StateSpinner);
        back= findViewById(R.id.back);

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

    }


    private void showRecycler() {
        orderProductDetailsAdapter = new OrderProductDetailsAdapter( getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        orderDtlsRecyc.setLayoutManager(layoutManager);
        orderDtlsRecyc.setItemAnimator(new DefaultItemAnimator());
        orderDtlsRecyc.setAdapter(orderProductDetailsAdapter);
    }
}
