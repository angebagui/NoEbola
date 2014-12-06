package com.angbeny.android.noebola.adapter;

import java.util.ArrayList;

import com.androidquery.AQuery;
import com.angbeny.android.noebola.R;
import com.angbeny.twittersearchapi.Tweet;
import com.angbeny.twittersearchapi.User;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoEbolaAdapter extends ArrayAdapter<Tweet> {
	private Activity activity;
	private AQuery listAq;
	
	public NoEbolaAdapter(Activity context, ArrayList<Tweet> items) {
		super(context, 0, items);
		this.activity = context;
		listAq = new AQuery(activity);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView ==null){
			convertView = activity.getLayoutInflater().inflate(R.layout.tweet_item, parent, false);
		}

		AQuery aq = listAq.recycle(convertView);
		
		final Tweet item = getItem(position);
		User user = item.getUser();
		TextView nameText = (TextView)convertView.findViewById(R.id.name);
		TextView timestampText = (TextView)convertView.findViewById(R.id.timestamp);
		TextView txtStatusMsg = (TextView)convertView.findViewById(R.id.txtStatusMsg);
		ImageView icShare = (ImageView)convertView.findViewById(R.id.imageView1);
		
		txtStatusMsg.setText(Html.fromHtml(item.getText()));
		Typeface Roboto_Light = Typeface.createFromAsset(activity.getAssets(), "Roboto-Light.ttf");
		Typeface Roboto_Bold = Typeface.createFromAsset(activity.getAssets(), "Roboto-Bold.ttf");
		Typeface Roboto_Medium = Typeface.createFromAsset(activity.getAssets(), "Roboto-Medium.ttf");
		
		txtStatusMsg.setTypeface(Roboto_Medium);
	    timestampText.setText(item.getCreated_at());
	    timestampText.setTypeface(Roboto_Light);
	    
	    if(user !=null && user.getName() !=null){
	    	nameText.setText(user.getName());
	    	nameText.setTypeface(Roboto_Bold);
	    }
	    if(user !=null && user.getProfile_image_url() !=null){
	    	String thumbnail  = user.getProfile_image_url();
	    	Bitmap preset = aq.getCachedImage(thumbnail);
	    	String imageUrl =user.getProfile_image_url();
	    	aq.id(R.id.profilePic).progress(R.id.progressBar1).image(imageUrl, true, true,0,R.drawable.noimage, preset, AQuery.FADE_IN_NETWORK, 1.0f);
	    	
	    }
	   
icShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(activity, item.getText(), 1000).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, createMessage(item.getText()));
                i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                i = Intent.createChooser(i, getString(R.string.send_info));
                activity.startActivity(i);
				
			}

			private CharSequence getString(int sendInfo) {
				Resources r = activity.getResources();
				
				return r.getString(R.string.send_info);
			}
			private CharSequence createMessage(String message) {
				Resources r = activity.getResources();
				
				return r.getString(R.string.message, message);
			}
		});
		
		
		return convertView;
	}
	  
}
