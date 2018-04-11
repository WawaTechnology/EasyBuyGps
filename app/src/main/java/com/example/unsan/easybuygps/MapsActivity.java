package com.example.unsan.easybuygps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    MarkerOptions mo;
    Marker marker;
    Button destreached;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        destreached=(Button)findViewById(R.id.reached_map);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mo=new MarkerOptions().position(new LatLng(1.290270,103.851959)).title("My Current Location");
        requestLocation();
        destreached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                locationManager.removeUpdates(MapsActivity.this);
                locationManager=null;
               Intent intent=new Intent(MapsActivity.this,DestinationActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng singapore=new LatLng(1.290270,103.851959);
        mMap = googleMap;
        // mMap.addMarker(new MarkerOptions().position(singapore));

        marker=mMap.addMarker(mo);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(singapore));


        // Add a marker in Sydney and move the camera

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("checklocatlat",location.getLatitude() +" "+location.getLongitude());
        LatLng myCoordinates=new LatLng(location.getLatitude(),location.getLongitude());
        marker.setPosition(myCoordinates);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(myCoordinates.latitude,myCoordinates.longitude) , 14.0f) );

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.d("permission not","granted");
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("requestlocation","called");
        locationManager.requestLocationUpdates(provider, 1000, 10, this);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);


    }
}
