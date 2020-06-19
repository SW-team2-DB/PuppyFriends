package com.sw.PuppyFriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Waiting extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String matching_id, usertype, owner_id, sitter_id;
    private TextView waitingtxt;
    private Button cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);

        Intent intent = getIntent();
        matching_id = intent.getExtras().getString("matching_id");
        usertype = intent.getExtras().getString("usertype");
        owner_id = intent.getExtras().getString("owner_id");
        sitter_id = intent.getExtras().getString("sitter_id");

        waitingtxt = (TextView)findViewById(R.id.waitingtxt);
        cancel = (Button)findViewById(R.id.cancelbtn);

        if(usertype.equals("owner")) waitingtxt.setText("펫시터의 동의를 기다리는 중입니다.");
        else if(usertype.equals("sitter")) waitingtxt.setText("아직 견주가 사전만남 결과서를 작성하지 않았습니다.");

        //견주의 매칭 취소
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usertype.equals("owner")){
                    //매칭 상태 변경
                    databaseReference.child("matching").child(matching_id).child("status").child("owner").setValue("견주취소");
                    databaseReference.child("matching").child(matching_id).child("status").child("sitter").setValue("견주취소");

                    //견주 신청 정보 삭제
                    removeSittingDetailInfo();

                    //펫시터 신청 정보 삭제
                    removeSittingApplicationInfo();

                    Intent homeintent = new Intent(getApplicationContext(), HomeActivity.class);
                    homeintent.putExtra("id",owner_id);
                    startActivity(homeintent);
                    finish();
                }
            }
        });

        //견주 시점. 펫시터 구하기 >> 펫시터 선택 >> 사전만남 작성 >> 결과서 띄우고 동의 >> (지금 여기) 펫시터 동의 기다린다고 알림
        databaseReference.child("matching").child(matching_id).child("사전만남").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(usertype.equals("owner") && dataSnapshot.child("sitter_agree").getValue().toString().equals("yes")){
                    //펫시터가 사전만남 결과서에 동의했다면, 돌봄 진행중 화면으로 intent
                    Intent intent = new Intent(getApplicationContext(), SittingOngoing.class);
                    intent.putExtra("matching_id", matching_id);
                    intent.putExtra("usertype","owner");
                    intent.putExtra("owner_id", owner_id);
                    intent.putExtra("sitter_id", sitter_id);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //펫시터 시점. 돌봄 확인 >> 매칭이 성사된 돌봄을 선택 >> (지금 여기) 결과서 작성 기다린다고 알리고 결과서 올라오면 띄움
        databaseReference.child("matching").child(matching_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(usertype.equals("sitter") && dataSnapshot.child("사전만남").exists()){
                    //사전만남 결과서가 작성되면 결과서 띄우기
                    Intent intent = new Intent(getApplicationContext(), MeetingResult.class);
                    intent.putExtra("owner_id", dataSnapshot.child("owner_id").getValue().toString());
                    intent.putExtra("sitter_id", dataSnapshot.child("sitter_id").getValue().toString());
                    intent.putExtra("matching_id", matching_id);
                    intent.putExtra("isSitter", true);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeSittingDetailInfo(){
        //매칭이 종료되면 DB에서 owner의 sitting_detail_info(견주가 '펫시터 구하기'를 위해 입력한 정보) 삭제
        databaseReference.child("sitting_detail_info").child(owner_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                        postSnapshot.getRef().removeValue();
                        postSnapshot.getRef().setValue("");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeSittingApplicationInfo(){
        //매칭이 종료되면 DB에서 sitting application info에서 정보 삭제
        databaseReference.child("sitting_application_info").child(sitter_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    dataSnapshot.child("application_id").getRef().setValue("");
                    databaseReference.child("sitting_application_info").push().child("application_id").setValue("견주("+owner_id+") 취소");
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                        postSnapshot.getRef().removeValue();
                        postSnapshot.getRef().setValue("");
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
