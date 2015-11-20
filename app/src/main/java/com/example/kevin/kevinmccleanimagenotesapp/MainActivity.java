package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OptionsFragment.OnOptionFragmentSelectedListener, ChoicesTabFragment.OnChoicesFragmentSelectedListener{
    private final int SAVE = 0;
    private final int TAKE_PICTURE = 1;
    private final int CAMERA_FRAGMENT = 0;
    private final int TEXT_FRAGMENT = 1;
    private final int SAVED_FRAGMENT = 2;


    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ChoicesTabFragment ctf = new ChoicesTabFragment();
        OptionsFragment of = new OptionsFragment();
        ImageNoteDisplayFragment indf = new ImageNoteDisplayFragment();
        ft.add(R.id.top_frame, ctf);
        ft.add(R.id.middle_frame, indf);
        ft.add(R.id.bottom_frame, of);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onOptionsFragmentButtonSelected(Integer event) {
        if(event == SAVE){
            //TODO sends information to the
        }
        else if(event == TAKE_PICTURE){
            //TODO sends request to
        }
    }

    @Override
    public void onChoicesFragmentSelection(Integer event) {
        //TODO switch which of the three main fragments are being displayed.
        if(event == CAMERA_FRAGMENT){
            ImageNoteDisplayFragment indf = new ImageNoteDisplayFragment();
            ft.replace(R.id.middle_frame, indf);
            ft.commit();
        }
        else if(event == TEXT_FRAGMENT){
            TextNoteDisplayFragment tndf = new TextNoteDisplayFragment();
            ft.replace(R.id.middle_frame, tndf);
            ft.commit();
        }
        else if(event == SAVED_FRAGMENT){
            SavedNoteDisplayFragment sndf = new SavedNoteDisplayFragment();
            ft.replace(R.id.middle_frame, sndf);
            ft.commit();
        }
    }
}
