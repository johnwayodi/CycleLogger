package com.johnwayodi.cyclelogger;

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

public class MainActivity extends AppCompatActivity {

    private Button start_clog, view_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: LOWER OR REMOVE GOOGLE SERVICES( NOT NEEDED).
        //TODO: FIND WAY TO CALL SPLASH SCREEN ONLY ONCE.

        start_clog = (Button) findViewById(R.id.cycle_log_btn);
        view_log = (Button) findViewById(R.id.log_button);

        start_clog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if permissions are enabled in device
                if (!check_permissions()){

                    // initialize the CycleLogActivity
                    start_cycle_log_activity();
                }
                else
                    check_permissions();
            }
        });

        view_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_log_activity();
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

    //take user to the log activity
    private void start_log_activity() {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
