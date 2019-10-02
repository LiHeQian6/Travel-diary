package com.project.li.travel_diary.MessageTree;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.li.travel_diary.R;

import java.util.List;
import java.util.Map;

public class TreeAdapter extends BaseAdapter {
    private Context context;    // 上下文环境
    private List<Map<String, String>> dataSource; // 声明数据源
    private int item_layout_id; // 声明列表项的布局
    // 声明列表项中的控件
    public TreeAdapter(Context context, List<Map<String, String>> dataSource,
                         int item_layout_id) {
        this.context = context;       // 上下文环境
        this.dataSource = dataSource; // 数据源
        this.item_layout_id = item_layout_id; // 列表项布局文件ID
    }
    public int getCount() {return dataSource.size();}
    public Object getItem(int position) { return dataSource.get(position); }
    public long getItemId(int position) { return position; }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id,null);
        }

        final Map<String,String> textFull = dataSource.get(position);
        TextView topTip = convertView.findViewById(R.id.toptip);
        TextView lMessage = convertView.findViewById(R.id.lmessage);
        topTip.setText(textFull.get("time")+"，你在"+textFull.get("location")+"留下了");
        lMessage.setText(textFull.get("message"));

        return convertView;
    }
}
