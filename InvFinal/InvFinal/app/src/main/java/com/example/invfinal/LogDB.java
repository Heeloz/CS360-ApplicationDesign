package com.example.invfinal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LogDB extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userInfo.db";
    private static LogDB mLogDB;
    private Context context;

    public static LogDB getInstance(Context context) {
        if(mLogDB == null) {
            mLogDB = new LogDB(context);
        }
        return mLogDB;
    }

    LogDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    private static final class userInfoTable {
        private static final String TABLE = "userInfo";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "userName";
        private static  final String COL_PASSWORD = "passWord";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ userInfoTable.TABLE + " ("
                    +userInfoTable.COL_ID + " integer primary key autoincrement, " +
                    userInfoTable.COL_USERNAME + " text, " +
                    userInfoTable.COL_PASSWORD + " text);" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + userInfoTable.TABLE);
        onCreate(db);
    }

    /*

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                db.execSQL("pragma foreign_keys = on;");
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }

     */

    public Boolean getUser(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + userInfoTable.TABLE +
                " where " + userInfoTable.COL_USERNAME + "= ?";
        Cursor cursor = db.rawQuery(sql, new String[] {userName});
        if(cursor.moveToFirst()) {
            return false;
        }
        return true;
    }

    public boolean createUser(String userName, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userInfoTable.COL_USERNAME, userName);
        values.put(userInfoTable.COL_PASSWORD, password);
        long id = db.insert(userInfoTable.TABLE, null, values);
        return id != -1;
    }

    public boolean validateLogin(String userName, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + userInfoTable.TABLE +
                " where " + userInfoTable.COL_USERNAME + "= ?";
        Cursor cursor = db.rawQuery(sql, new String[] {userName});
        if(cursor.moveToFirst()) {
//            Log.i("valid-first", cursor.getString(0));
//            Log.i("valid-second", cursor.getString(1));
//            Log.i("valid-third", cursor.getString(2));
            // Column 2 is password
            return Boolean.valueOf(cursor.getString(2).equals(password));
        }
        Log.i("validLogin", String.valueOf(cursor.moveToFirst()));
        return false;
    }
}