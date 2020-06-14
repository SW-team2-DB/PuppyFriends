package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    //4 : search , 5 : do , 6 : list
    Button listButton;
    Button searchSitterButton;
    Button doSitterButton;

    String id, usertype;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Toast.makeText(HomeActivity.this, "id : " + id, Toast.LENGTH_SHORT).show();

        usertype = intent.getExtras().getString("usertype");


        if (usertype.equals("견주")) { //usertype이 견주면 견주 화면(OwnerActivity)으로 이동
            Intent ointent = new Intent(getApplicationContext(), OwnerActivity.class);
            ointent.putExtra("id", id);
            startActivity(ointent);
            finish();
        }
        else if (usertype.equals("펫시터")) { //usertype이 펫시터면 펫시터 화면(SitterActivity)으로 이동
            Intent ointent = new Intent(getApplicationContext(), SitterActivity.class);
            ointent.putExtra("id", id);
            startActivity(ointent);
            finish();
        }
    }
}
