package com.project.li.travel_diary;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmailAddress;
    private EditText edtPassword;
    private EditText edtAgainPassword;
    private EditText edtVerifyCode;
    private TextView edtgetVerifyCode;
    private Button btnNext;
    private String emailAddress;
    private String password;
    private String againPassword;
    private String VerifyCode;
    private CustomeOnFocusListener onFocusListener;
    private CustomeOnClickListener onclickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBar();

        getView();
        textChange();
        registeListener();
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
                if(!emailAddress.equals("") && !password.equals("") && !againPassword.equals("") && !VerifyCode.equals("")){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
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
                if(!emailAddress.equals("") && !password.equals("") && !againPassword.equals("") && !VerifyCode.equals("")){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
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
                if(!emailAddress.equals("") && !password.equals("") && !againPassword.equals("") && !VerifyCode.equals("")){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
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
                if(!emailAddress.equals("") && !password.equals("") && !againPassword.equals("") && !VerifyCode.equals("")){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
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
