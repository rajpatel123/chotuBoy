package com.chotuboy.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chotuboy.R;
import com.chotuboy.fragment.DeliveredFragment;
import com.chotuboy.fragment.GettingNewOrderFragment;

public class AllOrderStatusActivity extends AppCompatActivity  {

    Button newOrderBtn, deliverBtn;
    LinearLayout newOrderLyOut, deliveredLyOut;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ordered_status);

        newOrderBtn = findViewById(R.id.NewOrderBtn);
        deliverBtn = findViewById(R.id.DeliverBtn);
        newOrderLyOut = findViewById(R.id.NewOrderLyOut);
        deliveredLyOut = findViewById(R.id.DeliveredLyOut);
        back = findViewById(R.id.back);

        Fragment fragment1 = GettingNewOrderFragment.newInstance();
        replaceFragment(fragment1, "");

        newOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment1 = GettingNewOrderFragment.newInstance();
                replaceFragment(fragment1, "");
                deliverBtn.setBackgroundColor(getResources().getColor(R.color.white));
                deliveredLyOut.setBackgroundColor(getResources().getColor(R.color.white));
                newOrderBtn.setBackgroundColor(getResources().getColor(R.color.dark_white));
                newOrderLyOut.setBackgroundColor(getResources().getColor(R.color.dark_white));

            }
        });
        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment1 = DeliveredFragment.newInstance();
                replaceFragment(fragment1, "");
                newOrderBtn.setBackgroundColor(getResources().getColor(R.color.white));
                newOrderLyOut.setBackgroundColor(getResources().getColor(R.color.white));
                deliverBtn.setBackgroundColor(getResources().getColor(R.color.dark_white));
                deliveredLyOut.setBackgroundColor(getResources().getColor(R.color.dark_white));

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count == 1) {
                    new AlertDialog.Builder(getApplicationContext())
                            .setMessage("Are you sure you want to exit?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AllOrderStatusActivity.super.onBackPressed();
                                    finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
            }
        });

    }

    public void handleChangedFragment(View view) {
        if (view == findViewById(R.id.NewOrderBtn)) {
            Fragment fragment1 = GettingNewOrderFragment.newInstance();
            replaceFragment(fragment1, "");
        } else if (view == findViewById(R.id.DeliverBtn)) {
            Fragment fragment2 = DeliveredFragment.newInstance();
            replaceFragment(fragment2, "");
        }

    }

    public void replaceFragment(Fragment fragment, String tag) {
        try {
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
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


    /*private void openDeliveredFragment() {
        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  replaceFragmentWithStack(R.id.container_main, DeliveredFragment.newInstance(), DeliveredFragment.class.getSimpleName());
              // do same another fragment
            }
        });

        }*/


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AllOrderStatusActivity.super.onBackPressed();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            getSupportFragmentManager().popBackStack();
        }


    }


}
