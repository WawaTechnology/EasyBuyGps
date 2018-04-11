package com.example.unsan.easybuygps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Unsan on 9/4/18.
 */

class CustomAdapter extends ArrayAdapter<CustomerNode> {
    Context context;
    List<CustomerNode> objects;

    @Override
    public int getCount() {
        if(objects!=null)
            return objects.size();
        else
            return -1;
    }

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<CustomerNode> objects) {
        super(context, resource,0, objects);
        this.context=context;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listV=convertView;
        if(listV==null)
            listV= LayoutInflater.from(context).inflate(R.layout.simple_display,parent,false);

        final CustomerNode customerNode=objects.get(position);
        final Customer c=customerNode.getCustomer();
        TextView tv=(TextView)listV.findViewById(R.id.custnamedisp);
        if(c!=null)
            tv.setText(customerNode.getRestaurantName());
        else
            tv.setText("No customer Found");
        listV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,CustomerDetail.class);
               intent.putExtra("customernd",customerNode);
               context.startActivity(intent);



            }
        });
        return listV;

    }
}

