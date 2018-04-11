package com.example.unsan.easybuygps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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




       /* for(int i=0;i<50;i++)
        {
            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = rand.nextInt((30 - 1) + 1) + 1;
            Log.d("checkr",randomNum+"");
            String name="customer "+(i+1);
            String address="53 Grange Road";
            String phone="85717485";
            Log.d("cl",name);

            Customer c=new Customer(carList.get(randomNum-1),name,address,phone);
            customerList.add(c);


        }
        */






      ;
        customAdapter=new CustomAdapter(HomePage.this,R.layout.simple_display,customerList);
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
        getCustomerData();



    }
    public void onResume()
    {
        super.onResume();

    }

    private void getCustomerData() {
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
