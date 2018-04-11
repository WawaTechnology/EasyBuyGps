package com.example.unsan.easybuygps;

/**
 * Created by Unsan on 9/4/18.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MyService extends Service
{
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    DatabaseReference trackingRef;
    String node;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            //  Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            //  Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            trackingRef.child(node).child("latitude").setValue(location.getLatitude());
            trackingRef.child(node).child("longitude").setValue(location.getLongitude());

        }

        @Override
        public void onProviderDisabled(String provider)
        {
            // Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            // Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            // Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        SharedPreferences sharedPreferences=getSharedPreferences("location_gpst", Context.MODE_PRIVATE);
        node=sharedPreferences.getString("node",null);
        // node=GlobalProvider.getGlobalInstance(getApplicationContext()).getNode();
        // node=intent.getStringExtra("node");
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        // Log.e(TAG, "onCreate");
        trackingRef= FirebaseDatabase.getInstance().getReference("Tracking");

        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            // Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            // Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            //Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            //Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        // Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    //Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
            mLocationManager=null;
            SharedPreferences sharedPreferences=getSharedPreferences("location_gpst",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("started", false);
            editor.putString("node", null);
            editor.putString("CustomerNode", null);
            editor.putString("startTime",null);
            editor.putString("carNumber",null);
            editor.commit();

        }
    }

    private void initializeLocationManager() {
        //Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
