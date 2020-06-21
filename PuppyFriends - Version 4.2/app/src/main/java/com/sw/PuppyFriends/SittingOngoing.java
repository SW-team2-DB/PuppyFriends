package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SittingOngoing extends AppCompatActivity {

    Button btn_calling, btn_endcaring;
    TextView textView_title, timetv;

    private Timer mTimer;
    private int count;
    private int limit;
    private boolean isRunning;
    private IMyAidlInterface binder;

    String matching_id, usertype, owner_id, sitter_id;
    private DatabaseReference conditionRef = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongoing_petsitting);

        Intent intent = getIntent();
        matching_id = intent.getExtras().getString("matching_id");
        usertype = intent.getExtras().getString("usertype");
        owner_id = intent.getExtras().getString("owner_id");
        sitter_id = intent.getExtras().getString("sitter_id");

        Log.d("output : ", owner_id + "   "  + sitter_id + "   " +  matching_id);
        final String[] splitedOwnerId = owner_id.split("@");
        final String[] splitedSitterId = sitter_id.split("@");

        btn_calling = (Button) findViewById(R.id.btn_calling);
        btn_calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Users.class);

                if(usertype.equals("sitter"))
                    intent.putExtra("target_id", splitedOwnerId[0]);
                else
                    intent.putExtra("target_id", splitedSitterId[0]);

                startActivity(intent);
            }
        });
        btn_endcaring = (Button) findViewById(R.id.btn_endcaring);
        btn_endcaring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unbindService(connection);
                isRunning = false;

                if(usertype.equals("sitter")){
                    Intent matchintent = new Intent(SittingOngoing.this, matchreport.class);
                    matchintent.putExtra("matching_id", matching_id);
                    matchintent.putExtra("usertype", "sitter");
                    matchintent.putExtra("owner_id", owner_id);
                    matchintent.putExtra("sitter_id", sitter_id);
                    startActivity(matchintent);
                    finish();
                } else if(usertype.equals("owner")){
                    Intent ratingintent = new Intent(SittingOngoing.this, Rating.class);
                    ratingintent.putExtra("your_id", sitter_id);
                    ratingintent.putExtra("usertype", "owner");
                    ratingintent.putExtra("matching_id", matching_id);
                    ratingintent.putExtra("my_id", owner_id);
                    startActivity(ratingintent);
                    finish();
                }

            }
        });

        timetv = (TextView)findViewById(R.id.timetv);
        textView_title = (TextView) findViewById(R.id.textView_title);
        settingTimer();

        Intent timerIntent = new Intent(SittingOngoing.this, TimerService.class);
        bindService(timerIntent, connection, BIND_AUTO_CREATE);
        isRunning = true;
        new Thread(new GetCountThread()).start();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void settingTimer(){
        conditionRef.child("matching").child(matching_id).child("사전만남").child("돌봄시간").addListenerForSingleValueEvent(new ValueEventListener() {
            String time;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                time = dataSnapshot.getValue().toString();
                limit = Integer.parseInt(time);
                Log.d("sitting time", time);
                TimerService.limit = Integer.parseInt(time);
                Log.d("Service Time : ", String.valueOf(TimerService.limit));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void postStatus(){
        conditionRef.child("matching").child(matching_id).child("사전만남").child("완료여부").setValue("true");
    }

    private class GetCountThread implements Runnable {

        private Handler handler = new Handler();

        @Override
        public void run() {
            while(isRunning){
                if(binder == null) {
                    continue;
                }

                handler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        try {
                            int remain = limit - binder.getCount()/60;

                            if(remain == 0){
                                textView_title.setText("돌봄이 종료되었습니다.");

                                unbindService(connection);
                                isRunning = false;

                                postStatus();

                                if(usertype.equals("sitter")){
                                    Intent matchintent = new Intent(SittingOngoing.this, matchreport.class);
                                    matchintent.putExtra("matching_id", matching_id);
                                    matchintent.putExtra("usertype", "sitter");
                                    matchintent.putExtra("owner_id", owner_id);
                                    matchintent.putExtra("sitter_id", sitter_id);
                                    startActivity(matchintent);
                                    finish();

                                } else if(usertype.equals("owner")){
                                    Intent ratingintent = new Intent(SittingOngoing.this, Rating.class);
                                    ratingintent.putExtra("your_id", sitter_id);
                                    ratingintent.putExtra("usertype", "owner");
                                    ratingintent.putExtra("matching_id", matching_id);
                                    ratingintent.putExtra("my_id", owner_id);
                                    startActivity(ratingintent);
                                    finish();
                                }
                            }

                            timetv.setText(remain + "분");

                        } catch (RemoteException e){
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}