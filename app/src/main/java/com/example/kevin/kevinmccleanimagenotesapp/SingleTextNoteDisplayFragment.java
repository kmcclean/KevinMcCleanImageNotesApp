package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class SingleTextNoteDisplayFragment extends Fragment {

    EditText mSavedNoteEditText;
    private final String NOTE_TEXT = "the_note's_text";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text_note_display_fragment, container, false);
        mSavedNoteEditText = (EditText)v.findViewById(R.id.note_text_et);
        try{
            Bundle b = this.getArguments();
            String text = b.getString(NOTE_TEXT);
            mSavedNoteEditText.setText(text);
        }
        catch(Exception e){
            Log.e("TextNoteDisplayFragment","Note's not chosen yet.");
        }
        return v;
    }
}
