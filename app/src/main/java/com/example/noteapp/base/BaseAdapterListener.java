package com.example.noteapp.base;

public interface BaseAdapterListener {

    void onItemClick(int position);

    void onItemRemoveClick (int position);

    void onItemLongClick (int position);

}