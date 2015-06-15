package com.dev.kidstools.uitls;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * db helper
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-10-21
 */
public class DbHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "trinea_android_common.db";
	public static final int DB_VERSION = 1;

	private static final String TERMINATOR = ";";

	/** image sdcard cache table **/
	public static final StringBuffer CREATE_IMAGE_SDCARD_CACHE_TABLE_SQL = new StringBuffer();
	public static final StringBuffer CREATE_IMAGE_SDCARD_CACHE_TABLE_INDEX_SQL = new StringBuffer();
	
	


	/** http response cache table **/
	public static final StringBuffer CREATE_HTTP_CACHE_TABLE_SQL = new StringBuffer();
	public static final StringBuffer CREATE_HTTP_CACHE_TABLE_INDEX_SQL = new StringBuffer();
	public static final StringBuffer CREATE_HTTP_CACHE_TABLE_UNIQUE_INDEX = new StringBuffer();
	
	

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(CREATE_IMAGE_SDCARD_CACHE_TABLE_SQL
					.toString());
			db.execSQL(CREATE_IMAGE_SDCARD_CACHE_TABLE_INDEX_SQL
					.toString());

			db.execSQL(CREATE_HTTP_CACHE_TABLE_SQL.toString());
			db.execSQL(CREATE_HTTP_CACHE_TABLE_INDEX_SQL.toString());
			db.execSQL(CREATE_HTTP_CACHE_TABLE_UNIQUE_INDEX
					.toString());
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
