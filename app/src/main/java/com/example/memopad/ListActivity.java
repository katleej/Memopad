package com.example.memopad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * @author Katniss Lee
 */
public class ListActivity extends AppCompatActivity {

    private ArrayList<String> dictionary = new ArrayList<String>();
    private MemopadDatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Cursor cursorHidden;
    private String message;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Set my toolbar and enable Up
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Pull up my listview
        ListView listView = findViewById(R.id.listview);
        databaseHelper = new MemopadDatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        cursor = db.query("MEMO", new String[]{"_id", "MESSAGE"},
                null, null, null, null, "_id DESC");
        final SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"MESSAGE"}, new int[]{android.R.id.text1}, 0);
        listView.setAdapter(simpleCursorAdapter);

        //Set my listview with the received message
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        message = simpleCursorAdapter.getCursor().getString(1);
                        Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
                        intent.putExtra("message", message);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                };
        listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new_memo:
                Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
                startActivity(intent);
                return true;
            case R.id.trash_memo_main:
                Intent intent2 = new Intent(ListActivity.this, DeleteOptionActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
        startActivity(intent);
    }

    public void onClickSearch(View view) {
        ListView listView = findViewById(R.id.listview);
        listView.setVisibility(View.INVISIBLE);

        ListView hiddenView = findViewById(R.id.hiddenlist);
        hiddenView.setVisibility(View.VISIBLE);

        EditText editText = (EditText) findViewById(R.id.search_tool);
        String keyword = editText.getText().toString();

        //creating dictionary
        dictionary = new ArrayList<String>();
        cursor = db.query("MEMO", new String[]{"_id", "MESSAGE"},
                null, null, null, null, "_id DESC");
        while (cursor.moveToNext()) {
            String message = cursor.getString(1);
            if (message.contains(keyword)) {
                dictionary.add(0, message);
            }
            ArrayAdapter<String> dictionaryAdapter = new ArrayAdapter<String>(
                    ListActivity.this, android.R.layout.simple_list_item_1, dictionary);
            hiddenView.setAdapter(dictionaryAdapter);

            AdapterView.OnItemClickListener hiddenItemClickListener =
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String message = dictionary.get(position);
                            Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
                            intent.putExtra("message", message);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    };
            hiddenView.setOnItemClickListener(hiddenItemClickListener);

        }
    }
}
