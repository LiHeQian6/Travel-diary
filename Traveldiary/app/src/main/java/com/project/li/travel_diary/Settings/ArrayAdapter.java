package com.project.li.travel_diary.Settings;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.li.travel_diary.R;

public class ArrayAdapter extends BaseAdapter {
    private Context context;
    private String[] itemName;
    private int id;
    @Override
    public int getCount() {
        if (null != itemName) {
            return itemName.length;
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (null != itemName) {
            return itemName[position];
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(id, null);
        }
        TextView textView =convertView.findViewById(R.id.itemName);
        textView.setText(itemName[position]);

        return convertView;
    }

    public ArrayAdapter(Context context,String[] itemName,int id){
        this.context = context;
        this.itemName = itemName;
        this.id = id;
    }
}
