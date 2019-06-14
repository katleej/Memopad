package com.example.memopad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


/**
 * @author Katniss Lee
 */
public class ListActivity extends AppCompatActivity {

    private ArrayList<String> _messages = new ArrayList<String>();
    private MemopadDatabaseHelper myDB;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Set my toolbar and enable Up
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Pull up my listview
        ListView listView = findViewById(R.id.listview);
        myDB = new MemopadDatabaseHelper(this);
        cursor = myDB.getMessages();

        while(cursor.moveToNext()) {
            _messages.add(0, cursor.getString(1));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    ListActivity.this, android.R.layout.simple_list_item_1, _messages);
            listView.setAdapter(arrayAdapter);
        }


        //Set my listview with the received message
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String message = _messages.get(position);
                        Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
                        intent.putExtra("message", message);
                        intent.putExtra("position", _messages.size() - position);
                        startActivity(intent);
                    }
                };
        listView.setOnItemClickListener(itemClickListener);
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
        EditText editText = (EditText) findViewById(R.id.search_tool);
        String keyword = editText.getText().toString();
        myDB = new MemopadDatabaseHelper(this);
        cursor = myDB.getSortedMessages(keyword);
        ListView listView = findViewById(R.id.listview);
        while(cursor.moveToNext()) {
            _messages.add(0, cursor.getString(1));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    ListActivity.this, android.R.layout.simple_list_item_1, _messages);
            listView.setAdapter(arrayAdapter);
        }


    }
}
