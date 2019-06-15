package com.example.memopad;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Katniss Lee
 */
public class WriteNewActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    int TEXT_POSITION = -1;
    SQLiteOpenHelper memopadData;
    SQLiteDatabase db;
    String _message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new);
        //Set up the same toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize db
        memopadData = new MemopadDatabaseHelper(this);
        db = memopadData.getWritableDatabase();

        //if we are opening up previous message, add that text to the screen
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            _message = intent.getStringExtra("message");
            TEXT_POSITION = intent.getIntExtra("position", -1);
            EditText layout = (EditText) findViewById(R.id.message);
            layout.setText(_message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trash_memo:
                alert();
                return true;

            case R.id.share_memo:
                EditText editText = (EditText) findViewById(R.id.message);
                String messageText = editText.getText().toString();
                setShareActionIntent(messageText);
                return true;

            case R.id.save_memo:
                onClickSave(findViewById(R.id.save));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickSave(View view) {
        CharSequence text = "Your message has been saved.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

        if (TEXT_POSITION != -1) {
            ((MemopadDatabaseHelper) memopadData).deleteMemo(_message);
        }

        EditText message = (EditText) findViewById(R.id.message);
        String messageText = message.getText().toString();

        boolean insertData = ((MemopadDatabaseHelper) memopadData).insertMemo(messageText);
        Intent intent = new Intent(WriteNewActivity.this, ListActivity.class);
        startActivity(intent);
    }


    public void onClickDelete(View view) {
        alert();
    }

    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MemopadDatabaseHelper) memopadData).deleteMemo(_message);
                        CharSequence text = "Your message has been deleted.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(WriteNewActivity.this, text, duration);

                        toast.show();

                        Intent intent = new Intent(WriteNewActivity.this, ListActivity.class);
                        startActivity(intent);
                    }
                }
        );

        AlertDialog alert = builder.create();
        alert.show();
    }

}
