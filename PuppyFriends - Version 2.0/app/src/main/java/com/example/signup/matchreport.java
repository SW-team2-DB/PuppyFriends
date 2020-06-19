package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class matchreport extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Button button;
    TextView  petsittername, matchdate, matchtime, eat, review, bowel, walk;
    EditText  petsittername1, match_month1, match_day1, matchtime1, eat1, review1, bowel1, walk1;
    String matching_id, usertype, owner_id, sitter_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchreport);

        Intent intent = getIntent();
        matching_id = intent.getExtras().getString("matching_id");
        usertype = intent.getExtras().getString("usertype");
        owner_id = intent.getExtras().getString("owner_id");
        sitter_id = intent.getExtras().getString("sitter_id");

        Toast.makeText(matchreport.this, "matching id : " + matching_id, Toast.LENGTH_SHORT).show();


        button = (Button)findViewById(R.id.button);

        petsittername = (TextView)findViewById(R.id.petsittername);
        matchdate = (TextView)findViewById(R.id.matchdate);
        matchtime = (TextView)findViewById(R.id.matchtime);
        eat = (TextView)findViewById(R.id.eat);
        review = (TextView)findViewById(R.id.review);
        bowel = (TextView)findViewById(R.id.bowel);
        walk = (TextView)findViewById(R.id.walk);

        petsittername1 = (EditText)findViewById(R.id.petsittername1);
        matchtime1 = (EditText)findViewById(R.id.matchtime1);
        match_month1 = (EditText)findViewById(R.id.date_month);
        match_day1 = (EditText)findViewById(R.id.date_day);
        eat1 = (EditText)findViewById(R.id.eat1);
        review1 = (EditText)findViewById(R.id.review1);
        bowel1 = (EditText)findViewById(R.id.bowel1);
        walk1 = (EditText)findViewById(R.id.walk1);

        //확인버튼 눌렀을 때
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petsittername = petsittername1.getText().toString();
                String matchtime = matchtime1.getText().toString();
                String match_month = match_month1.getText().toString();
                String match_day = match_day1.getText().toString();
                String eat = eat1.getText().toString();
                String review = review1.getText().toString();
                String bowel = bowel1.getText().toString();
                String walk = walk1.getText().toString();

                //DB에 올리기
                if(petsittername.isEmpty()||matchtime.isEmpty()||eat.isEmpty()||bowel.isEmpty())
                    Toast.makeText(getApplicationContext(), "내용을 모두 채워주세요",Toast.LENGTH_LONG).show();
                else{
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("펫시터이름").setValue(petsittername);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("밥먹은시간").setValue(eat);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("돌봄시간").setValue(matchtime);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("방문날짜").child("월").setValue(match_month);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("방문날짜").child("일").setValue(match_day);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("배변횟수").setValue(bowel);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("산책한시간").setValue(walk);
                    databaseReference.child("matching").child(matching_id).child("matchreport").child("특이사항").setValue(review);

                    Intent ratingintent = new Intent(getApplicationContext(), Rating.class);
                    ratingintent.putExtra("your_id", owner_id);
                    ratingintent.putExtra("usertype", "sitter");

                    ratingintent.putExtra("matching_id", matching_id);
                    startActivity(ratingintent);
                }
            }
        });

    }
}