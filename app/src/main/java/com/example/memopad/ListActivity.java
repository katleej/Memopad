package com.example.memopad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * @author Katniss Lee
 */
public class ListActivity extends AppCompatActivity {

    private ArrayList<String> _messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Set my listview with the received message
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                ListActivity.this, android.R.layout.simple_list_item_1, Message.messages);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String message = Message.messages.get(position);
                        Message.messages.remove(position);
                        Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
                        intent.putExtra("message", message);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                };
        listView.setOnItemClickListener(itemClickListener);
        }

    public void onClickAdd(View view) {
        Intent intent = new Intent(ListActivity.this, WriteNewActivity.class);
        startActivity(intent);
    }
}
