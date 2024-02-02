package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityMainBinding;
import com.example.noteapp.model.Note;
import com.google.android.material.navigation.NavigationBarView;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private NewsFragment newsFragment;
    private NoteFragment noteFragment;
    private UserFragment userFragment;
    private BookFragment bookFragment;
    private  int selectedTab=R.id.noteTab;





    @Override
    protected ActivityMainBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public boolean backButtonClickActivated() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.news);


        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                replaceFragment(item.getItemId());

                return true;


            }
        });

        replaceFragment(R.id.NewsTab);



        binding.addNotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(selectedTab==R.id.noteTab) {
                   Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                   startActivity(intent);
               } else if (selectedTab==R.id.bookTab){
                   Intent intent = new Intent(MainActivity.this, AddBookNoteActivity.class);
                   startActivity(intent);
               }

            }
        });



        String access_refresh_token = getAccessTokenFromFile();
        Log.d("MainActivity", "Token: " + access_refresh_token);

        //generateNotes();




    }

    public String getAccessTokenFromFile() {

        String filename = "my_access_token.txt";
        FileInputStream inputStream;

        StringBuilder builder = new StringBuilder();
        try {
            inputStream = openFileInput(filename);
            int c;

            while ((c = inputStream.read()) != -1) {
                builder.append((char) c);
            }

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    public void generateNotes() {


        for (int i = 0; i < 100; i++) {

            Note note = new Note("Title " + (i + 1), "CONTENT " + (i + 1) + ": A plain text editor that allows you to keep notes throughout the day, create a list, write or edit code without worrying about unwanted auto formatting.");

            Log.d("Note: ", note.toString());
            databaseHelper.addNote(note);

        }


    }



    private void replaceFragment(int tabId) {
        selectedTab=tabId;
        if (tabId == R.id.NewsTab) {
            binding.addNotebtn.setVisibility(View.GONE);
            if (newsFragment == null)
                newsFragment = new NewsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, newsFragment).commit();
            setTitle(R.string.news);
        } else if (tabId == R.id.noteTab) {
            binding.addNotebtn.setVisibility(View.VISIBLE);
            if (noteFragment == null)
                noteFragment = new NoteFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, noteFragment).commit();
            setTitle(R.string.notes);
        }else if (tabId == R.id.bookTab) {
            binding.addNotebtn.setVisibility(View.VISIBLE);
                 if (bookFragment == null)
                    bookFragment= new BookFragment();
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, bookFragment).commit();
                    setTitle(R.string.books);
        } else if (tabId==R.id.profileTab) {
            binding.addNotebtn.setVisibility(View.GONE);
            if (userFragment == null)
                userFragment = new UserFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, userFragment).commit();
            setTitle(R.string.profile);


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