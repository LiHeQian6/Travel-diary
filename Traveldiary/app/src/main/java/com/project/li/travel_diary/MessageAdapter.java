package com.project.li.travel_diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author li
 * @date 2019/10/6
 * @time 下午12:32
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List data;
    private int item_layout_id;

    public MessageAdapter() {
    }

    public MessageAdapter(Context context, List data, int item_layout_id) {
        this.context = context;
        this.data = data;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if(data!=null)
            return data.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != data)
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id,null);
        }
        return convertView;
    }
}
