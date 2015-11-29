package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SavedSingleNoteActivity extends AppCompatActivity implements SavedSingleNoteOptionsFragment.OnSingleNoteOptionsChoiceListener{

    FragmentManager fm;
    FragmentTransaction ft;
    DatabaseManager mDBM;

    private final String NOTE_TEXT = "the_note's_text";
    private final String NOTE_ID = "this is the note id.";
    private final String HASH_TAGS = "this is the hash tags.";

    private final String TEXT_NOTE_FRAME_TAG = "text_note_frame";
    private final String OPTIONS_FRAME_TAG = "options_frame";
    private final int SAVE = 0;
    private final int DELETE = 1;
    private final int CANCEL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_note);
        fm = getFragmentManager();
        ft = fm.beginTransaction();

        SingleTextNoteDisplayFragment stndf = new SingleTextNoteDisplayFragment();
        Bundle stndfBundle = new Bundle();
        stndfBundle.putString(NOTE_TEXT, getIntent().getStringExtra(NOTE_TEXT));
        stndf.setArguments(stndfBundle);

        SavedSingleNoteOptionsFragment ssnof = new SavedSingleNoteOptionsFragment();
        ssnof.setSSNOFListener(this);
        Bundle ssnofBundle = new Bundle();
        ssnofBundle.putString(HASH_TAGS, getIntent().getStringExtra(HASH_TAGS));
        ssnof.setArguments(ssnofBundle);

        ft.add(R.id.note_frame, stndf, TEXT_NOTE_FRAME_TAG);
        ft.add(R.id.options_frame, ssnof, OPTIONS_FRAME_TAG);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onSingleNoteOptionsChoiceListener(Integer event, String rowID) {
        if (event == SAVE){
            mDBM.updateRow(rowID);
            finish();
        }
        else if (event == DELETE){
            mDBM.deleteRow(rowID);
            finish();
        }
        else if(event == CANCEL){
            finish();
        }
    }
}
