package com.example.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CheckProfileActivity  extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView checkprofileNameTextView, checkprofileAddressTextView, checkprofilePhonenoTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView checkprofilePicImageView;
    private FirebaseStorage firebaseStorage;
    private TextView textViewemailname;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        checkprofilePicImageView = findViewById(R.id.profile_checkpic_imageView);
        checkprofileNameTextView = findViewById(R.id.profile_checkname_textView);
        checkprofileAddressTextView = findViewById(R.id.profile_checkaddress_textView);
        checkprofilePhonenoTextView = findViewById(R.id.profile_checkphoneno_textView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();
        // Firebase 이미지 경로 - "User id/Images/Profile Pic.jpg".
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(checkprofilePicImageView);
            }

        });
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                Userinformation userProfile = dataSnapshot.getValue(Userinformation.class);
                checkprofileNameTextView.setText(userProfile.getUserName());
                checkprofileAddressTextView.setText(userProfile.getUserAddress());
                checkprofilePhonenoTextView.setText(userProfile.getUserPhoneno());
                textViewemailname=(TextView)findViewById(R.id.textViewcheckEmailAdress);
                textViewemailname.setText(user.getEmail());
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(CheckProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}