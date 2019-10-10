package com.project.li.travel_diary.MessageTree;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.li.travel_diary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class EditPage extends AppCompatActivity {
    private EditText titleEdit;
    private EditText contentEdit;
    private Button saveButton;
    private int position;
    private String messages;
    private List<Map<String, String>> dataSource;
    public Handler handlerUpdate = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if((msg.obj+"").equals("true")){
                Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();

                Map<String,String> textFull = dataSource.get(position);
                textFull.put("title",titleEdit.getText().toString());
                //判断内容是否为空
                if(contentEdit.getText().toString().equals("")){
                    textFull.put("message",
                            getSharedPreferences("data",0).getString("nickName","")+"到此一游！");
                }else{
                    textFull.put("message",contentEdit.getText().toString());
                }
                dataSource.add(textFull);

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MessageTree.class);
                intent.putExtra("dataSource",(Serializable) dataSource);
                setResult(200,intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"保存失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        setStatusBar();

        titleEdit = findViewById(R.id.title_edit);
        contentEdit = findViewById(R.id.content_edit);
        saveButton = findViewById(R.id.save_button);

        Intent accept = getIntent();
        dataSource = (List<Map<String, String>>)accept.getSerializableExtra("dataSource");
        position = accept.getIntExtra("position",0);
        titleEdit.setText(dataSource.get(position).get("title"));
        contentEdit.setText(dataSource.get(position).get("message"));

        updateMyLeaveMessageHistory();

    }

    //向服务器发送数据，返回更新的结果
    public void updateMyLeaveMessageHistory() {

        //保存按钮监听事件
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断标题是否为空
                if(titleEdit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"标题不能为空哦，请重新输入！",Toast.LENGTH_SHORT).show();
                }else {

                    //封装messages对象成jason并向后台发送数据
                    try {
                        Map<String,String> item = dataSource.get(position);
                        JSONObject messagesObject = new JSONObject();
                        //判断内容是否为空
                        if(contentEdit.getText().toString().equals("")){
                            messagesObject.put("content",
                                    getSharedPreferences("data",0).getString("nickName","")+"到此一游！");
                        }else{
                            messagesObject.put("content",contentEdit.getText().toString());
                        }
                        messagesObject.put("id",item.get("id"));
                        messagesObject.put("address",item.get("location"));
                        messagesObject.put("title",titleEdit.getText().toString());
                        messagesObject.put("date",item.get("leavedate"));
                        messagesObject.put("likenum",item.get("finger"));
                        messagesObject.put("lat",Double.parseDouble(item.get("lat")));
                        messagesObject.put("lng",Double.parseDouble(item.get("lng")));
                        messages = messagesObject.toString();
                        Log.e("11",messages);

                        //设置线程执行
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/travel_diary/ChangeMessageServlet");
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoOutput(true);
                                    conn.setRequestMethod("POST");
                                    conn.setRequestProperty("Content-Type", "application/json");
                                    conn.setRequestProperty("charset", "utf-8");
                                    OutputStream stream = conn.getOutputStream();
                                    stream.write(messages.getBytes());
                                    stream.close();
                                    InputStream in = conn.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                                    String returnResult = reader.readLine();
                                    Message message = new Message();
                                    message.what = 180;
                                    message.obj = returnResult;
                                    handlerUpdate.sendMessage(message);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }
}
