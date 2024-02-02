package com.example.noteapp.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noteapp.NewsDetailsActivity;
import com.example.noteapp.R;
import com.example.noteapp.base.BaseAdapter;
import com.example.noteapp.base.BaseViewHolder;
import com.example.noteapp.databinding.ItemNewsBinding;
import com.example.noteapp.model.News;

import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {

    private ArrayList<News> newsArrayList;

    private static int GLIDE_VIEW_TYPE = 0;
    private static int PICASSO_VIEW_TYPE = 1;

    public NewsListAdapter(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsViewHolder(binding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), NewsDetailsActivity.class);
                //intent.putExtra("news_id", newsArrayList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("news", newsArrayList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    class NewsViewHolder extends BaseViewHolder {
        private ItemNewsBinding binding;

        private int viewType;

        public NewsViewHolder(ItemNewsBinding binding, int viewType) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewType = viewType;
        }

        @Override
        public void onBind(int position) {
            News news = newsArrayList.get(position);
            binding.newsTitle.setText(news.getTitle());
            binding.newsSource.setText(news.getSource());
            binding.newsPublishedAt.setText(news.getPublishedAt());
             if (viewType == GLIDE_VIEW_TYPE) {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                Glide.with(binding.getRoot()).load(news.getImageUrl()).apply(options).placeholder(R.drawable.ic_glide_image).into(binding.newsImageView);

            }
        }
    }
}
