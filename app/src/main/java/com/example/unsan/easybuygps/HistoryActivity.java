package com.example.unsan.easybuygps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Unsan on 9/4/18.
 */

public class HistoryActivity extends AppCompatActivity {
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    List<Delivery> deliveryList;
    FirebaseDatabase fbd;
    DatabaseReference historyDb;


    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.history_activity);
        recyclerView=(RecyclerView)findViewById(R.id.history_list);
        deliveryList=new ArrayList<>();
        fbd=FirebaseDatabase.getInstance();
        historyDb=fbd.getReference("delivery");
        historyAdapter=new HistoryAdapter(HistoryActivity.this,deliveryList);
        recyclerView.setAdapter(historyAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        findDelivery();
    }

    private void findDelivery() {
        historyDb.orderByChild("timeval").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    // Log.d("actchk", "dbchk");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //  if(ds.getValue().equals("driverName"))
                        //   {
                        Delivery rbd = ds.getValue(Delivery.class);
                        // Log.d("titleck", rbd.getTitle());

                        deliveryList.add(rbd);

                        // }
                    }
                    historyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
