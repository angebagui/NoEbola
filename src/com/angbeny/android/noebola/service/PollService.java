package com.angbeny.android.noebola.service;

import java.util.ArrayList;

import com.angbeny.android.noebola.NoEbolaActivity;
import com.angbeny.android.noebola.R;
import com.angbeny.twittersearchapi.Tweet;
import com.angbeny.twittersearchapi.TwitterSearchApi;
import com.angbeny.util.Constants;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class PollService extends IntentService {
    private static final String TAG = "PollService";
    
    private static final int POLL_INTERVAL = 1000*60*5; // 5 minutes
    public static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static final String ACTION_SHOW_NOTIFICATION = "com.angbeny.android.noebola.SHOW_NOTIFICATION";
    
    public static final String PERM_PRIVATE = "com.angbeny.android.noebola.PRIVATE";
    
    public PollService() {
        super(TAG);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() &&
            cm.getActiveNetworkInfo() != null;        
        Log.i(TAG, "In service! network available: " + isNetworkAvailable);
        if (!isNetworkAvailable) return;
        
        sendBroadcast(new Intent(Constants.NETWORK_STATE_ACTION));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lastResultId = prefs.getString(Constants.PREF_LAST_RESULT_ID, null);

        ArrayList<Tweet> items = new TwitterSearchApi().getTweets();
        

        if (items.size() == 0) 
            return;

        String resultId = items.get(0).getId_str();

        if (!resultId.equals(lastResultId)) {
            Log.i(TAG, "Got a new result: " + resultId);

            Resources r = getResources();
            PendingIntent pi = PendingIntent
                .getActivity(this, 0, new Intent(this, NoEbolaActivity.class), 0);
           String n = "" ;//getCountTweet(items, lastResultId);
          
            String text = r.getString(R.string.new_tweets_text, n);
            Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.new_tweets_title))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(r.getString(R.string.new_tweets_title))
                .setContentText(text)
                .setLights(0xff00ff00, 300, 1000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
            showBackgroundNotification(0, notification);
        }

        prefs.edit()
            .putString(Constants.PREF_LAST_RESULT_ID, resultId)
            .commit();
    }
    
    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC, 
                    System.currentTimeMillis(), POLL_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PollService.PREF_IS_ALARM_ON, isOn)
            .commit();
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
    
    void showBackgroundNotification(int requestCode, Notification notification) {
        Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra("REQUEST_CODE", requestCode);
        i.putExtra("NOTIFICATION", notification);
        
        sendOrderedBroadcast(i, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null);
    }
 /*   public String getCountTweet(ArrayList<Tweet> tweets, String lastResultId){
    	
    	int i = 0;
    	do{
    		i++;
    	}
    	while(!tweets.get(i).getId_str().equals(lastResultId) && i<tweets.size());
    	return String.valueOf(i);
    }*/
}
