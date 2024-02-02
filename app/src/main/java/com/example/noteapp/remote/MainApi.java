package com.example.noteapp.remote;

import com.example.noteapp.model.Book;
import com.example.noteapp.model.News;
import com.example.noteapp.model.Note;
import com.example.noteapp.model.Token;
import com.example.noteapp.model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MainApi {

    //Base URL=http://api.note-app.beknumonov.com


    // User   endpoint: v1/user/

    @POST("v1/user/")
    Call<User> createUser(@Body User user);

    @PUT("v1/user/{id}")
    Call<User> updateUser(@Path("id") Integer userId, @Header("Authorization") String accessToken, @Body User user);


    @GET("v1/user/")
    Call<ArrayList<User>> getUserList(@Header("Authorization") String accessToken);


    @POST("v1/auth-token/")
    Call<User> login(@Body User user);

    // endpoint: v1/notes/
    @GET("v1/note/")
    Call<ArrayList<Note>> getNotes(@Header("Authorization") String accessToken);

    @POST("v1/note/")
    Call<Note> createNote(@Header("Authorization") String accessToken, @Body Note note);



    @Multipart
    @POST("v1/book/")
    Call<Book> createBook(@Header("Authorization") String accessToken,
                          @Part("title") RequestBody title,
                          @Part("description") RequestBody description,
                          @Part MultipartBody.Part image
    );
    @Multipart
    @POST("v1/book/")
    Call<Book> getBook(@Header("Authorization") String accessToken,
                          @Part("title") RequestBody title,
                          @Part("description") RequestBody description,
                          @Part MultipartBody.Part image
    );




    @GET("v1/news/")
    Call<ArrayList<News>> getNews(@Header("Authorization") String accessToken);

    @POST("v1/news/{id}/share/")
    Call<JsonObject> shareNews(@Path("id") Integer newsId, @Header("Authorization") String accessToken, @Body JsonObject body);

    @GET("v1/book/")
    Call<ArrayList<Book>> getBook(@Header("Authorization") String accessToken


);
}
