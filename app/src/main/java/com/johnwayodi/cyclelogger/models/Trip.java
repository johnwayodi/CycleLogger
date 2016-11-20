package com.johnwayodi.cyclelogger.models;

/**
 * Created by johnwayodi on 11/20/16.
 */

public class Trip {
    long _id;
    double _dist;

    // Empty constructor
    public Trip(){

    }

    //constructor
    public Trip(double dist){
        this._dist = dist;
    }
    // constructor
    public Trip(double dist, long id){
        this._dist = dist;
        this._id = id;
    }

    public double getDist(){
        return this._dist;
    }

    public void setDist(double dist){
        this._dist = dist;
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
