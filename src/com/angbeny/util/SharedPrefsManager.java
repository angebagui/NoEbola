package com.angbeny.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPrefsManager {
	
	public static final String QDI_PREFS = "com.angbeny.android.noebola.prefs";
	public static final String KEY_HAS_NETWORK = "noebola.has.network";
	
	private static final String LOG_TAG = "SharedPrefsManager";
	
	public boolean saveNetworkState(Context context, boolean isConnected){
		Log.d(LOG_TAG, "Trying to save network state: " + String.valueOf(isConnected));
		
		SharedPreferences prefs = getSharedPrefs(context);
		Editor editor = prefs.edit();
		editor.putBoolean(KEY_HAS_NETWORK, isConnected);
		return editor.commit();
	}	
	
	public boolean hasNetwork(Context context){
		SharedPreferences prefs = getSharedPrefs(context);
		return prefs.getBoolean(KEY_HAS_NETWORK, false);
	}

	private SharedPreferences getSharedPrefs(Context context){
		return context.getSharedPreferences(QDI_PREFS, Context.MODE_PRIVATE);
	}
}
