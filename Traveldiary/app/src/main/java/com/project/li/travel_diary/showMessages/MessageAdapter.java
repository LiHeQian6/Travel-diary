package com.project.li.travel_diary.showMessages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.project.li.travel_diary.MessageTree.EditPage;
import com.project.li.travel_diary.R;
import com.project.li.travel_diary.bean.Messages;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author li
 * @date 2019/10/6
 * @time 下午12:32
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Messages> data;
    private int item_layout_id;
    private Handler handlerDelete=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 300:
                    if((msg.obj+"").equals("true")){
                        Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
                        data.remove(p);
                        notifyDataSetChanged();
                    }else{
                        Toast.makeText(context,"删除失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 301:
                    break;
            }

        }
    };
    private int p;
    private ImageView like;
    private TextView likeNum;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id,null);
        }
        p=position;
        TextView title=convertView.findViewById(R.id.title);
        final TextView content=convertView.findViewById(R.id.content);
        TextView address=convertView.findViewById(R.id.address);
        TextView time=convertView.findViewById(R.id.time);
        likeNum=convertView.findViewById(R.id.finger);
        like=convertView.findViewById(R.id.like);
        ImageView edit=convertView.findViewById(R.id.rewrite);
        final ImageView delete=convertView.findViewById(R.id.trash);
        title.setText(data.get(position).getTitle());
        content.setText(data.get(position).getContent());
        address.setText(data.get(position).getAddress());
        time.setText(data.get(position).getDate());
        likeNum.setText(""+data.get(position).getLikeNum());
        final SharedPreferences sharedPreferences=context.getSharedPreferences("data",0);
        final String name = sharedPreferences.getString("name", "");
        Log.e("name",data.toString());
        if(!name.equals(data.get(position).getUser())){
            edit.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
        List<String> liked=new ArrayList<>(Arrays.asList(data.get(position).getLiked().split(",")));
        Log.e("liked",liked.toString());
        if(liked.contains(name)){
            like.setImageResource(R.drawable.liked);
            like.setOnClickListener(null);
        }else{
            final View finalConvertView = convertView;
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //发送到后台赞数+1
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL("http://" +context.getResources().getString(R.string.IP) + ":8080/travel_diary/LikeMessageServlet?id="+data.get(position).getId()+"&user="+name);
                                URLConnection conn = url.openConnection();
                                InputStream in = conn.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                                String returnResult = reader.readLine();Log.e("like info",returnResult);
//                                Message message = new Message();
//                                message.what = 301;
//                                message.obj = returnResult;
//                                handlerDelete.sendMessage(message);
                                if(returnResult.equals("true")){
//                                    like.setImageResource(R.drawable.liked);
//                                    likeNum.setText(Integer.parseInt(likeNum.getText().toString())+1+"");
//                                    like.setOnClickListener(null);
                                    data.get(position).setLiked(data.get(position).getLiked()+","+name);
                                    data.get(position).setLikeNum(data.get(position).getLikeNum()+1);
                                    notifyDataSetChanged();
                                    /*View view = getView(position, finalConvertView, parent);
                                    ImageView l = view.findViewById(R.id.like);
                                    TextView n= view.findViewById(R.id.finger);
                                    l.setImageResource(R.drawable.liked);
                                    n.setText(Integer.parseInt(n.getText().toString())+1+"");
                                    l.setOnClickListener(null);*/
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            });
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Map<String,String>> list=new ArrayList<>();
                HashMap<String, String> e = new HashMap<>();
                e.put("title",data.get(position).getTitle());
                e.put("message",data.get(position).getContent());
                e.put("location",data.get(position).getAddress());
                e.put("leavedate",data.get(position).getDate());
                e.put("user",data.get(position).getUser());
                e.put("lng",data.get(position).getLng().toString());
                e.put("lat",data.get(position).getLat().toString());
                e.put("id",data.get(position).getId()+"");
                e.put("finger",data.get(position).getLikeNum()+"");
                list.add(e);
                Intent intent = new Intent(context, EditPage.class);
                intent.putExtra("dataSource",(Serializable)list);
                intent.putExtra("position",0);
                ((ShowMessageActivity)context).startActivityForResult(intent,110);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" +context.getResources().getString(R.string.IP) + ":8080/travel_diary/DeleteMessageServlet?id="+data.get(position).getId());
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
        });
        return convertView;
    }
}
