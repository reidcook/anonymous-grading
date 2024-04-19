package com.example.anonymousgrading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    private String[] classNames;
    private Context context;
    private LayoutInflater layoutInflater;
    public CustomAdapter(Context context, String[] classNames){
        this.context = context;
        this.classNames = classNames;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return classNames.length;
    }

    @Override
    public Object getItem(int position) {
        return classNames[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_layout, null);
        TextView text = (TextView) convertView.findViewById(R.id.studentInfoTxt);
        text.setText(classNames[position]);
        return convertView;
    }
}
