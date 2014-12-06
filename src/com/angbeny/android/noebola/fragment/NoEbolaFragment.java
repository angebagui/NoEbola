package com.angbeny.android.noebola.fragment;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angbeny.android.noebola.DescriptioActivity;
import com.angbeny.android.noebola.R;
import com.angbeny.android.noebola.SetPreference;
import com.angbeny.android.noebola.adapter.NoEbolaAdapter;
import com.angbeny.android.noebola.data.MyDB;
import com.angbeny.android.noebola.service.PollService;
import com.angbeny.twittersearchapi.Tweet;
import com.angbeny.twittersearchapi.TwitterSearchApi;
import com.angbeny.twittersearchapi.User;
import com.angbeny.util.BusProvider;
import com.angbeny.util.Constants;
import com.angbeny.util.NetworkEvent;
import com.angbeny.util.NetworkUtil;
import com.squareup.otto.Subscribe;

public class NoEbolaFragment extends ListFragment implements OnRefreshListener {
	private ArrayList<Tweet> feedItems;
	@SuppressWarnings("unused")
	private ArrayList<Tweet> newItems;
	private SwipeRefreshLayout mSwipeRefreshLayout ;
	private boolean hasInternet;
	private MyDB dba;
	private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// if we receive this, we're visible, so cancel
			// the notification
			Log.i(Constants.TAG_FRAG, "canceling notification");
			setResultCode(Activity.RESULT_CANCELED);
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		dba = new MyDB(getActivity());
		dba.open();
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		setHasOptionsMenu(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ActionBar ab = getActivity().getActionBar(); 
			ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffa000"));    
			          ab.setBackgroundDrawable(colorDrawable);
		}

		getActivity().setTitle(R.string.tweets_title);
		
		BusProvider.getInstance().register(this);
		loadPref();
		
		hasInternet = NetworkUtil.hasInternet(getActivity());
			
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_noebola, container, false);
		// Configure the swipe refresh layout
				mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.container);
				mSwipeRefreshLayout.setOnRefreshListener(this);
				mSwipeRefreshLayout.setColorScheme(
				   R.color.swipe_color_1, R.color.swipe_color_2,
				   R.color.swipe_color_3, R.color.swipe_color_4);
				if (!hasInternet) {
					Toast.makeText(getActivity(), getString(R.string.network_info), Toast.LENGTH_LONG).show();
					getDataFromDb();
				}else{
					uploadItems();
				}
				
				setupAdapter();
				
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setDivider(null);
	}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if(resultCode==1){
		SharedPreferences mySharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		 boolean notif_preference = mySharedPreferences.getBoolean("notification_pref", true);
		 if(!notif_preference){
			 PollService.setServiceAlarm(getActivity(), notif_preference);
		 }else{

			 PollService.setServiceAlarm(getActivity(), notif_preference);
		 }
	}
		
}
	public void uploadItems(){
		new GetDataTask().execute();
	}
	class GetDataTask extends AsyncTask<Void, Void, ArrayList<Tweet>>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			 // Start showing the refresh animation
		    mSwipeRefreshLayout.setRefreshing(true);
		}
		@Override
		protected ArrayList<Tweet> doInBackground(Void... params) {
			return new TwitterSearchApi().getTweets();
		}
		@Override
		protected void onPostExecute(ArrayList<Tweet> result) {
			 // Start showing the refresh animation
		    mSwipeRefreshLayout.setRefreshing(false);
			feedItems = result;
			// setupAdapter();
			saveItToDB(feedItems);
				if (result.size() > 0) {
		            String resultId = result.get(0).getId_str();
		            PreferenceManager.getDefaultSharedPreferences(getActivity())
		                .edit()
		                .putString(Constants.PREF_LAST_RESULT_ID, resultId)
		                .commit();
		        }
				getDataFromDb();
		}
	}
	void getDataFromDb(){
		new GetDataBaseTask().execute();
	}
	class GetDataBaseTask extends AsyncTask<Void, Void, ArrayList<Tweet>>{
		
		@Override
		protected ArrayList<Tweet> doInBackground(Void... params) {
			return getdata();
		}
		@Override
		protected void onPostExecute(ArrayList<Tweet> result) {
			feedItems = result;
			 setupAdapter();
			
				
		}
	}
	void setupAdapter() {
		if (getActivity() == null)
			return;

		if (feedItems != null) {
			setListAdapter(new NoEbolaAdapter(getActivity(), feedItems));
		} else {
			setListAdapter(null);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(
				PollService.ACTION_SHOW_NOTIFICATION);
		getActivity().registerReceiver(mOnShowNotification, filter,
				PollService.PERM_PRIVATE, null);
		
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(mOnShowNotification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		BusProvider.getInstance().unregister(this);

		
		//dba.close();
	}
	  @Override
	    @TargetApi(11)
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	        super.onCreateOptionsMenu(menu, inflater);
	        inflater.inflate(R.menu.fragment_noebola, menu);
	    }

	    @Override
	    @TargetApi(11)
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.settings:
	            	startActivityForResult(new Intent(getActivity(), SetPreference.class), 1);

	                return true;
	            case R.id.description:
	            	startActivity(new Intent(getActivity(), DescriptioActivity.class));
	            	return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }


	@Subscribe
	public void updateListView(ArrayList<Tweet> refreshNews) {
		newItems = refreshNews;
		if (refreshNews.size() > 0) {
            String resultId = refreshNews.get(0).getId_str();
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putString(Constants.PREF_LAST_RESULT_ID, resultId)
                .commit();
        }
		
		
		Toast.makeText(getActivity(), "New Tweets", Toast.LENGTH_LONG).show();
	}
	@Subscribe
	public void onNetworkEvent(NetworkEvent ne) {
		if (!ne.getStatus()) {
			Toast.makeText(getActivity(), getString(R.string.network_info), Toast.LENGTH_LONG).show();
			
		}
	}
	 

    public void saveItToDB(ArrayList<Tweet> t) {
		dba.update();
		for (Tweet tweet : t) {
			dba.insertTweet(tweet);
		}
	}

	@SuppressWarnings("deprecation")
	public ArrayList<Tweet> getdata() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		Cursor c = dba.getTweets();
		getActivity().startManagingCursor(c);
		if (c.moveToFirst()) {
			do {
				Tweet t = new Tweet();
				String text = c.getString(c.getColumnIndex(Constants.KEY_TEXT));
				String username = c.getString(c
						.getColumnIndex(Constants.KEY_USER_NAME));
				String userpic = c.getString(c
						.getColumnIndex(Constants.KEY_PIC_LINK));
				String date = c.getString(c.getColumnIndex(Constants.KEY_DATE));

				t.setText(text);
				t.setCreated_at(date);

				User user = new User();
				user.setName(username);
				user.setProfile_image_url(userpic);

				t.setUser(user);
				tweets.add(t);
			} while (c.moveToNext());
		}
		if (tweets.size() == 0) {
			return new ArrayList<Tweet>();
		}

		return tweets;
	}
	@Override
	public void onRefresh() {
		if (!NetworkUtil.hasInternet(getActivity())) {
			Toast.makeText(getActivity(), getString(R.string.network_info), Toast.LENGTH_LONG).show();
			mSwipeRefreshLayout.setRefreshing(false);
			return;
		}else{
			uploadItems();
		}
		
		
	}
	void loadPref() {
		SharedPreferences mySharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		 boolean notif_preference = mySharedPreferences.getBoolean("notification_pref", true);

			boolean shouldStartAlarm = !PollService
					.isServiceAlarmOn(getActivity());
			if(notif_preference && shouldStartAlarm){
				PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
			}
	}
}
