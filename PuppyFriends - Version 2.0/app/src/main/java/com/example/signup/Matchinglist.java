package com.example.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.inappmessaging.MessagesProto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Matchinglist extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference pathReference = databaseReference.child("matching");
    String id;

    static int cnt = 0;
    int size = getSize();

    LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchinglist);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        linearLayout = (LinearLayout)findViewById(R.id.matchinglistlayout);


        pathReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linearLayout.removeAllViews();

                addTextViewLayout(id);

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    String matchingidkey = postSnapshot.getKey();

                    if(postSnapshot.child("owner_id").getValue().toString().equals(id) || postSnapshot.child("sitter_id").getValue().toString().equals(id)){

                        if(postSnapshot.child("status").exists() && postSnapshot.child("status").getValue().toString().equals("종료")){
                            // 종료된 매칭
                            addTextViewLayout(matchingidkey);
                            Toast.makeText(getApplicationContext(), matchingidkey+"종료됨", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            addTextViewLayout(matchingidkey);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private int getSize(){
        pathReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size =  (int)dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return size;
    }

    private void addTextViewLayout(String str){
        TextView textView = new TextView(this);

        // text view 속성
        textView.setText(str);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setId(cnt++);
        linearLayout.addView(textView);
    }

}
