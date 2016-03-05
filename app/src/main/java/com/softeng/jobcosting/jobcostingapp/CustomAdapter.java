package com.softeng.jobcosting.jobcostingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softeng.jobcosting.jobcostingapp.UserInterface.EditOrderActivity;
import com.softeng.jobcosting.jobcostingapp.UserInterface.MainActivity;

import static android.support.v4.app.ActivityCompat.startActivity;

public class CustomAdapter extends ArrayAdapter<String> {

    private LayoutInflater lf;

public CustomAdapter(Context context, String[] objects) {
        super(context, 0, objects);
        lf = LayoutInflater.from(context);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
        convertView = lf.inflate(R.layout.main_list_layout, parent, false);
        holder = new ViewHolder();
        holder.button = (Button) convertView
        .findViewById(R.id.edit_button);
        holder.tvItem = (TextView) convertView.findViewById(R.id.order);
        holder.initListeners();
        convertView.setTag(holder);
        } else {
        holder = (ViewHolder) convertView.getTag();
        }

    holder.setData(getItem(position));

        return convertView;
        }

public class ViewHolder {
    TextView tvItem;
    Button button;
    String mItem;


    public String getItem(){
        return mItem;
    }

    public void setData(String item) {
        mItem = item;
        tvItem.setText(item);
    }

    public void initListeners() {


        button.setFocusable(false);
        button.setClickable(false);
        button.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(getContext(), EditOrderActivity.class);
            getContext().startActivity(myIntent);
        }
    });
    }



}

}
