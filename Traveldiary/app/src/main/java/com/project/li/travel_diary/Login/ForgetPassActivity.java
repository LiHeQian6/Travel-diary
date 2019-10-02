package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.li.travel_diary.R;

public class ForgetPassActivity extends AppCompatActivity {

    private EditText edtEmailaddress;
    private EditText edtVerifyCode;
    private TextView btnGetVerifyCode;
    private Button btnNext;
    private CustomeOnClickListener onClickListener;
    private CustomeOnFocusListener onFocusListener;
    private String emailAddress;
    private String verifyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        setStatusBar();
        getViews();
        textChange();
        registLitener();
    }

    /**
     * 注册监听器
     */
    protected void registLitener(){
        onClickListener = new CustomeOnClickListener();
        onFocusListener = new CustomeOnFocusListener();
        edtEmailaddress.setOnFocusChangeListener(onFocusListener);
        edtVerifyCode.setOnFocusChangeListener(onFocusListener);
        btnGetVerifyCode.setOnClickListener(onClickListener);
    }

    /**
     * 文本监听
     */
    protected void textChange() {
        edtEmailaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAddress = edtEmailaddress.getText().toString().trim();
                verifyCode = edtVerifyCode.getText().toString().trim();
                if(!emailAddress.equals("") && !verifyCode.equals("")){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(ForgetPassActivity.this,ResetPassActivity.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
        edtVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAddress = edtEmailaddress.getText().toString().trim();
                verifyCode = edtVerifyCode.getText().toString().trim();
                if(!emailAddress.equals("") && !verifyCode.equals("")){
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(ForgetPassActivity.this,ResetPassActivity.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
                }
            }
        });
    }

    /**
     * 获取控件ID
     */
    protected void getViews(){
        edtEmailaddress = findViewById(R.id.edtEmailaddress);
        edtVerifyCode = findViewById(R.id.edtVerifyCode);
        btnGetVerifyCode = findViewById(R.id.textGetVerifyCode);
        btnNext = findViewById(R.id.btnNext);
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
     * 点击事件
     */
    class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edtGetVerifyCode:
                    break;
            }
        }
    }

    /**
     * 文本焦点改变监听
     */
    class CustomeOnFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch(v.getId()){
                case R.id.edtEmailaddress:
                    if(hasFocus){
                        edtEmailaddress.setHint(null);
                    }else{
                        edtEmailaddress.setHint("请输入邮箱");
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
