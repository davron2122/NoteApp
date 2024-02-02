package com.example.noteapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityNewsDetailsBinding;
import com.example.noteapp.model.News;

public class NewsDetailsActivity extends BaseActivity<ActivityNewsDetailsBinding> {
    private News news;


    @Override
    protected ActivityNewsDetailsBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityNewsDetailsBinding.inflate(inflater);
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("News detail");
        news = (News) getIntent().getSerializableExtra("news");


        if (news != null) {
            binding.title.setText(news.getTitle());
            binding.content.setText(news.getContent());
            Glide.with(this)
                    .load(news.getImageUrl())
                    .placeholder(R.drawable.ic_glide_image)
                    .into(binding.newsImageView);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
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

