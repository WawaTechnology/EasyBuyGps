package com.example.unsan.easybuygps;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ed1,ed2;
    Button submit;
    ImageView deleteEmail,deletePsd;
    private static final int REQUEST = 112;
    private static final int PERMISSION_REQUEST_CODE=124;
    final static String[] PERMISSIONS={android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.email);
        ed2=(EditText)findViewById(R.id.psd);
        submit=(Button)findViewById(R.id.submit);
        deleteEmail=(ImageView)findViewById(R.id.deleteEmail);
        deletePsd=(ImageView)findViewById(R.id.deletePsd);
        deleteEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed1.setText("");
            }
        });
        deletePsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed2.setText("");
            }
        });
        sharedPreferences=getSharedPreferences("location_gpst", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("isLogin",false))
        {
            Intent intent =new Intent(MainActivity.this,MainPage.class);
            startActivity(intent);
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionGpsCheck=ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        // Log.d("checkpermission"," "+permissionCheck);
        if(permissionCheck==-1||permissionGpsCheck==-1)
        {
            requestPermission();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed1.getText().length()<=0||ed2.getText().length()<=0)
                {
                    Toast.makeText(MainActivity.this,"Email and Password can not be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("isLogin",true);
                    editor.commit();
                    Intent intent =new Intent(MainActivity.this,MainPage.class);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writePermission && locationPermission) {


                    } else {




                    }
                }
                break;
        }
    }
    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                ActivityCompat.shouldShowRequestPermissionRationale
                        (MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)){



        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }
}
