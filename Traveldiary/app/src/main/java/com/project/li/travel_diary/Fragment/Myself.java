package com.project.li.travel_diary.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.li.travel_diary.Login.LoginActivity;
import com.project.li.travel_diary.MessageTree.MessageTree;
import com.project.li.travel_diary.R;
import com.project.li.travel_diary.Settings.Setting;

import static android.content.Context.MODE_PRIVATE;

public class Myself extends Fragment {
    private CustomeOnClickListener onClickListener;
    private int displayWidth;
    private int displayHeight;
    private Button treeBtn;
    private Button notiBtn;
    private Button favoBtn;
    private Button quesBtn;
    private Button settingBtn;
    private Button downBtn;
    private TextView nickname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myself_layout,container,false);
        getButtons(view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", MODE_PRIVATE);
        nickname.setText(sharedPreferences.getString("nickname",""));

        registLitener();
        settingScreen();

        return view;
    }

    //注册监听器
    class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.settingbtn:
                    Intent intent1 = new Intent();
                    intent1.setClass(getContext(), Setting.class);
                    startActivity(intent1);
                    break;
                case R.id.treebtn:
                    Intent intent2 = new Intent();
                    intent2.setClass(getContext(), MessageTree.class);
                    startActivity(intent2);
                    break;
                case R.id.notibtn:
                    Toast.makeText(getContext(),"功能尚在开发，敬请期待...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.favobtn:
                    Toast.makeText(getContext(),"功能尚在开发，敬请期待...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.quesbtn:
                    Toast.makeText(getContext(),"功能尚在开发，敬请期待...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.downbtn:
                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("dataChange", MODE_PRIVATE).edit();
                    editor1.putString("password", "");
                    editor1.commit();
                    Intent intent = new Intent();
                    intent.setClass(getContext(),LoginActivity.class);
                    getActivity().finish();
                    break;
            }

        }
    }

    //获取视图控件
    private void getButtons(View view) {
        treeBtn = view.findViewById(R.id.treebtn);
        notiBtn = view.findViewById(R.id.notibtn);
        favoBtn = view.findViewById(R.id.favobtn);
        quesBtn = view.findViewById(R.id.quesbtn);
        settingBtn = view.findViewById(R.id.settingbtn);
        downBtn = view.findViewById(R.id.downbtn);
        nickname = view.findViewById(R.id.nickname);
    }

    //绑定监听器
    private void registLitener() {
        onClickListener = new CustomeOnClickListener();
        settingBtn.setOnClickListener(onClickListener);
        treeBtn.setOnClickListener(onClickListener);
        notiBtn.setOnClickListener(onClickListener);
        favoBtn.setOnClickListener(onClickListener);
        quesBtn.setOnClickListener(onClickListener);
        downBtn.setOnClickListener(onClickListener);
    }

    //适配屏幕
    private void settingScreen() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;


        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.85f + 0.5f),
                (int)(displayHeight * 0.15f + 0.5f));
        params1.setMargins(0,(int)(displayHeight * 0.06f + 0.5f),0,0);
        treeBtn.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.85f + 0.5f),
                (int)(displayHeight * 0.05f + 0.5f));
        params2.setMargins(0,(int)(displayHeight * 0.06f + 0.5f),0,0);
        notiBtn.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.85f + 0.5f),
                (int)(displayHeight * 0.05f + 0.5f));
        params3.setMargins(0,(int)(displayHeight * 0.01f + 0.5f),0,0);
        favoBtn.setLayoutParams(params3);
        quesBtn.setLayoutParams(params3);

        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.9f + 0.5f),
                (int)(displayHeight * 0.06f + 0.5f));
        params4.setMargins(0,(int)(displayHeight * 0.09f + 0.5f),0,0);
        settingBtn.setLayoutParams(params4);

        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.9f + 0.5f),
                (int)(displayHeight * 0.06f + 0.5f));
        params5.setMargins(0,(int)(displayHeight * 0.01f + 0.5f),0,0);
        downBtn.setLayoutParams(params5);
    }


}
