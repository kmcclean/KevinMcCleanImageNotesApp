package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;


public class SavedNoteDisplayFragment extends Fragment{

    GridView gv;
    private final String NOTE_LIST = "note_list";
    private final String NOTE_TEXT = "the_note's_text";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_note_fragment, container, false);

        Bundle b = this.getArguments();
        ArrayList<String> notes = b.getStringArrayList(NOTE_LIST);
        SavedNotesAdapter sna = new SavedNotesAdapter(getActivity(), notes);

        gv = (GridView)v.findViewById(R.id.gridView);
        gv.setAdapter(sna);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SavedSingleNoteActivity.class);
                Bundle b = new Bundle();
                i.putExtra(NOTE_TEXT, (String)parent.getItemAtPosition(position));
                startActivity(i);

            }
        });

        return v;
    }
}
