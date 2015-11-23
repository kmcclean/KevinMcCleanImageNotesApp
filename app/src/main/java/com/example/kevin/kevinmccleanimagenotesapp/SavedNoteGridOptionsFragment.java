package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Kevin on 11/22/2015.
 */
public class SavedNoteGridOptionsFragment extends Fragment {

    EditText mSearchField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_notes_grid_fragment_search_fragment, container, false);
        mSearchField = (EditText)v.findViewById(R.id.search_string_et);
        return v;
    }
}
