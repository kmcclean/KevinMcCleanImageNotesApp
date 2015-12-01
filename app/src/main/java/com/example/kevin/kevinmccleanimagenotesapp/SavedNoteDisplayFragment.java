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
    private final String IS_IMAGE = "this is or is not an image.";
    private final int CHANGES = 0;
    DatabaseManager mDBM;
    SavedNotesAdapter sna;
    ArrayList<Notes> notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_note_fragment, container, false);
        notes = getNotes();
        sna = new SavedNotesAdapter(getActivity(), notes);
        gv = (GridView)v.findViewById(R.id.gridView);
        gv.setAdapter(sna);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SavedSingleNoteActivity.class);
                Notes n = (Notes) parent.getItemAtPosition(position);
                Bundle b = new Bundle();
                b.putString(NOTE_ID, n.getNoteID());
                b.putString(NOTE_TEXT, n.getNoteText());
                b.putString(HASH_TAGS, n.getHashTag());
                b.putBoolean(IS_IMAGE, n.isPicture);
                i.putExtras(b);
                /*
                i.putExtra(NOTE_ID, n.getNoteID());
                i.putExtra(NOTE_TEXT, n.getNoteText());
                i.putExtra(HASH_TAGS, n.getHashTag());
                Boolean put = n.isPicture;
                i.putExtra(IS_IMAGE, put);*/
                getActivity().startActivityForResult(i, CHANGES);
            }
        });

        return v;
    }

    //in theory this will change the dataset as soon as the user returns to the mainActivity, but it's not as of yet.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        sna.notifyDataSetChanged();
    }

    //this gets the notes from the database and puts them in a note list. Accessed from a few different locations, hence being a method.
    public ArrayList<Notes>getNotes(){
        mDBM = new DatabaseManager(getActivity());
        ArrayList<Notes> notesList = mDBM.fetchAll();
        return notesList;
    }
    /*
    public void searchSavedNotes(){
        sna = new SavedNotesAdapter(getActivity(), note)
    }*/
}
