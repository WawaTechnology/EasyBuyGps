package com.example.unsan.easybuygps;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Unsan on 11/4/18.
 */

public class SearchResultsActivity extends AppCompatActivity {
    ListView lvw;
    TextView tvw;

    List list;
    ArrayAdapter<CustomerNode> listAdapter;
    DatabaseReference restaurantReference;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        lvw=(ListView)findViewById(R.id.listview);
        tvw=(TextView)findViewById(R.id.noResult);

        list=new ArrayList<CustomerNode>();
        restaurantReference= FirebaseDatabase.getInstance().getReference("Customer");
       listAdapter = new CustomAdapter(this, R.layout.searchtext, list);
        lvw.setAdapter(listAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem=menu.findItem(R.id.searchi);
        SearchView searchView=(SearchView)searchItem.getActionView();
        searchItem.expandActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(final String query) {
                list.clear();
                restaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds:dataSnapshot.getChildren())
                        {
                            String key=ds.getKey();
                            if(key.toLowerCase().contains(query.toLowerCase()))

                            {

                                Log.d("checkkey",key);
                                Customer c=ds.getValue(Customer.class);
                                CustomerNode customerNode=new CustomerNode(key,c);
                               list.add(customerNode);
                               ;
                                listAdapter.notifyDataSetChanged();
                                tvw.setVisibility(View.GONE);
                            }

                        }
                        if(list.isEmpty())
                        {
                            tvw.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
