package com.johnwayodi.cyclelogger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.johnwayodi.cyclelogger.models.MyLocation;
import com.johnwayodi.cyclelogger.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnwayodi on 11/15/16.
 */

public class CLDatabase {

    // TODO: CREATE ANOTHER TABLE TO STORE THE VARIOUS TRIPS
    private static final String DATABASE_NAME = "cyclelog.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CREATE_COORDINATES_TABLE = "create table " + CoordinatesTable.TABLE_NAME + " ( "
            + CoordinatesTable.ID + " integer primary key autoincrement, "
            + CoordinatesTable.LATITUDE + " text not null, "
            + CoordinatesTable.LONGITUDE + " text not null" + ");";

    public static final String CREATE_TRIPS_TABLE = "create table " + TripsTable.TABLE_NAME + " ( "
            + TripsTable.ID + " integer primary key autoincrement, "
            + TripsTable.DISTANCE + " text not null " + ");";

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

    // add new location to database
    public MyLocation addLocation(double lat, double lng){
        ContentValues values = new ContentValues();
        values.put(CoordinatesTable.LATITUDE, lat);
        values.put(CoordinatesTable.LONGITUDE, lng);

        long insertId = sqLiteDatabase.insert(CoordinatesTable.TABLE_NAME, null, values);

        Cursor cursor = sqLiteDatabase.query(CoordinatesTable.TABLE_NAME, CoordinatesTable.allColumns, CoordinatesTable.ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        MyLocation newLocation = cursorToLocation(cursor);
        cursor.close();
        return newLocation;
    }

    // add new trip to database
    public Trip addTrip(double distance){
        ContentValues values = new ContentValues();
        values.put(TripsTable.DISTANCE, distance);

        long insertId = sqLiteDatabase.insert(TripsTable.TABLE_NAME, null, values);

        Cursor cursor = sqLiteDatabase.query(TripsTable.TABLE_NAME, TripsTable.allColumns, TripsTable.ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Trip newTrip = cursorToTrip(cursor);
        cursor.close();
        return newTrip;
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

    private Trip cursorToTrip(Cursor cursor){
        Trip newTrip = new Trip(
                cursor.getDouble(1),
                cursor.getLong(2));

        return newTrip;
    }

    // Getting all location coordinates from database
    public ArrayList<MyLocation> getAllCoordinates() {

        ArrayList<MyLocation> dataList = new ArrayList<>();

        //  Select All Query
        String selectQuery = "SELECT * FROM " + CoordinatesTable.TABLE_NAME;

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

    public ArrayList<Trip> getAllTrips(){
        ArrayList<Trip> tripList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TripsTable.TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        // loop through all rows and add trips to triplist
        if (cursor.moveToFirst()) {
            do {
                Trip data = new Trip();
                data.setDist( cursor.getDouble(0));
                data.setId( cursor.getLong(2));
                //   Adding a trip to triplist
                tripList.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return a list of trips
        return tripList;
    }

    private static class DbHelper extends SQLiteOpenHelper{

        DbHelper(Context context1){
            super(context1, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_COORDINATES_TABLE);
            db.execSQL(CREATE_TRIPS_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //delete the note table and create new one
            db.execSQL("DROP TABLE IF EXISTS " + CoordinatesTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TripsTable.TABLE_NAME);
            onCreate(db);

        }
    }
}
