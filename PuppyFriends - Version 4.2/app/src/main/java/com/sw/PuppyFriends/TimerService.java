package com.sw.PuppyFriends;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class TimerService extends Service {
    private boolean isStop = false;
    private int cnt;
    public static int limit;

    public TimerService() {
    }

    public void setTimerLimit(int limit){
        TimerService.limit = limit;
        Log.d("get limit time : ", String.valueOf(TimerService.limit));
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Thread timer = new Thread(new Timer());
        timer.start();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        isStop = true;
    }

    IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub(){
        @Override
        public int getCount() throws RemoteException {
            return cnt;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class Timer implements Runnable{

        private Handler handler = new Handler();

        @Override
        public void run(){
            for(cnt = 0; cnt < limit*60; cnt++){

                if(isStop){
                    break;
                }

                handler.post(new Runnable() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void run() {
                        Log.d("time status : ", cnt + "분 지남");
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            handler.post(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "서비스 종료", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
