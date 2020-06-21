package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Objects;

// 견주가 영어로 뭔지 잘 모르겠어서 일단은 owner이라고 함
public class UserApplicationActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("sitting_application_info");

    LinearLayout linearLayout;
    Button myInfoBtn;
    TextView title;

    String id;
    int size = -1;
    static int cnt = 0;

    String matching_id = null;
    ImageButton imageButton;

    String date, desired_price, dog_age, dog_breed, dog_name, location, user_age, user_gender, user_name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // application_status 공유해서 사용함
        setContentView(R.layout.application_status);

        linearLayout = findViewById(R.id.user_info_layout);
        title = findViewById(R.id.applistatustitletext);

        // 전 화면에서 id값 가져옴
        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Log.d("id", id);

        getFirebaseDB();
    }

    // sitting detail info db에서 견주들 목록 받아옴
    public void getMatchingId(final String mId){

        conditionRef.addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

//                    SittingApplicationInfo get = postSnapshot.getValue(SittingApplicationInfo.class);
//
//                    if(get.id.equals(mId)){
//                        matching_id = get.matching_id;
//                        Log.d("matching_id", matching_id);
//                    }

                    String aid = postSnapshot.getKey();
                    if(aid.equals(mId)){ // sitting_application_info에서 mId와 같은 id를 찾아서 매칭아이디 가져옴
                        /////////////////////
                        if(postSnapshot.child("matching_id").exists()){
                            //////////////////////
                            matching_id = postSnapshot.child("matching_id").getValue().toString();
                            //////////////////
                        }
                        //////////////////
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        });
    }

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

                    result = get.id;

                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + result);

                    ////////////////////////////
                    if(postSnapshot.child("application_id").exists()) {
                        ///////////////////////////

                        // 자신을 선택한 펫시터가 있으면
                        if(get.application_id.equals(id)){
                            // 목록 보여줌
                            addLayout(key + ".com", id);
                        }

                    }
                    ///////////////////////

                }
                if(linearLayout.getChildCount()==0) {
                    //자신을 선택한 펫시터가 없으면
                    noApplication();

                } else{
                    //신청한 펫시터 있어도 조건 변경 버튼 추가
                    Button cancel = newButton();
                    cancel.setText("신청 취소");
                    cancel.setGravity(Gravity.CENTER);
                    cancel.setTextSize(20);
                    cancel.setBackground(getResources().getDrawable(R.drawable.custom_btn));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,100,0,0);
                    cancel.setLayoutParams(params);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(View v) {

                            //신청했던 펫시터의 신청정보 삭제
                            mRootRef.child("sitting_application_info").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                        if(postSnapshot.child("application_id").exists()){
                                            if(postSnapshot.child("application_id").getValue().toString().equals(id)){
                                                mRootRef.child("sitting_application_info").push().child("application_id").setValue("견주의 조건 변경으로 인한 취소");
                                                for(DataSnapshot postpostSnapshot : postSnapshot.getChildren()){
                                                    postSnapshot.getRef().removeValue();
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                            //신청했던 펫시터의 신청정보 삭제
                            mRootRef.child("sitting_detail_info").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                        if(Objects.equals(postSnapshot.getKey(), id)){
                                            postSnapshot.getRef().removeValue();
//                                            if(postSnapshot.child("application_id").getValue().toString().equals(id)){
//                                                mRootRef.child("sitting_application_info").push().child("application_id").setValue("견주의 조건 변경으로 인한 취소");
//                                                for(DataSnapshot postpostSnapshot : postSnapshot.getChildren()){
//                                                    postSnapshot.getRef().removeValue();
//                                                }
//                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });


                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        }
                    });
                    linearLayout.addView(cancel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        });
    }


    private void noApplication(){ //자신을 선택한 펫시터가 없을 때
        title.setText("아직 지원한 펫시터가 없습니다");

        final Button btn = new Button(this);

        btn.setText("조건 변경");
        btn.setTextColor(Color.WHITE);
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(20);
        btn.setBackground(getResources().getDrawable(R.drawable.custom_btn));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SettingDetailInfoActivity.class);
                        intent.putExtra("id", id);

                        //이전에 입력한 값 인텐트 보내기
                        intent.putExtra("date", date);
                        intent.putExtra("desired_price", desired_price);
                        intent.putExtra("dog_age", dog_age);
                        intent.putExtra("dog_breed", dog_breed);
                        intent.putExtra("dog_name", dog_name);
                        intent.putExtra("location", location);
                        intent.putExtra("user_age", user_age);
                        intent.putExtra("user_gender", user_gender);
                        intent.putExtra("user_name", user_name);

                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        // 이전에 설정한 값 받아오기
        mRootRef.child("sitting_detail_info").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("date").exists()){
                    date = dataSnapshot.child("date").getValue().toString();
                } else date = "";

                if(dataSnapshot.child("desired_price").exists()){
                    desired_price = dataSnapshot.child("desired_price").getValue().toString();
                } else desired_price = "";

                if(dataSnapshot.child("dog_age").exists()){
                    dog_age = dataSnapshot.child("dog_age").getValue().toString();
                } else dog_age = "";

                if(dataSnapshot.child("dog_breed").exists()){
                    dog_breed = dataSnapshot.child("dog_breed").getValue().toString();
                } else dog_breed = "";

                if(dataSnapshot.child("dog_name").exists()){
                    dog_name = dataSnapshot.child("dog_name").getValue().toString();
                } else dog_name = "";

                if(dataSnapshot.child("location").exists()){
                    location = dataSnapshot.child("location").getValue().toString();
                } else location = "";

                if(dataSnapshot.child("user_age").exists()){
                    user_age = dataSnapshot.child("user_age").getValue().toString();
                } else user_age = "";

                if(dataSnapshot.child("user_gender").exists()){
                    user_gender = dataSnapshot.child("user_gender").getValue().toString();
                } else user_gender = "";

                if(dataSnapshot.child("user_name").exists()){
                    user_name = dataSnapshot.child("user_name").getValue().toString();
                } else user_name = "";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        linearLayout.addView(btn);
    }

    private void addLayout(String userIdTxt, final String my_id){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.user_info_view);
        ((ViewGroup) layout.getParent()).removeView(layout);

        TextView textView = new TextView(this);
        textView.setText(userIdTxt);
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER);
        textView.setId(cnt++);

        View view1 = new View(this);
        view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1));

        final Button button = new Button(this);
        button.setText("프로필 확인");
        button.setTextColor(Color.WHITE);
        button.setGravity(Gravity.CENTER);
        button.setBackground(getResources().getDrawable(R.drawable.custom_btn));
        button.setId(cnt++);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 30, 30);
        button.setLayoutParams(params);
        textView.setLayoutParams(params);

        final String seletedId = textView.getText().toString();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] splitedId = seletedId.split(" |\\.");
                // Toast.makeText(UserApplicationActivity.this, splitedId[0], Toast.LENGTH_SHORT).show();

                getMatchingId(splitedId[0]);
                updateFirebaseDB(splitedId[0]);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//////////////////////////////////////////// 사전만남 입력하면 튕기는거 해결
                        mRootRef.child("matching").child(matching_id).child("owner_id").setValue("");
                        mRootRef.child("matching").child(matching_id).child("sitter_id").setValue("");
                        mRootRef.child("matching").child(matching_id).child("status").child("owner").setValue("");
                        mRootRef.child("matching").child(matching_id).child("status").child("sitter").setValue("");
////////////////////////////////////////////
                        Intent intent = new Intent(UserApplicationActivity.this, Meetingbefore_agree.class);
                        intent.putExtra("owner_id", id);
                        intent.putExtra("sitter_id", splitedId[0]);
                        intent.putExtra("matching_id", matching_id);
                        startActivity(intent);
                        finish();
                    }
                }, 500);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            final String idid[] = seletedId.split("@");

            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), CheckProfileActivity.class);
                        intent.putExtra("id", idid[0]);
                        intent.putExtra("my_id", my_id);
                        startActivity(intent);

                    }
                }, 500);
            }
        });

        layout.addView(textView);
        layout.addView(view1);
        layout.addView(button);
        linearLayout.addView(layout);
    }

    private Button newButton(){
        Button b = new Button(this);
        return b;
    }


}
