package com.angbeny.android.noebola;

import android.support.v4.app.Fragment;

import com.angbeny.android.noebola.fragment.DescriptioFragment;
import com.angbeny.util.SingleFragmentActivity;

public class DescriptioActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new DescriptioFragment();
	}

}
