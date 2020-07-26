package com.chotuboy.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.chotuboy.OrderDetailsActivity;
import com.chotuboy.R;
import com.chotuboy.activity.AllOrderStatusActivity;

public class NewOrderFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AllOrderStatusActivity allOrderStatusActivity;
    LinearLayout orderShowLayOut;

    public NewOrderFragment() { }

    public static NewOrderFragment newInstance() {
        NewOrderFragment fragment = new NewOrderFragment();
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
        View view =  inflater.inflate(R.layout.fragment_new_order, container, false);
        orderShowLayOut = view.findViewById(R.id.OrderShowLayOut);


        getNewOrder();

        orderShowLayOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                startActivity(intent);
               /* Fragment fragment1 = OrderedDetailsFragment.newInstance();
                replaceFragment(fragment1, "");*/
            }
        });

        return view;
    }

    private void getNewOrder() {

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
