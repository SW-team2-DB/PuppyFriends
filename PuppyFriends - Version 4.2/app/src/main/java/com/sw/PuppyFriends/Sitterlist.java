package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Sitterlist extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference pathReferences = databaseReference.child("rated_sitter");
    Query pathReference = pathReferences.orderByChild("order");

    static int cnt = 0;

    String my_id;
    LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sitterlist);

        linearLayout = (LinearLayout)findViewById(R.id.sitterlistlayout);

        Intent intent = getIntent();
        my_id = intent.getExtras().getString("id");


        pathReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linearLayout.removeAllViews();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    String sitterkey = postSnapshot.getKey();
                    String point = postSnapshot.child("point").getValue().toString();
                    addLayout(sitterkey, point, my_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addLayout(String userIdTxt, String point, final String my_id){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.user_info_view);
        ((ViewGroup) layout.getParent()).removeView(layout);

        TextView textView = new TextView(this);
        textView.setText(userIdTxt + "(" + point + ")");
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
