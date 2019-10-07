package com.project.li.travel_diary.MessageTree;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.project.li.travel_diary.R;
import com.project.li.travel_diary.bean.Messages;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    private int positionQ;

    private Handler handlerDelete = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if((msg.obj+"").equals("true")){
                Toast.makeText((MessageTree)context,"删除成功！",Toast.LENGTH_SHORT).show();
                dataSource.remove(positionQ);
                notifyDataSetChanged();
            }else{
                Toast.makeText((MessageTree)context,"删除失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

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
                new Thread(){
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(context,EditPage.class);
                        intent.putExtra("dataSource",(Serializable) dataSource);
                        intent.putExtra("position",position);
                        ((MessageTree)context).startActivityForResult(intent,100);
                    }
                }.start();
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionQ = position;
                deleteMyLeaveMessageHistory(Integer.parseInt(dataSource.get(position).get("id")));
            }
        });

        return convertView;
    }

    //删除用户所选择的留言，并返回值
    public void deleteMyLeaveMessageHistory(final int id) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + MessageTree.IPaddress + ":8080/travel_diary/DeleteMessageServlet?id="+id);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String returnResult = reader.readLine();
                    Message message = new Message();
                    message.what = 300;
                    message.obj = returnResult;
                    handlerDelete.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
