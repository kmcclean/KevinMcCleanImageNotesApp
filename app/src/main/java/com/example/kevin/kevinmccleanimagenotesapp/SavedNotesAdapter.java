package com.example.kevin.kevinmccleanimagenotesapp;

import android.content.Context;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/25/2015.
 */
public class SavedNotesAdapter extends ArrayAdapter {


    public SavedNotesAdapter(Context context, ArrayList<String> notes){
        super (context, R.layout.saved_notes_grid_fragment_search_fragment, notes);
    }
}
