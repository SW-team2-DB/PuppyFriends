package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Button searchSitterButton;
    Button doSitterButton;
    Button sittingcheck;
    Button sitterlistbtn;

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
        sittingcheck = (Button)findViewById(R.id.sittingcheck);

        sittingcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Matchinglist.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        sitterlistbtn = (Button)findViewById(R.id.sitterlistbtn);
        sitterlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sitterlist.class);
                startActivity(intent);
            }
        });



        searchSitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //이미 등록한 sitting deatil info가 있으면 입력받는건 건너뛰고 없으면 입력받기
                databaseReference.child("sitting_detail_info").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){ //없을 때 입력받기
                            Intent intent = new Intent(getApplicationContext(), SettingDetailInfoActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }else{ //있을 때 건너뛰기
                            Intent intent = new Intent(getApplicationContext(), UserApplicationActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                                startActivity(new Intent(HomeActivity.this, Users.class));
                                break;
                            case R.id.action_home:
                                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_profile:
                                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                                startActivity(intentProfile);
                                break;
                        }
                        return true;
                    }
                });
    }
}