package com.project.li.travel_diary.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.*;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPassActivity extends AppCompatActivity {

    private EditText edtEmailaddress;//邮箱输入
    private EditText edtVerifyCode;//验证码输入
    private TextView btnGetVerifyCode;//点击获取验证码
    private Button btnNext;//点击下一步跳转
    private CustomeOnClickListener onClickListener;
    private CustomeOnFocusListener onFocusListener;
    private String emailAddress;//邮箱
    private String verifyCode;//验证码
    private String textBtngetVerifyCode;
    private Handler handler;
    private Handler mainHandler;
    private Handler sendEmailHandler;
    private int time = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        setStatusBar();
        getViews();
        textChange();
        registLitener();

        mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 100:
                        btnGetVerifyCode.setText(time-- + "秒后获取");
                        break;
                    case 200:
                        btnGetVerifyCode.setText("获取验证码");
                        btnGetVerifyCode.setTextColor(Color.parseColor("#1E90FF"));
                        time = 30;
                        break;
                }
            }
        };

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                if (info.equals("T")) {
                    Intent intent = new Intent();
                    intent.putExtra("emailAddress",emailAddress);
                    intent.setClass(ForgetPassActivity.this, ResetPassActivity.class);
                    startActivity(intent);
                    finish();
                }else if(info.equals("F")){
                    Toast toastTip = Toast.makeText(ForgetPassActivity.this, "验证失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
                else {
                    Toast toastTip = Toast.makeText(ForgetPassActivity.this, "没有此用户！", Toast.LENGTH_LONG);
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
                    Toast toastTip = Toast.makeText(ForgetPassActivity.this, "验证码已发送，请查收！", Toast.LENGTH_LONG);
                    toastTip.show();
                }else if(info.equals("S")){
                    Toast toastTip = Toast.makeText(ForgetPassActivity.this, "没有该邮箱的注册信息！", Toast.LENGTH_LONG);
                    toastTip.show();
                }else {
                    Toast toastTip = Toast.makeText(ForgetPassActivity.this, "发送失败！", Toast.LENGTH_LONG);
                    toastTip.show();
                }
            }
        };
    }

    /**
     * 向服务器发送数据并接收返回信息
     * @param name
     * @param verifyCode
     */
    public void toResetPassword(final String name,final String verifyCode){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/travel_diary/ForgetPasswordServlet?name="+name+"&&"+"verifyCode="+verifyCode);
                    Log.e("url",name+verifyCode);
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
                    Log.e("url",name+Tag);
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
     * 执行倒计时操作
     */
    public void start() {
        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected void onPreExecute() {
                // 准备执行前调用，用于界面初始化操作
                btnGetVerifyCode.setText("30秒后再次获取");
                btnGetVerifyCode.setTextColor(getResources().getColor(R.color.changeColor));
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
                btnGetVerifyCode.setText(progress+"秒后再次获取");
            };

            @Override
            protected void onPostExecute(Integer result) {
                btnGetVerifyCode.setText("获取验证码");
                btnGetVerifyCode.setTextColor(Color.parseColor("#1E90FF"));
            }

        }.execute(0,100);
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
     * 输入账号文本框监听器
     * 监听文本内容改变，点击button传递参数，执行toLogin方法向服务器发送数据
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
                            emailAddress = edtEmailaddress.getText().toString().trim();
                            verifyCode = edtVerifyCode.getText().toString().trim();
                            if(isEmail(emailAddress)) {
                                toResetPassword(emailAddress,verifyCode);
                            }else{
                                Toast toastTip = Toast.makeText(ForgetPassActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
                                toastTip.show();
                            }
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
                            emailAddress = edtEmailaddress.getText().toString().trim();
                            verifyCode = edtVerifyCode.getText().toString().trim();
                            if(isEmail(emailAddress)) {
                                toResetPassword(emailAddress,verifyCode);
                            }else{
                                Toast toastTip = Toast.makeText(ForgetPassActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_LONG);
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
    protected void getViews(){
        edtEmailaddress = findViewById(R.id.edtEmailaddress);
        edtVerifyCode = findViewById(R.id.edtVerifyCode);
        btnGetVerifyCode = findViewById(R.id.textGetVerifyCode);
        btnNext = findViewById(R.id.btnNext);
    }

    /**
     * 改变标题栏及状态栏style
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

    /**
     * 倒计时
     */
    private class CountThread extends Thread{
        @Override
        public void run() {
            while (true){
                //每1秒向主线程发送一个Message
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.what = 100;
                btnGetVerifyCode.setTextColor(Color.parseColor("#bcbcbc"));
                mainHandler.sendMessage(message);
                //当数字减为0时，停止循环
                if (time <= 0){
                    message.what = 200;
                    break;
                }
            }
        }
    }


    /**
     * 点击事件监听器
     */
    class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.textGetVerifyCode:
                    textBtngetVerifyCode = btnGetVerifyCode.getText().toString().trim();
                    emailAddress = edtEmailaddress.getText().toString().trim();
                    verifyCode = edtVerifyCode.getText().toString().trim();
                    if(edtEmailaddress.equals("")){
                        Toast toastTip = Toast.makeText(ForgetPassActivity.this, "请填写邮箱信息！", Toast.LENGTH_LONG);
                        toastTip.show();
                    }else if(!isEmail(emailAddress)){
                        Toast toastTip = Toast.makeText(ForgetPassActivity.this, "请正确填写邮箱信息！", Toast.LENGTH_LONG);
                        toastTip.show();
                    }else{
                        if (textBtngetVerifyCode.equals("获取验证码") && isEmail(emailAddress)) {
                            toSendEmail(emailAddress, "forgetPassword");
                        }else {
                            Toast toastTip = Toast.makeText(ForgetPassActivity.this, "请稍后再获取！", Toast.LENGTH_LONG);
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
