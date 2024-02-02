package com.example.noteapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {



    private boolean isLogin=false;


    @Override
    protected ActivitySplashBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivitySplashBinding.inflate(inflater);

    }

    @Override
    public boolean backButtonClickActivated() {
        return false;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.containsKey("note_id")) {
            Log.d("FCM", "note_id=" + bundle.getString("note_id"));
        }




        //SharedPreferences sharedPreferences = getSharedPreferences("NoteApp", Context.MODE_PRIVATE);
        isLogin=(Boolean) preferenceManager.getValue(Boolean.class, "isLogin",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        },3000);

    }





}



