package com.chotupartner.activity.ui.store;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chotupartner.R;
import com.chotupartner.activity.DashBoardActivity;
import com.chotupartner.activity.ui.home.HomeViewModel;
import com.chotupartner.adapter.ProductAdapter;
import com.chotupartner.databinding.FragmentStoreBinding;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Utils;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreFragment extends Fragment implements ProductAdapter.ItemClickListener {


    private HomeViewModel homeViewModel;
    FragmentStoreBinding mBinding;

    DashBoardActivity dashBoardActivity;
    private AlertDialog dialog;
    private ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_store, container, false);
        View view = mBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider\

        mBinding.cView.setVisibility(View.VISIBLE);
        mBinding.oView.setVisibility(View.GONE);

        mBinding.confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cView.setVisibility(View.VISIBLE);
                mBinding.oView.setVisibility(View.GONE);
                getAvailableProduct();
            }
        });


        mBinding.pendingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cView.setVisibility(View.GONE);
                mBinding.oView.setVisibility(View.VISIBLE);
                getNotAvailableProduct();
            }
        });


        return view;


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashBoardActivity = (DashBoardActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAvailableProduct();

    }

    private void getAvailableProduct() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getOutletProduct(customeridRequest, new Callback<List<ProductOnOutlet>>() {
                @Override
                public void onResponse(Call<List<ProductOnOutlet>> call, Response<List<ProductOnOutlet>> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            List<ProductOnOutlet> productOnOutletList = response.body();

                            if (productOnOutletList != null && productOnOutletList.size() > 0) {
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.cView.setVisibility(View.VISIBLE);
                                mBinding.oView.setVisibility(View.GONE);

                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                productAdapter = new ProductAdapter(getActivity(), productOnOutletList, StoreFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewConfirm.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewConfirm.setAdapter(productAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);

                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);
                        mBinding.recyclerCartViewPending.setVisibility(View.GONE);


                    }
                }

                @Override
                public void onFailure(Call<List<ProductOnOutlet>> call, Throwable t) {
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.recyclerCartViewPending.setVisibility(View.GONE);
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


    private void getNotAvailableProduct() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getOutletProductNtAvailable(customeridRequest, new Callback<List<ProductOnOutlet>>() {
                @Override
                public void onResponse(Call<List<ProductOnOutlet>> call, Response<List<ProductOnOutlet>> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            List<ProductOnOutlet> productOnOutletList = response.body();


                            if (productOnOutletList != null && productOnOutletList.size() > 0) {
                                mBinding.cView.setVisibility(View.GONE);
                                mBinding.oView.setVisibility(View.VISIBLE);

                                mBinding.recyclerCartViewPending.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                productAdapter = new ProductAdapter(getActivity(), productOnOutletList, StoreFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewPending.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewPending.setAdapter(productAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<List<ProductOnOutlet>> call, Throwable t) {
                    mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
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


//    @Override
//    public void on(int orderId) {
//        RequestBody outletID = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));
//        RequestBody orderID = RequestBody.create(MediaType.parse("text/plain"), "" + orderId);
//        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"), "processing");
//        if (Utils.isInternetConnected(getActivity())) {
//            Utils.showProgressDialog(getActivity());
//            RestClient.updateOrderStatus(outletID, orderID, statusBody, new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Utils.dismissProgressDialog();
//                    if (response.body() != null) {
//                       // orderInfos.clear();
//                        if (response.body() != null) {
//                            getPendingOrders();
//                        }
//                    } else {
//                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
//                        mBinding.emptyCart.setVisibility(View.VISIBLE);
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
//                    mBinding.emptyCart.setVisibility(View.VISIBLE);
//                    Log.d("tag", "onFailure: ");
//                    Utils.dismissProgressDialog();
//                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Utils.dismissProgressDialog();
//            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
//        }
//
//    }


//    private void pickupOrderDialog(int orderId, String orderCustomerId, String title, String type) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(dashBoardActivity);
//        View view = LayoutInflater.from(dashBoardActivity).inflate(R.layout.enter_otp_dialog, null);
//
//        Button verifyOTp = view.findViewById(R.id.verifyOTp);
//        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
//        TextView orderIdTV = view.findViewById(R.id.orderId);
//        EditText verifyPinEdtText = view.findViewById(R.id.verifyPinEdtTxt);
//
//        dialogTitle.setText("" + title);
//        orderIdTV.setText("Order ID : " + orderCustomerId);
//        ImageView crossBtn = view.findViewById(R.id.closeDialog);
//        builder.setCancelable(false);
//        builder.setView(view);
//
//        verifyOTp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (!TextUtils.isEmpty(verifyPinEdtText.getText().toString())) {
//                    if (type.equalsIgnoreCase("pickup")){
//                        verifPickupyOTp(verifyPinEdtText.getText().toString(), orderId);
//                    }else{
//                        dialog.dismiss();
//                        verifDeliveredOTp(verifyPinEdtText.getText().toString(),orderId);
//                    }
//                } else {
//                    Toast.makeText(dashBoardActivity, "Please enter otp from outlet", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//
//        crossBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog = builder.create();
//        dialog.show();
//    }


    @Override
    public void updateAvailable(ProductOnOutlet productOnOutlet, String available) {
        if (TextUtils.isEmpty(productOnOutlet.getDiscountPrice())) {
            Toast.makeText(getActivity(), "Please update selling price first", Toast.LENGTH_LONG).show();
            return;
        }

//        RequestBody outletID = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));
//        RequestBody productId = RequestBody.create(MediaType.parse("text/plain"), "" + productOnOutlet.getId());
//        RequestBody mrp = RequestBody.create(MediaType.parse("text/plain"), "" + (!TextUtils.isEmpty(productOnOutlet.getMrp()) ? productOnOutlet.getMrp() : productOnOutlet.getPrice()));
//        RequestBody d_price = RequestBody.create(MediaType.parse("text/plain"), "" + productOnOutlet.getDiscountPrice());
//        RequestBody discount = RequestBody.create(MediaType.parse("text/plain"), "" + productOnOutlet.getDiscount());
//        RequestBody availability = RequestBody.create(MediaType.parse("text/plain"), available);
//        if (Utils.isInternetConnected(getActivity())) {
//            Utils.showProgressDialog(getActivity());
//            RestClient.updateProductAvailability(outletID, productId, mrp, d_price, discount, availability, new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Utils.dismissProgressDialog();
//                    if (response.body() != null) {
//                        if (available.equalsIgnoreCase("1")) {
//                            getNotAvailableProduct();
//                        } else {
//                            getAvailableProduct();
//                        }
//                    } else {
//                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
//                        mBinding.emptyCart.setVisibility(View.VISIBLE);
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
//                    mBinding.emptyCart.setVisibility(View.VISIBLE);
//                    Log.d("tag", "onFailure: ");
//                    Utils.dismissProgressDialog();
//                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Utils.dismissProgressDialog();
//            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onEdit(ProductOnOutlet productOnOutlet) {
        //pickupOrderDialog("Customer Order Delivery Code","delivery");
    }
}
