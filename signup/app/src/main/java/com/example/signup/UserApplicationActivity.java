package com.example.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

// 견주가 영어로 뭔지 잘 모르겠어서 일단은 owner이라고 함
public class UserApplicationActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("sitting_application_info");

    LinearLayout linearLayout;
    Button myInfoBtn;

    String id;
    int size = -1;
    static int cnt = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // application_status 공유해서 사용함
        setContentView(R.layout.application_status);

        linearLayout = findViewById(R.id.user_info_layout);

        // 전 화면에서 id값 가져옴
        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Log.d("id", id);

        // 견주는 들어가자마자 자기 정보가 DB에 올라감
        // 나중에는 화면을 하나 추가해서 기본 정보 작성하고 submit 버튼 누르면 정보 전송되도록 하면 될듯
        // false -> 견주 정보 올림, true -> 펫시터 정보 올림
//        postFirebaseDB(id, null,false);

        getFirebaseDB();
    }

//    public void postFirebaseDB(String mId, String applicationId){
//
//        mPostReference = FirebaseDatabase.getInstance().getReference();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        Map<String, Object> postValue = null;
//
//        SittingApplicationInfo post;
//
//        post = new SittingApplicationInfo(mId,"sitter", applicationId, true);
//        postValue = post.toMap();
//        childUpdates.put("/sitting_application_info/" + mId , postValue);
//        mPostReference.updateChildren(childUpdates);
//
//    }

    public void updateFirebaseDB(String mId){
        DatabaseReference hopperRef = conditionRef.child(mId);
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("is_connected", "t");

        hopperRef.updateChildren(hopperUpdates);
    }


    // sitting_application_info에 있는 펫시터 중에서 자신을 선택한 펫시터만 골라서 보여주는 함수
    public void getFirebaseDB(){

        conditionRef.addValueEventListener(new ValueEventListener() {
            String result;

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linearLayout.removeAllViews();

                Log.d("getFirebaseDB","key : " + dataSnapshot.getChildrenCount());

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    String key = postSnapshot.getKey();
                    SittingApplicationInfo get = postSnapshot.getValue(SittingApplicationInfo.class);

                    result = "mail : " + get.id + ".com";

                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + result);

                    // 자신을 선택한 펫시터가 있으면
                    if(get.application_id.equals(id)){
                        // 목록 보여줌
                        addTextViewLayout(result);
                        addButtonLayout();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void addButtonLayout(){
        final Button btn = new Button(this);

        btn.setText("선택");
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(20);
        btn.setId(cnt++);
        btn.setBackgroundColor(getResources().getColor(R.color.purple1));

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                TextView t = findViewById(btn.getId()-1);
                String seletedId = t.getText().toString();
                String[] splitedId = seletedId.split(" |\\.");
                Log.d("clicked : ", splitedId[2]);
                updateFirebaseDB(splitedId[2]);

                Intent intent = new Intent(UserApplicationActivity.this, Meeting.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        linearLayout.addView(btn);
    }

    private void addTextViewLayout(String str){
        TextView textView = new TextView(this);

        textView.setText(str);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setId(cnt++);
        linearLayout.addView(textView);
    }


}
