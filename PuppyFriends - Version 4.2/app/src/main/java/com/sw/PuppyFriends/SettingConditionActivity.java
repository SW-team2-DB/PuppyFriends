package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingConditionActivity extends AppCompatActivity {

    Button nextBtn;
    String id;

    EditText price1;
    EditText price2;

    Button dateBtn1;
    Button dateBtn2;
    Button dateBtn3;

    RadioGroup locRadioGroup;
    String location;

    int dateSelection = -1;

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;

    byte isChecked = 0;

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

        checkBox1 = findViewById(R.id.service_check1);
        checkBox2 = findViewById(R.id.service_check2);
        checkBox3 = findViewById(R.id.service_check3);
        checkBox4 = findViewById(R.id.service_check4);

        locRadioGroup = findViewById(R.id.loc_radio_btn_group);

        locRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.loc1_btn:
                        location = "대덕구";
                        break;
                    case R.id.loc2_btn:
                        location = "유성구";
                        break;
                    case R.id.loc3_btn:
                        location = "동구";
                        break;
                    case R.id.loc4_btn:
                        location = "서구";
                        break;
                    case R.id.loc5_btn:
                        location = "중구";
                        break;
                    case R.id.loc6_btn:
                        location = "상관없음";
                        break;
                }
            }
        });

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
                dateSelection = 300;
                dateBtn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24px, 0, 0, 0);
                dateBtn1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                dateBtn2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateSelection != -1){
                    getCheckBoxStatus();
                    Intent intent = new Intent(SettingConditionActivity.this, SitterApplicationActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("price1", price1.getText().toString());
                    intent.putExtra("price2", price2.getText().toString());
                    intent.putExtra("date", dateSelection);
                    intent.putExtra("location", location);
                    intent.putExtra("service", isChecked);
                    startActivity(intent);
                }
            }
        });
    }

    private void getCheckBoxStatus(){
        if (checkBox1.isChecked()) {
            isChecked |= (byte) 1;
        } else {
            isChecked &= (byte) 14;
        }

        if (checkBox2.isChecked()) {
            isChecked |= (byte) 2;
        } else {
            isChecked &= (byte) 13;
        }

        if (checkBox3.isChecked()) {
            isChecked |= (byte) 4;
        } else {
            isChecked &= (byte) 11;
        }

        if (checkBox4.isChecked()) {
            isChecked |= (byte) 8;
        } else {
            isChecked &= (byte) 7;
        }

        Log.d("check box :" , String.valueOf(isChecked));
    }

}
