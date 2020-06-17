package com.example.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Matchinglist extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference pathReference = databaseReference.child("matching");
    String id;

    String owner_id, sitter_id, usertype, status;

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

                    if(postSnapshot.child("owner_id").exists()){
                        if(postSnapshot.child("owner_id").getValue().toString().equals(id) || postSnapshot.child("sitter_id").getValue().toString().equals(id)){

                            addTextViewLayout(matchingidkey);
                            addButtonLayout();

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

    @SuppressLint("ResourceAsColor")
    private void addButtonLayout(){
        final Button btn = new Button(this);

        btn.setText("선택");
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(20);
        btn.setId(cnt++);
        btn.setBackgroundColor(getResources().getColor(R.color.purple1));

        @SuppressLint("ResourceType") TextView t = findViewById(btn.getId()-1);
        final String seletedId = t.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                pathReference.child(seletedId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        owner_id = dataSnapshot.child("owner_id").getValue().toString();
                        sitter_id = dataSnapshot.child("sitter_id").getValue().toString();
                        if(id.equals(owner_id)) usertype = "owner";
                        else if(id.equals(sitter_id)) usertype = "sitter";

                        //status 구분
                        if(dataSnapshot.child("status").exists() && dataSnapshot.child("status").child(usertype).getValue().toString().equals("진행중")) status = "진행중";
                        else if(dataSnapshot.child("status").exists() && dataSnapshot.child("status").child(usertype).getValue().toString().equals("종료")) status = "종료";

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(status.equals("진행중")){
                            Intent intent = new Intent(getApplication(), Waiting.class);
                            intent.putExtra("owner_id", owner_id);
                            intent.putExtra("sitter_id", sitter_id);
                            intent.putExtra("matching_id", seletedId);
                            intent.putExtra("usertype", usertype);
                            startActivity(intent);
                        } else if(status.equals("종료")){
                            //////////////////////////////////////////////
                            //////////////////////////////////////////////
                            //사전만남 결과서와 돌봄 일지 볼 수 있도록 수정
                            //////////////////////////////////////////////
                            //////////////////////////////////////////////
                            Toast.makeText(getApplicationContext(), seletedId+"는 종료되었습니다.", Toast.LENGTH_SHORT).show();
                        }



                    }
                }, 500);
            }
        });

        linearLayout.addView(btn);
    }

}
