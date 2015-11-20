package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//This fragment is used to switch between the camera, text, and saved notes fragments.
//It does so by having the user click on one of the three TextViews, which will then swap in...
//...the appropriate fragment.
public class ChoicesTabFragment extends Fragment implements View.OnClickListener {

    TextView mImages;
    TextView mText;
    TextView mSaved;


    private final int CAMERA_FRAGMENT = 0;
    private final int TEXT_FRAGMENT = 1;
    private final int SAVED_FRAGMENT = 2;

    OnChoicesFragmentSelectedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChoicesFragmentSelection(CAMERA_FRAGMENT);
                //TODO When pressed, the program should switch the camera fragment.

            }
        });

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO When pressed, the program should switch the text fragment.
                mListener.onChoicesFragmentSelection(TEXT_FRAGMENT);
            }
        });

        mSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO When pressed, the program should switch the saved fragment.
                mListener.onChoicesFragmentSelection(SAVED_FRAGMENT);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choice_tab_fragment, container, false);
        mImages = (TextView) v.findViewById(R.id.image_tv);
        mText = (TextView) v.findViewById(R.id.text_tv);
        mSaved = (TextView) v.findViewById(R.id.saved_tv);
        return v;
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnChoicesFragmentSelectedListener{
        public void onChoicesFragmentSelection(Integer event);
    }
}
