package com.project.li.travel_diary.Fragment;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.li.travel_diary.MessageTree.MessageTree;
import com.project.li.travel_diary.R;

import java.util.List;
import java.util.Map;

public class FuntionAdapter extends BaseAdapter {
    private Context context;    // 上下文环境
    private List<Map<String, String>> dataSource; // 声明数据源
    private int item_layout_id; // 声明列表项的布局
    private ImageView funcitonImage;
    private TextView functionText;
    private ImageView arrow;
    private int displayWidth;
    private int displayHeight;
    // 声明列表项中的控件
    public FuntionAdapter(Context context, List<Map<String, String>> dataSource,
                       int item_layout_id) {
        this.context = context;       // 上下文环境
        this.dataSource = dataSource; // 数据源
        this.item_layout_id = item_layout_id; // 列表项布局文件ID
    }
    public int getCount() {return dataSource.size();}
    public Object getItem(int position) { return dataSource.get(position); }
    public long getItemId(int position) { return position; }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id,null);
        }

        final Map<String,String> textFull = dataSource.get(position);

        settingScreen(convertView);
        functionImageSet(position);

        functionText.setText(textFull.get("text").toString());

        return convertView;
    }

    //屏幕的适配以及控件的获取
    public void settingScreen(View convertView){

        LinearLayout functionLayout = convertView.findViewById(R.id.function_item_layout);
        funcitonImage = convertView.findViewById(R.id.function_image);
        functionText = convertView.findViewById(R.id.function_text);
        arrow = convertView.findViewById(R.id.arrow);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;


        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int)(displayHeight * 0.08f + 0.5f));
        params0.setMargins(0,(int)(displayHeight * 0.05f + 0.5f),0,0);
        functionLayout.setLayoutParams(params0);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                (int)(displayHeight * 0.04f + 0.5f),
                (int)(displayHeight * 0.04f + 0.5f));
        params1.setMargins((int)(displayWidth * 0.08f + 0.5f),(int)(displayHeight * 0.02f + 0.5f),(int)(displayWidth * 0.08f + 0.5f),(int)(displayHeight * 0.02f + 0.5f));
        funcitonImage.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 0.35f + 0.5f),
                (int)(displayHeight * 0.05f + 0.5f));
        params2.setMargins((int)(displayWidth * 0.1f + 0.5f),(int)(displayHeight * 0.015f + 0.5f),(int)(displayWidth * 0.22f + 0.5f),(int)(displayHeight * 0.015f + 0.5f));
        functionText.setTextSize(displayHeight * 0.006f + 0.5f);
        functionText.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                (int)(displayHeight * 0.02f + 0.5f),
                (int)(displayHeight * 0.02f + 0.5f));
        params3.setMargins(0,(int)(displayHeight * 0.03f + 0.5f),0,(int)(displayHeight * 0.03f + 0.5f));
        arrow.setLayoutParams(params3);
    }

    //图片资源的设置
    public void functionImageSet(int position){
        switch (position){
            case 0:
                funcitonImage.setImageResource(R.mipmap.image0);
                break;
            case 1:
                funcitonImage.setImageResource(R.mipmap.image1);
                break;
            case 2:
                funcitonImage.setImageResource(R.mipmap.image2);
                break;
            case 3:
                funcitonImage.setImageResource(R.mipmap.image3);
                break;
            case 4:
                funcitonImage.setImageResource(R.mipmap.image4);
                break;
        }
    }

}
