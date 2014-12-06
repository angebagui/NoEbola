package com.angbeny.android.noebola;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
public class SetPreference extends PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ActionBar ab = getActionBar(); 
			ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffa000"));    
			          ab.setBackgroundDrawable(colorDrawable);
		}
		setTitle(R.string.action_settings);
		setResult(1);
		
		
		
	}
}
