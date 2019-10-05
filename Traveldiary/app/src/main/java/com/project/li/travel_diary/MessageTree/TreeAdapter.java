package com.project.li.travel_diary.MessageTree;

import android.content.Context;
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

import java.util.List;
import java.util.Map;

public class TreeAdapter extends BaseAdapter {
    private Context context;    // 上下文环境
    private List<Map<String, String>> dataSource; // 声明数据源
    private int item_layout_id; // 声明列表项的布局
    private LinearLayout messageLayoutEd;
    private LinearLayout messageLayout;
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
        TextView location = convertView.findViewById(R.id.location);
        final TextView message = convertView.findViewById(R.id.message);
        TextView leaveDate = convertView.findViewById(R.id.leavedate);
        TextView finger = convertView.findViewById(R.id.finger);
        ImageView trash = convertView.findViewById(R.id.trash);
        ImageView rewrite = convertView.findViewById(R.id.rewrite);

        //editview框
        TextView locationEd = convertView.findViewById(R.id.location_ed);
        final TextView messageEd = convertView.findViewById(R.id.message_ed);
        TextView leaveDateEd = convertView.findViewById(R.id.leavedate_ed);
        TextView fingerEd = convertView.findViewById(R.id.finger_ed);
        ImageView trashEd = convertView.findViewById(R.id.trash_ed);
        ImageView save = convertView.findViewById(R.id.save);

        location.setText(textFull.get("location"));
        message.setText(textFull.get("message"));
        leaveDate.setText(textFull.get("leavedate"));
        finger.setText(textFull.get("finger"));
        messageLayout = convertView.findViewById(R.id.message_layout);
        messageLayoutEd = convertView.findViewById(R.id.message_layout_ed);



        rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageLayoutEd.setVisibility(View.VISIBLE);
                messageLayout.setVisibility(View.GONE);
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.remove(position);
                notifyDataSetChanged();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("调试信息","执行");
                notifyDataSetChanged();
                messageLayout.setVisibility(View.VISIBLE);
                messageLayoutEd.setVisibility(View.GONE);
            }
        });
        trashEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
