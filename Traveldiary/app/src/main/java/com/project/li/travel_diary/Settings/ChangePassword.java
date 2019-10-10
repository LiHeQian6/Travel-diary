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
import android.widget.TextView;
import android.widget.Toast;

import com.project.li.travel_diary.Login.ForgetPassActivity;
import com.project.li.travel_diary.Login.LoginActivity;
import com.project.li.travel_diary.Login.ResetPassActivity;
import com.project.li.travel_diary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ChangePassword extends AppCompatActivity {
    private EditText oldPassword;
    private EditText newPassword;
    private TextView textForgetpass;
    private TextView noSame;
    private TextView passwordLength;
    private EditText againNewPassword;
    private Button save;
    private Handler handler;
    private String oldpassword;
    private String newpassword;
    private String againnewpassword;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setStatusBar();
        getView();
        textChange();

        textForgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChangePassword.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    SharedPreferences.Editor editor1 = getSharedPreferences("dataChange", MODE_PRIVATE).edit();
                    editor1.putString("password", newpassword);
                    editor1.commit();
                    Intent intent = new Intent();
                    intent.setClass(ChangePassword.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toastTip = Toast.makeText(ChangePassword.this, "修改失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };
    }

    protected void getView(){
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        textForgetpass = findViewById(R.id.textForgetPass);
        save = findViewById(R.id.save);
        noSame = findViewById(R.id.noSame);
        passwordLength = findViewById(R.id.passwordLength);
        againNewPassword = findViewById(R.id.againNewPassword);
    }

    public void toResetPassword(final String name,final String newPassword) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/travel_diary/ResetPasswordServlet?name=" + name + "&&" + "newPassword=" + newPassword);
                    Log.e("url", name + newPassword);
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

        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                oldpassword = oldPassword.getText().toString().trim();
                newpassword = newPassword.getText().toString().trim();
                againnewpassword = againNewPassword.getText().toString().trim();
                if (!oldpassword.equals("") && !newpassword.equals("") && newpassword.equals(againnewpassword) && newpassword.length()>=6) {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    pref = getSharedPreferences("data", MODE_PRIVATE);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (oldpassword.equals(pref.getString("password", ""))) {
                                pref = getSharedPreferences("data", MODE_PRIVATE);
                                toResetPassword(pref.getString("name", ""), newpassword);
                            }else {
                                Toast toastTip = Toast.makeText(ChangePassword.this, "密码不正确！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
                        }
                    });
                } else {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                oldpassword = oldPassword.getText().toString().trim();
                newpassword = newPassword.getText().toString().trim();
                againnewpassword = againNewPassword.getText().toString().trim();
                if(!newpassword.equals("") && newpassword.length()<6){
                    passwordLength.setText("密码长度最低为6位");
                }else{
                    passwordLength.setText("");
                }
                if (!oldpassword.equals("") && !newpassword.equals("") && newpassword.equals(againnewpassword) && newpassword.length()>=6) {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    pref = getSharedPreferences("data", MODE_PRIVATE);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (oldpassword.equals(pref.getString("password", ""))) {
                                pref = getSharedPreferences("data", MODE_PRIVATE);
                                toResetPassword(pref.getString("name", ""), newpassword);
                            }else {
                                Toast toastTip = Toast.makeText(ChangePassword.this, "密码不正确！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
                        }
                    });
                } else {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });

        againNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                oldpassword = oldPassword.getText().toString().trim();
                newpassword = newPassword.getText().toString().trim();
                againnewpassword = againNewPassword.getText().toString().trim();
                if(!newpassword.equals(againnewpassword) && !newpassword.equals("") && !againnewpassword.equals("")){
                    noSame.setText("两次输入密码不同！");
                }else{
                    noSame.setText("");
                }
                if (!oldpassword.equals("") && !newpassword.equals("") && newpassword.equals(againnewpassword) && newpassword.length()>=6) {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    pref = getSharedPreferences("data", MODE_PRIVATE);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (oldpassword.equals(pref.getString("password", ""))) {
                                pref = getSharedPreferences("data", MODE_PRIVATE);
                                toResetPassword(pref.getString("name", ""), newpassword);
                            }else {
                                Toast toastTip = Toast.makeText(ChangePassword.this, "密码不正确！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
                        }
                    });
                }else {
                    save.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                }
            }
        });
    }

}
