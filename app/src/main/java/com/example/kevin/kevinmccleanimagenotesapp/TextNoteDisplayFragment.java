package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

//This class is to show what the
public class TextNoteDisplayFragment extends Fragment {

    EditText mTextNote;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mTextNote = (EditText)v.findViewById(R.id.note_text_et);
        return v;
    }
}
