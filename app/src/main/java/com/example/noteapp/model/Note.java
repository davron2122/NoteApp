package com.example.noteapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Note implements Serializable {

    @SerializedName("id")
    private  Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("created_at")
    private  String createdAt;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(Integer id, String title, String content, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Note() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt(String createdAt) {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Note{" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
               '}';
    }

    public static class NoteEntry{
        public  static String TABLE_NAME = "NOTES";
        public static String ID="ID";
        public static String TITLE= "TITLE";
        public static String CONTENT="CONTENT";
        public  static String CREATED_AT = "CREATED_AT";



        /*String sql= "CREATE TABLE" + NoteEntry.TABLE_NAME + "("+
                ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE +"TEXT NOT NULL," +
                CONTENT + "TEXT NOT NULL," +
                CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+
                ");";

         */
        public static String createTableQuery() {
            String sql= "  CREATE TABLE " + NoteEntry.TABLE_NAME + "( "+
                    ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TITLE +" TEXT NOT NULL," +
                    CONTENT + " TEXT NOT NULL," +
                    CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                    ");";
            return sql;
        }
    }

    }





