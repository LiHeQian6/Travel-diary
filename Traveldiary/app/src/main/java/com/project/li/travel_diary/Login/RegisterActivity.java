package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmailAddress;
    private EditText edtPassword;
    private EditText edtAgainPassword;
    private EditText edtVerifyCode;
    private TextView edtgetVerifyCode;
    private Button btnNext;
    private TextView noSame;
    private TextView passwordLength;
    private String emailAddress;
    private String password;
    private String againPassword;
    private String VerifyCode;
    private CustomeOnFocusListener onFocusListener;
    private CustomeOnClickListener onclickListener;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBar();

        getView();
        textChange();
        registeListener();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(info.equals("S")){
                    Toast toastTip = Toast.makeText(RegisterActivity.this, "当前邮箱已被注册！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
                else {
                    Toast toastTip = Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };

    }

    public void toRegister(final String name, final String password,final String verifyCode){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+"192.168.1.101"+":8080/travel_diary/RegisterServlet?name="+name+"&&"+"password="+password+"&&"+"verifyCode="+verifyCode);
                    Log.e("url",name+password+verifyCode);
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

    protected void registeListener(){
        onFocusListener = new CustomeOnFocusListener();
        onclickListener = new CustomeOnClickListener();
        edtEmailAddress.setOnFocusChangeListener(onFocusListener);
        edtPassword.setOnFocusChangeListener(onFocusListener);
        edtAgainPassword.setOnFocusChangeListener(onFocusListener);
        edtVerifyCode.setOnFocusChangeListener(onFocusListener);
        edtgetVerifyCode.setOnClickListener(onclickListener);
    }
    protected void textChange(){
        /**
         * 输入账号文本框监听器
         */
        edtEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAddress = edtEmailAddress.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                againPassword = edtAgainPassword.getText().toString().trim();
                VerifyCode = edtVerifyCode.getText().toString().trim();
                if(!emailAddress.equals("")
                        && !password.equals("")
                        && !againPassword.equals("")
                        && !VerifyCode.equals("")
                        && password.length()>=6
                        && password.equals(againPassword)
                        && VerifyCode.length()==4) {
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            emailAddress = edtEmailAddress.getText().toString().trim();
                            password = edtPassword.getText().toString().trim();
                            VerifyCode = edtVerifyCode.getText().toString().trim();
                            toRegister(emailAddress,password,VerifyCode);
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
        /**
         * 输入密码文本框监听器
         */
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAddress = edtEmailAddress.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                againPassword = edtAgainPassword.getText().toString().trim();
                VerifyCode = edtVerifyCode.getText().toString().trim();
                if(!password.equals("") && password.length()<6){
                    passwordLength.setText("密码长度最低为6位");
                }else{
                    passwordLength.setText("");
                }
                if(!emailAddress.equals("")
                        && !password.equals("")
                        && !againPassword.equals("")
                        && !VerifyCode.equals("")
                        && password.length()>=6
                        && password.equals(againPassword)
                        && VerifyCode.length()==4){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            emailAddress = edtEmailAddress.getText().toString().trim();
                            password = edtPassword.getText().toString().trim();
                            VerifyCode = edtVerifyCode.getText().toString().trim();
                            toRegister(emailAddress,password,VerifyCode);
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
        /**
         * 再次输入密码文本框监听器
         */
        edtAgainPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAddress = edtEmailAddress.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                againPassword = edtAgainPassword.getText().toString().trim();
                VerifyCode = edtVerifyCode.getText().toString().trim();
                if(!emailAddress.equals("") && !password.equals("") && !againPassword.equals("") && !password.equals(againPassword)){
                    noSame.setText("两次输入密码不一致！");
                }else{
                    noSame.setText("");
                }
                if(!emailAddress.equals("")
                        && !password.equals("")
                        && !againPassword.equals("")
                        && !VerifyCode.equals("")
                        && password.length()>=6
                        && password.equals(againPassword)
                        && VerifyCode.length()==4){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            emailAddress = edtEmailAddress.getText().toString().trim();
                            password = edtPassword.getText().toString().trim();
                            VerifyCode = edtVerifyCode.getText().toString().trim();
                            toRegister(emailAddress,password,VerifyCode);
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
        /**
         * 输入验证码文本框监听器
         */
        edtVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAddress = edtEmailAddress.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                againPassword = edtAgainPassword.getText().toString().trim();
                VerifyCode = edtVerifyCode.getText().toString().trim();
                if(!emailAddress.equals("")
                        && !password.equals("")
                        && !againPassword.equals("")
                        && !VerifyCode.equals("")
                        && password.length()>=6
                        && password.equals(againPassword)
                        && VerifyCode.length()==4){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            emailAddress = edtEmailAddress.getText().toString().trim();
                            password = edtPassword.getText().toString().trim();
                            VerifyCode = edtVerifyCode.getText().toString().trim();
                            toRegister(emailAddress,password,VerifyCode);
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
    }
    protected void getView(){
        edtEmailAddress = findViewById(R.id.edtEmailaddress);
        edtPassword = findViewById(R.id.edtPassword);
        edtAgainPassword = findViewById(R.id.edtAgainPass);
        edtVerifyCode = findViewById(R.id.edtVerifyCode);
        edtgetVerifyCode = findViewById(R.id.edtGetVerifyCode);
        btnNext = findViewById(R.id.btnNext);
        noSame = findViewById(R.id.noSame);
        passwordLength = findViewById(R.id.passwordLength);
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

    class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edtGetVerifyCode:

                    break;
            }
        }
    }

    class CustomeOnFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.edtEmailaddress:
                    if(hasFocus){
                        edtEmailAddress.setHint(null);
                    }else{
                        edtEmailAddress.setHint("请输入邮箱");
                    }
                    break;
                case R.id.edtPassword:
                    if(hasFocus){
                        edtPassword.setHint(null);
                    }else{
                        edtPassword.setHint("请输入密码");
                    }
                    break;
                case R.id.edtAgainPass:
                    if(hasFocus){
                        edtAgainPassword.setHint(null);
                    }else{
                        edtAgainPassword.setHint("请再次输入密码");
                    }
                    break;
                case R.id.edtVerifyCode:
                    if(hasFocus){
                        edtVerifyCode.setHint(null);
                    }else{
                        edtVerifyCode.setHint("请输入验证码");
                    }
                    break;
            }
        }
    }
}
