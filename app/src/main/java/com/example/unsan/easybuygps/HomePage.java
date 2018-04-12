package com.example.unsan.easybuygps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Unsan on 9/4/18.
 */

public class HomePage extends AppCompatActivity  {

    ListView listView;
    DatabaseReference customerReference;
    public static boolean sorted;


    List<CustomerNode> customerList;
    CustomAdapter customAdapter;


    ArrayAdapter<String> arrayAdapter;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        FirebaseDatabase fbd=FirebaseDatabase.getInstance();
        customerList=new ArrayList<>();
        customerReference=fbd.getReference("Customer");



        listView=(ListView) findViewById(R.id.list_view);
        customAdapter=new CustomAdapter(HomePage.this,R.layout.simple_display,customerList);
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
        getCustomerData();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.searchid: {
                Intent intent = new Intent(HomePage.this, SearchResultsActivity.class);
                startActivity(intent);
                break;


            }
            case R.id.sortadd:
            {
                getCustomerAddress();
                break;

            }
            case R.id.sortrest:
            {
                getCustomerData();
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void getCustomerAddress() {
        sorted =true;
        customerList.clear();
        customerReference.orderByChild("Address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        String key=ds.getKey();
                        Log.d("checkkey",key);
                        Customer c=ds.getValue(Customer.class);
                        CustomerNode customerNode=new CustomerNode(key,c);
                        customerList.add(customerNode);
                        customAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }

    public void onResume()
    {
        super.onResume();

    }

    private void getCustomerData() {
        sorted=false;
        customerList.clear();
        customerReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String key=ds.getKey();
                    Log.d("checkkey",key);
                    Customer c=ds.getValue(Customer.class);
                    CustomerNode customerNode=new CustomerNode(key,c);
                    customerList.add(customerNode);
                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
