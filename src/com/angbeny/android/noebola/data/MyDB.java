package com.angbeny.android.noebola.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.angbeny.twittersearchapi.Tweet;
import com.angbeny.util.Constants;

public class MyDB {

	private SQLiteDatabase db;
	private final Context context;
	private final MyDBhelper dbhelper;

	public MyDB(Context c) {
		context = c;
		dbhelper = new MyDBhelper(context, Constants.DATABASE_NAME, null,
				Constants.DATABASE_VERSION);
	}

	public void close() {
		db.close();
	}
	public void update(){
		dbhelper.onUpgrade(db, db.getVersion(), Constants.DATABASE_VERSION);
		
	}
	public void open() throws SQLiteException {
		try {
			db = dbhelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			Log.v("Open database exception caught", ex.getMessage());
			db = dbhelper.getReadableDatabase();
		}
	}

	public long insertTweet(Tweet t) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.KEY_TEXT, t.getText());
			newTaskValue.put(Constants.KEY_USER_NAME, t.getUser().getName());
			newTaskValue.put(Constants.KEY_PIC_LINK, t.getUser().getProfile_image_url());
			newTaskValue.put(Constants.KEY_DATE, t.getCreated_at());
			return dbhelper.getWritableDatabase() .insert(Constants.DATABASE_TABLE, null, newTaskValue);
		} catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}
	public Cursor getTweets()
	{
	Cursor c = dbhelper.getReadableDatabase().query(Constants.DATABASE_TABLE, null, null,
	null, null, null, null);
	return c;
	}
}
