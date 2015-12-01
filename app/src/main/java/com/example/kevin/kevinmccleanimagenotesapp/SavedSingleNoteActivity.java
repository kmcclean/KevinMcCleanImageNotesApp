package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class SavedSingleNoteActivity extends AppCompatActivity implements SavedSingleNoteOptionsFragment.OnSingleNoteOptionsChoiceListener{

    FragmentManager fm;
    FragmentTransaction ft;
    DatabaseManager mDBM;

    private final String NOTE_TEXT = "the_note's_text";
    private final String NOTE_ID = "this is the note id.";
    private final String HASH_TAGS = "this is the hash tags.";

    private final String TEXT_NOTE_FRAME_TAG = "text_note_frame";
    private final String OPTIONS_FRAME_TAG = "options_frame";
    private final String IMAGE_NOTE_FRAME_TAG = "impage_note_Frame_TAg";
    private final int SAVE = 0;
    private final int DELETE = 1;
    private final int CANCEL = 2;
    private final String IS_IMAGE = "this is or is not an image.";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_note);
        mDBM = new DatabaseManager(this);
        fm = getFragmentManager();
        ft = fm.beginTransaction();

        Bundle b = getIntent().getExtras();

        SavedSingleNoteOptionsFragment ssnof = new SavedSingleNoteOptionsFragment();
        ssnof.setSSNOFListener(this);
        Bundle ssnofBundle = new Bundle();
        ssnofBundle.putString(HASH_TAGS, b.getString(HASH_TAGS));
        ssnofBundle.putString(NOTE_ID, b.getString(NOTE_ID));
        ssnof.setArguments(ssnofBundle);

        if(b.getBoolean(IS_IMAGE)){
            SingleImageNoteDisplayFragment sindf = new SingleImageNoteDisplayFragment();
            Bundle sindfBundle = new Bundle();
            sindfBundle.putString(NOTE_ID, b.getString(NOTE_ID));
            ft.add(R.id.note_frame, sindf, IMAGE_NOTE_FRAME_TAG);
            ft.add(R.id.options_frame, ssnof, OPTIONS_FRAME_TAG);
            ft.addToBackStack(null);
            ft.commit();
        }
        else {
            SingleTextNoteDisplayFragment stndf = new SingleTextNoteDisplayFragment();
            Bundle stndfBundle = new Bundle();
            stndfBundle.putString(NOTE_TEXT, b.getString(NOTE_TEXT));
            stndf.setArguments(stndfBundle);
            ft.add(R.id.note_frame, stndf, TEXT_NOTE_FRAME_TAG);
            ft.add(R.id.options_frame, ssnof, OPTIONS_FRAME_TAG);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onSingleNoteOptionsChoiceListener(Integer event, String rowID) {
        if (event == SAVE){
            SingleTextNoteDisplayFragment stndf = (SingleTextNoteDisplayFragment)getFragmentManager().findFragmentById(R.id.note_frame);
            SavedSingleNoteOptionsFragment ssnof = (SavedSingleNoteOptionsFragment)getFragmentManager().findFragmentById(R.id.options_frame);
            String newText = stndf.mSavedNoteEditText.getText().toString();
            String newHash = ssnof.mHashTagEditText.getText().toString();
            mDBM.updateRow(rowID, newText, newHash);
            //Intent returnIntent = new Intent();
            setResult(SAVE);
            finish();
        }
        else if (event == DELETE){
            if(mDBM.deleteRow(rowID)){
                Toast.makeText(SavedSingleNoteActivity.this, "Row deleted.", Toast.LENGTH_SHORT).show();
                setResult(DELETE);
                finish();
            }
            else{
                Toast.makeText(SavedSingleNoteActivity.this, "Row not deleted.", Toast.LENGTH_SHORT).show();
            }

        }
        else if(event == CANCEL){
            setResult(CANCEL);
            finish();
        }
    }
}
