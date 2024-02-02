package com.example.noteapp.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private BaseAdapterListener baseAdapterListener;
    public  void setBaseAdapterListener(BaseAdapterListener baseAdapterListener){
        this.baseAdapterListener=baseAdapterListener;
    }

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public   abstract  void onBind(int position);


}


