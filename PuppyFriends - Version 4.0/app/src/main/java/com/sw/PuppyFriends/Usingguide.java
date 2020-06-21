package com.sw.PuppyFriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Usingguide extends AppCompatActivity {

    Button btn_before;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.using_guide);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        btn_before = (Button) findViewById(R.id.btn_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent before = new Intent(getApplicationContext(), HomeActivity.class);
                before.putExtra("id", id);
                startActivity(before);
                finish();
            }
        });
    }
}