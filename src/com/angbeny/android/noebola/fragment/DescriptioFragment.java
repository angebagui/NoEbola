package com.angbeny.android.noebola.fragment;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angbeny.android.noebola.R;

public class DescriptioFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ActionBar ab = getActivity().getActionBar(); 
			ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffa000"));    
			          ab.setBackgroundDrawable(colorDrawable);
		}
		getActivity().setTitle(R.string.app_name);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.description, container, false);
		
		TextView title = (TextView)v.findViewById(R.id.textView1);
		TextView content = (TextView)v.findViewById(R.id.textView2);
		TextView author = (TextView)v.findViewById(R.id.textView3);
		TextView thank = (TextView)v.findViewById(R.id.textView4);
		
      Typeface Roboto_Thin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
      Typeface Roboto_Medium = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Medium.ttf");
		title.setTypeface(Roboto_Thin);
		content.setTypeface(Roboto_Medium);
		author.setTypeface(Roboto_Thin);
		thank.setTypeface(Roboto_Thin);
		return v;
	}
}
