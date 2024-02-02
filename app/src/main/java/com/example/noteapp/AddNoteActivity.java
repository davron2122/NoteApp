package com.example.noteapp;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityAddNewNoteBinding;
import com.example.noteapp.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNoteActivity extends BaseActivity<ActivityAddNewNoteBinding> {

    private Note note;

    @Override
    protected ActivityAddNewNoteBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityAddNewNoteBinding.inflate(inflater);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_new_note_activity);

        if (getIntent().hasExtra("note")) {
            note = (Note) getIntent().getSerializableExtra("note");
            binding.createBtn.setText("Update");
            setTitle("Edit Note");
            binding.title.setText(note.getTitle());
            binding.content.setText(note.getContent());
        }

        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.title.getText().toString();
                String content = binding.content.getText().toString();
                if (note == null) {


                    note = new Note();
                    note.setTitle(title);
                    note.setContent(content);
                    databaseHelper.addNote(note);


                    String access_token = (String) preferenceManager.getValue(String.class, "access_token", "");
                    String bearer_token = "Bearer " + access_token;

                    Call<Note> call = mainApi.createNote(bearer_token,  note);
                    call.enqueue(new Callback<Note>() {
                        @Override
                        public void onResponse(Call<Note> call, Response<Note> response) {
                            Log.d("Note", response.body().toString() + " is created");
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Note> call, Throwable t) {
                            finish();
                        }
                    });

                } else {
                    note.setTitle(title);
                    note.setContent(content);
                    databaseHelper.updateNote(note);
                    finish();
                }

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
