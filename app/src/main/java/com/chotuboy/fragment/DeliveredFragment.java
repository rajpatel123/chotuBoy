package com.chotuboy.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.chotuboy.R;
import com.chotuboy.activity.AllOrderStatusActivity;


public class DeliveredFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AllOrderStatusActivity allOrderStatusActivity;

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
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        allOrderStatusActivity = (AllOrderStatusActivity) getActivity();
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
