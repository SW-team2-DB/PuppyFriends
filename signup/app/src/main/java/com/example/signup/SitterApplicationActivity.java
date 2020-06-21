package com.example.signup;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SitterApplicationActivity extends Activity {

    private DatabaseReference mPostReference;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("sitting_detail_info");
    DatabaseReference sitterRef = mRootRef.child("sitting_application_info");

    LinearLayout linearLayout;

    static int cnt = 0;
    String id;
    int size = getSize();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // application_status 공유해서 사용함
        setContentView(R.layout.application_status);

        // 이 레이아웃에 버튼이랑 textview 추가할 것임
        linearLayout = findViewById(R.id.user_info_layout);

        // 전 화면에서 id값 가져옴
        // login activity에 mail을 split해서 다른 액티비티로 넘겨주는 코드 추가함
        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Log.d("id", id);

        // 견주 목록 출력
        getFirebaseDB();
    }

    // 펫시터 정보를 db에 올림
    public void postFirebaseDB(String str){

        mPostReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValue = null;

        // 처음에는 is_connected 부분을 f로 지정함
        // 만약 견주가 펫시터를 선택하면 그 때 t로 바뀌게 됌
        SittingApplicationInfo post = new SittingApplicationInfo(id,"sitter", str, false, Integer.toString(size++));

        postValue = post.toMap();

        childUpdates.put("/sitting_application_info/" + id , postValue);
        mPostReference.updateChildren(childUpdates);

    }

    public int getSize(){
        sitterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return size;
    }

    // sitting detail info db에서 견주들 목록 받아옴
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
                    SittingDetailInfo get = postSnapshot.getValue(SittingDetailInfo.class);

                    // DB에 '.'이 안들어감 메일이 xxx@gmail.com 이면 xxx@gmail만 넣도록 일단은 해놓음
                    // 메일을 보여줄 때는 전체를 보여줘야하므로 .com을 추가함
                    result = "mail : " + get.id + ".com";

                    // 확인용
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + result);

                    // 동적으로 버튼과 text 추가
                    // 이 if문은 확인용임
                    if(get.type.equals("not-sitter")){
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

    // 동적으로 버튼 추가하는 함수
    private void addButtonLayout(){
        final Button btn = new Button(this);

        // 버튼 속성
        btn.setText("선택");
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(20);
        // 동적으로 아이디 설정, 숫자임
        // text view를 먼저 넣고 버튼을 넣어서 text view 아이디가 0이면 버튼 아이디는 1임
        btn.setId(cnt++);
        btn.setBackgroundColor(getResources().getColor(R.color.purple1));

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                // 바로 위의 mail 정보를 가지고 있는 text view 가져옴
                TextView t = findViewById(btn.getId()-1);
                String seletedId = t.getText().toString();
                // mail : xxx@gmail.com -> 이런 형식으로 만들었으므로 split해서 2번째 요소만 가져옴
                // 참고로 db에 '.'이 안들어가서 .com을 빼고 집어넣을 것, 공백과 '.'을 기준으로 나눔
                String[] splitedId = seletedId.split(" |\\.");
                Log.d("clicked : ", splitedId[2]);
                // db에 넣어줄 것임
                postFirebaseDB(splitedId[2]);
            }
        });

        linearLayout.addView(btn);
    }

    // 동적으로 text view 추가하는 함수
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
