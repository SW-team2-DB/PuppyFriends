package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DetailConditionCheck extends Activity {

    private DatabaseReference mPostReference;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    RadioButton r1,r2,r3,r4,r5,r6,r7;
    Button b1;

    String id;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // application_status 공유해서 사용함
        setContentView(R.layout.detail_condition_check);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        tv1 = (TextView)findViewById(R.id.dog_name_inp_txt);
        tv2 = (TextView)findViewById(R.id.dog_breed_inp_txt);
        tv3 = (TextView)findViewById(R.id.dog_age_inp_txt);
        tv4 = (TextView)findViewById(R.id.user_name_inp_txt);
        tv5 = (TextView)findViewById(R.id.user_age_inp_txt);
        tv6 = (TextView)findViewById(R.id.date_inp_txt1);
        tv7 = (TextView)findViewById(R.id.date_inp_txt2);
        tv8 = (TextView)findViewById(R.id.price_inp_txt);

        r1 = (RadioButton)findViewById(R.id.male_btn);
        r2 = (RadioButton)findViewById(R.id.female_btn);
        r3 = (RadioButton)findViewById(R.id.loc1_btn);
        r4 = (RadioButton)findViewById(R.id.loc2_btn);
        r5 = (RadioButton)findViewById(R.id.loc3_btn);
        r6 = (RadioButton)findViewById(R.id.loc4_btn);
        r7 = (RadioButton)findViewById(R.id.loc5_btn);


        mRootRef.child("sitting_detail_info").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv1.setText(dataSnapshot.child("dog_name").getValue().toString());
                tv2.setText(dataSnapshot.child("dog_breed").getValue().toString());
                tv3.setText(dataSnapshot.child("dog_age").getValue().toString());
                tv4.setText(dataSnapshot.child("user_name").getValue().toString());
                tv5.setText(dataSnapshot.child("user_age").getValue().toString());

                String[] date = dataSnapshot.child("date").getValue().toString().split("/");
                tv6.setText(date[0]);
                tv7.setText(date[1]);
                tv8.setText(dataSnapshot.child("desired_price").getValue().toString());

                String gender;

                if(dataSnapshot.child("user_gender").getValue().toString().equals("male")) r1.setChecked(true);
                else r2.setChecked(true);

                if(dataSnapshot.child("location").getValue().toString().equals("대덕구")) r3.setChecked(true);
                else if(dataSnapshot.child("location").getValue().toString().equals("유성구")) r4.setChecked(true);
                else if(dataSnapshot.child("location").getValue().toString().equals("동구")) r5.setChecked(true);
                else if(dataSnapshot.child("location").getValue().toString().equals("서구")) r6.setChecked(true);
                else if(dataSnapshot.child("location").getValue().toString().equals("중구")) r7.setChecked(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
