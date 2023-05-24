package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION=3;
    public static final String TB_NAME="tbl_dangky";
    public static final String ID="id";
    public static final String UserName="userName";
    public static final String Password="password";
    public static final String ConFirmPassword="conFirmPassword";
    public Context context;

    public DBHelper(@Nullable Context context) {
        super(context,TB_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="Create table "+TB_NAME+"( "+ID+"integer primary key , "+UserName+" text, "+Password+" text, "
                +ConFirmPassword+" text );";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql ="DROP table if exists "+ TB_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
    public Boolean insertDatas(String UserName, String Password, String ConFirmPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("UserName", UserName);
        values.put("Password", Password);
        values.put("ConFirmPassword", ConFirmPassword);

        long result = db.insert(TB_NAME, "null", values);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean checkUserName(String UserName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_dangky where UserName=?", new String[]{UserName});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkPassword(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_dangky where Password=?", new String[]{pass});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
