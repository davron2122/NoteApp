package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityRegisterBinding;
import com.example.noteapp.model.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private String deviceToken;



    @Override
    protected ActivityRegisterBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityRegisterBinding.inflate(inflater);
    }

    @Override
    public boolean backButtonClickActivated() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registration");


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.d("FCM", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                deviceToken = task.getResult();
                Log.d("FCM", "Device token: " + deviceToken);
            }
        });


        binding.completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String passcode = binding.passcodeEditText.getText().toString();
                String fistName = binding.firstNameEditText.getText().toString();
                String lastName = binding.lastNameEditText.getText().toString();
                String phoneNumber = binding.phoneNumberEditText.getText().toString();
                String address = binding.addressEditText.getText().toString();
                if (!email.isEmpty() && !passcode.isEmpty()) {
                    // preferenceManager.setValue("username", email);
                    //preferenceManager.setValue("passcode", passcode);
                    //preferenceManager.setValue("isLogin", true);

                    User user = new User(email, passcode, fistName, lastName,phoneNumber,address, deviceToken);

                    Log.d("User", new Gson().toJson(user));

                    Call<User> call =mainApi.createUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User createdUser = response.body();
                            preferenceManager.setValue("isLogin", true);
                            preferenceManager.setValue("access_token",createdUser.getAccessToken());
                            Intent intent= new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });


                }
            }
        });
        binding.completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });







    }


    @Override
    public boolean hasActionBar() {
        return true;
    }


    @Override
    public boolean hasBackButton() {
        return true;
    }
}
