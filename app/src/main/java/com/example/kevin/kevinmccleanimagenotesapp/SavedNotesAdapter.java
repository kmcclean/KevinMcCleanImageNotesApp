package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class SavedNotesAdapter extends ArrayAdapter {

    private ImageView thumbnail;
    private TextView hashtags;
    private Activity mActivity;
    //private ArrayList<String> mTextNotesArrayList = new ArrayList<>();
    private ArrayList<Notes> mNotesArrayList = new ArrayList<>();
    public TextView mNoteListTextView;
    public boolean conditionalSearch;

    public SavedNotesAdapter(Activity a, ArrayList<Notes> notes){
        super (a, R.layout.note_list, notes);
        this.mActivity = a;
        /*for(Notes n : notes){
            this.mTextNotesArrayList.add(n.getNoteText());
        }*/
        this.mNotesArrayList = notes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.note_list, null, true);
        Notes note = mNotesArrayList.get(position);
        hashtags = (TextView) rowView.findViewById(R.id.hash_tags_saved_notes_text_view);
        if(!note.isPicture) {
            mNoteListTextView = (TextView) rowView.findViewById(R.id.note_list_text_view);
            mNoteListTextView.setText("Note: " + note.getNoteText());

        }
        else if (note.isPicture){
            thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail_image_view);
            thumbnail.setImageBitmap(createThumbnail(note.getNoteID()));
        }
        hashtags.setText("Hashtags: " + note.getHashTag());
        return rowView;

    }

    Bitmap createThumbnail (String fileName) {

        int imageViewHeight = thumbnail.getHeight();
        int imageViewWidth = thumbnail.getWidth();

        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inJustDecodeBounds = true;
        File file = new File(Environment.getExternalStorageDirectory(), fileName + ".jpg");
        Uri imageFileUri = Uri.fromFile(file);
        String photoFilePath = imageFileUri.getPath();

        BitmapFactory.decodeFile(photoFilePath, bOptions);

        int pictureHeight = bOptions.outHeight;
        int pictureWidth = bOptions.outWidth;

        int scaleFactor = Math.min((pictureHeight / imageViewHeight)/10, (pictureWidth / imageViewWidth)/10);

        bOptions.inJustDecodeBounds = false;
        bOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, bOptions);
        return bitmap;
    }
}
