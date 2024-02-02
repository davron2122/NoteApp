package com.example.noteapp.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.noteapp.databinding.FragmentNewsBinding;

public abstract class BaseFragment <VS extends ViewBinding> extends Fragment {
    protected VS binding;

    protected BaseActivity baseActivity;
    protected abstract VS inflateViewBinding(LayoutInflater layoutInflater, ViewGroup container, boolean toAttachParent);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=inflateViewBinding(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity= (BaseActivity) getActivity();
    }


}