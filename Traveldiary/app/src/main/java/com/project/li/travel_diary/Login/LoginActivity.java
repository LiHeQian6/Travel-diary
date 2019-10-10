package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.li.travel_diary.showMessages.MainPageActivity;
import com.project.li.travel_diary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout login;
    private LinearLayout activity_Login;
    private TextView btnRegister;
    private TextView forgetPass;
    private EditText Emaiaddress;
    private EditText password;
    private String emaiaddress;
    private CheckBox ifRembemer;
    private String pass;
    private LoginActivity.CustomeOnClickListener listener;
    private LoginActivity.CustomeOnFocusListener onFocusListener;
    private Handler handler;
    private SharedPreferences pref;
    private SharedPreferences prefChange;
    private int width;
    private int height;
    private float density;
    private int densityDpi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setStatusBar();
        getView();
        textChange();
        registLitener();
        pref = getSharedPreferences("data", MODE_PRIVATE);
        prefChange = getSharedPreferences("dataChange", MODE_PRIVATE);
        if(pref.getString("isChecked","").equals("true")) {
            ifRembemer.setChecked(true);
            Emaiaddress.setText(pref.getString("name", ""));
            password.setText(pref.getString("password", ""));
        }
        if(!pref.getString("name","").equals("") && pref.getString("name","").equals(prefChange.getString("name",""))
                && pref.getString("password","").equals(prefChange.getString("password",""))){
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
                if (!info.equals("F") && !info.equals("S")) {
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    SharedPreferences.Editor editor1 = getSharedPreferences("dataChange", MODE_PRIVATE).edit();
                    Log.e("nickName",info);
                    editor.putString("name", emaiaddress);
                    editor.putString("password", pass);
                    editor.putString("nickName",info);
                    editor1.putString("name",emaiaddress);
                    editor1.putString("password",pass);
                    editor1.putString("nickName",info);
                    if(ifRembemer.isChecked())
                        editor.putString("isChecked","true");
                    else
                        editor.putString("isChecked","false");
                    editor.commit();
                    editor1.commit();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish();
                }else if(info.equals("S")){
                    Toast toastTip = Toast.makeText(LoginActivity.this, "没有此账号注册信息！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
                else {
                    Toast toastTip = Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };
    }


    /**
     * 向服务器发送数据并接收返回信息
     * @param name
     * @param password
     */
    public void toLogin(final String name, final String password){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/travel_diary/Login?name="+name+"&&"+"password="+password);
                    //Log.e("url","http://"+"192.168.1.101"+":8080/Travel_diary/Login?name="+name+"&&"+"password="+password);
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

    /**
     * 输入账号文本框监听器
     * 监听文本内容改变，点击button传递参数，执行toLogin方法向服务器发送数据
     */
    protected void textChange(){

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
                            if(isEmail(name)) {
                                toLogin(name, passage);
                            }else{
                                Toast toastTip = Toast.makeText(LoginActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
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
                            if(isEmail(name)) {
                                toLogin(name, passage);
                            }else{
                                Toast toastTip = Toast.makeText(LoginActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
                        }
                    });
                }else{
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });
    }

    /**
     * 正则判断邮箱是否合法
     * @param email
     * @return
     */
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
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
        activity_Login = findViewById(R.id.activity_login);
        ifRembemer = findViewById(R.id.ifRemember);
    }

    /**
     * 调整标题栏与状态栏style
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

    /**
     * 点击事件监听器
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

    /**
     * 是否获得焦点监听器，用于监听输入框焦点改变并改变输入框hint内容
     */
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
