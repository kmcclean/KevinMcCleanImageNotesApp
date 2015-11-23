package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
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
        ft.addToBackStack(null);
        ft.commit();
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
                OptionsFragment optionsFragment = (OptionsFragment)getFragmentManager().findFragmentById(R.id.bottom_frame);
                String hashTags = optionsFragment.getmHashTagEditText();
                mDBM.addRow(noteID, hashTags, noteText, 0);
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
        //TODO switch which of the three main fragments are being displayed.
        //this sets the event to the image fragment, and makes sure that the optionsFragment is showing.
        Log.e("MainActivity", "inOnChoicesFragmentSelection, before if statements. Integer event = " + event.toString());
        if(event == IMAGE_FRAGMENT){
            Fragment mf = getFragmentManager().findFragmentById(R.id.middle_frame);
            if(!mf.getTag().equals(IMAGE_FRAGMENT_TAG)) {
                //TODO set the OptionsFragment to appear.
                Fragment bf = getFragmentManager().findFragmentById(R.id.bottom_frame);
                if(!bf.getTag().equals(OPTIONS_FRAGMENT_TAG)){
                    ft = fm.beginTransaction();
                    OptionsFragment of = new OptionsFragment();
                    ft.replace(R.id.bottom_frame, of, OPTIONS_FRAGMENT_TAG);
                    ft.commit();
                }
                OptionsFragment optionsFragment = (OptionsFragment) getFragmentManager().findFragmentById(R.id.bottom_frame);
                optionsFragment.makeTakePictureButtonAppear();
                ImageNoteDisplayFragment indf = new ImageNoteDisplayFragment();
                ft.replace(R.id.middle_frame, indf, IMAGE_FRAGMENT_TAG);
                ft.commit();
            }
        }
        else if(event == TEXT_FRAGMENT){
            //TODO set the OptionsFragment to appear.
            Fragment mf = getFragmentManager().findFragmentById(R.id.middle_frame);
            if(!mf.getTag().equals(TEXT_FRAGMENT_TAG)) {
                Fragment bf = getFragmentManager().findFragmentById(R.id.bottom_frame);
                if(!bf.getTag().equals(OPTIONS_FRAGMENT_TAG)){
                    ft = fm.beginTransaction();
                    OptionsFragment of = new OptionsFragment();
                    ft.replace(R.id.bottom_frame, of, OPTIONS_FRAGMENT_TAG);
                    ft.commit();
                }
                OptionsFragment optionsFragment = (OptionsFragment) getFragmentManager().findFragmentById(R.id.bottom_frame);
                optionsFragment.makeTakePictureButtonDisappear();
                TextNoteDisplayFragment tndf = new TextNoteDisplayFragment();
                ft.replace(R.id.middle_frame, tndf, TEXT_FRAGMENT_TAG);
                ft.commit();
            }
        }
        else if(event == SAVED_FRAGMENT){
            Fragment mf = getFragmentManager().findFragmentById(R.id.middle_frame);
            if(!mf.getTag().equals(SAVED_FRAGMENT_TAG)) {
                ft = fm.beginTransaction();
                SavedNoteDisplayFragment sndf = new SavedNoteDisplayFragment();
                SavedNoteGridOptionsFragment sngof = new SavedNoteGridOptionsFragment();
                ft.replace(R.id.middle_frame, sndf, SAVED_FRAGMENT_TAG);
                ft.replace(R.id.bottom_frame, sngof, SAVED_OPTIONS_FRAGMENT_TAG);
                ft.commit();
            }
        }
    }
}
