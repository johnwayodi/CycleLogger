package com.johnwayodi.cyclelogger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.johnwayodi.cyclelogger.models.MyLocation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by johnwayodi on 11/15/16.
 */

public class CLDatabase {

    // TODO: CREATE ANOTHER TABLE TO STORE THE VARIOUS TRIPS
    private static final String DATABASE_NAME = "cyclelog.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COORDINATES_TABLE = "coordinates";
    private static final String COORDINATES_ID = "_id";
    private static final String COORDINATES_LAT = "latitude";
    private static final String COORDINATES_LONG = "longitude";

    private String[] allColumns = {COORDINATES_ID, COORDINATES_LAT, COORDINATES_LONG};

    public static final String CREATE_COORDINATES_TABLE = "create table " + COORDINATES_TABLE + " ( "
            + COORDINATES_ID + " integer primary key autoincrement, "
            + COORDINATES_LAT + " text not null, "
            + COORDINATES_LONG + " text not null" + ");";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DbHelper dbHelper;

    public CLDatabase(Context ctx){
        context = ctx;
    }

    //open the database for writing operations
    public CLDatabase open() throws android.database.SQLException{
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    //adding a new location to database
    public MyLocation addLocation(double lat, double lng){
        ContentValues values = new ContentValues();
        values.put(COORDINATES_LAT, lat);
        values.put(COORDINATES_LONG, lng);

        long insertId = sqLiteDatabase.insert(COORDINATES_TABLE, null, values);

        Cursor cursor = sqLiteDatabase.query(COORDINATES_TABLE, allColumns, COORDINATES_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        MyLocation newLocation = cursorToLocation(cursor);
        cursor.close();
        return newLocation;
    }


    //close the database after writing operations
    public void close(){
        dbHelper.close();
    }

    private MyLocation cursorToLocation(Cursor cursor){
        MyLocation newLocation = new MyLocation(
                cursor.getDouble(1),
                cursor.getDouble(2),
                cursor.getLong(3));

        return newLocation;
    }

//    // Getting one database object by id
//    public MyLocation get_singleLocation(long id) {
//
//        Cursor cursor = sqLiteDatabase.query(COORDINATES_TABLE, allColumns, COORDINATES_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null,null,null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        MyLocation singleLocation = new MyLocation(
//                cursor.getDouble(0),
//                cursor.getDouble(1),
//                cursor.getLong(2));
//        // return database object
//        return singleLocation;
//    }

    // Getting all location coordinates from database
    public ArrayList<MyLocation> getAllCoordinates() {

        ArrayList<MyLocation> dataList = new ArrayList<>();

        //  Select All Query
        String selectQuery = "SELECT * FROM " + COORDINATES_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding locations to list
        if (cursor.moveToFirst()) {
            do {
                MyLocation data = new MyLocation();
                data.setLat( cursor.getDouble(0));
                data.setLng( cursor.getDouble(1));
                data.setId( cursor.getLong(2));
                //   Adding a location to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return a list of coordinates
        return dataList;
    }

    private static class DbHelper extends SQLiteOpenHelper{

        DbHelper(Context context1){
            super(context1, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_COORDINATES_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //delete the note table and create new one
            db.execSQL("DROP TABLE IF EXISTS " + COORDINATES_TABLE);
            onCreate(db);

        }
    }
}
