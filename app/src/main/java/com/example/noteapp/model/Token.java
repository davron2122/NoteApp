package com.example.noteapp.model;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access")
    private String access;

    @SerializedName("refresh")
    private String refresh;

    public Token(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }

    public Token() {
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
