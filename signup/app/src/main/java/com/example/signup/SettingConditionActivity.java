package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingConditionActivity extends AppCompatActivity {

    Button nextBtn;
    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // application_status 공유해서 사용함
        setContentView(R.layout.condition_setting);

        nextBtn = findViewById(R.id.next_btn);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Log.d("id", id);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingConditionActivity.this, SitterApplicationActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

}
