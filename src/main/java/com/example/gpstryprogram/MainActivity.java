package com.example.gpstryprogram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView Lat;
    TextView Long;
    TextView dist;
    TextView time;
    int totalTime;
    Double LatNum, LongNum;
    LocationManager locationManager;
    Location currentLoc = null;
    ArrayList<Location> locations = new ArrayList<>();
    Double overallDistance = 0.0;
    LocationListener l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Lat = findViewById(R.id.textView2);
        Long = findViewById(R.id.textView4);
        dist = findViewById(R.id.textView);
        time = findViewById(R.id.textView3);

        Lat.setText("Latitude: ");
        Long.setText("Longitude: ");

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        l = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatNum = location.getLatitude();
                LongNum = location.getLongitude();
                Lat.setText("Latitude: " + location.getLatitude());
                Long.setText("Longitude: " + location.getLongitude());
                locations.add(location);


                currentLoc = location;

                Geocoder add = new Geocoder(getBaseContext(), Locale.US);
                List<Address> addresses = null;

                try {
                    addresses = add.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    //Lat.setText(String.valueOf(addresses.get(0).getAddressLine(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (locations.size() > 1) {
                    overallDistance += ((locations.get(locations.size() - 2).distanceTo(locations.get(locations.size() - 1))) / 1000) * .621371;
                    Log.d("TAG", "Total Distance: " + overallDistance);
                    totalTime += (int) (((locations.get(locations.size() - 2).getTime() - locations.get(locations.size() - 1).getTime())) * -1) / 1000;

                }

                dist.setText(overallDistance.toString());

                if (currentLoc != null) {
                    time.setText("Time Running: " + totalTime);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }


        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, l);

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, l);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
            }
        }
    }

    }





