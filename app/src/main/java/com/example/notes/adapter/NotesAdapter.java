package com.example.notes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.notes.R;
import com.example.notes.model.Note;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {
    Context mCtx;
    int listLayoutRes;
    List<Note> noteList;

    public NotesAdapter(Context mCtx, int listLayoutRes, List<Note> noteList) {

        super(mCtx, listLayoutRes, noteList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        Note note = noteList.get(position);

        TextView textViewTitle = view.findViewById(R.id.tile);
        TextView textViewDesc = view.findViewById(R.id.description);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_layout);

        constraintLayout.setBackgroundColor(Color.parseColor(note.getColorCode()));
        textViewTitle.setText(note.getTitle());
        textViewDesc.setText(note.getDesc());



        return view;
    }
}
