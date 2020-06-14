package com.example.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class SettingConditionActivity extends AppCompatActivity {

    Button nextBtn;
    String id;

    EditText price1;
    EditText price2;

    Button dateBtn1;
    Button dateBtn2;
    Button dateBtn3;

    int dateSelection = -1;

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // application_status 공유해서 사용함
        setContentView(R.layout.condition_setting);

        nextBtn = findViewById(R.id.next_btn);
        price1 = findViewById(R.id.target_low_price);
        price2 = findViewById(R.id.target_high_price);

        dateBtn1 = findViewById(R.id.day5_select_btn);
        dateBtn2 = findViewById(R.id.day10_select_btn);
        dateBtn3 = findViewById(R.id.extra_day_select_btn);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Log.d("id", id);

        dateBtn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                dateSelection = 1;
                dateBtn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24px, 0, 0, 0);
                dateBtn2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                dateBtn3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });

        dateBtn2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                dateSelection = 2;
                dateBtn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24px, 0, 0, 0);
                dateBtn1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                dateBtn3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });

        dateBtn3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                dateSelection = 100;
                dateBtn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24px, 0, 0, 0);
                dateBtn1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                dateBtn2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateSelection != -1){
                    Intent intent = new Intent(SettingConditionActivity.this, SitterApplicationActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("price1", price1.getText().toString());
                    intent.putExtra("price2", price2.getText().toString());
                    intent.putExtra("date", dateSelection);
                    startActivity(intent);
                }
            }
        });
    }

}
