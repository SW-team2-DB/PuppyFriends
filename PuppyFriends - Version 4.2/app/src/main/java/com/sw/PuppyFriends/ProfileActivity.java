package com.sw.PuppyFriends;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity  extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView profileNameTextView, profileAddressTextView, profilePhonenoTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView profilePicImageView;
    private FirebaseStorage firebaseStorage;
    private TextView textViewemailname;
    private EditText editTextName,editTextAge;

    TextView user_gender, user_loc,user_age;
    String loc1;
    //RadioGroup radioGroup3;



    String id;
    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        editTextName = (EditText)findViewById(R.id.et_username);
        profilePicImageView = findViewById(R.id.profile_pic_imageView);
        profileNameTextView = findViewById(R.id.profile_name_textView);
        profileAddressTextView = findViewById(R.id.profile_address_textView);
        profilePhonenoTextView = findViewById(R.id.profile_phoneno_textView);
        editTextAge = findViewById(R.id.et_userage);



        user_gender = findViewById(R.id.user_gender);
        user_loc= findViewById(R.id.user_loc);
        user_age=findViewById(R.id.user_age);




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        username = id.substring(0, id.lastIndexOf("@"));

        storageReference.child("ProfilePic").child(username).child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(profilePicImageView);
            }
        });
        //처음 profile 띄워주는 화면
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                Userinformation userProfile = dataSnapshot.getValue(Userinformation.class);
                profileNameTextView.setText(userProfile.getUserName());
                user_gender.setText(userProfile.getUserGender());
                user_loc.setText(userProfile.getUserLoc());
                user_age.setText(userProfile.getUserAge());
                profileAddressTextView.setText(userProfile.getUserAddress());
                profilePhonenoTextView.setText(userProfile.getUserPhoneno());
                textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
                textViewemailname.setText(user.getEmail());
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //이름편집
    public void buttonClickedEditName(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_name, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("이름 편집");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                String address = profileAddressTextView.getText().toString();
                String phoneno =  profilePhonenoTextView.getText().toString();
                String gender = user_gender.getText().toString();
                String loc = user_loc.getText().toString();
                String age = user_age.getText().toString();
                Userinformation userinformation = new Userinformation(name,address, phoneno, gender,loc,age);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child("users").child(username).child("Info").setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    //원하는 매칭 지역 편집
    public void buttonClickedEditLoc(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_matching_loc, null);
        final EditText etUserloc = alertLayout.findViewById(R.id.user_loc);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("원하는매칭지역");
        alert.setView(alertLayout);
        alert.setCancelable(false);




        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = profileNameTextView.getText().toString();
                String address = profileAddressTextView.getText().toString();
                String phoneno =  profilePhonenoTextView.getText().toString();
                String gender = user_gender.getText().toString();
                String loc = etUserloc.getText().toString();
                String age = user_age.getText().toString();
                Userinformation userinformation = new Userinformation(name,address, phoneno, gender,loc,age);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child("users").child(username).child("Info").setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUserloc.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    //매칭지역 수정할때 라디오 버튼 동작 메소드
   /* public void RadioButtonChecked(View v) {

        boolean checked= ((RadioButton) v).isChecked();


        switch (v.getId()) {
                    case R.id.radioButton1:
                        if(checked) user_loc.setText("대덕구");
                        break;
                    case R.id.radioButton2:
                        if(checked) user_loc.setText("유성구");
                        break;
                    case R.id.radioButton3:
                        if(checked) user_loc.setText("동구");
                        break;
                    case R.id.radioButton4:
                        if(checked) user_loc.setText("서구");
                        break;
                    case R.id.radioButton5:
                        if(checked) user_loc.setText("중구");
                        break;
                }
            }
*/

    //상세주소 편집
    public void buttonClickedEditAddress(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_address, null);
        final EditText etUserAddress = alertLayout.findViewById(R.id.et_userAddress);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("상세주소 편집");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = profileNameTextView.getText().toString();
                String address = etUserAddress.getText().toString();
                String phoneno =  profilePhonenoTextView.getText().toString();
                String gender = user_gender.getText().toString();
                String loc = user_loc.getText().toString();
                String age = user_age.getText().toString();
                Userinformation userinformation = new Userinformation(name,address, phoneno, gender,loc,age);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child("users").child(username).child("Info").setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUserAddress.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    //나이변경
    public void buttonClickedEditAge(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_age, null);
        final EditText etUserAge = alertLayout.findViewById(R.id.et_userage);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("나이 수정");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = profileNameTextView.getText().toString();
                String address = profileAddressTextView.getText().toString();
                String phoneno =  profilePhonenoTextView.getText().toString();
                String gender = user_gender.getText().toString();
                String loc = user_loc.getText().toString();
                String age = etUserAge.getText().toString();
                Userinformation userinformation = new Userinformation(name,address, phoneno,gender,loc,age);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child("users").child(username).child("Info").setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUserAge.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }



    //전화번호 변경
    public void buttonClickedEditPhoneNo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_phoneno, null);
        final EditText etUserPhoneno = alertLayout.findViewById(R.id.et_userPhoneno);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("연락처 편집");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = profileNameTextView.getText().toString();
                String address = profileAddressTextView.getText().toString();
                String phoneno =  etUserPhoneno.getText().toString();
                String gender = user_gender.getText().toString();
                String loc = user_loc.getText().toString();
                String age = user_age.getText().toString();
                Userinformation userinformation = new Userinformation(name,address, phoneno,gender,loc,age);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child("users").child(username).child("Info").setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUserPhoneno.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void navigateLogOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent inent = new Intent(this, Login.class);
        startActivity(inent);
    }
}