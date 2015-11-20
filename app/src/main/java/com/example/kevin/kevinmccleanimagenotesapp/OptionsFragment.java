package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

//
public class OptionsFragment extends Fragment implements View.OnClickListener{

    EditText mHashTagEditText;
    Button mTakePictureButton;
    Button mSaveButton;

    OnOptionFragmentSelectedListener mListener;

    private final int SAVE_BUTTON = 0;
    private final int TAKE_PICTURE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionsFragmentButtonSelected(TAKE_PICTURE);
                //TODO When this happens have a request sent through the MainActivity to have a picture taken by the ImageNoteFragment.
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionsFragmentButtonSelected(SAVE_BUTTON);
                //TODO when this button is pressed, the system will check to see if there information in the hashtag, and that there is either a picture taken or a TextNote written. If so, it will send the information to the MainActivity in order to save the note
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_note_options_fragment, container, false);
        mHashTagEditText = (EditText)v.findViewById(R.id.hash_tag_et);
        mTakePictureButton = (Button)v.findViewById(R.id.take_picture_button);
        mSaveButton = (Button)v.findViewById(R.id.save_button);
        return v;
    }

    @Override
    public void onClick(View v) {


    }

    public interface OnOptionFragmentSelectedListener {
        public void onOptionsFragmentButtonSelected(Integer event);
    }
}
