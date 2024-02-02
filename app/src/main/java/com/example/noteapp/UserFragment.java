package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.noteapp.base.BaseFragment;
import com.example.noteapp.databinding.FragmentUserBinding;
import com.example.noteapp.model.User;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class UserFragment extends BaseFragment<FragmentUserBinding> {


    @Override
    protected FragmentUserBinding inflateViewBinding(LayoutInflater layoutInflater, ViewGroup container, boolean toAttachParent) {
        return FragmentUserBinding.inflate(layoutInflater, container, toAttachParent);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = (String) baseActivity.preferenceManager.getValue(String.class, "email", "");
        binding.title1.setText(email);
        String phoneNumber = (String) baseActivity.preferenceManager.getValue(String.class, "phoneNumber", "");
        binding.title2.setText(phoneNumber);
        String address = (String) baseActivity.preferenceManager.getValue(String.class, "address", "");
        binding.title3.setText(address);

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.preferenceManager.setValue("isLogin", false);
                String filename = "my_access_token.txt";

                Log.d("ProfileFragment", getContext().getFilesDir() + "/" + filename);

                File file = new File(getContext().getFilesDir(), filename);

                if (file.delete()) {
                    Log.d("ProfileFragment ", "Token file is deleted");
                }
                Intent intent = new Intent(getContext(), SplashActivity.class);
                startActivity(intent);
            }
            });

//
//    @Override
//    public void onResume() {
//        super.onResume();
////        usersArrayList.clear();
////        usersArrayList.addAll(baseActivity.databaseHelper.getUser());
//        String access_token = (String) baseActivity.preferenceManager.getValue(String.class, "access_token", "");
//        String bearer_token = "Bearer " + access_token;
//        Call<ArrayList<User>> call = baseActivity.mainApi.getUserList(bearer_token);
//
//        call.enqueue(new Callback<ArrayList<User>>() {
//            @Override
//            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
//                Log.d("User", response.raw().toString());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

    }
    }

