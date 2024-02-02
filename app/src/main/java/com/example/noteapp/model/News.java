package com.example.noteapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("source")
    private String source;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("published_at")
    private String publishedAt;


    public News(Integer id, String title, String source, String author, String content, String imageUrl, String publishedAt) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.author = author;
        this.content = content;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

//   public News() {
//    }

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
