package com.sw.PuppyFriends;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingDetailInfoActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference conditionRef = mRootRef.child("sitting_application_info");

    Button nextBtn;
    EditText dogNameTxt;
    EditText dogBreedTxt;
    EditText dogAgeTxt;
    EditText userNameTxt;

    RadioGroup locRadioGroup;

    EditText userAgeTxt;
    EditText userAddress;
    EditText priceTxt;

    DatePicker dataPicker;

    String id;
    String gender;
    String loc;

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;

    TextView dateTxt;
    TextView genderTxt;

    byte isChecked = 0;
    boolean flag = false;

    String date, desired_price, dog_age, dog_breed, dog_name, location, user_age, user_gender, user_name;
    int month =1;
    int day =1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_condition_setting);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        Toast.makeText(SettingDetailInfoActivity.this, "ID : " + id, Toast.LENGTH_SHORT).show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        checkBox1 = findViewById(R.id.service_check1);
        checkBox2 = findViewById(R.id.service_check2);
        checkBox3 = findViewById(R.id.service_check3);
        checkBox4 = findViewById(R.id.service_check4);

        final FirebaseUser user = firebaseAuth.getCurrentUser();//최근유저가져오기

        nextBtn = findViewById(R.id.next_btn2);
        dogNameTxt = findViewById(R.id.dog_name_inp_txt);
        dogBreedTxt = findViewById(R.id.dog_breed_inp_txt);
        dogAgeTxt = findViewById(R.id.dog_age_inp_txt);

        userNameTxt = findViewById(R.id.user_name_inp_txt);
        userAgeTxt = findViewById(R.id.user_age_inp_txt);

        locRadioGroup = findViewById(R.id.loc_radio_btn_group);

        userAddress = findViewById(R.id.user_address);  //상세주소
        genderTxt = findViewById(R.id.gender_txt);

        dataPicker = findViewById(R.id.dataPicker);

        priceTxt = findViewById(R.id.price_inp_txt);

        dateTxt = findViewById(R.id.contect_info_txt);

        //프로필에서 겹치는 정보 다시 입력하지 않게 정보 가져오기
        // 이름 띄우기
        databaseReference.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userNameTxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // 상세주소 띄우기
        databaseReference.child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userAddress.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                genderTxt.setText(dataSnapshot.getValue().toString());
                gender = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // loc 라디오 버튼에 띄우기
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//                locRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                       if(dataSnapshot.child("loc").getValue().toString().equals("대덕구")){
//                           ((RadioButton)locRadioGroup.getChildAt(0)).setChecked(true);
//                       }
//                        else if(dataSnapshot.child("loc").getValue().toString().equals("유성구")){
//                           ((RadioButton)locRadioGroup.getChildAt(1)).setChecked(true);
//                       }
//                        else if(dataSnapshot.child("loc").getValue().toString().equals("동구")){
//                           ((RadioButton)locRadioGroup.getChildAt(2)).setChecked(true);
//                       }
//                        else if(dataSnapshot.child("loc").getValue().toString().equals("서구")){
//                           ((RadioButton)locRadioGroup.getChildAt(3)).setChecked(true);
//                        }
//                       else if(dataSnapshot.child("loc").getValue().toString().equals("중구")) {
//                           ((RadioButton)locRadioGroup.getChildAt(4)).setChecked(true);
//                        }
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        //성별 띄우기
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        if(dataSnapshot.child("gender").getValue().toString().equals("남자")){
//                            ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
//                        }
//                        else if(dataSnapshot.child("gender").getValue().toString().equals("여자")){
//                            ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
//                        }
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        // 나이 띄우기
        databaseReference.child("age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userAgeTxt.setText(dataSnapshot.getValue().toString()+"살");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //설정한 값 받아오기
        mRootRef.child("sitting_detail_info").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("desired_price").exists()){
                        if(dataSnapshot.child("date").exists()){
                            date = dataSnapshot.child("date").getValue().toString();
                        } else date = "1/1";

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

                        //설정한 값 있으면 적용하기
                        if(!date.isEmpty()){
                            String date_split[] = date.split("/");
                            int month = Integer.parseInt(date_split[0]);
                            int day = Integer.parseInt(date_split[1]);
                            setMD(month, day);
                            dataPicker.init(2020, month-1, day, new DatePicker.OnDateChangedListener() {
                                @Override
                                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                    date = monthOfYear-1 + "/" + dayOfMonth;
                                    date = monthOfYear+1 + "/" + dayOfMonth;
                                }
                            });
                        } else {
                            setMD(1,1);
                        }

                        if(!desired_price.isEmpty()){
                            priceTxt.setText(desired_price);
                        }

                        if(!dog_age.isEmpty()){
                            dogAgeTxt.setText(dog_age);

                        }

                        if(!dog_breed.isEmpty()){
                            dogBreedTxt.setText(dog_breed);
                        }

                        if(!dog_name.isEmpty()){
                            dogNameTxt.setText(dog_name);
                        }

                        if(!user_gender.isEmpty()){
                            genderTxt.setText(user_gender);
                        }

                        if(!location.isEmpty()){
                            if(location.equals("대덕구")){
                                ((RadioButton)locRadioGroup.getChildAt(0)).setChecked(true);
                            }
                            else if(location.equals("유성구")){
                                ((RadioButton)locRadioGroup.getChildAt(1)).setChecked(true);
                            }
                            else if(location.equals("동구")){
                                ((RadioButton)locRadioGroup.getChildAt(2)).setChecked(true);
                            }
                            else if(location.equals("서구")){
                                ((RadioButton)locRadioGroup.getChildAt(3)).setChecked(true);
                            }
                            else if(location.equals("중구")){
                                ((RadioButton)locRadioGroup.getChildAt(4)).setChecked(true);
                            }
                        }

                        if(!user_age.isEmpty()){
                            userAgeTxt.setText(user_age);
                        }

                        if(!user_name.isEmpty()){
                            userNameTxt.setText(user_name);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dataPicker.init(2020, 0, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = monthOfYear+1 + "/" + dayOfMonth;
                Log.d("date", date);
            }
        });

        locRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.loc1_btn:
                        loc = "대덕구";
                        break;
                    case R.id.loc2_btn:
                        loc = "유성구";
                        break;
                    case R.id.loc3_btn:
                        loc = "동구";
                        break;
                    case R.id.loc4_btn:
                        loc = "서구";
                        break;
                    case R.id.loc5_btn:
                        loc = "중구";
                        break;
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((dogNameTxt.getText().toString().equals("")) || (dogBreedTxt.getText().toString().equals(""))
                        || (dogAgeTxt.getText().toString().equals("")) || (priceTxt.getText().toString().equals(""))){
                    Toast.makeText(SettingDetailInfoActivity.this, "입력되지 않은 문항이 있습니다.", Toast.LENGTH_LONG).show();

                } else {
                    try {
                        getCheckBoxStatus();
                        Integer.parseInt(priceTxt.getText().toString());

                        ///////////////////////////// DatePicker 안만지면 db에 date값 안들어가서 펫시터가 필터링하면 튕기는 오류 수정
                        mRootRef.child("sitting_detail_info").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.child("date").exists()){
                                    dataSnapshot.child("date").getRef().setValue("1/1");
                                }
                                else if(dataSnapshot.child("date").getValue().toString().equals("")){
                                    dataSnapshot.child("date").getRef().setValue("1/1");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        /////////////////////////////

                        postFirebaseDB(id, dogNameTxt.getText().toString(), dogBreedTxt.getText().toString(), dogAgeTxt.getText().toString(),
                                userNameTxt.getText().toString(), gender, userAgeTxt.getText().toString(), loc,
                                date,priceTxt.getText().toString(), Byte.toString(isChecked));



                        Intent intent = new Intent(SettingDetailInfoActivity.this, UserApplicationActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        finish();
                    } catch (NumberFormatException e) {
                        Toast.makeText(SettingDetailInfoActivity.this, "금액 항목에는 숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public void postFirebaseDB(String mId, String dogName, String dogBreed, String dogAge, String userName
            , String userGender, String userAge, String location, String date, String price, String serviceType){

        mPostReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValue = null;

        SittingDetailInfo post;

        post = new SittingDetailInfo(mId, dogName, dogBreed, dogAge, userName, userAge,
                userGender, location, date, price, serviceType);

        postValue = post.toMap();
        childUpdates.put("/sitting_detail_info/" + mId , postValue);
        mPostReference.updateChildren(childUpdates);
    }

    private void setMD(int m, int d){
        month = m;
        day = d;
    }

    private void getCheckBoxStatus(){
        if (checkBox1.isChecked()) {
            isChecked |= (byte) 1;
        } else {
            isChecked &= (byte) 14;
        }

        if (checkBox2.isChecked()) {
            isChecked |= (byte) 2;
        } else {
            isChecked &= (byte) 13;
        }

        if (checkBox3.isChecked()) {
            isChecked |= (byte) 4;
        } else {
            isChecked &= (byte) 11;
        }

        if (checkBox4.isChecked()) {
            isChecked |= (byte) 8;
        } else {
            isChecked &= (byte) 7;
        }
    }

}