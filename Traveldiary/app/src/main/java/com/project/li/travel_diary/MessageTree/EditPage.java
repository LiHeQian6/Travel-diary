package com.project.li.travel_diary.MessageTree;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.li.travel_diary.R;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EditPage extends AppCompatActivity {
    private EditText titleEdit;
    private EditText contentEdit;
    private Button saveButton;
    private int position;
    private List<Map<String, String>> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        setStatusBar();

        titleEdit = findViewById(R.id.title_edit);
        contentEdit = findViewById(R.id.content_edit);
        saveButton = findViewById(R.id.save_button);

        Intent accept = getIntent();
        dataSource = (List<Map<String, String>>)accept.getSerializableExtra("dataSource");
        position = accept.getIntExtra("position",0);
        titleEdit.setText(dataSource.get(position).get("title"));
        contentEdit.setText(dataSource.get(position).get("message"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> textFull = dataSource.get(position);
                textFull.put("title",titleEdit.getText().toString());
                textFull.put("message",contentEdit.getText().toString());
                dataSource.add(textFull);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MessageTree.class);
                intent.putExtra("dataSource",(Serializable) dataSource);
                setResult(200,intent);
                finish();
                Log.e("title",dataSource.get(position).get("title"));

            }
        });
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }
}
