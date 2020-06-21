package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    String id;

    RadioButton editPradioButton, editPradioButton2;
    EditText editp1;
    Button editpbtn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Intent intent = getIntent();

        //id 받아오기
        id = intent.getExtras().getString("id");
        Toast.makeText(getApplicationContext(), "id : " + id, Toast.LENGTH_SHORT).show();

        editPradioButton = (RadioButton)findViewById(R.id.editPradioButton);
        editPradioButton2 = (RadioButton)findViewById(R.id.editPradioButton2);
        editp1 = (EditText)findViewById(R.id.editp1);
        editpbtn = (Button)findViewById(R.id.editpbtn);

        databaseReference.child("test").child("profile").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //프로필이 있을 경우
                if(dataSnapshot.exists()) { //기존 프로필 가져옴
                    if(dataSnapshot.child("usertype").getValue().toString().equals("견주")) editPradioButton.setChecked(true);
                    else if(dataSnapshot.child("usertype").getValue().toString().equals("펫시터")) editPradioButton2.setChecked(true);
                    editp1.setText(dataSnapshot.child("프로필1").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usertype = "";
                if(editPradioButton.isChecked()) usertype = "견주";
                else if(editPradioButton2.isChecked()) usertype = "펫시터";
                else {
                    Toast.makeText(getApplicationContext(), "사용자 유형을 선택해주세요", Toast.LENGTH_LONG).show();
                }

                //usertype을 선택했을 때만 데이터베이스에 올림
                if(!usertype.isEmpty()){


                    //데이터베이스 테스트
                    databaseReference.child("test").child("profile").child(id).child("usertype").setValue(usertype);
                    databaseReference.child("test").child("profile").child(id).child("프로필1").setValue(editp1.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("usertype", usertype);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }

}
