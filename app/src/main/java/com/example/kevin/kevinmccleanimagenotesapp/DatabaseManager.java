package com.example.kevin.kevinmccleanimagenotesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

//This controls the database that stores all of the saved notes.
public class DatabaseManager {


    private Context context;
    private SQLHelper helper;
    private SQLiteDatabase db;

    protected static final String DB_NAME = "notes";
    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "savedNotesTable";

    protected static final String NOTE_ID = "NOTE_ID";
    protected static final String HASH_TAGS = "hash_tags";
    protected static final String NOTE_TEXT = "note_text";
    protected static final String NOTE_TYPE = "image";

    private static final String DBTAG = "DatabaseManager";
    private static final String SQLTAG = "SQLHelper";

    public DatabaseManager(Context c) {
        this.context = c;
        this.helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    //this is to reset and launch the database on the first use of the table. It's not what I would use if this were built for full use, but it works for testing purposes.
    public DatabaseManager (Context c, boolean createNew){
        if (createNew){
            this.context = c;
            this.helper = new SQLHelper(c);
            this.db = helper.getWritableDatabase();
            helper.onCreate(db);
        }
    }

    public void close() {
        helper.close(); //Closes the database - very important!
    }



    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE + ";");
        //    String createTable = "CREATE TABLE " + DB_TABLE + " (" + NOTE_ID + " TEXT, "  + NOTE_TEXT + " TEXT, " + HASH_TAGS + " TEXT);";
            String createTable = "CREATE TABLE " + DB_TABLE + " (" + NOTE_ID + " TEXT, "  + NOTE_TEXT + " TEXT, " + HASH_TAGS + " TEXT, " + NOTE_TYPE + " INTEGER);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQLTAG, "Upgrade table - drop and recreate it");
        }
    }

    //fetches everything in the database.
    public ArrayList<Notes> fetchAll() {
        String cols[] = {NOTE_ID, HASH_TAGS, NOTE_TEXT};//, NOTE_TYPE};
        Cursor cursor = db.query(DB_TABLE, cols, null, null, null, null, HASH_TAGS);
        ArrayList<Notes> notesList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Notes newNote;
            Log.e("cursor.getString(1) = ", cursor.getString(1));
            if(cursor.getInt(2)==1) {
                newNote = new Notes(cursor.getString(0), cursor.getString(1), cursor.getString(2), true);
            }
            else {
                newNote = new Notes(cursor.getString(0), cursor.getString(1), cursor.getString(2), false);
            }
            notesList.add(newNote);
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return notesList;
    }

    //adds a new row to the database.
    public boolean addRow(String rowID, String hashTags, String noteText, int isPicture) {
        ContentValues newRow = new ContentValues();
        newRow.put(NOTE_ID, rowID);
        newRow.put(HASH_TAGS, hashTags);
        newRow.put(NOTE_TEXT, noteText);
        newRow.put(NOTE_TYPE, isPicture);
        try {
            db.insertOrThrow(DB_TABLE, null, newRow);
            return true;
        } catch (Exception e) {
            Log.e(DBTAG, "Error inserting new data into table", e);
            return false;
        }
    }

    //This will delete a row from the table. It is non-paramaterized b/c running it as a parameter was causing the system to delete everything in the database.
    public boolean deleteRow(String rowID){
        ContentValues deleteRow = new ContentValues();
        deleteRow.put(NOTE_ID, rowID);
        try{
            db.execSQL("DELETE FROM " + DB_TABLE + " WHERE " + NOTE_ID + " = " + rowID);
            return true;
        }
        catch (Exception e){
            Log.e(e.toString(), "Error deleting row.");
            return false;
        }
    }

    //updates the database with any changes that user made to their note.
    public boolean updateRow(String rowID, String newText, String newHash){
        ContentValues upDateRow = new ContentValues();
        upDateRow.put(NOTE_TEXT, newText);
        upDateRow.put(HASH_TAGS, newHash);
        try{
            db.update(DB_TABLE, upDateRow, rowID, null);
            return true;
        }
        catch(Exception e){
            Log.e(e.toString(), "Error updatingRow");
            return false;
        }
    }
}
