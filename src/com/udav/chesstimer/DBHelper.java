package com.udav.chesstimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper{
	private static DBHelper mDBHelper;
	private Context context;

	private DBHelper(Context context) {
		super(context, "MyBusDB", null, 1);
		this.context = context;
	}

	private static SQLiteDatabase getDB(Context context) {
		if (mDBHelper == null) mDBHelper = new DBHelper(context);
		return mDBHelper.getWritableDatabase();
	}

	public static boolean setDataToDB(Context context, String winPl, String losePl, int stepCount, 
			String winTime, String loseTime){
		SQLiteDatabase db = getDB(context);
		ContentValues mContentValues = new ContentValues();
		mContentValues.put("win_pl", winPl);
		mContentValues.put("lose_pl", losePl);
		mContentValues.put("step_count", stepCount);
		mContentValues.put("win_time", winTime);
		mContentValues.put("lose_time", loseTime);
		db.insert("GameStatistic", null, mContentValues);
		return true;
	}
	
	public static Cursor getDataFromDB(Context context) {
		Cursor mCursor = getDB(context).rawQuery("SELECT * " +
				"FROM GameStatistic;", new String[]{});
		return mCursor;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE GameStatistic(_id integer primary key autoincrement, win_pl text, " +
				"lose_pl text, step_count integer, win_time text, lose_time text);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}


}