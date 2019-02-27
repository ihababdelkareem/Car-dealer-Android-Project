package com.example.ihab.labproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ihab.labproject.models.Car;
import com.example.ihab.labproject.models.Profile;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "ProjectDB5", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CAR(ID INTEGER PRIMARY KEY UNIQUE,MAKE TEXT, MODEL TEXT,DISTANCE TEXT,PRICE INTEGER,ACCIDENTS TEXT,OFFERS TEXT)");
        db.execSQL("CREATE TABLE USER(EMAIL TEXT PRIMARY KEY UNIQUE,GENDER TEXT,ISADMIN TEXT,FIRSTNAME TEXT, LASTNAME  TEXT,PASSWORD TEXT,COUNTRY INTEGER,CITY TEXT,PHONE TEXT)");
        db.execSQL("CREATE TABLE RESERVE(EMAIL TEXT, CARID INTEGER, DATE TEXT)");
        db.execSQL("CREATE TABLE FAVORITE(EMAIL TEXT, CARID INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertCar(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", car.getId());
        contentValues.put("MAKE", car.getMake());
        contentValues.put("MODEL", car.getModel());
        contentValues.put("DISTANCE",car.getDistance());
        contentValues.put("PRICE", car.getPrice());
        contentValues.put("ACCIDENTS",car.isAccidents());
        contentValues.put("OFFERS", car.isOffers());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("CAR", null, contentValues);
    }

    public void insertFavorite(String email, int carid){
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",email);
        contentValues.put("CARID", carid);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("FAVORITE", null, contentValues);
    }

    public void insertReserve(String email, int carid,String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",email);
        contentValues.put("CARID", carid);
        contentValues.put("DATE",date);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("RESERVE", null, contentValues);
    }


    public void insertUser(Profile user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",user.getEmail());
        contentValues.put("ISADMIN", user.isAdmin());
        contentValues.put("GENDER", user.getGender());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("COUNTRY",user.getCountry());
        contentValues.put("CITY",user.getCity());
        contentValues.put("PHONE",user.getPhone());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("USER", null, contentValues);
    }
    public Cursor getAllCars(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM CAR",null); return cursor;
    }
    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM USER",null); return cursor;
    }

    public Cursor getAllReserves(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM RESERVE",null); return cursor;
    }

    public Cursor getAllFavorite(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM FAVORITE",null); return cursor;
    }
    public Cursor getUserByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE EMAIL = '"+email+"'",null);
        return cursor;
    }

    public Cursor getReservesByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM RESERVE WHERE EMAIL = '"+email+"'",null);
        return cursor;
    }


    public Cursor getFavByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM FAVORITE WHERE EMAIL = '"+email+"'",null);
        return cursor;
    }

    public Cursor getAllSpecialOffers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM CAR WHERE OFFERS = '"+1+"'",null);
        return cursor;
    }


    public void deleteAllCars(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete("CAR",null,null);
    }

    public void deleteByEmail(String email){
        System.out.println("DELETING : " + email);
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        sqLiteDatabase.execSQL("DELETE FROM USER  WHERE EMAIL = '"+email+"'",null);
        sqLiteDatabase.delete("USER","EMAIL="+"'"+email+"'",null);

    }
}
