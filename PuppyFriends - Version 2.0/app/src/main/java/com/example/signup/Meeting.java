package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Meeting extends AppCompatActivity {

    String matching_id;

    LinearLayout container;
    EditText meeting_edit;
    Button complete;
    EditText feeEdit;

    EditText application_date, meetingguide1, meetingguide2, meetingguide3, meetingguide4, meetingguide5, meetingguide6;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting);

        Intent intent = getIntent();
        matching_id = intent.getExtras().getString("matching_id");
        Toast.makeText(Meeting.this, "matching id : " + matching_id, Toast.LENGTH_SHORT).show();

        container = (LinearLayout)findViewById(R.id.container);
        meeting_edit = (EditText)findViewById(R.id.meeting_edit);

        feeEdit = (EditText)findViewById(R.id.fee);
        complete = (Button)findViewById(R.id.complete);

        application_date = (EditText)findViewById(R.id.application_date);
        meetingguide1 = (EditText)findViewById(R.id.meetingguide1);
        meetingguide2 = (EditText)findViewById(R.id.meetingguide2);
        meetingguide3 = (EditText)findViewById(R.id.meetingguide3);
        meetingguide4 = (EditText)findViewById(R.id.meetingguide4);
        meetingguide5 = (EditText)findViewById(R.id.meetingguide5);
        meetingguide6 = (EditText)findViewById(R.id.meetingguide6);

        complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String date = application_date.getText().toString();
                String detail1 = meetingguide1.getText().toString();
                String detail2 = meetingguide2.getText().toString();
                String detail3 = meetingguide3.getText().toString();
                String detail4 = meetingguide4.getText().toString();
                String detail5 = meetingguide5.getText().toString();
                String detail6 = meetingguide6.getText().toString();
                String etc = meeting_edit.getText().toString();
                String fee = feeEdit.getText().toString();

                //데이터베이스에 올리기

                //데이터베이스 test용, 나중에 지워주세요.
                if(date.isEmpty()) Toast.makeText(getApplicationContext(), "날짜를 입력해주세요", Toast.LENGTH_LONG).show();
                else if(detail1.isEmpty() || detail2.isEmpty() || detail3.isEmpty() || detail4.isEmpty() || detail5.isEmpty()){
                    Toast.makeText(getApplicationContext(), "빈 칸을 채워주세요", Toast.LENGTH_LONG).show();
                }
                else if(fee.isEmpty()) Toast.makeText(getApplicationContext(), "최종 가격을 입력해주세요", Toast.LENGTH_LONG).show();
                    //특이사항이랑 기타 이외에는 필수적으로 채워야 한다
                else{
                    databaseReference.child("test").child(matching_id).child("날짜").setValue(date);
                    databaseReference.child("test").child(matching_id).child("장소").setValue(detail1);
                    databaseReference.child("test").child(matching_id).child("밥주는시간").setValue(detail2);
                    databaseReference.child("test").child(matching_id).child("사료위치").setValue(detail3);
                    databaseReference.child("test").child(matching_id).child("산책시간").setValue(detail4);
                    databaseReference.child("test").child(matching_id).child("산책상세").setValue(detail5);
                    databaseReference.child("test").child(matching_id).child("특이사항").setValue(detail6);
                    databaseReference.child("test").child(matching_id).child("etc").setValue(etc);
                    databaseReference.child("test").child(matching_id).child("가격").setValue(fee);

                    Intent intent = new Intent(getApplicationContext(), MeetingResult.class);
                    startActivity(intent);
                }
            }
        });
    }
}
