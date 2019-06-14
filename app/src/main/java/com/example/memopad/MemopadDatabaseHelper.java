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

    public ArrayList<String> getMessages(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("MEMO_TABLE", new String[]{"MESSAGE"}, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ArrayList<String> messages = new ArrayList<>();
            messages.add(cursor.getString(cursor.getColumnIndex("MESSAGE")));
        }
        return messages;
    }

    public void insertMemo(SQLiteDatabase db, String memo) {
        ContentValues memoValues = new ContentValues();
        memoValues.put("MESSAGE", memo);
        db.insert("MEMO", null, memoValues);
    }
}
