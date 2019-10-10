package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmailAddress;//邮箱编辑框
    private EditText edtPassword;//密码编辑框
    private EditText edtAgainPassword;//再次输入密码编辑框
    private EditText edtVerifyCode;//验证码输入框
    private TextView edtgetVerifyCode;//获取验证码Button
    private Button btnNext;//下一步按钮
    private TextView noSame;//提示密码是否相同文本框
    private TextView passwordLength;//提示密码最小长度
    private String emailAddress;//邮箱
    private String password;//密码
    private String againPassword;//再次输入的密码
    private String VerifyCode;//验证码
    private String textBtnedtgetVerifyCode;
    private CustomeOnFocusListener onFocusListener;
    private CustomeOnClickListener onclickListener;
    private Handler handler;
    private Handler sendEmailHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBar();

        getView();
        textChange();
        registeListener();
        edtgetVerifyCode.setText("获取验证码");
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
                }else {
                    Toast toastTip = Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };

        sendEmailHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    start();
                    Toast toastTip = Toast.makeText(RegisterActivity.this, "验证码已发送，请查收！", Toast.LENGTH_LONG);
                    toastTip.show();
                }else if(info.equals("S")){
                    Toast toastTip = Toast.makeText(RegisterActivity.this, "该邮箱已被注册！", Toast.LENGTH_LONG);
                    toastTip.show();
                }else {
                    Toast toastTip = Toast.makeText(RegisterActivity.this, "发送失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };

    }

    /**
     * 执行倒计时操作
     */
    public void start() {
        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected void onPreExecute() {
                // 准备执行前调用，用于界面初始化操作
                edtgetVerifyCode.setText("30秒后再次获取");
                edtgetVerifyCode.setTextColor(getResources().getColor(R.color.changeColor));
            }

            @Override
            protected Integer doInBackground(Integer... params) {
                // 子线程，耗时操作
                int start = 0;
                int end = 29;
                int result = 0;
                for (int i = end; i >= start; i--) {
                    SystemClock.sleep(1000);
                    result = i;
                    publishProgress(result);//把进度推出去，推给onProgressUpdate参数位置
                }
                return result;
            }

            @Override
            protected void onProgressUpdate(Integer[] values) {
                int progress = values[0];
                edtgetVerifyCode.setText(progress+"秒后再次获取");
            };

            @Override
            protected void onPostExecute(Integer result) {
                edtgetVerifyCode.setText("获取验证码");
                edtgetVerifyCode.setTextColor(Color.parseColor("#1E90FF"));
            }

        }.execute(0,100);
    }

    /**
     * 向服务器发送注册信息
     * @param name
     * @param password
     * @param verifyCode
     */
    public void toRegister(final String name, final String password,final String verifyCode){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/travel_diary/RegisterServlet?name="+name+"&&"+"password="+password+"&&"+"verifyCode="+verifyCode);
                    Log.e("url",name+password+verifyCode);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
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
     * 请求服务器发送邮件
     * @param name
     * @param Tag
     */
    public void toSendEmail(final String name, final String Tag){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/travel_diary/SendEmailServlet?emailAddress="+name+"&&"+"Tag="+Tag);
                    Log.e("url",name+password+Tag);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    sendEmailHandler.sendMessage(msg);
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
    protected void registeListener(){
        onFocusListener = new CustomeOnFocusListener();
        onclickListener = new CustomeOnClickListener();
        edtEmailAddress.setOnFocusChangeListener(onFocusListener);
        edtPassword.setOnFocusChangeListener(onFocusListener);
        edtAgainPassword.setOnFocusChangeListener(onFocusListener);
        edtVerifyCode.setOnFocusChangeListener(onFocusListener);
        edtgetVerifyCode.setOnClickListener(onclickListener);
    }

    /**
     * 输入文本框监听器
     * 监听文本内容改变，点击button传递参数，执行toLogin方法向服务器发送数据
     */
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
                            if(isEmail(emailAddress)) {
                                toRegister(emailAddress,password,VerifyCode);
                            }else{
                                Toast toastTip = Toast.makeText(RegisterActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }

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
                            if(isEmail(emailAddress)) {
                                toRegister(emailAddress,password,VerifyCode);
                            }else{
                                Toast toastTip = Toast.makeText(RegisterActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
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
                            if(isEmail(emailAddress)) {
                                toRegister(emailAddress,password,VerifyCode);
                            }else{
                                Toast toastTip = Toast.makeText(RegisterActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
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
                            if(isEmail(emailAddress)) {
                                toRegister(emailAddress,password,VerifyCode);
                            }else{
                                Toast toastTip = Toast.makeText(RegisterActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
                        }
                    });
                }else{
                    btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.registerstyle));
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
        edtEmailAddress = findViewById(R.id.edtEmailaddress);
        edtPassword = findViewById(R.id.edtPassword);
        edtAgainPassword = findViewById(R.id.edtAgainPass);
        edtVerifyCode = findViewById(R.id.edtVerifyCode);
        edtgetVerifyCode = findViewById(R.id.edtGetVerifyCode);
        btnNext = findViewById(R.id.btnNext);
        noSame = findViewById(R.id.noSame);
        passwordLength = findViewById(R.id.passwordLength);
    }

    /**
     * 调整状态栏和标题栏style
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
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
                case R.id.edtGetVerifyCode:
                    textBtnedtgetVerifyCode = edtgetVerifyCode.getText().toString().trim();
                    emailAddress = edtEmailAddress.getText().toString().trim();
                    password = edtPassword.getText().toString().trim();
                    VerifyCode = edtVerifyCode.getText().toString().trim();
                    if (emailAddress.equals("") || password.equals("")) {
                        Toast toastTip = Toast.makeText(RegisterActivity.this, "请填写信息！", Toast.LENGTH_LONG);
                        toastTip.show();
                    } else {
                        if (textBtnedtgetVerifyCode.equals("获取验证码")
                                && !edtgetVerifyCode.getTextColors().equals(R.color.changeColor)
                                && isEmail(emailAddress)) {
                            toSendEmail(emailAddress, "register");
                        } else {
                            Toast toastTip = Toast.makeText(RegisterActivity.this, "请稍后再获取！", Toast.LENGTH_LONG);
                            toastTip.show();
                        }
                    }
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
