package com.angbeny.android.noebola;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Splash extends Activity {
	protected int splashTime=3000;
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.splash_screen);
		TextView Text = (TextView)findViewById(R.id.splash_msg);
		
		Typeface Roboto_Thin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
		
		Text.setTypeface(Roboto_Thin);
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				
					finish();
				Intent intent = new Intent(Splash.this, NoEbolaActivity.class);
				startActivity(intent);
		     }
					
		  }, splashTime);
						
	}
}
