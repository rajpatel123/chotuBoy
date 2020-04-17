package com.chotuboy.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chotuboy.R;
import com.chotuboy.adapter.GettingNewOrderAdapter;
import com.chotuboy.fragment.DeliveredFragment;
import com.chotuboy.fragment.GettingNewOrderFragment;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.chotuboy.utils.Constants;

public class MainActivity extends AppCompatActivity {
    GettingNewOrderAdapter gettingNewOrderAdapter;
    ImageView back;
    RecyclerView OrderedRecy;
    public String userType;
    private String selectedId;
    private String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = findViewById(R.id.back);

        mobileNo = ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE);
        userType = ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE);
        selectedId = ChotuBoyPrefs.getString(getApplicationContext(), Constants.UserTypeSelectedID);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AccordingUserType();
    }

    private void AccordingUserType() {

        userType = ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE);
        mobileNo = ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE);
        selectedId = ChotuBoyPrefs.getString(getApplicationContext(), Constants.UserTypeSelectedID);

        switch (userType) {

            case "OutLet":
                Fragment fragment1 = GettingNewOrderFragment.newInstance();
                replaceFragment(fragment1, "");
                break;

            case "Delivery Boy":
                Fragment fragment2 = DeliveredFragment.newInstance();
                replaceFragment(fragment2, "");
                break;

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



    // onBacked pressed registration
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
