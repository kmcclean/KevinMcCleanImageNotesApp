package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Kevin on 11/30/2015.
 */
public class SingleImageNoteDisplayFragment extends Fragment{

    private final String NOTE_ID = "This is the note id.";
    ImageView mSingleImage;
    Bitmap shownBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.single_image_note_display, container, false);
        mSingleImage = (ImageView)v.findViewById(R.id.single_image_image_view);
        try{
            Bundle b = getArguments();
            shownBitmap = showImage(b.getString(NOTE_ID));
            mSingleImage.setImageBitmap(shownBitmap);
        }
        catch (Exception e){
            Log.e(e.toString(), "setting image failed");
        }
        return v;
    }

    Bitmap showImage (String tempFileName) {

        int imageViewHeight = mSingleImage.getHeight();
        int imageViewWidth = mSingleImage.getWidth();

        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inJustDecodeBounds = true;
        File file = new File(Environment.getExternalStorageDirectory(), tempFileName);
        Uri imageFileUri = Uri.fromFile(file);
        String photoFilePath = imageFileUri.getPath();

        BitmapFactory.decodeFile(photoFilePath, bOptions);

        int pictureHeight = bOptions.outHeight;
        int pictureWidth = bOptions.outWidth;

        int scaleFactor = Math.min(pictureHeight / imageViewHeight, pictureWidth / imageViewWidth);

        bOptions.inJustDecodeBounds = false;   //now we want to get a bitmap
        bOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, bOptions);
        return bitmap;
    }

}
