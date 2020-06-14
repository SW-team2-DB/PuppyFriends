package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rating extends AppCompatActivity {

    String your_id, usertype, ratingtype, matching_id;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    TextView title;
    TextView detail;
    RadioGroup r;
    TextView point;
    EditText review;
    Button ratingbtn;

    int m;
    int p;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        Intent intent = getIntent();
        your_id = intent.getExtras().getString("your_id");
        usertype = intent.getExtras().getString("usertype");

        matching_id = intent.getExtras().getString("matching_id");

        title = (TextView)findViewById(R.id.ratingtitle);
        detail = (TextView)findViewById(R.id.ratingdetail);
        r = (RadioGroup)findViewById(R.id.ratingradiogroup);
        point = (TextView)findViewById(R.id.ratingstarpoint);
        review = (EditText)findViewById(R.id.ratingreview);
        ratingbtn = (Button)findViewById(R.id.ratingbtn);

        if(usertype.equals("owner")) {
            title.setText("펫시터 평가");
            detail.setText("펫시터의 돌봄에 대한 리뷰를 작성해주세요. 약속을 성실히 이행했는지, 돌봄의 만족도는 어떘는지 등 종합적인 의견을 작성해주세요.");
            ratingtype = "펫시터";
        }
        else if(usertype.equals("sitter")) {
            title.setText("견주 평가");
            detail.setText("돌봄을 부탁한 견주에 대한 리뷰를 작성해주세요. 펫시터를 충분히 배려하였는지, 특이사항을 정확하게 고지했는지 등 종합적인 의견을 작성해주세요.");
            ratingtype = "견주";
        }

        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rating1) {
                    point.setText("1");
                    m=1;
                }
                else if (i==R.id.rating2) {
                    point.setText("2");
                    m=2;
                }
                else if (i==R.id.rating3) {
                    point.setText("3");
                    m=3;
                }
                else if (i==R.id.rating4) {
                    point.setText("4");
                    m=4;
                }
                else if (i==R.id.rating5) {
                    point.setText("5");
                    m=5;
                }
            }

        });

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child("point").exists()){ //이미 점수가 있으면 불러와서 더하고 평균
//                    int nowpoint = Integer.parseInt(dataSnapshot.child("point").getValue().toString());
//
//                    if(point.getText().toString().equals("1")) newpoint = 1;
//                    else if (point.getText().toString().equals("2")) newpoint = 2;
//                    else if (point.getText().toString().equals("3")) newpoint = 3;
//                    else if (point.getText().toString().equals("4")) newpoint = 4;
//                    else if (point.getText().toString().equals("5")) newpoint = 5;
//
//                    finalpoint = nowpoint + newpoint;
//                }
//                else{ //기존 점수가 없으면
//
//                    if(point.getText().toString().equals("1")) newpoint = 1;
//                    else if (point.getText().toString().equals("2")) newpoint = 2;
//                    else if (point.getText().toString().equals("3")) newpoint = 3;
//                    else if (point.getText().toString().equals("4")) newpoint = 4;
//                    else if (point.getText().toString().equals("5")) newpoint = 5;
//
//                    finalpoint = newpoint;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        ratingbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(point.getText().toString().equals("0"))  Toast.makeText(getApplicationContext(), "평점을 입력해주세요", Toast.LENGTH_LONG).show();
                else{
                    databaseReference.child("rating").child(your_id).child("point").push().setValue(m);
                    databaseReference.child("rating").child(your_id).child("review").push().setValue(review.getText().toString());
                    databaseReference.child("matching").child(matching_id).child("status").setValue("종료");
                }
            }
        });
    }
//
//    private int nowpoint(){
//
//        databaseReference.child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(your_id).child("point").exists()){ //이미 점수가 있으면 받아오기
////                    p = Integer.parseInt(dataSnapshot.child(your_id).child("point").getValue().toString());
//                    p = 8;
//
//                }
//                else{ //기존 점수가 없으면 0
//                    p = 0;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        return p;
//    }
}
