package com.project.li.travel_diary.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.li.travel_diary.Login.LoginActivity;
import com.project.li.travel_diary.MainPageActivity;
import com.project.li.travel_diary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SetName extends AppCompatActivity {
    private EditText newName;
    private Button save;
    private CustomeOnFocusListener customeOnFocusListener;
    private Handler handler;
    private SharedPreferences pref;
    private String newname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);
        newName = findViewById(R.id.newName);
        save = findViewById(R.id.save);
        customeOnFocusListener = new CustomeOnFocusListener();
        setStatusBar();
        textChange();
        newName.setOnFocusChangeListener(customeOnFocusListener);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putString("nickName", newname);
                    editor.commit();
                    Toast toastTip = Toast.makeText(SetName.this, "修改成功！", Toast.LENGTH_LONG);
                    toastTip.show();
                    finish();
                } else {
                    Toast toastTip = Toast.makeText(SetName.this, "修改失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };
    }

    public void toResetName(final String address, final String newname) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + LoginActivity.IPaddress + ":8080/travel_diary/ResetMessageServlet?name=" + address + "&&" + "newName=" + newname);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("info", info);
                    Message msg = Message.obtain();
                    msg.obj = info;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

    protected void textChange() {
        newName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                newname = newName.getText().toString().trim();
                if (!newname.equals("")) {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newname = newName.getText().toString().trim();
                            pref = getSharedPreferences("data", MODE_PRIVATE);
                            toResetName(pref.getString("name",""),newname);
                        }
                    });
                } else {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });
    }

    class CustomeOnFocusListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.edtUsername:
                    if (hasFocus) {
                        newName.setHint(null);
                    } else {
                        newName.setHint("请输入昵称");
                    }
                    break;
            }
        }
    }
}
