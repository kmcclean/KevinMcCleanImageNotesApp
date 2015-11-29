package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SavedNotesAdapter extends ArrayAdapter {

    private Activity mActivity;
    private ArrayList<String> mTextNotesArrayList = new ArrayList<>();
    private ArrayList<Notes> mNotesArrayList = new ArrayList<>();
    public TextView mNoteListTextView;

    public SavedNotesAdapter(Activity c, ArrayList<Notes> notes){
        super (c, R.layout.note_list, notes);
        this.mActivity = c;
        for(Notes n : notes){
            this.mTextNotesArrayList.add(n.getNoteText());
        }
        this.mNotesArrayList = notes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.note_list, null, true);
        mNoteListTextView = (TextView)rowView.findViewById(R.id.note_list_text_view);
        Notes note = mNotesArrayList.get(position);
        mNoteListTextView.setText(note.getNoteText());
        return rowView;
    }
}
