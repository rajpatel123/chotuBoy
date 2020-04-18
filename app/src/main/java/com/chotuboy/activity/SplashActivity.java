package com.chotuboy.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.chotuboy.R;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.chotuboy.utils.Constants;

import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    int a;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23&& context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    final int REQUEST = 112;
    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        a = 'A' - '9';
        Log.d(TAG, "onCreate: " + a);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST );
            } else {
                handler();
            }
        } else {
            handler();
        }
    }

    public void handler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ChotuBoyPrefs.getBoolean(SplashActivity.this, Constants.LoginCheck)) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);

                    if (getIntent().getExtras() != null && getIntent().hasExtra(Constants.PUSH_NEW_ORDER_DATA_KEY)) {
                        JSONObject pushData;
                        try {
                            pushData = new JSONObject(getIntent().getStringExtra(Constants.PUSH_NEW_ORDER_DATA_KEY));
                            i.putExtra(Constants.PUSH_NEW_ORDER_DATA_KEY, pushData.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

//                    String data = getIntent().getStringExtra(Constants.PUSH_NEW_ORDER_DATA_KEY);
//                    Log.e(getClass().getSimpleName(), data);
                    String token = ChotuBoyPrefs.getString(SplashActivity.this, "device_token");
                    System.out.println("asdf fcm --- : " + token);


                    startActivity(i);
                    finish();


                } else {
                    Intent i = new Intent(SplashActivity.this, LoginThroughMobileNumberActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 5000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handler();
                    //Do here
                } else {
                    //Toast.makeText(this, "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
