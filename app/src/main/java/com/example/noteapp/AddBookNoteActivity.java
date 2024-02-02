package com.example.noteapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noteapp.base.BaseActivity;
import com.example.noteapp.databinding.ActivityAddBookNoteBinding;
import com.example.noteapp.model.Book;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookNoteActivity extends BaseActivity<ActivityAddBookNoteBinding> {

    private Book book;
    private File selectImageFile;

    @Override
    protected ActivityAddBookNoteBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityAddBookNoteBinding.inflate(inflater);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_book_note_activity);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);

        }

        if (getIntent().hasExtra("book")) {
            book = (Book) getIntent().getSerializableExtra("book");
            binding.createBtn.setText("Update");
            setTitle("Edit Book ");
            binding.title.setText(book.getTitle());
            binding.content.setText(book.getDescription());

            RequestOptions options = new RequestOptions();
            options.centerCrop();
            Glide.with(binding.getRoot()).load(book.getImage()).apply(options).placeholder(R.drawable.ic_glide_image).into(binding.loadImageBtn);

        }




        binding.loadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "SELECT PHOTOS"), 1000);

            }
        });

        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.title.getText().toString();
                String content = binding.content.getText().toString();
                if (book == null) {


                    book = new Book();
                    book.setTitle(title);
                    book.setDescription(content);

                    //databaseHelper.addNote(note);


                    if (selectImageFile != null) {

                        RequestBody titleRB = RequestBody.create(MultipartBody.FORM, title);
                        RequestBody descriptionRB = RequestBody.create(MultipartBody.FORM, content);
                        String mediaType = "jpg";
                        RequestBody imageRB = RequestBody.create(MediaType.parse(mediaType), selectImageFile);

                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", selectImageFile.getName(), imageRB);


                        String access_token = (String) preferenceManager.getValue(String.class, "access_token", "");
                        String bearer_token = "Bearer " + access_token;

                        Call<Book> call = mainApi.createBook(bearer_token, titleRB, descriptionRB, imagePart);
                        call.enqueue(new Callback<Book>() {
                            @Override
                            public void onResponse(Call<Book> call, Response<Book> response) {
                                Log.d("Book: ", response.body().toString() + " is created");
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Book> call, Throwable t) {
                                finish();
                            }
                        });
                    }

                } else {
                    book.setTitle(title);
                    book.setDescription(content);
                    databaseHelper.updateBook(book);
                    finish();
                }

            }
        });


    }


    public String getPathFromUri(Uri contentUri) {
        String path = null;

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }

        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImageUri = data.getData();
                binding.loadImageBtn.setImageURI(selectedImageUri);
                String path = getPathFromUri(selectedImageUri);
                selectImageFile = new File(path);

                Log.d("Path", path);
            } catch (Exception e) {

            }
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

