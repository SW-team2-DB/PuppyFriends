package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SittingOngoing extends AppCompatActivity {

    private static final int MILLISINFUTURE = 11*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    Button btn_calling, btn_endcaring;
    TextView textView_title, timetv;

    private int count = 10;
    private CountDownTimer countDownTimer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongoing_petsitting);

        btn_calling = (Button) findViewById(R.id.btn_calling);
        btn_calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chatinactivity 연결
                Intent Chatintent = new Intent(SittingOngoing.this, HomeActivity.class);
                startActivity(Chatintent);
            }
        });
        btn_endcaring = (Button) findViewById(R.id.btn_endcaring);
        btn_endcaring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mathreport 연결
                Intent matchintent = new Intent(SittingOngoing.this, matchreport.class);
                startActivity(matchintent);
            }
        });
        timetv = (TextView)findViewById(R.id.timetv);
        countDownTimer();
        countDownTimer.start();

        textView_title = (TextView) findViewById(R.id.textView_title);
    }
    public void countDownTimer(){

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                timetv.setText(String.valueOf(count + "분 남았습니다."));
                count --;
            }
            public void onFinish() {
                timetv.setText(String.valueOf("Finish ."));
            }
        };
    }
}