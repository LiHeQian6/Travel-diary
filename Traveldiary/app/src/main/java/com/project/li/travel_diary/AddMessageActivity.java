package com.project.li.travel_diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.amap.api.maps.model.LatLng;
import com.project.li.travel_diary.bean.Messages;

import java.io.Serializable;

public class AddMessageActivity extends AppCompatActivity {
    private Button add;
    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        title=findViewById(R.id.title);
        content=findViewById(R.id.add_content);
        add=findViewById(R.id.add);
        Intent intent = getIntent();
        final Messages messages = (Messages) intent.getSerializableExtra("messages");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getBundleExtra("lng");
                String t=title.getText().toString().trim();
                String c=content.getText().toString().trim();
                messages.setTitle(t);
                messages.setContent(c);
                Intent intent = new Intent();
                intent.putExtra("msg", messages);
                setResult(101,intent);
                finish();
            }
        });

    }
}
