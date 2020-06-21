package com.example.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageActivity extends AppCompatActivity {

    ImageView iv;

    //업로드할 이미지 파일의 경로 Uri
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        iv=findViewById(R.id.iv);
    }

    public void clickLoad(View view) {

        //Firebase Storage에 저장되어 있는 이미지 파일 읽어오기

        //1. Firebase Storeage관리 객체 얻어오기
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

        //2. 최상위노드 참조 객체 얻어오기
        StorageReference rootRef= firebaseStorage.getReference();

        //읽어오길 원하는 파일의 참조객체 얻어오기
        //예제에서는 자식노드 이름은 monkey.png
        StorageReference imgRef= rootRef.child("monkey.png");
        //하위 폴더가 있다면 폴더명까지 포함하여
        imgRef= rootRef.child("photo/9.png");

        if(imgRef!=null){
            //참조객체로 부터 이미지의 다운로드 URL을 얻어오기
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //다운로드 URL이 파라미터로 전달되어 옴.
                    Glide.with(ImageActivity.this).load(uri).into(iv);
                }
            });

        }

    }
    public void clickSelect(View view) {
        //사진을 선탣할 수 있는 Gallery앱 실행
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //선택한 이미지의 경로 얻어오기
                    imgUri= data.getData();
                    Glide.with(this).load(imgUri).into(iv);
                }
                break;
        }
    }

    public void clickUpload(View view) {
        //firebase storage에 업로드하기

        //1. FirebaseStorage을 관리하는 객체 얻어오기
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

        //2. 업로드할 파일의 node를 참조하는 객체
        //파일 명이 중복되지 않도록 날짜를 이용
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String filename= sdf.format(new Date())+ ".png";//현재 시간으로 파일명 지정 20191023142634
        //원래 확장자는 파일의 실제 확장자를 얻어와서 사용해야함. 그러려면 이미지의 절대 주소를 구해야함.

        StorageReference imgRef= firebaseStorage.getReference("uploads/"+filename);
        //uploads라는 폴더가 없으면 자동 생성

        //참조 객체를 통해 이미지 파일 업로드
        // imgRef.putFile(imgUri);
        //업로드 결과를 받고 싶다면
        UploadTask uploadTask =imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ImageActivity.this, "success upload", Toast.LENGTH_SHORT).show();
            }
        });

        //업로드한 파일의 경로를 firebaseDB에 저장하면 게시판 같은 앱도 구현할 수 있음.

    }
}
