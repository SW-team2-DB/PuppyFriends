package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Usingguide extends AppCompatActivity {


    Button btn_before;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.using_guide);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Toast.makeText(Usingguide.this, "ID : " + id, Toast.LENGTH_SHORT).show();



        btn_before = (Button) findViewById(R.id.btn_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
    }
}