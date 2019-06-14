package com.example.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MemopadDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "memopad";
    private static final int DB_VERSION = 1;
    public static final String MEMO_TABLE = "MEMO";

    private ArrayList<String> messages = Message.messages;

    MemopadDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MEMO_TABLE + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "MESSAGE TEXT);");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Cursor getMessages(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MEMO_TABLE, null);
        return cursor;
    }

    public Cursor getSortedMessages(String string){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MEMO_TABLE, new String[] {"MESSAGE"},
                "MESSAGE = ?", new String[] {string}, null, null, null);
        return cursor;
    }

    public boolean insertMemo(String memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues memoValues = new ContentValues();
        memoValues.put("MESSAGE", memo);
        long result = db.insert("MEMO", null, memoValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteMemo(String string) {
        this.getWritableDatabase().delete(MEMO_TABLE, "MESSAGE = ?",
                new String[] {string});
        return true;
    }

}

