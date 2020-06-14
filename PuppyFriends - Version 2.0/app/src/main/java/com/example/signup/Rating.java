package com.example.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rating extends AppCompatActivity {

    String matching_id = "매칭임시"; //매칭id 받아오기
    String my_id = "me"; //사용자 id 받아오기
    String obj_id = "you"; //상대방 id 받아오기
    String usertype = "견주"; //사용자유형 받아오기
    String ratingtype;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("test").child(matching_id).child("rating");

    TextView title;
    TextView detail;
    RadioGroup r;
    TextView point;
    EditText review;
    Button ratingbtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        title = (TextView)findViewById(R.id.ratingtitle);
        detail = (TextView)findViewById(R.id.ratingdetail);
        r = (RadioGroup)findViewById(R.id.ratingradiogroup);
        point = (TextView)findViewById(R.id.ratingstarpoint);
        review = (EditText)findViewById(R.id.ratingreview);
        ratingbtn = (Button)findViewById(R.id.ratingbtn);

        if(usertype.equals("견주")) {
            title.setText("펫시터 평가");
            detail.setText("펫시터의 돌봄에 대한 리뷰를 작성해주세요. 약속을 성실히 이행했는지, 돌봄의 만족도는 어떘는지 등 종합적인 의견을 작성해주세요.");
            ratingtype = "펫시터";
        }
        else if(usertype.equals("펫시터")) {
            title.setText("견주 평가");
            detail.setText("돌봄을 부탁한 견주에 대한 리뷰를 작성해주세요. 펫시터를 충분히 배려하였는지, 특이사항을 정확하게 고지했는지 등 종합적인 의견을 작성해주세요.");
            ratingtype = "견주";
        }

        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rating1) point.setText("1");
                else if (i==R.id.rating2) point.setText("2");
                else if (i==R.id.rating3) point.setText("3");
                else if (i==R.id.rating4) point.setText("4");
                else if (i==R.id.rating5) point.setText("5");
            }
        });

        ratingbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(point.getText().toString().equals("0"))  Toast.makeText(getApplicationContext(), "평점을 입력해주세요", Toast.LENGTH_LONG).show();
                else{
                    databaseReference.child(ratingtype+"평가").child("평가자").setValue(my_id);
                    databaseReference.child(ratingtype+"평가").child("평가대상").setValue(obj_id);
                    databaseReference.child(ratingtype+"평가").child("point").setValue(point.getText().toString());
                    databaseReference.child(ratingtype+"평가").child("review").setValue(review.getText().toString());
                }
            }
        });
    }
}
