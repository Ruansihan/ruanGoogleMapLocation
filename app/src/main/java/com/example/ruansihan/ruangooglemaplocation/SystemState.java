package com.example.ruansihan.ruangooglemaplocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ruansihan on 08.12.2015.
 */
public class SystemState extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent myintent = new Intent(context, LocationServiceManager.class);
            context.startService(myintent);
            return;
        }
        if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
            return;
        boolean noConnectivity = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        @SuppressWarnings("deprecation")
        NetworkInfo aNetworkInfo = (NetworkInfo) intent
                .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        if (!noConnectivity) {
            if ((aNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                    || (aNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
                Intent myintent = new Intent(context,
                        LocationServiceManager.class);
                context.startService(myintent);
            }
        } else {
            if ((aNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                    || (aNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
                Intent myintent = new Intent(context,
                        LocationServiceManager.class);
                context.stopService(myintent);
            }
        }
    }
}
