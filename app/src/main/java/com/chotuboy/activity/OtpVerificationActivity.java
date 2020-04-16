package com.chotuboy.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.chotuboy.R;
import com.chotuboy.modelClass.login.LoginWithOtpResponse;
import com.chotuboy.modelClass.otpVerify.VerifyOtpResponse;
import com.chotuboy.receivers.SmsListener;
import com.chotuboy.receivers.SmsReceiver;
import com.chotuboy.retrofit.RestClient;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.chotuboy.utils.Constants;
import com.chotuboy.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity implements SmsListener {

    TextView changeNumberTv, mobileNumberTv, resend;
    EditText verifyPinEdtText;
    Button btnLogin;
    public String mobileNo;
    Context context;
    public String otpNumber;
    public String customerUserId, firstName, lastName, userEmail, userAddress;
    VerifyOtpResponse verifyOtpResponse;
    SmsReceiver smsReceiver = new SmsReceiver();
    private String userType;


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
        setContentView(R.layout.activity_otp_verification);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Login Using OTP");
        }

     //   allowPermission();
        initView();
    }

    private void allowPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST );
            } else {
                verifyPinOperation();
            }
        } else {
            verifyPinOperation();
        }
    }


    private void initView() {

        changeNumberTv = findViewById(R.id.changeNumberTv);
        mobileNumberTv = findViewById(R.id.mobileNumberTv);
        verifyPinEdtText = findViewById(R.id.verifyPinEdtTxt);
        btnLogin = findViewById(R.id.vrfyPin_btnLogin);
        resend = findViewById(R.id.resend);

        smsReceiver.bindListener(this);

        mobileNumberTv.setText(ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE));
        mobileNo = ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE);
        userType = ChotuBoyPrefs.getString(getApplicationContext(),Constants.USERTYPE);

        changeNumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginThroughMobileNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
                Utils.showProgressDialog(context);

                RestClient.logInWithOtpNewUser(phoneno, new Callback<LoginWithOtpResponse>() {
                    @Override
                    public void onResponse(Call<LoginWithOtpResponse> call, Response<LoginWithOtpResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response != null && response.code() == 200 && response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                Toast.makeText(getApplicationContext(), "OTP resend your mobile number successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginWithOtpResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed! resent OTP ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPinOperation();
            }
        });

    }

    public void verifyPinOperation() {
        boolean check = true;
        otpNumber = verifyPinEdtText.getText().toString().trim();
        if (otpNumber.isEmpty() || verifyPinEdtText.length()<4) {
            verifyPinEdtText.setError("enter a valid otp");
            check = false;
        } else {
            verifyPinEdtText.setError(null);
            check = true;
        }

        if (check) {

            RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
            RequestBody otp = RequestBody.create(MediaType.parse("text/plain"), otpNumber);

            Utils.showProgressDialog(this);
            RestClient.verifyOtpOtpNewUser(phoneno, otp, new Callback<VerifyOtpResponse>() {
                @Override
                public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.code() == 200 && response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {

                            customerUserId = response.body().getUser().getCustomerId();
                            firstName = (String) response.body().getUser().getFirstname();
                            lastName = (String) response.body().getUser().getLastname();
                            mobileNo = response.body().getUser().getPhone();
                            userEmail = (String) response.body().getUser().getEmail();
                            userAddress = (String) response.body().getUser().getAddress();

                            ChotuBoyPrefs.putBoolean(getApplicationContext(), Constants.LoginCheck, true);

                            Intent intent = new Intent(getApplicationContext(), AllOrderStatusActivity.class);
                            ChotuBoyPrefs.putString(getApplicationContext(),Constants.USERTYPE,userType);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.CUSTOMERUSERID, customerUserId);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.CUSTOMERUSERID, customerUserId);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.FIRSTNAME, firstName);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.LASTNAME, lastName);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.MOBILE, mobileNo);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.USER_EMAIL, userEmail);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.USER_ADDRESS, userAddress);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter valid otp", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Response failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // close this activity and return to preview activity (if there is any)
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if (smsReceiver != null)
                unregisterReceiver(smsReceiver);
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


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onBackPressed();
        return super.onContextItemSelected(item);
    }

    @Override
    public void messageReceived(String messageText) {
      verifyPinEdtText.setText(messageText);
       verifyPinOperation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    verifyPinOperation();
                    //Do here
                } else {
                    //Toast.makeText(this, "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
