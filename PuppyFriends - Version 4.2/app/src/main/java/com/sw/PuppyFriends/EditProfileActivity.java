package com.sw.PuppyFriends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    Button btnsave;
    String id;
    String gender1,loc1,age1;

    RadioGroup radioGroup, radioGroup2;

    private FirebaseAuth firebaseAuth;
    private TextView textViewemailname;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextAddress,editTextAge;
    private EditText editTextPhoneNo;
    private ImageView profileImageView;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    public EditProfileActivity() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth= FirebaseAuth.getInstance();
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText)findViewById(R.id.EditTextName);
        editTextAge = (EditText)findViewById(R.id.EditTextOlder);
        editTextAddress = (EditText)findViewById(R.id.EditTextAddress);
        editTextPhoneNo = (EditText)findViewById(R.id.EditTextPhoneNo);
        btnsave=(Button)findViewById(R.id.btnSaveButton);


        radioGroup = findViewById(R.id.radioGroup);//성별 라디오 그룹
        radioGroup2 = findViewById(R.id.radioGroup2);//지역 라디오 그룹

        FirebaseUser user=firebaseAuth.getCurrentUser();
        btnsave.setOnClickListener(this);
        textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
        textViewemailname.setText(user.getEmail());
        profileImageView = findViewById(R.id.update_imageView);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        //성별 추가하는 부분
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonMale:
                        gender1 = "남자";
                        break;
                    case R.id.radioButtonFemale:
                        gender1 = "여자";
                        break;
                }
            }
        });

        //지역 라디오 부분
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton1:
                        loc1 = "대덕구";
                        break;
                    case R.id.radioButton2:
                        loc1 = "유성구";
                        break;
                    case R.id.radioButton3:
                        loc1 = "동구";
                        break;
                    case R.id.radioButton4:
                        loc1 = "서구";
                        break;
                    case R.id.radioButton5:
                        loc1 = "중구";
                        break;
                }
            }
        });

        //프로필 이미지 넣는 부분
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
            }
        });
    }
    private void userInformation(){
        String username = id.substring(0, id.lastIndexOf("@"));
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneno = editTextPhoneNo.getText().toString().trim();
        String gender = gender1;
        String loc=loc1;
        String age= editTextAge.getText().toString();
        Userinformation userinformation = new Userinformation(name,address,phoneno,gender,loc,age);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("users").child(username).child("Info").setValue(userinformation);
        databaseReference.child(user.getUid()).setValue(userinformation);
        Toast.makeText(getApplicationContext(),"가입 성공", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        if (view==btnsave){
            if (imagePath == null) {

                Drawable drawable = this.getResources().getDrawable(R.drawable.defavatar);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.defavatar);
                // openSelectProfilePictureDialog();
                userInformation();
                // sendUserData();
                finish();
                startActivity(new Intent(EditProfileActivity.this, Login.class));
            }
            else {
                userInformation();
                sendUserData();
                finish();
                startActivity(new Intent(EditProfileActivity.this, Login.class));
            }
        }
    }

    private void sendUserData() {
        String username = id.substring(0, id.lastIndexOf("@"));
        StorageReference imageReference = storageReference.child("ProfilePic").child(username).child("Profile Pic");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "프로필 사진 등록 실패", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfileActivity.this, "프로필 사진 등록 성공", Toast.LENGTH_SHORT).show();
            }
        });
    }

}