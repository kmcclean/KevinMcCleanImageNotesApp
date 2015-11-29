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
    protected static final String DB_TABLE = "savedNotes";

    protected static final String NOTE_ID = "NOTE_ID";
    protected static final String HASH_TAGS = "hash_tags";
    protected static final String NOTE_TEXT = "note_text";
    protected static final String NOTE_TYPE = "image";

    private static final String DBTAG = "DatabaseManager";
    private static final String SQLTAG = "SQLHelper";

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
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
            String createTable = "CREATE TABLE " + DB_TABLE + " (" + NOTE_ID + " TEXT, "  + NOTE_TEXT + " TEXT, " + HASH_TAGS + " TEXT);";//, " + NOTE_TYPE + " INTEGER);";
            db.execSQL(createTable);
            db.execSQL("DELETE FROM " + DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQLTAG, "Upgrade table - drop and recreate it");
        }
    }

    public ArrayList<Notes> fetchAll() {
        String cols[] = {NOTE_ID, HASH_TAGS, NOTE_TEXT};//, NOTE_TYPE};
        Cursor cursor = db.query(DB_TABLE, cols, null, null, null, null, HASH_TAGS);
        ArrayList<String> notes = new ArrayList<>();
        ArrayList<Notes> notesList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Notes newNote = new Notes(cursor.getString(0), cursor.getString(1), cursor.getString(2), false);
            notes.add(cursor.getString(2));
            notesList.add(newNote);
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return notesList;
    }

    public boolean addRow(String rowID, String hashTags, String noteText, Integer isPicture) {
        ContentValues newRow = new ContentValues();
        newRow.put(NOTE_ID, rowID);
        newRow.put(HASH_TAGS, hashTags);
        newRow.put(NOTE_TEXT, noteText);
        //newRow.put(NOTE_TYPE, isPicture);
        try {
            db.insertOrThrow(DB_TABLE, null, newRow);
            return true;
        } catch (Exception e) {
            Log.e(DBTAG, "Error inserting new data into table", e);
            return false;
        }
    }

    public boolean deleteRow(String rowID){
        ContentValues deleteRow = new ContentValues();
        deleteRow.put("removedRow", rowID);
        try{
            db.delete(DB_TABLE, deleteRow.getAsString("removedRow"), null);
            return true;
        }
        catch (Exception e){
            Log.e(e.toString(), "Error deleting row.");
            return false;
        }
    }

    public boolean updateRow(String rowID){
        ContentValues upDateRow = new ContentValues();
        upDateRow.put("updatedColumn", HASH_TAGS);
        try{
            db.update(DB_TABLE, upDateRow, rowID, null);
            return true;
        }
        catch(Exception e){
            Log.e(e.toString(), "Error updatingRow");
            return false;
        }
    }

/*    public String getPhonesForName(String name) {
        String cols[] = {phoneCol};
        String whereClause = HASH_TAGS + "=" + name + "'";
        Cursor cursor = db.query(DB_TABLE, cols, whereClause, null, null, null, null);
        cursor.moveToFirst();
        String phoneNumbers = "";
        while (!cursor.isLast()) {
            phoneNumbers = phoneNumbers + cursor.getLong(0) + "\n";
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return phoneNumbers;
    }*/
}
