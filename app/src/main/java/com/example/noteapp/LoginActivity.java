package com.example.noteapp;




import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityLoginBinding;
import com.example.noteapp.model.Token;
import com.example.noteapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private String deviceToken;




    @Override
    protected ActivityLoginBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);

    }

    @Override
    public boolean backButtonClickActivated() {
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String password = binding.passcodeEditText.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(password);
                    Log.d("User", new Gson().toJson(user));
                    Call<User> call = mainApi.login(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            preferenceManager.setValue("isLogin",true);
                            preferenceManager.setValue("access_token", user.getAccess());
                            preferenceManager.setValue("email", user.getEmail());
                            preferenceManager.setValue("phoneNumber", user.getPhoneNumber());
                            preferenceManager.setValue("address",user.getAddress());
//                            saveToFile(user.getAccessToken(), user.getRefreshToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            //updateDeviceToken(token.getAccess());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });




                }
            }
        });
    }


    private void updateDeviceToken(Integer userId, String accessToken) {
        User user = new User();
        user.setDeviceToken(deviceToken);
        Call<User> call = mainApi.updateUser(userId,"Bearer " + accessToken, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void saveToFile(String access_token, String refresh) {
        String filename = "my_access_token.txt";
        FileOutputStream outputStream;
        try {

            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(access_token.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(refresh.getBytes());
            outputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

