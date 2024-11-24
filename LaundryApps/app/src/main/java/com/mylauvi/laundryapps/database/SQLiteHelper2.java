package com.mylauvi.laundryapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mylauvi.laundryapps.model.ModelLayanan;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper2 extends SQLiteOpenHelper {
    public static String DATABASE_NAME = " my_laundry.db";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_LAYANAN = "layanan";
    public static final String KEY_LAYANAN_ID = "layanan_id";
    public static final String KEY_LAYANAN_TIPE = "tipeLayanan";
    public static final String KEY_LAYANAN_HARGA = "harga";
    private static final String CREATE_TABLE_LAYANAN = "CREATE TABLE " + TABLE_LAYANAN + "(" + KEY_LAYANAN_ID +
            " TEXT, " + KEY_LAYANAN_TIPE + " TEXT, " + KEY_LAYANAN_HARGA + " TEXT )";

    public SQLiteHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_LAYANAN);
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAYANAN);
        onCreate(db);
    }

    public boolean insertLayanan(ModelLayanan ml) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LAYANAN_ID, ml.getId());
        contentValues.put(KEY_LAYANAN_TIPE, ml.getTipe());
        contentValues.put(KEY_LAYANAN_HARGA, ml.getHarga());
        long id = database.insert(TABLE_LAYANAN, null, contentValues);
        database.close();
        if(id != -1){
            return true;
        }else {
            return false;
        }
    }
    public List<ModelLayanan> getLayanan(){
        List<ModelLayanan> lay = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LAYANAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                ModelLayanan k = new ModelLayanan();
                k.setId(cursor.getString(0));
                k.setTipe(cursor.getString(1));
                k.setHarga(cursor.getString(2));
                lay.add(k);
            }while (cursor.moveToNext());
        }
        return lay;
    }
    public boolean deleteLayanan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LAYANAN, KEY_LAYANAN_ID + "=?", new String[]{id}) > 0;
    }
    public boolean updateLayanan(String id, String tipe, String harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LAYANAN_TIPE, tipe);  // Memperbarui tipe layanan
        contentValues.put(KEY_LAYANAN_HARGA, harga); // Memperbarui harga layanan

        // Melakukan update berdasarkan id layanan
        int rowsAffected = db.update(TABLE_LAYANAN, contentValues, KEY_LAYANAN_ID + " = ?", new String[]{id});
        db.close();
        return rowsAffected > 0;  // Jika ada data yang diupdate, return true
    }

    // Fungsi untuk mendapatkan data layanan berdasarkan ID
    public ModelLayanan getLayananById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LAYANAN, null, KEY_LAYANAN_ID + " = ?", new String[]{id}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            ModelLayanan layanan = new ModelLayanan();
            layanan.setId(cursor.getString(0));
            layanan.setTipe(cursor.getString(1));
            layanan.setHarga(cursor.getString(2));
            cursor.close();
            return layanan;
        }
        return null; // Jika tidak ditemukan
    }

}
