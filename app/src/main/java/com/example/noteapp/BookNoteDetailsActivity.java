package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityBookNoteDetailsBinding;
import com.example.noteapp.model.Book;

import java.io.Serializable;


public class BookNoteDetailsActivity extends BaseActivity<ActivityBookNoteDetailsBinding> {
    private Book book;

    @Override
    protected ActivityBookNoteDetailsBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityBookNoteDetailsBinding.inflate(inflater);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.book_note_details_title);
       /*
        int id = getIntent().getIntExtra("id", 0);
        *if (id != 0) {
         *   Note note = dataBaseHelper.getNote(id);
          *  binding.title.setText(note.getTitle());
           * binding.content.setText(note.getContent());
        }
        */
        book = (Book) getIntent().getSerializableExtra("book");
        if (book != null) {
            binding.title.setText(book.getTitle());
            binding.content.setText(book.getDescription());
            Glide.with(this)
                    .load(book.getImageUrl())
                    .placeholder(R.drawable.ic_glide_image)
                    .into(binding.bookNoteDetailsImage);
        }


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (bookNote!=null){
//            bookNote=dataBaseHelper.getBookNote(bookNote.getId());
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_note_details_menu,  menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (book != null) {
            binding.title.setText(book.getTitle());
            binding.content.setText(book.getDescription());

            RequestOptions options = new RequestOptions();
            options.centerCrop();

            Glide.with(binding.getRoot()).load(book.getImage()).apply(options).placeholder(R.drawable.ic_glide_image).into(binding.bookNoteDetailsImage);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editNote) {

            Intent intent = new Intent(BookNoteDetailsActivity.this, AddBookNoteActivity.class);

            intent.putExtra("book", (book));

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




