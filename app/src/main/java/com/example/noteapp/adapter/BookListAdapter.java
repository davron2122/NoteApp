package com.example.noteapp.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noteapp.BookNoteDetailsActivity;
import com.example.noteapp.R;
import com.example.noteapp.base.BaseAdapter;
import com.example.noteapp.base.BaseViewHolder;
import com.example.noteapp.databinding.ItemBookBinding;
import com.example.noteapp.model.Book;

import java.util.ArrayList;

public class BookListAdapter extends BaseAdapter {
    private ArrayList<Book> bookArrayList;
    private static int GLIDE_VIEW_TYPE = 0;

    public BookListAdapter(ArrayList<Book> bookArrayList) {
        this.bookArrayList = bookArrayList;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookViewHolder(binding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = bookArrayList.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), BookNoteDetailsActivity.class);
                //intent.putExtra("id", noteArrayList.get(holder.getAdapterPosition()).getId());


                //intent.putExtra("title", note.getTitle());
                //intent.putExtra("content", note.getContent());

                intent.putExtra("book", book);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }


    class BookViewHolder extends BaseViewHolder {
        private ItemBookBinding binding;

        private int viewType;

        public BookViewHolder(ItemBookBinding binding, int viewType) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewType = viewType;
        }

        @Override
        public void onBind(int position) {
            Book book = bookArrayList.get(position);
            binding.title.setText(book.getTitle());
            binding.content.setText(book.getDescription());
            if (viewType == GLIDE_VIEW_TYPE) {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                Glide.with(binding.getRoot()).load(book.getImageUrl()).apply(options).placeholder(R.drawable.ic_glide_image).into(binding.bookImageView);


            }
        }
    }
}



