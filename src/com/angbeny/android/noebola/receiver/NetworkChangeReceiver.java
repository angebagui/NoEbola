package com.angbeny.android.noebola.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.angbeny.util.BusProvider;
import com.angbeny.util.NetworkEvent;
import com.angbeny.util.NetworkUtil;
import com.angbeny.util.SharedPrefsManager;

public class NetworkChangeReceiver extends BroadcastReceiver {
		
		private static final String LOG_TAG = "NetworkChangeReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			boolean hasInternet = false;

			Log.d(LOG_TAG, "Received broadcast!");
			
			if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				hasInternet = NetworkUtil.hasInternet(context);
			}

			SharedPrefsManager prefs = new SharedPrefsManager();
			boolean prevValue = prefs.hasNetwork(context);
			
			if(prevValue != hasInternet){
				(new SharedPrefsManager()).saveNetworkState(context, hasInternet);
			
				BusProvider.getInstance().register(this);
				BusProvider.getInstance().post( new NetworkEvent(hasInternet));
				BusProvider.getInstance().unregister(this);
			}
		}

		

}
