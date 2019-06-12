package com.example.memopad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Katniss Lee
 */
public class WriteNewActivity extends AppCompatActivity {

    int TEXT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new);
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String message = intent.getStringExtra("message");
            TEXT_POSITION = intent.getIntExtra("position", -1);
            EditText layout = (EditText) findViewById(R.id.message);
            layout.setText(message);
        }
    }

    public void onClickSave(View view) {
        CharSequence text = "Your message has been saved.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

        EditText message = (EditText) findViewById(R.id.message);
        String messageText = message.getText().toString();
        Message.messages.add(0, messageText);
        Intent intent = new Intent(WriteNewActivity.this, ListActivity.class);
        startActivity(intent);
    }

    public void onClickShare(View view) {
        EditText message2 = (EditText) findViewById(R.id.message);
        String messageText2 = message2.getText().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra("shared message", messageText2);
        startActivity(shareIntent);
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
