package com.project.li.travel_diary.Login;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.project.li.travel_diary.R;
import com.project.li.travel_diary.showMessages.MainPageActivity;

public class WelcomActivity extends AppCompatActivity {
    private int displayHeight;
    private int displayWidth;
    private TextView appTitle;
    private TextView appMeaning;
    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.in,R.anim.out);
            finish();
        }
    };
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),MainPageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.in,R.anim.out);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setStatusBar();
        settingSrceen();

        if(getSharedPreferences("data",MODE_PRIVATE).getString("name","") == null ||
                getSharedPreferences("dataChange",MODE_PRIVATE).getString("password","").equals("")){
            handler1.sendEmptyMessageDelayed(1,1500);
        }else{
            handler2.sendEmptyMessageDelayed(1,1200);
        }



    }
    //动态设置控件
    public void settingSrceen(){
        appTitle = findViewById(R.id.app_title);
        appMeaning = findViewById(R.id.app_meaning);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0, 0,(int)(displayHeight * 0.01f + 0.5f));
        appTitle.setLayoutParams(params);
        appTitle.setTextSize((int)(displayHeight * 0.03f + 0.5f));

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0,0, 0,(int)(displayHeight * 0.3f + 0.5f));
        appMeaning.setLayoutParams(params1);
        appMeaning.setTextSize((int)(displayHeight * 0.01f + 0.5f));


    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

}
