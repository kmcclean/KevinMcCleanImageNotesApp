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

    public SavedNotesAdapter (Activity a, ArrayList<Notes> notes, String searchConditions){
        super (a, R.layout.note_list, notes);
        this.mActivity = a;
        this.mNotesArrayList = new ArrayList<>();
        for (Notes n : notes) {
            if (n.getNoteText().toLowerCase().contains(searchConditions.toLowerCase()) || n.getHashTag().toLowerCase().contains(searchConditions.toLowerCase())) {
                mNotesArrayList.add(n);
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.note_list, null, true);
        Notes note = mNotesArrayList.get(position);
        hashtags = (TextView) rowView.findViewById(R.id.hash_tags_saved_notes_text_view);
        if(!note.isPicture) {
            mNoteListTextView = (TextView) rowView.findViewById(R.id.note_list_text_view);
            mNoteListTextView.setText("Note: " + note.getNoteText());
//            thumbnail.setVisibility(View.INVISIBLE);

        }
        else if (note.isPicture){
            thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail_image_view);
            thumbnail.setImageBitmap(createThumbnail(note.getNoteID()));
//            mNoteListTextView.setVisibility(View.INVISIBLE);
        }
        hashtags.setText("Hashtags: " + note.getHashTag());
        return rowView;

    }

    Bitmap createThumbnail (String fileName) {

        // * Scale picture taken to fit into the ImageView */
        //Step 1: what size is the ImageView?
        int imageViewHeight = thumbnail.getHeight();
        int imageViewWidth = thumbnail.getWidth();

        //Step 2: decode file to find out how large the image is.
        //BitmapFactory is used to create bitmaps from pixels in a file.
        // Many options and settings, so use a BitmapFactory.Options object to store our desired settings.
        //Set the inJustDecodeBounds flag to true,
        //which means just the *information about* the picture is decoded and stored in bOptions
        //Not all of the pixels have to be read and stored here.
        //When we've done this, we can query bOptions to find out the original picture's height and width.
        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inJustDecodeBounds = true;
        //File file = new File("C:\\temp\\image_app_photos", tempFileName);
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        Uri imageFileUri = Uri.fromFile(file);
        String photoFilePath = imageFileUri.getPath();

        BitmapFactory.decodeFile(photoFilePath, bOptions);

        //What size is the picture?
        int pictureHeight = bOptions.outHeight;
        int pictureWidth = bOptions.outWidth;

        //Step 3. Can use the original size and target size to calculate scale factor
        int scaleFactor = Math.min((pictureHeight / imageViewHeight)/10, (pictureWidth / imageViewWidth)/10);

        //Step 4. Decode the image file into a new bitmap, scaled to fit the ImageView
        bOptions.inJustDecodeBounds = false;   //now we want to get a bitmap
        bOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, bOptions);
        return bitmap;
    }
}
