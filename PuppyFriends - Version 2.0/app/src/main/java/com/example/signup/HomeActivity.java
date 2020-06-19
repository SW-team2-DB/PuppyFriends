package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    Button searchSitterButton;
    Button doSitterButton;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Toast.makeText(HomeActivity.this, "ID : " + id, Toast.LENGTH_SHORT).show();

        searchSitterButton = (Button) findViewById(R.id.search);
        doSitterButton = (Button) findViewById(R.id.sitter);

        searchSitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingDetailInfoActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        doSitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingConditionActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });





        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_home:
                                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_profile:
                                Intent intentProfile = new Intent(HomeActivity.this, InfoActivity.class);
                                startActivity(intentProfile);
                                break;
                        }
                        return true;
                    }
                });
    }
}