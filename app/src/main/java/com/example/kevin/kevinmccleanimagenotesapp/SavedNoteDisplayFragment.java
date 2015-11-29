package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;


public class SavedNoteDisplayFragment extends Fragment{

    GridView gv;
    private final String NOTE_TEXT = "the_note's_text";
    private final String NOTE_ID = "this is the note id.";
    private final String HASH_TAGS = "this is the hash tags.";
    DatabaseManager mDBM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_note_fragment, container, false);
        mDBM = new DatabaseManager(getActivity());
        ArrayList<Notes> notesList = mDBM.fetchAll();
        SavedNotesAdapter sna = new SavedNotesAdapter(getActivity(), notesList);

        gv = (GridView)v.findViewById(R.id.gridView);
        gv.setAdapter(sna);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SavedSingleNoteActivity.class);
                Notes n = (Notes)parent.getItemAtPosition(position);
                i.putExtra(NOTE_TEXT, n.getNoteText());
                i.putExtra(NOTE_ID, n.getNoteID());
                i.putExtra(HASH_TAGS, n.getHashTag());
                getActivity().startActivity(i);

            }
        });

        return v;
    }
}
