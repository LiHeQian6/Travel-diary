package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.li.travel_diary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ResetPassActivity extends AppCompatActivity {

    private EditText edtNewPass;//新密码编辑框
    private EditText edtAgainPass;//重复新密码编辑框
    private TextView passwordLength;//提示密码长度文本框
    private TextView isSame;//提示两次输入密码相同文本框
    private Button goLogin;//下一步button
    private String newPass;//新密码
    private String againPass;//重复新密码
    private CustomeOnFocusListener onFocusListener;
    private Handler handler;
    private String emailAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        setStatusBar();
        getViews();
        textChange();
        registLitener();

        Intent intent = getIntent();
        emailAddress = intent.getStringExtra("emailAddress");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    Intent intent = new Intent();
                    intent.setClass(ResetPassActivity.this, WelcomActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toastTip = Toast.makeText(ResetPassActivity.this, "修改失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };

    }

    /**
     * 向服务器发送新密码数据
     * @param name
     * @param newPassword
     */
    public void toResetPassword(final String name,final String newPassword){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/travel_diary/ResetPasswordServlet?name="+name+"&&"+"newPassword="+newPassword);
                    Log.e("url",name+newPassword);
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
     * 获取控件ID
     */
    protected void getViews(){
        edtNewPass = findViewById(R.id.edtNewPass);
        edtAgainPass = findViewById(R.id.edtAgainNewPass);
        goLogin = findViewById(R.id.btnNext);
        passwordLength = findViewById(R.id.passwordLength);
        isSame = findViewById(R.id.noSame);
    }

    /**
     * 输入文本框监听器
     * 监听文本内容改变，点击button传递参数，执行toLogin方法向服务器发送数据
     */
    protected void textChange(){
        edtNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newPass = edtNewPass.getText().toString().trim();
                againPass = edtAgainPass.getText().toString().trim();
                if(!newPass.equals("") && newPass.length()<6){
                    passwordLength.setText("密码长度最低为6位");
                }else{
                    passwordLength.setText("");
                }
                if(!newPass.equals("") && !againPass.equals("") && newPass.equals(againPass)){
                    goLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    goLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toResetPassword(emailAddress,newPass);
                        }
                    });
                }else{
                    goLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
        edtAgainPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newPass = edtNewPass.getText().toString().trim();
                againPass = edtAgainPass.getText().toString().trim();
                if(!newPass.equals("") && !againPass.equals("") && !newPass.equals(againPass)){
                    isSame.setText("两次输入密码不一致！");
                }else{
                    isSame.setText("");
                }
                if(!newPass.equals("") && !againPass.equals("") && newPass.equals(againPass)){
                    goLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    goLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toResetPassword(emailAddress,newPass);
                        }
                    });
                }else{
                    goLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
    }

    /**
     * 注册事件监听器
     */
    protected void registLitener(){
        onFocusListener = new CustomeOnFocusListener();
        edtNewPass.setOnFocusChangeListener(onFocusListener);
        edtAgainPass.setOnFocusChangeListener(onFocusListener);
    }

    /**
     * 改变标题栏即状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

    /**
     * 是否获得焦点监听器，用于监听输入框焦点改变并改变输入框hint内容
     */
    class CustomeOnFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch(v.getId()){
                case R.id.edtEmailaddress:
                    if(hasFocus){
                        edtNewPass.setHint(null);
                    }else{
                        edtNewPass.setHint("请输入新密码");
                    }
                    break;
                case R.id.edtVerifyCode:
                    if(hasFocus){
                        edtAgainPass.setHint(null);
                    }else{
                        edtAgainPass.setHint("请确认新密码");
                    }
                    break;
            }
        }
    }
}
