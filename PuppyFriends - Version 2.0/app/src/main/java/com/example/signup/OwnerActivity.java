package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OwnerActivity extends AppCompatActivity {
    //4 : search , 5 : do , 6 : list
    Button listButton;
    Button searchSitterButton;
    Button doSitterButton;

    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Toast.makeText(getApplicationContext(), "id : " + id, Toast.LENGTH_SHORT).show();

//        doSitterButton = (Button) findViewById(R.id.button5);
        searchSitterButton = (Button) findViewById(R.id.button4);

//        doSitterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingConditionActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });

        searchSitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingDetailInfoActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        Intent intent = getIntent();
        String userid = intent.getExtras().getString("id"); //프로필 편집 들어가기 위한 유저아이디

        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == R.id.action_account) {
            Toast.makeText(this, "내 정보", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_home) {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }
        if (id == R.id.logout) {
            Intent logoutIntent = new Intent(this, LogInActivity.class);
            startActivity(logoutIntent);
        }
        if (id == R.id.action_button2) {
            Intent ImageIntent = new Intent(this, ImageActivity.class);
            startActivity(ImageIntent);
        }
        if (id == R.id.chat) {
            Intent chatIntent = new Intent(this, ChatActivity.class);
            startActivity(chatIntent);
        }
        if (id == R.id.action_button_edit_profile) {
            Intent editprofileintent = new Intent(this, EditProfile.class);
            editprofileintent.putExtra("id", userid);
            startActivity(editprofileintent);
        }
        return super.onOptionsItemSelected(item);
    }

}
