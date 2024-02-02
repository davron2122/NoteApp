package com.example.noteapp;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityNoteDetailsBinding;
import com.example.noteapp.model.Note;

public class NoteDetailsActivity extends BaseActivity<ActivityNoteDetailsBinding> {
    private Note note;

    @Override
    protected ActivityNoteDetailsBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityNoteDetailsBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.note_details_title);
        /*
        int id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            Note note = databaseHelper.getNote(id);
            binding.title.setText(note.getTitle());
            binding.content.setText(note.getContent());
        }
        */


//        String title = getIntent().getStringExtra("title");
//        String content = getIntent().getStringExtra("content");
//        binding.title.setText(title);
//        binding.content.setText(content);

        note = (Note) getIntent().getSerializableExtra("note");
        if (note != null) {
            binding.title.setText(note.getTitle());
            binding.content.setText(note.getContent());

        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (note != null) {
            note = databaseHelper.getNote(note.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.editNote) {

            Intent intent = new Intent(NoteDetailsActivity.this, AddNoteActivity.class);

            intent.putExtra("note", note);

            startActivity(intent);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
