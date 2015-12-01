package com.example.kevin.kevinmccleanimagenotesapp;

import android.graphics.Bitmap;

public class Notes {

    String noteID;
    String hashTag;
    String noteText;
    Bitmap notePicture;
    boolean isPicture;

    public Notes(String id, String ht, String nt, boolean isPic){
        this.noteID = id;
        this.hashTag = ht;
        this.noteText = nt;
        this.isPicture = isPic;
    }

    /*public Notes(String id, String ht, Bitmap pic, boolean isPic){
        this.noteID = id;
        this.hashTag = ht;
        notePicture = pic;
        this.isPicture = isPic;
    }*/

    public String getHashTag() {
        return hashTag;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getNoteID() {
        return noteID;
    }

    public boolean getIsPicture(){
        return isPicture;
    }
}
