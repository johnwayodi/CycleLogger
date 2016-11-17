package com.johnwayodi.cyclelogger.models;

/**
 * Created by johnwayodi on 11/15/16.
 */

public class MyLocation {
    long _id;
    double _lat;
    double _lng;

    // Empty constructor
    public MyLocation(){

    }

    //constructor
    public MyLocation(double lat, double lng){
        this._lat = lat;
        this._lng = lng;
    }
    // constructor
    public MyLocation(double lat, double lng, long id){
        this._lat=lat;
        this._lng=lng;
        this._id = id;
    }

    public double getLat(){
        return this._lat;
    }

    public void setLat(double lat){
        this._lat = lat;
    }

    public double getLng(){
        return this._lng;
    }

    public void setLng(double lng){
        this._lng = lng;
    }

    // getting ID
    public long getId(){
        return this._id;
    }

    // setting id
    public void setId(long id){
        this._id = id;
    }

}
