package com.angbeny.android.noebola;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.angbeny.android.noebola.fragment.NoEbolaFragment;
import com.angbeny.util.SingleFragmentActivity;

public class NoEbolaActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new NoEbolaFragment();
	}
	@Override
	protected void onNewIntent(Intent intent) {
		NoEbolaFragment fragment = (NoEbolaFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);
		fragment.uploadItems();
	}
}
