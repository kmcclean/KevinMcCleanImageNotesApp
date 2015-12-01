package com.example.kevin.kevinmccleanimagenotesapp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

//This display allows the user to take a picture with their phone, which they then have the option of saving.
public class ImageNoteDisplayFragment extends Fragment {

    ImageView cameraPicture;

    final String tempFileName = "temp_photo.jpg";
    Uri imageFileUri;

    private static final String PICTURE_TO_DISPLAY = "picture has been taken";
    boolean pictureToDisplay = false;

    private static final int TAKE_PICTURE_REQUEST = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_note_display_fragment, container, false);
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraPicture = (ImageView)v.findViewById(R.id.image_note);
        //File file = new File("C:\\temp\\image_app_photos", tempFileName);
        File file = new File(Environment.getExternalStorageDirectory(), tempFileName);
        imageFileUri = Uri.fromFile(file);

        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(pictureIntent, TAKE_PICTURE_REQUEST);
        }
        else{
            Toast.makeText(getActivity(), "No camera available", Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == getActivity().RESULT_OK && requestCode == TAKE_PICTURE_REQUEST){
            pictureToDisplay = true;
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            //File file = new File("C:\\temp\\image_app_photos", tempFileName);
            File file = new File(Environment.getExternalStorageDirectory(), tempFileName);
            imageFileUri = Uri.fromFile(file);
            mediaScanIntent.setData(imageFileUri);
            getActivity().sendBroadcast(mediaScanIntent);
            cameraPicture.setImageBitmap(scaleBitmap());
        }
        else {
            pictureToDisplay = false;
        }
    }


    Bitmap scaleBitmap () {

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
        //File file = new File("C:\\temp\\image_app_photos", tempFileName);
        File file = new File(Environment.getExternalStorageDirectory(), tempFileName);
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

        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, bOptions);
        return bitmap;
    }

    public boolean saveImagePermanently(String permanentFileName){
        try {
            //File tempFile = new File("C:\\temp\\image_app_photos", tempFileName);
            File tempFile = new File(Environment.getExternalStorageDirectory(), tempFileName);
            Uri tempImageFileUri = Uri.fromFile(tempFile);
            //String photoFilePath = imageFileUri.getPath();
            //File permanentFile = new File("C:\\temp\\moved_images", permanentFileName);
            File permanentFile = new File(tempImageFileUri.getPath(), permanentFileName);
            //Uri permImageFileUri = Uri.fromFile(permanentFile);
            //mediaScanIntent.setData(imageFileUri);
            //getActivity().sendBroadcast(mediaScanIntent);
            return true;
        }
        catch (Exception e){
            Log.e(e.toString(), " Error in ImageNoteDisplayFragment.java");
            return false;
        }
    }
}