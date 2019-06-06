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

public class ListActivity extends AppCompatActivity {

    private ArrayList<String> _messages = new ArrayList<String>();
    private ListView listView = findViewById(R.id.listview);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        receiveMessage();
        //Set my listview with the received message
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                ListActivity.this, android.R.layout.simple_list_item_1, _messages);
        listView.setAdapter(arrayAdapter);
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
