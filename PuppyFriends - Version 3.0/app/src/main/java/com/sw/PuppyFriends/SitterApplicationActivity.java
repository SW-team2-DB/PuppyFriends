package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.Resource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SitterApplicationActivity extends Activity {

    private DatabaseReference mPostReference;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("sitting_detail_info");
    DatabaseReference sitterRef = mRootRef.child("sitting_application_info");

    LinearLayout linearLayout;

    static int cnt = 0;
    String id;
    String price1;
    String price2;
    String location;
    byte serviceType = (byte) 0;

    int dateSelection;
    int size;

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
        price1 = intent.getExtras().getString("price1");
        price2 = intent.getExtras().getString("price2");
        dateSelection = intent.getExtras().getInt("date");
        location = intent.getExtras().getString("location");
        serviceType = intent.getExtras().getByte("service");
        size = getSize();

        Log.d("info : ", String.valueOf(size));

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
        SittingApplicationInfo post = new SittingApplicationInfo(id,"sitter", str, false, Integer.toString(++size));

        postValue = post.toMap();

        childUpdates.put("/sitting_application_info/" + id , postValue);
        mPostReference.updateChildren(childUpdates);

    }

    public int getSize(){
        sitterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
                Log.d("account size : ", String.valueOf(size));
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

            TextView title = (TextView)findViewById(R.id.applistatustitletext);

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                linearLayout.removeAllViews();

                Log.d("getFirebaseDB","key : " + dataSnapshot.getChildrenCount());

                boolean flag = false;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    /////////////////////////////////////////////////////////
                    if(postSnapshot.exists() && postSnapshot.child("date").exists() && !postSnapshot.child("date").getValue().toString().equals("")){
                    /////////////////////////////////////////////////////////

                    String key = postSnapshot.getKey();
                    SittingDetailInfo get;

                    get = postSnapshot.getValue(SittingDetailInfo.class);
//                    result = "mail : " + get.id + ".com";

                    long differ = calDateDiffer(get.date);
                    byte service = 0;

                    if(get.service_type != null)
                        service = (byte) Integer.parseInt(get.service_type);
                    Log.d("service type", String.valueOf(service));
                    service &= serviceType;

                    if(get.type.equals("not-sitter") && (Integer.parseInt(get.desired_price) >= Integer.parseInt(price1))
                            && (Integer.parseInt(get.desired_price) <= Integer.parseInt(price2)) && (differ <= dateSelection*5) &&
                            location.equals("상관없음") && (!key.equals(id)) && (service == serviceType)) {

                        addLayout(get.id + ".com", id);
//                        addTextViewLayout(result);
//                        addButtonLayout();
                        title.setText("신청 정보");
                        flag = true;

                    } else if(get.type.equals("not-sitter") && (Integer.parseInt(get.desired_price) >= Integer.parseInt(price1))
                            && (Integer.parseInt(get.desired_price) <= Integer.parseInt(price2)) && (differ <= dateSelection*5) &&
                            location.equals(get.location) && (!key.equals(id)) && (service == serviceType)) {

                        addLayout(get.id + ".com", id);
//                        addTextViewLayout(result);
//                        addButtonLayout();
                        title.setText("신청 정보");
                        flag = true;

                    }


                    /////////////////////////////////////////////////////////
                    }
                    /////////////////////////////////////////////////////////
                }

                if(!flag) {
                    title.setText("조건에 맞는 견주가 없습니다");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private long calDateDiffer(String s){
         String[] splited = s.split("/");
        SimpleDateFormat format = null;

        long now = System.currentTimeMillis();
        long differ = 0;
        Date time = new Date(now);
        Date mDate = null;
        Date targetDate = null;

        if(splited.length == 2){
            format = new SimpleDateFormat("MM/dd");

        } else if (splited.length == 3) {
            format = new SimpleDateFormat("YYYY/MM/dd");
        }

        try {
            String sTime = format.format(time);
            mDate = format.parse(sTime);
            targetDate = format.parse(s);

            long n;

            differ = Math.abs(targetDate.getTime() - mDate.getTime()) / (24*60*60*1000);

            return differ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Integer.MAX_VALUE;
    }

    private void addLayout(String userIdTxt, final String my_id){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.user_info_view);
        ((ViewGroup) layout.getParent()).removeView(layout);

        TextView textView = new TextView(this);
        textView.setText(userIdTxt);
        textView.setTextSize(20);
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

        final String seletedId = textView.getText().toString();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 30, 30);
        button.setLayoutParams(params);
        textView.setLayoutParams(params);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") TextView t = findViewById(button.getId()-1);
                String seletedId = t.getText().toString();
                String[] splitedId = seletedId.split(" |\\.");
                Toast.makeText(SitterApplicationActivity.this, "clicked : " + splitedId[0], Toast.LENGTH_SHORT).show();

                postFirebaseDB(splitedId[0]);
                finish();
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

}
