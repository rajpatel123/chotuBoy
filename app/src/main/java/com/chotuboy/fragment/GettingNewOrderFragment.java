package com.chotuboy.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.activity.AllOrderStatusActivity;
import com.chotuboy.activity.MainActivity;
import com.chotuboy.activity.OrderDetailsActivity;
import com.chotuboy.adapter.GettingNewOrderAdapter;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.orderDetailsModel.OrderInfo;
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

public class GettingNewOrderFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AllOrderStatusActivity allOrderStatusActivity;
    LinearLayout orderShowLayOut;
    RecyclerView gettingOrderedRecycler;
    GettingNewOrderAdapter gettingNewOrderAdapter;
    MainActivity mainActivity;
    List<OrderInfo> orderInfoList = new ArrayList<>();
    OrderDetailsResponse orderDetailsResponse;

    public GettingNewOrderFragment() {
    }

    public static GettingNewOrderFragment newInstance() {
        GettingNewOrderFragment fragment = new GettingNewOrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getting_new_order, container, false);
        gettingOrderedRecycler = view.findViewById(R.id.GettingOrderedRecycler);

        gettingNewOrder();
        showGettingOrderRecy();

        return view;
    }

    private void gettingNewOrder() {

        RequestBody userType = RequestBody.create(MediaType.parse("text/plain"),
                ChotuBoyPrefs.getString(getActivity(), Constants.USERTYPE));
        RequestBody outlet_id = RequestBody.create(MediaType.parse("text/plain"),
                ChotuBoyPrefs.getString(getActivity(), Constants.UserTypeSelectedID));

        Utils.showProgressDialog(getActivity());
        RestClient.getAllOrederForOutLet(outlet_id, new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus() == true) {

                        orderDetailsResponse = response.body();
                        //orderDetailsResponse = (OrderDetailsResponse) response.body().getOrderInfo();
                        gettingNewOrderAdapter.setData(orderDetailsResponse);
                        gettingNewOrderAdapter.notifyDataSetChanged();

                        goToOrderDetailsActivity();

                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "Getting new order failure!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void goToOrderDetailsActivity() {

        gettingNewOrderAdapter = new GettingNewOrderAdapter(orderInfoList,getActivity(),R.layout.getting_ordered_recy_item);
        gettingNewOrderAdapter.setGetNewOrderListInterface(new GettingNewOrderAdapter.GetNewOrderListInterface() {
            @Override
            public void newOrderListI(Integer orderId, Integer customerId, Integer shippingId,
                                      Integer outletId, Integer deliveryId, Object razorpayOrderId,
                                      String amount, String discount, String paymentStatus, String orderStatus,
                                      String firstname, String lastname, String email, String phone,
                                      String address, String city, String postcode, Object stateId,
                                      Integer deliveryCharge, String paymentType, String dateAdded,
                                      Integer orderProductId, Integer productId, Integer priceId,
                                      Integer quantity) {

                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);

                intent.putExtra("OrderId",orderId);
                intent.putExtra("CustomerId",customerId);
                intent.putExtra("ShippingId",shippingId);
                intent.putExtra("OutletId",outletId);
                intent.putExtra("DeliveryId",deliveryId);
                intent.putExtra("RazorpayOrderId", (Integer) razorpayOrderId);
                intent.putExtra("Amount",amount);
                intent.putExtra("Discount",discount);
                intent.putExtra("PaymentStatus",paymentStatus);
                intent.putExtra("OrderStatus",orderStatus);
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                intent.putExtra("Email",email);
                intent.putExtra("Lhone",phone);
                intent.putExtra("Address",address);
                intent.putExtra("City",city);
                intent.putExtra("Postcode",postcode);
                intent.putExtra("StateId", (Integer) stateId);
                intent.putExtra("DeliveryCharge",deliveryCharge);
                intent.putExtra("PaymentType",paymentType);
                intent.putExtra("DateAdded",dateAdded);
                intent.putExtra("OrderProductId",orderProductId);
                intent.putExtra("ProductId",productId);
                intent.putExtra("PriceId",priceId);
                intent.putExtra("Quantity",quantity);
                startActivity(intent);

                gettingOrderedRecycler.setAdapter(gettingNewOrderAdapter);

            }
        });

    }

    private void showGettingOrderRecy() {
        if (orderInfoList != null && orderInfoList.size() > 0) {
            gettingNewOrderAdapter = new GettingNewOrderAdapter(orderInfoList, getActivity(), R.layout.getting_ordered_recy_item);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            gettingOrderedRecycler.setLayoutManager(layoutManager);
            gettingOrderedRecycler.setItemAnimator(new DefaultItemAnimator());
            gettingOrderedRecycler.setAdapter(gettingNewOrderAdapter);
        } else {
            Toast.makeText(mainActivity, "Sorry no any order for you", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // allOrderStatusActivity = (AllOrderStatusActivity) getActivity();
        mainActivity = (MainActivity) getActivity();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
