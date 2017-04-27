package edu.utep.cs.cs4330.litterreporter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{
    double longi;
    double lati;
    Geocoder g;
    List<Address> addresses;
    Button cont;
    Button location;
    EditText city;
    EditText state;
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = (Button) findViewById(R.id.cont);
        location = (Button) findViewById(R.id.location);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        address = (EditText) findViewById(R.id.address);
        g = new Geocoder(MainActivity.this, Locale.getDefault());
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!city.getText().toString().equals("") && !state.getText().toString().equals("") && !address.getText().toString().equals("")) {
                    Intent i = new Intent(MainActivity.this, Report.class);
                    Bundle extras = new Bundle();
                    extras.putString("city", city.getText().toString());
                    extras.putString("state", state.getText().toString());
                    extras.putString("address", address.getText().toString());
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    toast("Fields incomplete");
                }
            }

        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, MainActivity.this);
                    longi = location.getLongitude();
                    lati = location.getLatitude();
                }catch (SecurityException e){
                    toast("no permissions");
                }
                try {
                    addresses = g.getFromLocation(lati, longi, 1);
                    String addr = addresses.get(0).getAddressLine(0);
                    String ct = addresses.get(0).getLocality();
                    String st = addresses.get(0).getAdminArea();
                    address.setText(addr);
                    state.setText(st);
                    city.setText(ct);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });
    }
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
