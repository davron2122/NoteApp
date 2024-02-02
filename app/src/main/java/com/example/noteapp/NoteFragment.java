package com.example.noteapp;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.noteapp.adapter.NoteListAdapter;
import com.example.noteapp.base.BaseFragment;
import com.example.noteapp.databinding.FragmentNotesBinding;
import com.example.noteapp.model.Note;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoteFragment extends BaseFragment<FragmentNotesBinding> {


    private ArrayList<Note> noteArrayList = new ArrayList<>();
    private NoteListAdapter adapter;

    @Override
    protected FragmentNotesBinding inflateViewBinding(LayoutInflater layoutInflater, ViewGroup container, boolean toAttachParent) {
        return FragmentNotesBinding.inflate(layoutInflater, container, toAttachParent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new NoteListAdapter(noteArrayList);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.notesRecyclerView.setLayoutManager(gridLayoutManager);
        binding.notesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        noteArrayList.clear();
        //noteArrayList.addAll(baseActivity.databaseHelper.getNotes());
        String access_token = (String) baseActivity.preferenceManager.getValue(String.class, "access_token", "");
        String bearer_token = "Bearer " + access_token;
        Call<ArrayList<Note>> call = baseActivity.mainApi.getNotes(bearer_token);

        call.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                Log.d("Note", response.raw().toString());
                ArrayList<Note> notes = response.body();
                noteArrayList.addAll(notes);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });




    }

}







