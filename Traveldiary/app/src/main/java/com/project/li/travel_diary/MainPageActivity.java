package com.project.li.travel_diary;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.project.li.travel_diary.Fragment.Findings;
import com.project.li.travel_diary.Fragment.Footprint;
import com.project.li.travel_diary.Fragment.Message;

import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends AppCompatActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.drawable.footg,"足迹"));
        fragmentTabHost.addTab(tabSpec1, Footprint.class,null);

        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.drawable.fangda,"发现"));

        fragmentTabHost.addTab(tabSpec2, Findings.class,null);

        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.drawable.tipg,"消息"));
        fragmentTabHost.addTab(tabSpec3, Message.class,null);

        fragmentTabHost.setCurrentTab(2);
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.colorMyBlue));

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                textViewMap.get(tabId).setTextColor(getResources().getColor(R.color.colorMyBlue));
                switch(tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.drawable.foot);
                        imageViewMap.get("tag2").setImageResource(R.drawable.fangdag);
                        imageViewMap.get("tag3").setImageResource(R.drawable.tipg);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.colorMyGray));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.colorMyGray));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.drawable.footg);
                        imageViewMap.get("tag2").setImageResource(R.drawable.fangda);
                        imageViewMap.get("tag3").setImageResource(R.drawable.tipg);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.colorMyGray));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.colorMyGray));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.drawable.footg);
                        imageViewMap.get("tag2").setImageResource(R.drawable.fangdag);
                        imageViewMap.get("tag3").setImageResource(R.drawable.tip);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.colorMyGray));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.colorMyGray));
                        break;
                }
            }
        });



    }

    public View getTabSpecView(String tag, int imageResId, String title){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_layout,null);

        ImageView imageView = view.findViewById(R.id.item_image);
        imageView.setImageResource(imageResId);

        TextView textView = view.findViewById(R.id.item_text);
        textView.setText(title);

        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);

        return view;
    }
}
