package com.johnwayodi.cyclelogger.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.johnwayodi.cyclelogger.database.CLDatabase;

/**
 * Created by johnwayodi on 11/15/16.
 */

public class GPS_Service extends Service {

    private LocationListener locationListener;
    private LocationManager locationManager;
    private Double currentLatitude, currentLongitude;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                //write values to database
                //CLDatabase dbAdapter = new CLDatabase(getApplicationContext());
                //dbAdapter.open();
                //dbAdapter.addLocation(currentLatitude,currentLongitude);
                //dbAdapter.close();

                //create an intent to broadcast both latitude and longitude
                Intent intent = new Intent("location_details");
                intent.putExtra("latitude", currentLatitude);
                intent.putExtra("longitude", currentLongitude);
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                // direct user to the location settings panel.
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {

            //noinspection MissingPermission
            locationManager.removeUpdates(locationListener);
        }
    }
}
