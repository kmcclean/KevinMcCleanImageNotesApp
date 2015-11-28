package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


public class SingleTextNoteDisplayFragment extends Fragment {

    EditText mSavedNoteEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Bundle b = this.getArguments();
            String text = b.getString("text");
            mSavedNoteEditText.setText(text);
            Log.e("TextNoteDisplayFragment", "Note's chosen.");
        }
        catch(Exception e){
            Log.e("TextNoteDisplayFragment","Note's not chosen yet.");
        }
    }
}
