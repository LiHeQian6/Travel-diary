package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.li.travel_diary.MainPageActivity;
import com.project.li.travel_diary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout login;
    private TextView btnRegister;
    private TextView forgetPass;
    private EditText Emaiaddress;
    private EditText password;
    private String emaiaddress;
    private String pass;
    private CustomeOnClickListener listener;
    private CustomeOnFocusListener onFocusListener;
    private Handler handler;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setStatusBar();
        getView();
        textChange();
        registLitener();
        pref = getSharedPreferences("data", MODE_PRIVATE);
        if(!pref.getString("name","").equals("") && !pref.getString("password","").equals("")){
//            emaiaddress = pref.getString("name","");
//            pass = pref.getString("password","");
//            toLogin(emaiaddress,pass);
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putString("name", emaiaddress);
                    editor.putString("password", pass);
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toastTip = Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };
    }

    public void toLogin(final String name, final String password){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+"192.168.1.101"+":8080/travel_diary/Login?name="+name+"&&"+"password="+password);
                    Log.e("url","http://"+"192.168.1.101"+":8080/Travel_diary/Login?name="+name+"&&"+"password="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("info",info);
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
    /**
     * 注册监听器
     */
    protected void registLitener(){
        listener = new CustomeOnClickListener();
        onFocusListener = new CustomeOnFocusListener();
        btnRegister.setOnClickListener(listener);
        Emaiaddress.setOnFocusChangeListener(onFocusListener);
        password.setOnFocusChangeListener(onFocusListener);
        forgetPass.setOnClickListener(listener);
    }
    protected void textChange(){
        /**
         * 输入账号文本框监听器
         */
        Emaiaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                emaiaddress = Emaiaddress.getText().toString().trim();
                pass = password.getText().toString().trim();
                if(!emaiaddress.equals("") && !pass.equals("") && pass.length()>=6){
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = Emaiaddress.getText().toString();
                            String passage = password.getText().toString();
                            toLogin(name,passage);
                        }
                    });
                }else{
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });
        /**
         * 输入密码文本框监听器
         */
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                emaiaddress = Emaiaddress.getText().toString().trim();
                pass = password.getText().toString().trim();
                if(!emaiaddress.equals("") && !pass.equals("") && pass.length()>=6){
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = Emaiaddress.getText().toString();
                            String passage = password.getText().toString();
                            toLogin(name,passage);
                        }
                    });
                }else{
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });
    }

    /**
     * 获取控件ID
     */
    protected void getView(){
        login = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        Emaiaddress = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        forgetPass = findViewById(R.id.btnForgetPass);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

    /**
     * 监听器
     */
    class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnRegister:
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnForgetPass:
                    Intent intent1 = new Intent();
                    intent1.setClass(LoginActivity.this,ForgetPassActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }

    class CustomeOnFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.edtUsername:
                    if(hasFocus){
                        Emaiaddress.setHint(null);
                    }else{
                        Emaiaddress.setHint("请输入账号");
                    }
                    break;
                case R.id.edtPassword:
                    if(hasFocus){
                        password.setHint(null);
                    }else{
                        password.setHint("请输入密码");
                    }
                    break;
            }
        }
    }
}
