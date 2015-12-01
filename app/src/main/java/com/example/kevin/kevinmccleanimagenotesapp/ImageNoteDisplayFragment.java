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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

//This display allows the user to take a picture with their phone, which they then have the option of saving.
public class ImageNoteDisplayFragment extends Fragment {

    ImageView cameraPicture;

    final String tempFileName = "temp_photo.jpg";
    Uri imageFileUri;

    Bitmap mBitmap;
    boolean pictureToDisplay = false;

    private static final int TAKE_PICTURE_REQUEST = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_note_display_fragment, container, false);
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraPicture = (ImageView)v.findViewById(R.id.image_note);
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
            File file = new File(Environment.getExternalStorageDirectory(), tempFileName);
            imageFileUri = Uri.fromFile(file);
            mediaScanIntent.setData(imageFileUri);
            getActivity().sendBroadcast(mediaScanIntent);
            mBitmap = scaleBitmap();
            cameraPicture.setImageBitmap(mBitmap);
        }
        else {
            pictureToDisplay = false;
        }
    }


    //this makes the bitmap image which is shown to the user.
    Bitmap scaleBitmap () {

        int imageViewHeight = cameraPicture.getHeight();
        int imageViewWidth = cameraPicture.getWidth();

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

    //this will save the image permanately. It will stay in the same file, but will have a name besides "temp_photo".
    public boolean saveImagePermanently(String permanentFileName) {
        String path = Environment.getExternalStorageDirectory().toString();
        File directory = new File(path, permanentFileName + ".jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(directory);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            if (out != null) {
                out.close();
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}