package com.chotuboy.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chotuboy.R;
import com.chotuboy.activity.MainActivity;
import com.chotuboy.adapter.ShowOrderProductDetailsAdapter;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.orderDetailsModel.OrderInfo;
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

public class ShowOrderProductDetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView showOrderedRecycler;
    List<OrderProduct> orderProductList = new ArrayList<>();
    ShowOrderProductDetailsAdapter showOrderProductDetailsAdapter;
    private MainActivity mainActivity;
    List<OrderInfo> orderInfoList = new ArrayList<>();
    OrderDetailsResponse orderDetailsResponse;


    public ShowOrderProductDetailsFragment() {
    }

    public static ShowOrderProductDetailsFragment newInstance() {
        ShowOrderProductDetailsFragment fragment = new ShowOrderProductDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_show_order_details, container, false);
        showOrderedRecycler = view.findViewById(R.id.ShowOrderedRecycler);


          getOrdrProductdetails();  //  goToOrderDetailsActivity, sending  data previously t

        showOrderDetailsRecy();

        return view;
    }

    private void getOrdrProductdetails() {
        Bundle bundle = getActivity().getIntent().getExtras();
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

       /* authorName1.setText(OrderId);
        description1.setText(CustomerId);
        description1.setText(ShippingId);
        title1.setText(OutletId);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        showProductOrderDetails();
    }

    private void showProductOrderDetails() {

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
                        showOrderProductDetailsAdapter.setData(orderDetailsResponse);
                        showOrderProductDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "Show product order details failure!", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void showOrderDetailsRecy() {
        if (orderProductList != null && orderProductList.size() > 0) {
            showOrderProductDetailsAdapter = new ShowOrderProductDetailsAdapter(orderProductList, getActivity(), R.layout.order_product_details_item);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            showOrderedRecycler.setLayoutManager(layoutManager);
            showOrderedRecycler.setItemAnimator(new DefaultItemAnimator());
            showOrderedRecycler.setAdapter(showOrderProductDetailsAdapter);
        } else {
            Toast.makeText(mainActivity, "Sorry no any order for you", Toast.LENGTH_SHORT).show();
        }
    }


    public void replaceFragment(Fragment fragment, String tag) {
        try {
            androidx.fragment.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager != null) {
                androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main, fragment, tag);
                fragmentTransaction.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
