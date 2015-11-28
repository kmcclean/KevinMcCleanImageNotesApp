package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/25/2015.
 */
public class SavedNotesAdapter extends ArrayAdapter {

    private Activity mActivity;
    private ArrayList<String> mTextNotesArrayList = new ArrayList<>();

    public TextView mNoteListTextView;

    public SavedNotesAdapter(Activity c, ArrayList<String> notes){
        super (c, R.layout.note_list, notes);
        this.mActivity = c;
        this.mTextNotesArrayList = notes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.note_list, null, true);

        String[] mTextNotes = mTextNotesArrayList.toArray(new String[mTextNotesArrayList.size()]);
        mNoteListTextView = (TextView)rowView.findViewById(R.id.note_list_text_view);
        mNoteListTextView.setText(mTextNotes[position]);
        return rowView;
    }
}
