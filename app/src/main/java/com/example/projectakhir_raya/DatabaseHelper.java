package com.example.projectakhir_raya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pakaian.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "pakaian";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_KATEGORI = "kategori";
    private static final String COLUMN_HARGA = "harga";
    private static final String COLUMN_JUMLAH = "jumlah";
    private static final String COLUMN_FOTO = "foto";


public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA + " TEXT, " +
                COLUMN_KATEGORI + " TEXT, " +
                COLUMN_HARGA + " TEXT, " +
                COLUMN_JUMLAH + " TEXT, " +
                COLUMN_FOTO + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean insertData (String nama, String kategori, String harga, String jumlah, String foto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_KATEGORI, kategori);
        values.put(COLUMN_HARGA, harga);
        values.put(COLUMN_JUMLAH, jumlah);
        values.put(COLUMN_FOTO, foto);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateData (String id, String nama, String harga, String foto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_HARGA, harga);
        values.put(COLUMN_FOTO, foto);
        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{id});
        return result > 0;
    }

    public Integer deletData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
    }
}

