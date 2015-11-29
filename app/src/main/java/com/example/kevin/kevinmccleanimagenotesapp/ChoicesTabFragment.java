package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choice_tab_fragment, container, false);
        mImages = (TextView) v.findViewById(R.id.image_tv);
        mText = (TextView) v.findViewById(R.id.text_tv);
        mSaved = (TextView) v.findViewById(R.id.saved_tv);
        mImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ChoicesTabFragment", "mImages.onClick accessed.");
                mListener.onChoicesFragmentSelection(CAMERA_FRAGMENT);

            }
        });

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ChoicesTabFragment", "mText.onClick accessed.");
                mListener.onChoicesFragmentSelection(TEXT_FRAGMENT);
            }
        });

        mSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ChoicesTabFragment", "mSaved.onClick accessed.");
                mListener.onChoicesFragmentSelection(SAVED_FRAGMENT);
            }
        });

        return v;
    }
    @Override
    public void onClick(View v) {
    }

    public void setmListener(OnChoicesFragmentSelectedListener listener){
        this.mListener = listener;

    }

    public interface OnChoicesFragmentSelectedListener{
        void onChoicesFragmentSelection(Integer event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (OnChoicesFragmentSelectedListener) context;
        }
        catch (ClassCastException e){
            Log.e(e.toString(), "Must attach ChoicesTabFragment");
        }
    }
}
