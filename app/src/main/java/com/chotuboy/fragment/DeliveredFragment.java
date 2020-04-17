package com.chotuboy.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.activity.AllOrderStatusActivity;
import com.chotuboy.activity.MainActivity;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.retrofit.RestClient;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.chotuboy.utils.Constants;
import com.chotuboy.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveredFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AllOrderStatusActivity allOrderStatusActivity;
    private MainActivity mainActivity;
    private RecyclerView DeliveredRecycler;


    public DeliveredFragment() {
    }

    public static DeliveredFragment newInstance() {
        DeliveredFragment fragment = new DeliveredFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_delivered, container, false);
        DeliveredRecycler = view.findViewById(R.id.DeliveredRecycler);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        deliveryStatus();
    }

    private void deliveryStatus() {

        RequestBody userType = RequestBody.create(MediaType.parse("text/plain"),
                ChotuBoyPrefs.getString(getActivity(), Constants.USERTYPE));
        RequestBody delivery_id = RequestBody.create(MediaType.parse("text/plain"),
                ChotuBoyPrefs.getString(getActivity(), Constants.UserTypeSelectedID));

        Utils.showProgressDialog(getActivity());
        RestClient.getAllOrederForDelivery(delivery_id, new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus() == true) {



                    }else {
                        Toast.makeText(getActivity(), "massage"+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "response"+response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "Getting new order failure!", Toast.LENGTH_SHORT).show();

            }
        });

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
