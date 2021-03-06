package com.se17.attendancesystem;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class GPSStudentActivity extends AppCompatActivity implements LocationListener{

    private Button btnGPS;
    private double latitude = 0;
    private double longitude = 0;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsstudent);
        btnGPS = (Button) findViewById(R.id.btnGPSStud);
        btnGPS.setOnClickListener(btnGPSListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    }

    private Location getCurrentLocation(){

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null ;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location==null){
            Toast.makeText(getApplicationContext(), "Enable GPS and try again", Toast.LENGTH_LONG).show();
        }else {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Toast.makeText(getApplicationContext(), "" + latitude + "," + longitude, Toast.LENGTH_LONG).show();
        }

        return location;

    }


    private View.OnClickListener btnGPSListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Location loc = getCurrentLocation();
            if(loc==null || latitude==0 || longitude==0)
                return;

            ServerComm serverComm = new ServerComm();
            String result = "";
            try {
                result = serverComm.execute("2", MainActivity.user.getUserId(), MainActivity.user.getPassword(),
                        "" + latitude, "" + longitude).get();
            }catch (InterruptedException ex){
                Toast.makeText(getApplicationContext(),"Attendance not registered, try again",Toast.LENGTH_SHORT).show();
                return;
            }catch (ExecutionException ex){
                Toast.makeText(getApplicationContext(),"Attendance not registered, try again",Toast.LENGTH_SHORT).show();
                return;
            }

            if(result==null){
                Toast.makeText(getApplicationContext(),"Check your Internet Connection",Toast.LENGTH_LONG).show();
                return;
            }

            if(result.equalsIgnoreCase("successful")){
                Toast.makeText(getApplicationContext(),"Attendance Registered successfully!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Attendance not registered, try again",Toast.LENGTH_SHORT).show();
            }

        }
    };


    @Override
    public void onProviderEnabled(String provider) {

        getCurrentLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        getCurrentLocation();
    }

    @Override
    public void onLocationChanged(Location location) {

        getCurrentLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        getCurrentLocation();
    }
}
