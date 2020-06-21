package com.example.signup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MeetingResult extends AppCompatActivity {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference =  mDatabase.getReference();

    private TextView meetingresult1, meetingresult2, meetingresult3, meetingresult4, meetingresult5, meetingresult6, meetingresult7, meetingresult8, meetingresult9;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_result);

        meetingresult1 = (TextView)findViewById(R.id.meetingresult1);
        meetingresult2 = (TextView)findViewById(R.id.meetingresult2);
        meetingresult3 = (TextView)findViewById(R.id.meetingresult3);
        meetingresult4 = (TextView)findViewById(R.id.meetingresult4);
        meetingresult5 = (TextView)findViewById(R.id.meetingresult5);
        meetingresult6 = (TextView)findViewById(R.id.meetingresult6);
        meetingresult7 = (TextView)findViewById(R.id.meetingresult7);
        meetingresult8 = (TextView)findViewById(R.id.meetingresult8);
        meetingresult9 = (TextView)findViewById(R.id.meetingresult9);


        //이건 임시로. 데이터베이스에 맞도록 수정
        String root1 = "test";
        //이건 임시로. 데이터베이스에서 매칭ID 받아오기
        String root2 = "매칭임시";

        String meeting1 = "날짜";
        String meeting2 = "장소";
        String meeting3 = "밥주는시간";
        String meeting4 = "사료위치";
        String meeting5 = "산책시간";
        String meeting6 = "산책상세";
        String meeting7 = "특이사항";
        String meeting8 = "etc";
        String meeting9 = "가격";

        //날짜
        mReference.child(root1).child(root2).child(meeting1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult1.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //장소
        mReference.child(root1).child(root2).child(meeting2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult2.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //밥주는시간
        mReference.child(root1).child(root2).child(meeting3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult3.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //사료위치
        mReference.child(root1).child(root2).child(meeting4).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult4.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //산책시간
        mReference.child(root1).child(root2).child(meeting5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult5.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //산책상세
        mReference.child(root1).child(root2).child(meeting6).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult6.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //특이사항
        mReference.child(root1).child(root2).child(meeting7).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult7.setText(dataSnapshot.getValue().toString());
                if(meetingresult7.getText().toString().isEmpty()) meetingresult7.setText("(공백)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //기타
        mReference.child(root1).child(root2).child(meeting8).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult8.setText(dataSnapshot.getValue().toString());
                if(meetingresult8.getText().toString().isEmpty()) meetingresult8.setText("(공백)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //가격
        mReference.child(root1).child(root2).child(meeting9).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingresult9.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}