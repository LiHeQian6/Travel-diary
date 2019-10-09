package com.project.li.travel_diary.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.li.travel_diary.Login.LoginActivity;
import com.project.li.travel_diary.MessageTree.MessageTree;
import com.project.li.travel_diary.MessageTree.TreeAdapter;
import com.project.li.travel_diary.R;
import com.project.li.travel_diary.Settings.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Myself extends Fragment {
    /*private CustomeOnClickListener onClickListener;*/
    private List<Map<String, String>> functionDataSource;
    private int displayWidth;
    private int displayHeight;
    private TextView myEmail;
    private Button downBtn;
    private TextView nickname;
    private ImageView myPen;
    private Boolean up = false;
    private FuntionAdapter funtionAdapter;
    private TextView line;
    private SharedPreferences prefChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myself_layout,container,false);

        getViews(view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", MODE_PRIVATE);
        nickname.setText(sharedPreferences.getString("nickName",""));
        myEmail.setText("邮箱："+sharedPreferences.getString("name",""));

        settingScreen(view);
        funtionItem(view);

        //注销按钮的点击事件
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor1 = getContext().getSharedPreferences("dataChange", MODE_PRIVATE).edit();
                editor1.putString("password","");
                editor1.commit();
                Intent intent = new Intent();
                intent.setClass(getContext(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        up = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(up){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", MODE_PRIVATE);
            nickname.setText(sharedPreferences.getString("nickName",""));
        }
        up = false;
    }

    //获取视图控件
    private void getViews(View view) {
        nickname = view.findViewById(R.id.nickname);
        myEmail = view.findViewById(R.id.my_email);
        downBtn = view.findViewById(R.id.down_btn);
        myPen = view.findViewById(R.id.my_pen);
        line = view.findViewById(R.id.list_view_topline);
    }

    //设置功能adapter
    public void funtionItem(View view){
        functionDataSource = new ArrayList<>();
        String text[] = {"历史留言","收藏","通知","问题与反馈","设置"};
        for(int i=0;i<5;i++){
            Map<String,String> item = new HashMap<>();
            item.put("text",text[i]);
            functionDataSource.add(item);
        }
        ListView listView = view.findViewById(R.id.myself_item);
        funtionAdapter = new FuntionAdapter(getContext(),functionDataSource,R.layout.funtion_item);
        listView.setAdapter(funtionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent();
                    intent.setClass(getContext(), MessageTree.class);
                    startActivity(intent);
                }else if(position == 4){
                    Intent intent = new Intent();
                    intent.setClass(getContext(),Setting.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"功能尚在开发，敬请期待...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //动态设置控件大小
    public void settingScreen(View view){
        LinearLayout myself = view.findViewById(R.id.myself);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int)(displayHeight * 0.15f + 0.5f));
        params.setMargins(0,(int)(displayHeight * 0.05f + 0.5f),0,0);
        myself.setLayoutParams(params);

        RoundImageView roundImageView = view.findViewById(R.id.touxaing);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                (int)(displayHeight * 0.12f + 0.5f),
                (int)(displayHeight * 0.12f + 0.5f));
        params1.setMargins((int)(displayHeight * 0.03f + 0.5f),(int)(displayHeight * 0.015f + 0.5f), (int)(displayWidth * 0.025f + 0.5f),(int)(displayHeight * 0.015f + 0.5f));
        roundImageView.setLayoutParams(params1);

        LinearLayout nickLayout = view.findViewById(R.id.nick_layout);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.5f + 0.5f),
                (int)(displayHeight * 0.12f + 0.5f));
        params2.setMargins((int)(displayWidth * 0.03f + 0.5f),(int)(displayHeight * 0.05f + 0.5f), 0,(int)(displayHeight * 0.015f + 0.5f));
        nickLayout.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                (int)(displayHeight * 0.08f + 0.5f),
                (int)(displayHeight * 0.08f + 0.5f));
        params3.setMargins(-(int)(displayWidth * 0.06f + 0.5f),(int)(displayHeight * 0.04f + 0.5f), 0,(int)(displayHeight * 0.015f + 0.5f));
        myPen.setLayoutParams(params3);

        nickname.setTextSize((int)(displayWidth * 0.013f + 0.5f));
        myEmail.setTextSize((int)(displayWidth * 0.01f + 0.5f));

        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params4.setMargins(0,(int)(displayHeight * 0.08f + 0.5f), 0,0);
        line.setLayoutParams(params4);

        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.8f + 0.5f), (int)(displayHeight * 0.06f + 0.5f));
        params5.setMargins(0,(int)(displayHeight * 0.08f + 0.5f), 0,0);
        downBtn.setLayoutParams(params5);
    }

}
