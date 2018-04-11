package com.example.unsan.easybuygps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Unsan on 9/4/18.
 */

public class CustomerDetail extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,AdapterView.OnItemSelectedListener {
    private FusedLocationProviderClient mFusedLocationClient;
    TextView name, phone, address, journeytxt,restName,zipText;
    final int REQUEST_CHECK_SETTINGS = 125;
    Button start, reached;
    Customer c;
    String timestart, date;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    String carNumber;

    FirebaseDatabase fbdr;
    String mAddressOutput;
    String node;
    double dlat,dlng;
    DatabaseReference startReference,trackingReference;
    double longitude, latitude;
    String startAddress;
    LocationManager lm;
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    SharedPreferences sharedPreferences;
    List<String> carList;
    CustomerNode cn;



    public static String REQUESTING_LOCATION_UPDATES_KEY="location_upd";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);
        sharedPreferences=getSharedPreferences("location_gpst", Context.MODE_PRIVATE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mResultReceiver = new AddressResultReceiver(null);
        carList=new ArrayList<>();

        spinner=(Spinner) findViewById(R.id.sp1);
        restName=(TextView) findViewById(R.id.rest_name);
        zipText=(TextView) findViewById(R.id.zip);

        fbdr = FirebaseDatabase.getInstance();
        startReference = fbdr.getReference("Started");
        trackingReference=fbdr.getReference("Tracking");
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        start = (Button) findViewById(R.id.start);
        reached = (Button) findViewById(R.id.reached);
        journeytxt = (TextView) findViewById(R.id.journryst);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        for(int i=1;i<=30;i++)
        {
            carList.add("car "+i);

        }
        Intent intent = getIntent();
         cn = (CustomerNode) intent.getSerializableExtra("customernd");
        c=cn.getCustomer();
        name.setText(c.getContactPerson());
        phone.setText(c.getContactNumber()+"");
        address.setText(c.getAddress());

        restName.setText(cn.getRestaurantName());
        zipText.setText(c.getZip()+"");


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            Log.d("checklatlong", longitude + " " + latitude);

                            mLastLocation = location;

                            startIntentService();
                         /*   Geocoder geocoder = new Geocoder(CustomerDetail.this, Locale.getDefault());
                            try {
                                Log.d("herec",latitude+" "+longitude);
                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                Log.d("checkss",addressList.size()+" ");
                                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                if(addressList.size()>0) {
                                    startAddress = addressList.get(0).getAddressLine(0);
                                    Log.d("getlocation", startAddress);
                                }
                            }
                            catch(IOException e)
                            {
                                e.printStackTrace();
                            }
                            //startIntentService();
                            // Logic to handle location object
                        }
                        */

                        }

                    }
                });
        Geocoder geocoder = new Geocoder(CustomerDetail.this, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocationName(c.Address, 1);
            if ((addresses != null) && (addresses.size() > 0)) {
                Address fetchedAddress = addresses.get(0);
                dlat = fetchedAddress.getLatitude();
                dlng = fetchedAddress.getLongitude();
                Log.v("try-if", "ok great work");
            } else {
                Log.v("try-else", "something wrong");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("catch", "Could not get address....!");
        }
     /*   Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        */


        //  createLocationRequest();
        // if(globalProvider.isStarted()==true)
        if(sharedPreferences.getBoolean("started",false))
        {
            start.setEnabled(false);
            reached.setVisibility(View.VISIBLE);
        }
        arrayAdapter=new ArrayAdapter<String>(CustomerDetail.this,R.layout.support_simple_spinner_dropdown_item,carList);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(CustomerDetail.this);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat simpleD = new SimpleDateFormat("dd/MM/yyyy");
                date = simpleD.format(time);
                long tm=-1 * new Date().getTime();

                //globalProvider.setCustomer(c);




                timestart = simpleDateFormat.format(time);
               // String car = c.carNumber.replaceAll("\\s", "");
               // node = car + time;
                // globalProvider.setNode(node);
                start.setText("Trip Started");
                start.setEnabled(false);
                //globalProvider.setStarted(true);


                StartJourney startJourney = new StartJourney(carNumber, date, timestart, startAddress, c.Address, false,tm);
                node = startReference.push().getKey();
                startReference.child(node).setValue(startJourney);
                // globalProvider.setStartingAddress(startAddress);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("started",true);
                editor.putString("node",node);
                Gson gson = new Gson();
                String json = gson.toJson(cn);
                editor.putString("CustomerNode", json);
                editor.putString("startAddress",startAddress);
                editor.putString("startTime",timestart);
                editor.putString("carNumber",carNumber);
                editor.commit();


                Tracking tracking=new Tracking(carNumber,latitude,longitude,dlat,dlng,c.ContactPerson);
                trackingReference.child(node).setValue(tracking);
                journeytxt.setVisibility(View.VISIBLE);
                journeytxt.setText("Journey started at: " + timestart);
                reached.setVisibility(View.VISIBLE);
                Intent intent=new Intent(CustomerDetail.this,MyService.class);
                intent.putExtra("node",node);
                startService(intent);
            }
        });
        reached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(CustomerDetail.this, DestinationActivity.class);
                intent.putExtra("customerds", cn);
                intent.putExtra("node", node);
                intent.putExtra("startLocation",startAddress);

                startActivity(intent);
            }
        });



    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode > 0) {

                }
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        // if(globalProvider.isStarted()==true)
        if(sharedPreferences.getBoolean("started",false))
        {
            Intent intent=new Intent(CustomerDetail.this,MainPage.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        carNumber=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /*  protected void startIntentService() {
          Intent intent = new Intent(this, FetchAddressIntentService.class);
          intent.putExtra(Constants.RECEIVER, mResultReceiver);
          intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
          startService(intent);
      }
      */
    class  AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            if (mAddressOutput == null) {
                mAddressOutput = "";
            }


            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(CustomerDetail.this,"address foundc"+mAddressOutput,Toast.LENGTH_SHORT).show();
                startAddress=mAddressOutput;
            }

        }
    }

}
