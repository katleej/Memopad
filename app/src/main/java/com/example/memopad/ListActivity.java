package com.example.memopad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Katniss Lee
 */
public class ListActivity extends AppCompatActivity {

    private ArrayList<String> _messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (savedInstanceState != null) {
            _messages = savedInstanceState.getStringArrayList("messages");
        }
        //Set my listview with the received message
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                ListActivity.this, android.R.layout.simple_list_item_1, _messages);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);
        receiveMessage();
        }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("messages", _messages);
    }
    public void receiveMessage() {
        Intent intent = getIntent();
        String content = intent.getStringExtra("memo message");
        _messages.add(content);
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
        startActivity(intent);
    }
}
