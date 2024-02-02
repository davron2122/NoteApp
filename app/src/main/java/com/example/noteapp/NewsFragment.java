package com.example.noteapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.noteapp.adapter.NewsListAdapter;
import com.example.noteapp.base.BaseFragment;
import com.example.noteapp.databinding.FragmentNewsBinding;

import com.example.noteapp.model.News;
import com.example.noteapp.model.Note;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends BaseFragment<FragmentNewsBinding> {

    private NewsListAdapter adapter;
    private ArrayList<News> newsArrayList = new ArrayList<>();
    @Override
    protected FragmentNewsBinding inflateViewBinding(LayoutInflater layoutInflater, ViewGroup container, boolean toAttachParent) {
        return FragmentNewsBinding.inflate(layoutInflater,container,toAttachParent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsListAdapter(newsArrayList);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.newsRecyclerView.setLayoutManager(layoutManager);
        binding.newsRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        newsArrayList.clear();
        //newsArrayList.addAll(baseActivity.databaseHelper.getNews());
        String access_token = (String) baseActivity.preferenceManager.getValue(String.class, "access_token", "");
        String bearer_token = "Bearer " + access_token;
        Call<ArrayList<News>> call = baseActivity.mainApi.getNews(bearer_token);

        call.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, Response<ArrayList<News>> response) {
                Log.d("News", response.raw().toString());
                ArrayList<News> news = response.body();
                newsArrayList.addAll(news);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
    }



}