package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Kevin on 11/16/2015.
 */
public class ImageNoteDisplayFragment extends Fragment implements View.OnClickListener{

    Button takePictureButton;
    ImageView cameraPicture;

    final String filename = "temp_photo.jpg";
    Uri imageFileUri;

    private static final String PICTURE_TO_DISPLAY = "picture has been taken";
    boolean pictureToDisplay = false;

    private static final int TAKE_PICTURE_REQUEST = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            pictureToDisplay = savedInstanceState.getBoolean(PICTURE_TO_DISPLAY, false);
        }


        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File file = new File(Environment.getExternalStorageDirectory(), filename);
                imageFileUri = Uri.fromFile(file);

                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

                if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(pictureIntent, TAKE_PICTURE_REQUEST);
                } else {
                    Toast.makeText(getActivity(), "No camera available", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_note_display_fragment, container, false);
        ;
        cameraPicture = (ImageView) v.findViewById(R.id.image_note);
        takePictureButton = (Button) v.findViewById(R.id.take_picture_button);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == TAKE_PICTURE_REQUEST) {
            pictureToDisplay = true;

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            imageFileUri = Uri.fromFile(file);
            mediaScanIntent.setData(imageFileUri);
            getActivity().sendBroadcast(mediaScanIntent);
        } else {
            pictureToDisplay = false;
        }
    }

   /* @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && pictureToDisplay) {
            Bitmap image = scaleBitmap();
            cameraPicture.setImageBitmap(image);
        }
    }*/

    Bitmap scaleBitmap() {

        // * Scale picture taken to fit into the ImageView */
        //Step 1: what size is the ImageView?
        int imageViewHeight = cameraPicture.getHeight();
        int imageViewWidth = cameraPicture.getWidth();

        //Step 2: decode file to find out how large the image is.
        //BitmapFactory is used to create bitmaps from pixels in a file.
        // Many options and settings, so use a BitmapFactory.Options object to store our desired settings.
        //Set the inJustDecodeBounds flag to true,
        //which means just the *information about* the picture is decoded and stored in bOptions
        //Not all of the pixels have to be read and stored here.
        //When we've done this, we can query bOptions to find out the original picture's height and width.
        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inJustDecodeBounds = true;

        File file = new File(Environment.getExternalStorageDirectory(), filename);
        Uri imageFileUri = Uri.fromFile(file);
        String photoFilePath = imageFileUri.getPath();

        BitmapFactory.decodeFile(photoFilePath, bOptions);

        //What size is the picture?
        int pictureHeight = bOptions.outHeight;
        int pictureWidth = bOptions.outWidth;

        //Step 3. Can use the original size and target size to calculate scale factor
        int scaleFactor = Math.min(pictureHeight / imageViewHeight, pictureWidth / imageViewWidth);

        //Step 4. Decode the image file into a new bitmap, scaled to fit the ImageView
        bOptions.inJustDecodeBounds = false;   //now we want to get a bitmap
        bOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(photoFilePath, bOptions);

    }

    @Override
    public void onSaveInstanceState (Bundle outBundle){
        outBundle.putBoolean(PICTURE_TO_DISPLAY, pictureToDisplay);
    }

    @Override
    public void onClick(View v) {

    }
}