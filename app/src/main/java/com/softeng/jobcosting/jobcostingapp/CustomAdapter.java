package com.softeng.jobcosting.jobcostingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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
        holder.btnLocalData = (Button) convertView
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

public static class ViewHolder {
    TextView tvItem;
    Button btnOnlineData;
    Button btnLocalData;
    String mItem;

    public String getItem(){
        return mItem;
    }

    public void setData(String item) {
        mItem = item;
        tvItem.setText(item);
    }

    public void initListeners() {
        btnLocalData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

}

}
