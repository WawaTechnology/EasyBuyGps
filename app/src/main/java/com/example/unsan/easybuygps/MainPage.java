package com.example.unsan.easybuygps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Unsan on 9/4/18.
 */

public class MainPage extends AppCompatActivity {
    Button startButton,onGoingButton,historyButton,cancelTripButton;

    //  GlobalProvider globalProvider;
    SharedPreferences sharedPreferences;
    DatabaseReference startedRef,trackingRef;
    String node;




    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        startButton=(Button)findViewById(R.id.start_trip);
        onGoingButton=(Button)findViewById(R.id.ongoing);
        historyButton=(Button)findViewById(R.id.history);
        cancelTripButton=(Button)findViewById(R.id.cancel_trip);
        FirebaseDatabase fbd=FirebaseDatabase.getInstance();
        startedRef=fbd.getReference("Started");
        trackingRef=fbd.getReference("Tracking");
        sharedPreferences=getSharedPreferences("location_gpst", Context.MODE_PRIVATE);

        //globalProvider=GlobalProvider.getGlobalInstance(MainPage.this);
       /* if(globalProvider.isStarted()==true)
        {
            startButton.setEnabled(false);
        }
        */
        if(sharedPreferences.getBoolean("started",false))
        {
            startButton.setEnabled(false);
        }
        cancelTripButton.setEnabled(false);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainPage.this,HomePage.class);
                startActivity(intent);
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainPage.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        onGoingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(globalProvider.getNode()==null)
                if(!sharedPreferences.getBoolean("started",false))
                {
                    Toast.makeText(MainPage.this,"No Ongoing Trip",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(MainPage.this, MapsActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
    public void onResume()
    {
        super.onResume();
        if(!startButton.isEnabled())
        {
            cancelTripButton.setEnabled(true);
        }
        cancelTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setEnabled(true);
                cancelTripButton.setEnabled(false);
                Intent intent = new Intent(MainPage.this, MyService.class);
                stopService(intent);
                node=sharedPreferences.getString("node",null);
                if(node!=null) {
                    startedRef.child(node).setValue(null);
                    trackingRef.child(node).setValue(null);
                }

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("started", false);
                editor.putString("node", null);
                editor.putString("CustomerNode", null);
                editor.putString("startTime",null);
                editor.putString("carNumber",null);


                editor.commit();
            }
        });
    }
}
