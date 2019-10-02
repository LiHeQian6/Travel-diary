package com.project.li.travel_diary;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetPassActivity extends AppCompatActivity {

    private EditText edtNewPass;
    private EditText edtAgainPass;
    private Button goLogin;
    private String newPass;
    private String againPass;
    private CustomeOnFocusListener onFocusListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        setStatusBar();
        getViews();
        textChange();
        registLitener();
    }
    /**
     * 获取控件ID
     */
    protected void getViews(){
        edtNewPass = findViewById(R.id.edtNewPass);
        edtAgainPass = findViewById(R.id.edtAgainNewPass);
        goLogin = findViewById(R.id.btnNext);
    }

    /**
     * 文本改变监听
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
                if(!newPass.equals("") && !againPass.equals("")){
                    goLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    goLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(ResetPassActivity.this,LoginActivity.class);
                            startActivity(intent);
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
                if(!newPass.equals("") && !againPass.equals("")){
                    goLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerforstyle));
                    goLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(ResetPassActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
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
