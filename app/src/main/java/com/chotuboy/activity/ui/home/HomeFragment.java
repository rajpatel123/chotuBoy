package com.chotuboy.activity.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chotuboy.R;
import com.chotuboy.adapter.MyOrderAdapter;
import com.chotuboy.databinding.FragmentHomeBinding;
import com.chotuboy.modelClass.orderlist.OrderInfo;
import com.chotuboy.modelClass.orderlist.OrderListResponse;
import com.chotuboy.retrofit.RestClient;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.chotuboy.utils.Utils;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    ArrayList<OrderInfo> orderInfos = new ArrayList<>();
    FragmentHomeBinding mBinding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = mBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider\

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        get_order_list();
    }

    private void get_order_list() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getAllOrederForOutLet(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        orderInfos.clear();
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                mBinding.recyclerCartView.setVisibility(View.VISIBLE);
                                orderInfos.addAll(cartInfoList.getOrderInfo());
                                mBinding.recyclerCartView.setVisibility(View.VISIBLE);
                                MyOrderAdapter horizontalAdapter = new MyOrderAdapter(getActivity(), orderInfos);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartView.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartView.setAdapter(horizontalAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartView.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartView.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {
                    mBinding.recyclerCartView.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

}
