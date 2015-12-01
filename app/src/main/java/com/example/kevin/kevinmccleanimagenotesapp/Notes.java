package com.example.kevin.kevinmccleanimagenotesapp;

public class Notes {

    String noteID;
    String hashTag;
    String noteText;
    boolean isPicture;

    //this class functions as storage for Notes once they have been pulled from the database, a container for all the information for a given note.
    public Notes(String id, String ht, String nt, boolean isPic){
        this.noteID = id;
        this.hashTag = ht;
        this.noteText = nt;
        this.isPicture = isPic;
    }

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
