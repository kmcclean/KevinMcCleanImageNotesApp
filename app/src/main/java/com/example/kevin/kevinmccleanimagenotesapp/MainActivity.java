package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OptionsFragment.OnOptionFragmentSelectedListener, ChoicesTabFragment.OnChoicesFragmentSelectedListener{
    private final int SAVE = 0;
    private final int TAKE_PICTURE = 1;
    private final int IMAGE_FRAGMENT = 0;
    private final int TEXT_FRAGMENT = 1;
    private final int SAVED_FRAGMENT = 2;
    private final String IMAGE_FRAGMENT_TAG = "image_fragment";
    private final String TEXT_FRAGMENT_TAG = "text_fragment";
    private final String SAVED_FRAGMENT_TAG = "saved_fragment";
    private final String OPTIONS_FRAGMENT_TAG = "options_fragment";
    private final String SAVED_OPTIONS_FRAGMENT_TAG = "saved_options_fragment_tag";
    private final String CHOICES_FRAGMENT_TAG = "choices_fragment";
    private final String NOTE_LIST = "note_list";

    FragmentManager fm;
    FragmentTransaction ft;
    DatabaseManager mDBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ChoicesTabFragment ctf = new ChoicesTabFragment();
        OptionsFragment of = new OptionsFragment();
        TextNoteDisplayFragment tndf = new TextNoteDisplayFragment();
        ft.add(R.id.top_frame, ctf, CHOICES_FRAGMENT_TAG);
        ctf.setmListener(this);
        ft.add(R.id.middle_frame, tndf, TEXT_FRAGMENT_TAG);
        ft.add(R.id.bottom_frame, of, OPTIONS_FRAGMENT_TAG);
        of.setmListener(this);
        ft.addToBackStack(null);
        ft.commit();

        mDBM = new DatabaseManager(this);
    }

    //This controls what happens when the optionsFragment button is pressed. It sends and receives data between the Fragments here.
    @Override
    public void onOptionsFragmentButtonSelected(Integer event) {
        if(event == SAVE){
            Date d = new Date();
            String noteID = Long.toString(d.getTime());
            Fragment save = getFragmentManager().findFragmentById(R.id.middle_frame);

            if(save.getTag().equals(IMAGE_FRAGMENT_TAG)){
                ImageNoteDisplayFragment imageNoteDisplayFragment = (ImageNoteDisplayFragment)getFragmentManager().findFragmentById(R.id.middle_frame);
                OptionsFragment optionsFragment = (OptionsFragment)getFragmentManager().findFragmentById(R.id.bottom_frame);
                String hashTags = optionsFragment.getmHashTagEditText();
                mDBM.addRow(noteID, hashTags, null, 1);
            }

            else if(save.getTag().equals(TEXT_FRAGMENT_TAG)){
                TextNoteDisplayFragment textNoteDisplayFragment = (TextNoteDisplayFragment)getFragmentManager().findFragmentById(R.id.middle_frame);
                String noteText = textNoteDisplayFragment.getmTextNote();
                if(noteText.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter a note to save.", Toast.LENGTH_SHORT).show();
                }
                OptionsFragment optionsFragment = (OptionsFragment)getFragmentManager().findFragmentById(R.id.bottom_frame);
                String hashTags = optionsFragment.getmHashTagEditText();
                if(mDBM.addRow(noteID, hashTags, noteText, 0)){
                    Toast.makeText(MainActivity.this, "Note added to database.", Toast.LENGTH_SHORT).show();
                    textNoteDisplayFragment.mTextNote.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "Adding note failed.", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if(event == TAKE_PICTURE){
            ImageNoteDisplayFragment imageNoteDisplayFragment = (ImageNoteDisplayFragment)getFragmentManager().findFragmentById(R.id.middle_frame);
            imageNoteDisplayFragment.takePicture();
        }
    }

    //This controls what happens when buttons are pressed in the OptionsFragment. It is used to send and receive information between multiple fragments.
    @Override
    public void onChoicesFragmentSelection(Integer event) {
        //this sets the event to the image fragment, and makes sure that the optionsFragment is showing.
        Fragment mf = getFragmentManager().findFragmentById(R.id.middle_frame);
        if (event == IMAGE_FRAGMENT && !mf.getTag().equals(IMAGE_FRAGMENT_TAG)) {
            ft = fm.beginTransaction();
            ImageNoteDisplayFragment indf = new ImageNoteDisplayFragment();
            ft.replace(R.id.middle_frame, indf, IMAGE_FRAGMENT_TAG);
            ft.commit();
            optionFragmentSetUp(false, true);
        }
        else if (event == TEXT_FRAGMENT && !mf.getTag().equals(TEXT_FRAGMENT_TAG)) {
            ft = fm.beginTransaction();
            TextNoteDisplayFragment tndf = new TextNoteDisplayFragment();
            ft.replace(R.id.middle_frame, tndf, TEXT_FRAGMENT_TAG);
            ft.commit();
            optionFragmentSetUp(false, false);
        }
        else if (event == SAVED_FRAGMENT && !mf.getTag().equals(SAVED_FRAGMENT_TAG)) {
            ft = fm.beginTransaction();
            SavedNoteDisplayFragment sndf = new SavedNoteDisplayFragment();
            ArrayList<Notes> notesList = mDBM.fetchAll();
            ArrayList<String> notes = new ArrayList<>();
            for(Notes n: notesList){
                notes.add(n.getNoteText());
            }
            Bundle b = new Bundle();
            b.putStringArrayList(NOTE_LIST, notes);
            sndf.setArguments(b);
            ft.replace(R.id.middle_frame, sndf, SAVED_FRAGMENT_TAG);
            ft.commit();
            optionFragmentSetUp(true, false);
        }
    }

    public void optionFragmentSetUp(boolean isSaved, boolean appear) {
        try {
            ft = fm.beginTransaction();
            if (isSaved){
                SavedNoteGridOptionsFragment sngof = new SavedNoteGridOptionsFragment();
                ft.replace(R.id.bottom_frame, sngof, SAVED_OPTIONS_FRAGMENT_TAG);
                ft.commit();
            }
            else {
                OptionsFragment of = new OptionsFragment();
                of.setmListener(this);
                ft.replace(R.id.bottom_frame, of, OPTIONS_FRAGMENT_TAG);
                ft.commit();
                if (appear) {
                    of.makeTakePictureButtonAppear();
                } else {
                    of.makeTakePictureButtonDisappear();
                }
            }
        }
        catch (Exception e){
            Log.e("MainActivity", e.toString() + " in optionsFragmentSetUp method.");
        }
    }
}
