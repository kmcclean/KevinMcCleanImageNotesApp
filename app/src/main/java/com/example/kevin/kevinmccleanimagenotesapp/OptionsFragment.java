package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Set;

//This displays the buttons at the bottom of the screen for the ImageNoteDisplayFragment and the TextNoteDisplayFragment.
public class OptionsFragment extends Fragment implements View.OnClickListener{

    EditText mHashTagEditText;
    Button mSaveButton;

    OnOptionFragmentSelectedListener mListener;

    private final int SAVE_BUTTON = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_note_options_fragment, container, false);
        mHashTagEditText = (EditText)v.findViewById(R.id.hash_tag_et);
        mSaveButton = (Button)v.findViewById(R.id.save_button);

        mHashTagEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHashTagEditText.setTextColor(Color.parseColor("#000000"));
                mHashTagEditText.setText("");
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionsFragmentButtonSelected(SAVE_BUTTON);
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
    }

    public void setmListener(OnOptionFragmentSelectedListener listener){
        this.mListener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (OnOptionFragmentSelectedListener) context;
        }
        catch (ClassCastException e){
            Log.e(e.toString(), "Must attach ChoicesTabFragment");
        }
    }

    public String getmHashTagEditText() {
        return mHashTagEditText.getText().toString();
    }

    public interface OnOptionFragmentSelectedListener {
        void onOptionsFragmentButtonSelected(Integer event);
    }
}
