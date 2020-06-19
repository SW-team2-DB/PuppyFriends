package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private EditText editProfileName;

    private EditText editProfileCountry;

    private EditText editProfilePhoneNumber;

    private EditText editProfileHobby;

    private EditText editProfileBirthday;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile Information");

        editProfileName = (EditText)findViewById(R.id.profile_name);
        editProfileCountry = (EditText)findViewById(R.id.profile_country);
        editProfilePhoneNumber = (EditText)findViewById(R.id.profile_phone);
        editProfileHobby = (EditText)findViewById(R.id.profile_hobby);
        editProfileBirthday = (EditText)findViewById(R.id.profile_birth);


    }
}
