package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GhiChuDB extends SQLiteOpenHelper {

    //    public static final String DB_NAME="Ghichu.db"; //DB chưa có cột giờ

    public static final String DB_NAME="Ghichu2.db";
    public static final int VERSION = 4;
    public static final String TB_NAME="tbl_ghichu";
    public static final String ID="id";
    public static final String TIEUDE="tieude";
    public static final String NGAYTHANG="ngaythang";
    public static final String NOIDUNG="noidung";
    public static final String TGTHUC="thoigian";


    //Update thêm cột giờ
    public static final String GIO = "gio";

    public Context context;
    public GhiChuDB(@Nullable Context context) {
        super(context,DB_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String sql="Create table "+TB_NAME+"( "+ID+"integer primary key , "+TIEUDE+" text, "+NGAYTHANG+" text, "
//                +NOIDUNG+" text );";

        //Update thêm cột giờ
        String sql="Create table "+TB_NAME+"( "+ID+"integer primary key , "+TIEUDE+" text, "+NGAYTHANG+" text, "
                +NOIDUNG+" text, "+GIO+" text," + TGTHUC +" text);";
        sqLiteDatabase.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql ="DROP table if exists "+ TB_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
    public void insGhichu(GhiChu ghiChu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIEUDE,ghiChu.getTieude());
        contentValues.put(NGAYTHANG, ghiChu.getNgaythang());
        contentValues.put(NOIDUNG,ghiChu.getNoidung());
        contentValues.put(TGTHUC,ghiChu.getTgiannhap());

        //Update thêm cột giờ
        contentValues.put(GIO,ghiChu.getGio());


        SQLiteDatabase db=getWritableDatabase();
        long result=db.insert(TB_NAME,null,contentValues);
        if(result!=-1){
            Toast.makeText(context,"Insert success!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Insert fail!", Toast.LENGTH_LONG).show();
        }
    }
    public void updateGhichu(GhiChu ghiChu, String tieude){
        ContentValues contentValues=new ContentValues();
        contentValues.put(TIEUDE,ghiChu.getTieude());
        contentValues.put(NGAYTHANG,ghiChu.getNgaythang());
        contentValues.put(NOIDUNG,ghiChu.getNoidung());
        //Update thêm cột giờ
        contentValues.put(GIO,ghiChu.getGio());
        SQLiteDatabase db=getWritableDatabase();
        long result=db.update(TB_NAME,contentValues,TIEUDE+" like '%"+tieude+"%'",null);
        if(result>0){
            Toast.makeText(context,"update Success! ",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"update fail!",Toast.LENGTH_LONG).show();
        }
    }
    public void delGhiChu(String tieude){
        SQLiteDatabase db=getWritableDatabase();
        int result=db.delete(TB_NAME,TIEUDE+" = ?",new String[]{tieude});
        if(result>0){
            Toast.makeText(this.context,"delete success",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this.context,"delete fail",Toast.LENGTH_LONG).show();
        }
    }
    public ArrayList<GhiChu> getALLGhiChu(){
        ArrayList<GhiChu> result=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from "+TB_NAME,null);
        while (cursor.moveToNext()){
            GhiChu ghichu=new GhiChu();
            ghichu.setTieude(cursor.getString(1));
            ghichu.setNgaythang(cursor.getString(2));
            ghichu.setNoidung(cursor.getString(3));
            //Update thêm cột giờ
            ghichu.setGio(cursor.getString(4));
            ghichu.setTgiannhap(cursor.getString(5));
            result.add(ghichu);
        }
        return result;
    }

    public Boolean checkNgayThang(String ngaythang, String thoigian){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_ghichu where ngaythang=? and gio=?", new String[]{ngaythang,thoigian});
        if(cursor.getCount()>0)
            return true;
        return false;
    }

}
