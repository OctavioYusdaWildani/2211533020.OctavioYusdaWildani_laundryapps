package com.mylauvi.laundryapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mylauvi.laundryapps.model.ModelPelanggan;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my_laundry.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PELANGGAN = "pelanggan";
    public static final String KEY_PELANGGAN_ID = "pelanggan_id";
    public static final String KEY_PELANGGAN_NAMA = "nama";
    public static final String KEY_PELANGGAN_EMAIL = "email";
    public static final String KEY_PELANGGAN_HP = "hp";
    private static final String CREATE_TABLE_PELANGGAN = "CREATE TABLE " +
            TABLE_PELANGGAN + "("
            + KEY_PELANGGAN_ID + " TEXT, " + KEY_PELANGGAN_NAMA + " TEXT, "+
            KEY_PELANGGAN_EMAIL + " TEXT, "+KEY_PELANGGAN_HP +" TEXT )";
    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PELANGGAN);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELANGGAN);
        onCreate(db);
    }
    public boolean insertPelanggan(ModelPelanggan mp){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PELANGGAN_ID, mp.getId());
        contentValues.put(KEY_PELANGGAN_NAMA, mp.getNama());
        contentValues.put(KEY_PELANGGAN_EMAIL, mp.getEmail());
        contentValues.put(KEY_PELANGGAN_HP, mp.getHp());
        long id = database.insert(TABLE_PELANGGAN, null, contentValues);
        database.close();
        if (id != -1){
            return true;
        }else{
            return false;
        }
    }
    public List<ModelPelanggan> getPelanggan(){
        List<ModelPelanggan> pel = new ArrayList<ModelPelanggan>();
        String query = "SELECT * FROM " + TABLE_PELANGGAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                ModelPelanggan k = new ModelPelanggan();
                k.setId(cursor.getString(0));
                k.setNama(cursor.getString(1));
                k.setEmail(cursor.getString(2));
                k.setHp(cursor.getString(3));
                pel.add(k);
            }while (cursor.moveToNext());
        }
        return pel;
    }
    public ModelPelanggan getPelangganById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PELANGGAN + " WHERE " + KEY_PELANGGAN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        ModelPelanggan pelanggan = null;
        if (cursor.moveToFirst()){
            pelanggan = new ModelPelanggan();
            pelanggan.setId(cursor.getString(0));
            pelanggan.setNama(cursor.getString(1));
            pelanggan.setEmail(cursor.getString(2));
            pelanggan.setHp(cursor.getString(3));
        }
        cursor.close();
        return pelanggan;
    }
    // Memperbarui data pelanggan
    public boolean updatePelanggan(ModelPelanggan mp) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PELANGGAN_NAMA, mp.getNama());
        contentValues.put(KEY_PELANGGAN_EMAIL, mp.getEmail());
        contentValues.put(KEY_PELANGGAN_HP, mp.getHp());
        int rows = database.update(TABLE_PELANGGAN, contentValues, KEY_PELANGGAN_ID + " = ?", new String[]{mp.getId()});
        database.close();
        return rows > 0;
    }

    public boolean deletePelanggan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PELANGGAN, KEY_PELANGGAN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }

}
