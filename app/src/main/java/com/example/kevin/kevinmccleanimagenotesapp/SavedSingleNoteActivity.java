package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

public class SavedSingleNoteActivity extends AppCompatActivity implements SavedSingleNoteOptionsFragment.OnSingleNoteOptionsChoiceListener{

    FragmentManager fm;
    FragmentTransaction ft;
    DatabaseManager mDBM;

    private final String TEXT_NOTE_FRAME_TAG = "text_note_frame";
    private final String OPTIONS_FRAME_TAG = "options_frame";
    private final int SAVE = 0;
    private final int DELETE = 1;
    private final int CANCEL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_saved_note);

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        TextNoteDisplayFragment tndf = new TextNoteDisplayFragment();
        SavedSingleNoteOptionsFragment ssnof = new SavedSingleNoteOptionsFragment();
        ft.replace(R.id.note_frame, tndf, TEXT_NOTE_FRAME_TAG);
        ft.replace(R.id.options_frame, ssnof, OPTIONS_FRAME_TAG);
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
