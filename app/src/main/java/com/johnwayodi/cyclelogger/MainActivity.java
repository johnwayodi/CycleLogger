package com.johnwayodi.cyclelogger;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private Button start_clog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: FIND ERROR THAT CAUSES ACTIVITY NOT TO LAUNCH.
        //TODO: LOWER THE GOOGLE SERVICES VERSION(TOO LATEST).
        if (gServicesAvailable()){
            Toast.makeText(this, "Play Services Available", Toast.LENGTH_LONG).show();
        }


        start_clog = (Button) findViewById(R.id.cycle_log_btn);

        start_clog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if permissions are enabled in device
                if (!check_permissions()){

                    // initialize the CycleLogActivity
                    start_cycle_log_activity();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //take user to the cyclelog activity
    private void start_cycle_log_activity() {
        Intent intent = new Intent(this, CycleLogActivity.class);
        startActivity(intent);
    }


    // check if google services is available on the device
    public boolean gServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }else {
            Toast.makeText(this, "Cannot connect to Play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    // permission request runtime feature for api 23 and above
    public boolean check_permissions(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                // permissions are granted so just go to cyclelog activity
                start_clog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            // initialize the CycleLogActivity
                            start_cycle_log_activity();
                    }
                });

            }else {
                // if permission not granted just check permissions again
                check_permissions();
            }
        }
    }
}
