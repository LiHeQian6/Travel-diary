package com.project.li.travel_diary.MessageTree;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.li.travel_diary.R;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreeAdapter extends BaseAdapter {
    private Context context;    // 上下文环境
    private List<Map<String, String>> dataSource; // 声明数据源
    private int item_layout_id; // 声明列表项的布局
    private TextView messageTitile; //留言记录文字框标题
    private TextView message; //留言记录文字框留言
    private TextView location; //留言记录文字框地点
    private TextView leaveDate; //留言记录文字框时间
    private TextView finger; //留言记录点赞数
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

        //textview框
        messageTitile = convertView.findViewById(R.id.message_title);
        location = convertView.findViewById(R.id.location);
        message = convertView.findViewById(R.id.message);
        leaveDate = convertView.findViewById(R.id.leavedate);
        finger = convertView.findViewById(R.id.finger);
        ImageView trash = convertView.findViewById(R.id.trash);
        ImageView rewrite = convertView.findViewById(R.id.rewrite);

        //设置文字框的信息
        messageTitile.setText(textFull.get("title"));
        location.setText(textFull.get("location"));
        message.setText(textFull.get("message"));
        leaveDate.setText(textFull.get("leavedate"));
        finger.setText(textFull.get("finger"));


        rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,EditPage.class);
                intent.putExtra("dataSource",(Serializable) dataSource);
                intent.putExtra("position",position);
                ((MessageTree)context).startActivityForResult(intent,100);
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
