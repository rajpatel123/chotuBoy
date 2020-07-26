package com.chotuboy.commonBase.broad_cast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MySenderBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

       /* if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean boleanExtra = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);

            if (!boleanExtra){
                Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        }*/


        if ("com.chotuboy.receivers.ACTION_SEND".equals(intent.getAction())){
            String extraData = intent.getStringExtra("com.chotuboy.EXTRA_DATA");
            Toast.makeText(context, "from receiver:"+extraData ,Toast.LENGTH_LONG).show();
        }

    }
}
