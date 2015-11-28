package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SavedSingleNoteOptionsFragment extends Fragment {

    Button mSaveButton;
    Button mDeleteButton;
    Button mCancelButton;

    private final int SAVE = 0;
    private final int DELETE = 1;
    private final int CANCEL = 2;

    String rowID;
    OnSingleNoteOptionsChoiceListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_single_note_options_fragment, container, false);
        mSaveButton = (Button)v.findViewById(R.id.single_note_save);
        mDeleteButton = (Button)v.findViewById(R.id.delete_button);
        mCancelButton = (Button)v.findViewById(R.id.cancel_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSingleNoteOptionsChoiceListener(SAVE, rowID);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSingleNoteOptionsChoiceListener(DELETE, rowID);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSingleNoteOptionsChoiceListener(CANCEL, null);
            }
        });
        return v;
    }

    public interface OnSingleNoteOptionsChoiceListener{
        void onSingleNoteOptionsChoiceListener(Integer event, String rowID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (OnSingleNoteOptionsChoiceListener) context;
        }
        catch (ClassCastException e){
            Log.e(e.toString(), "Must attach SavedSingleNoteOptionsFragment");
        }
    }
}
