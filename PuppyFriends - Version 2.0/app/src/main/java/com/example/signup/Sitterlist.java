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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Sitterlist extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference pathReferences = databaseReference.child("rated_sitter");
    Query pathReference = pathReferences.orderByChild("order");

    static int cnt = 0;

    LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sitterlist);

        linearLayout = (LinearLayout)findViewById(R.id.sitterlistlayout);


        pathReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linearLayout.removeAllViews();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    String sitterkey = postSnapshot.getKey();
                    String evev = postSnapshot.child("point").getValue().toString();
                    addTextViewLayout(sitterkey, evev);
                    addButtonLayout(sitterkey, evev);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void addTextViewLayout(String str, String eve){
        TextView textView = new TextView(this);

        // text view 속성
        textView.setText(str+" ★"+eve);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setId(cnt++);
        linearLayout.addView(textView);
    }

    @SuppressLint("ResourceAsColor")
    private void addButtonLayout(String st, String eve){
        final Button btn = new Button(this);

        btn.setText("프로필");
//        btn.setText(st+" ★"+eve);
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(20);
        btn.setId(cnt++);
        btn.setBackgroundColor(getResources().getColor(R.color.purple1));

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.bottomMargin = 30;
        btn.setLayoutParams(param);

        @SuppressLint("ResourceType") TextView t = findViewById(btn.getId()-1);
        final String seletedId = t.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 500);
            }
        });

        linearLayout.addView(btn);
    }

}
