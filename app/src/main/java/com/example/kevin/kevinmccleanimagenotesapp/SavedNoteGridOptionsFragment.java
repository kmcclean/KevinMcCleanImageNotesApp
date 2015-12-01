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
import android.widget.EditText;

public class SavedNoteGridOptionsFragment extends Fragment {

    EditText mSearchField;
    Button mSearchButton;
    SearchNotes searchListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_notes_grid_fragment_search_fragment, container, false);
        mSearchField = (EditText)v.findViewById(R.id.search_string_et);
        mSearchButton = (Button)v.findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchListener.searchNotes(mSearchField.getText().toString());
            }
        });
        return v;
    }

    public void setSearchListener(SearchNotes sn){
        this.searchListener = sn;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            searchListener = (SearchNotes) context;
        }
        catch (ClassCastException e){
            Log.e(e.toString(), "Must attach SearchNotes");
        }
    }


    public interface SearchNotes{
        void searchNotes(String searchterm);
    }
}
