package com.project.li.travel_diary;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.amap.api.maps.model.LatLng;
import com.project.li.travel_diary.Login.LoginActivity;
import com.project.li.travel_diary.bean.Messages;

import java.io.Serializable;

public class AddMessageActivity extends AppCompatActivity {
    private Button add;
    private EditText title;
    private EditText content;
    private String t;
    private String c;
    private Messages messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        title=findViewById(R.id.title);
        content=findViewById(R.id.add_content);
        add=findViewById(R.id.add);
        textChange();
        Intent intent = getIntent();
        messages = (Messages) intent.getSerializableExtra("messages");
    }
    protected void textChange(){
        /**
         * 输入账号文本框监听器
         */
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                t=title.getText().toString().trim();
                c=content.getText().toString().trim();
                if(!t.equals("") && !c.equals("")){
                    add.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messages.setTitle(t);
                            messages.setContent(c);
                            Intent intent = new Intent();
                            intent.putExtra("msg", messages);
                            setResult(101,intent);
                            finish();
                        }
                    });
                }else{
                    add.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }
        });
        /**
         * 输入密码文本框监听器
         */
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                t=title.getText().toString().trim();
                c=content.getText().toString().trim();
                if(!t.equals("") && !c.equals("")){
                    add.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnloginstyle));
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messages.setTitle(t);
                            messages.setContent(c);
                            Intent intent = new Intent();
                            intent.putExtra("msg", messages);
                            setResult(101,intent);
                            finish();
                        }
                    });
                }else{
                    add.setBackgroundDrawable(getResources().getDrawable(R.drawable.loginstyle));
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }
        });
    }
}
