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

import com.example.noteapp.adapter.BookListAdapter;
import com.example.noteapp.base.BaseFragment;
import com.example.noteapp.databinding.FragmentBookBinding;

import com.example.noteapp.model.Book;
import com.example.noteapp.model.Note;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends BaseFragment<FragmentBookBinding> {
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private BookListAdapter adapter;


    @Override
    protected FragmentBookBinding inflateViewBinding(LayoutInflater layoutInflater, ViewGroup container, boolean toAttachParent) {
        return FragmentBookBinding.inflate(layoutInflater, container, toAttachParent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new BookListAdapter(bookArrayList);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.booksRecyclerView.setLayoutManager(gridLayoutManager);
        binding.booksRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        bookArrayList.clear();
        //noteArrayList.addAll(baseActivity.databaseHelper.getNotes());
        String access_token = (String) baseActivity.preferenceManager.getValue(String.class, "access_token", "");
        String bearer_token = "Bearer " + access_token;
        Call<ArrayList<Book>> call = baseActivity.mainApi.getBook(bearer_token);

        call.enqueue(new Callback<ArrayList<Book>>() {
            @Override
            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                Log.d("Book", response.raw().toString());
                ArrayList<Book> book = response.body();
                bookArrayList.addAll(book);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
adapter.notifyDataSetChanged();

    }
}
