package com.project.li.travel_diary;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout login;
    private Button btnRegister;
    private EditText Emaiaddress;
    private EditText password;
    private String emaiaddress;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setStatusBar();
        getView();
        textChange();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

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
                if(!emaiaddress.equals("") && !pass.equals("")){
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this,MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else{
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
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
                if(!emaiaddress.equals("") && !pass.equals("")){
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this,MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else{
                    login.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }
        });
    }

    protected void getView(){
        login = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        Emaiaddress = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }
}
