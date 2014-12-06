package com.angbeny.android.noebola.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.angbeny.util.Constants;

public class MyDBhelper extends SQLiteOpenHelper{
	
	private static final String CREATE_TABLE="create table "+
			Constants.DATABASE_TABLE+" ("+
			Constants.KEY_ROWID+" integer primary key autoincrement, "+
			Constants.KEY_TEXT+" text not null,"+
			Constants.KEY_USER_NAME+" text not null,"+
			Constants.KEY_PIC_LINK+" text not null,"+
			Constants.KEY_DATE+" text not null);";
	
	public MyDBhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("MyDBhelper onCreate","Creating all the tables");
		try {
		db.execSQL(CREATE_TABLE);
		} catch(SQLiteException ex) {
		Log.v("Create table exception", ex.getMessage());
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("TaskDBAdapter", "Upgrading from version "+oldVersion
				+" to "+newVersion
				+", which will destroy all old data");
				db.execSQL("drop table if exists "+Constants.DATABASE_TABLE);
				onCreate(db);
		
	}
}
