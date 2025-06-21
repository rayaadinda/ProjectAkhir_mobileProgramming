// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.content.Context;
import android.content.SharedPreferences;
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
    
    private Context context;
    private static final String PREFS_NAME = "app_prefs";
    private static final String FIRST_TIME_KEY = "first_time";


public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
    
    // Check if it's first time and schedule sample data
    checkFirstTimeApp();
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
    
    private void checkFirstTimeApp() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(FIRST_TIME_KEY, true);
        
        if (isFirstTime) {
            // Mark first time as done immediately to prevent multiple insertions
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(FIRST_TIME_KEY, false);
            editor.apply();
            
            // Add sample data in a separate thread to avoid blocking UI
            new Thread(() -> {
                try {
                    // Give time for database to be created
                    Thread.sleep(500);
                    insertSampleData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    
    private void insertSampleData() {
     //mock data
        insertData(
            "Arc'teryx Beta AR Jacket",
            "Jacket",
            "9500000",
            "10",
            "arcteryx_betaar"
        );
        

        insertData(
            "Arc'teryx Gamma MX Hoody",
            "Jacket",
            "6800000",
            "8",
            "arcteryx_gamma"
        );
        

        insertData(
            "Arc'teryx Beta LT Jacket",
            "Jacket",
            "8750000",
            "12",
            "arcteryx_betalt"
        );
        

        insertData(
            "Carumby Drizzle Jacket",
            "Jacket",
            "850000",
            "25",
            "carumby_drizzle"
        );
        

        insertData(
            "Consina Alpine",
            "Tas Carrier",
            "895000",
            "22",
            "consina_alpine"
        );
        

        insertData(
            "Eiger Kinkajou",
            "Sandal",
            "189000",
            "50",
            "eiger_kinkajou"
        );
        

        insertData(
            "Osprey Stratos 50",
            "Tas Carrier",
            "3599000",
            "18",
            "osprey_stratos"
        );
        

        insertData(
            "Patagonia Torrentshell Jacket",
            "Jacket",
            "2100000",
            "20",
            "patagonia"
        );
        

        insertData(
            "Salomon Quest 4D Forces",
            "Sepatu",
            "3500000",
            "15",
            "salomon_quest_forces"
        );
        

        insertData(
            "Salomon XT-4",
            "Sepatu",
            "2900000",
            "30",
            "salomon_xt4"
        );
        

        insertData(
            "The North Face Vectiv",
            "Sepatu",
            "2750000",
            "28",
            "tnf_vectif"
        );
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

    public boolean updateData (String id, String nama, String harga, String foto, String jumlah, String kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_KATEGORI, kategori);
        values.put(COLUMN_HARGA, harga);
        values.put(COLUMN_JUMLAH, jumlah);
        values.put(COLUMN_FOTO, foto);
        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{id});
        return result > 0;
    }

    public Integer deletData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
    }
    
    public void deleteAllPakaian() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}

