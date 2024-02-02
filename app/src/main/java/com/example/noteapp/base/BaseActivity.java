package com.example.noteapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.example.noteapp.R;
import com.example.noteapp.remote.MainApi;
import com.example.noteapp.util.PreferenceManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseActivity <VS extends ViewBinding> extends AppCompatActivity {

    protected VS binding;
    private static String BASE_URL = "http://api.note-app.beknumonov.com";

    public PreferenceManager preferenceManager;
    public DatabaseHelper databaseHelper;



    public MainApi mainApi;


    protected abstract VS inflateViewBinding(LayoutInflater inflater);


    public boolean hasActionBar() {
        return false;
    }

    public boolean hasBackButton() {
        return false;
    }


    public boolean backButtonClickActivated() {
        return true;
    }

    public int getBackButtonIcon() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateViewBinding(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = PreferenceManager.getInstance(this);
        databaseHelper = new DatabaseHelper(this);

        if (hasActionBar()) {
            Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
            if (toolbar != null)
                setSupportActionBar(toolbar);

        }

        if (hasBackButton()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (getBackButtonIcon() != 0) {
                getSupportActionBar().setHomeAsUpIndicator(getDrawable(getBackButtonIcon()));
            }


        }


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient().newBuilder().
                addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mainApi = retrofit.create(MainApi.class);

    }

    public void setTitle(int titleId) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(titleId);
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (backButtonClickActivated()) {
                    onBackPressed();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}










