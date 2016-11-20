package com.johnwayodi.cyclelogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.johnwayodi.cyclelogger.database.CLDatabase;
import com.johnwayodi.cyclelogger.services.GPS_Service;

public class CycleLogActivity extends AppCompatActivity {

    Button start_service, stop_service;
    TextView coordinates;
    BroadcastReceiver broadcastReceiver;
//    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_log);

        start_service = (Button) findViewById(R.id.start_service_btn);
        stop_service = (Button) findViewById(R.id.stop_service_btn);
        coordinates = (TextView) findViewById(R.id.coordinates_view);

        start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start the gps service
                Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
                startService(intent);
            }
        });

        stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // stop the gps service
                Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
                stopService(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // create new broadcast receiver if not present
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

//                    latitude = (Double) intent.getExtras().get("latitude");
//                    longitude = (Double) intent.getExtras().get("longitude");

//                    //add values to database
//                    CLDatabase dbAdapter = new CLDatabase(getBaseContext());
//                    dbAdapter.open();
//                    dbAdapter.addLocation(latitude, longitude);
//                    dbAdapter.close();

                    // append results to map the text view
                    coordinates.append("\nUpdated Coordinates: \n"
                            + "latitude: " + intent.getExtras().get("latitude")
                            + "\nlongitude: " + intent.getExtras().get("longitude"));

//                    // append results to map the text view
//                    coordinates.append("\nUpdated Coordinates: \n"
//                            + "latitude: "+ latitude
//                            + "\nlongitude: "+ longitude);




                }
            };
        }
        //
        registerReceiver(broadcastReceiver, new IntentFilter("location_details"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
