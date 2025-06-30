// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pakaian.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "pakaian";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_KATEGORI = "kategori";
    private static final String COLUMN_HARGA = "harga";
    private static final String COLUMN_JUMLAH = "jumlah";
    private static final String COLUMN_FOTO = "foto";

    // User table constants
    private static final String TABLE_USER = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // SharedPreferences constants
    private static final String PREF_NAME = "UserSessionPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";

    private Context context;
    private static final String PREFS_NAME = "app_prefs";
    private static final String FIRST_TIME_KEY = "first_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        
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


        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_EMAIL + " TEXT UNIQUE, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);
    }

    private void checkFirstTimeApp() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(FIRST_TIME_KEY, true);

        if (isFirstTime) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(FIRST_TIME_KEY, false);
            editor.apply();

            new Thread(() -> {
                try {
                    
                    Thread.sleep(500);
                    insertSampleData();

                    registerUser("admin@example.com", "1234");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void insertSampleData() {
        insertData(
                "Arc'teryx Beta AR Jacket",
                "Jacket",
                "9500000",
                "10",
                "arcteryx_betaar");

        insertData(
                "Arc'teryx Gamma MX Hoody",
                "Jacket",
                "6800000",
                "8",
                "arcteryx_gamma");

        insertData(
                "Arc'teryx Beta LT Jacket",
                "Jacket",
                "8750000",
                "12",
                "arcteryx_betalt");

        insertData(
                "Carumby Drizzle Jacket",
                "Jacket",
                "850000",
                "25",
                "carumby_drizzle");

        insertData(
                "Consina Alpine",
                "Tas Carrier",
                "895000",
                "22",
                "consina_alpine");

        insertData(
                "Eiger Kinkajou",
                "Sandal",
                "189000",
                "50",
                "eiger_kinkajou");

        insertData(
                "Osprey Stratos 50",
                "Tas Carrier",
                "3599000",
                "18",
                "osprey_stratos");

        insertData(
                "Patagonia Torrentshell Jacket",
                "Jacket",
                "2100000",
                "20",
                "patagonia");

        insertData(
                "Salomon Quest 4D Forces",
                "Sepatu",
                "3500000",
                "15",
                "salomon_quest_forces");

        insertData(
                "Salomon XT-4",
                "Sepatu",
                "2900000",
                "30",
                "salomon_xt4");

        insertData(
                "The North Face Vectiv",
                "Sepatu",
                "2750000",
                "28",
                "tnf_vectif");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertData(String nama, String kategori, String harga, String jumlah, String foto) {
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

    public boolean updateData(String id, String nama, String harga, String foto, String jumlah, String kategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_KATEGORI, kategori);
        values.put(COLUMN_HARGA, harga);
        values.put(COLUMN_JUMLAH, jumlah);
        values.put(COLUMN_FOTO, foto);
        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[] { id });
        return result > 0;
    }

    public Integer deletData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { id });
    }

    public void deleteAllPakaian() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { COLUMN_USER_ID },
                COLUMN_USER_EMAIL + "=?", new String[] { email }, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return false; 
        }

        if (cursor != null) {
            cursor.close();
        }

 
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password); 

        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

   
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { COLUMN_USER_ID };
        String selection = COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(
                TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }


    public int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { COLUMN_USER_ID },
                COLUMN_USER_EMAIL + "=?", new String[] { email }, null, null, null);

        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            cursor.close();
        }

        return id;
    }




    public void createLoginSession(Context context, String email, int userId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putInt(KEY_USER_ID, userId);

        editor.apply();
    }


    public boolean isLoggedIn(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public String getLoggedInEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_USER_EMAIL, null);
    }

    public int getLoggedInUserId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(KEY_USER_ID, -1);
    }


    public void logoutUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        editor.apply();
    }
}
