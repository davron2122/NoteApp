package com.example.noteapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.noteapp.NoteDetailsActivity;
import com.example.noteapp.base.BaseAdapter;
import com.example.noteapp.base.BaseViewHolder;
import com.example.noteapp.databinding.ItemNoteBinding;
import com.example.noteapp.model.Note;

import java.util.ArrayList;

public class NoteListAdapter extends BaseAdapter {

    private ArrayList<Note> noteArrayList;

    public NoteListAdapter(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding itemNoteBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NoteViewHolder(itemNoteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = noteArrayList.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), NoteDetailsActivity.class);
                //intent.putExtra("id", noteArrayList.get(holder.getAdapterPosition()).getId());


                //intent.putExtra("title", note.getTitle());
                //intent.putExtra("content", note.getContent());

                intent.putExtra("note", note);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }


    class NoteViewHolder extends BaseViewHolder {
        ItemNoteBinding binding;

        public NoteViewHolder(@NonNull ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            Note note = noteArrayList.get(position);
            binding.title.setText(note.getTitle());
            binding.content.setText(note.getContent());

        }
    }
}






