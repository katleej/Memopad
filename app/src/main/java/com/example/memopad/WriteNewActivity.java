package com.example.memopad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @author Katniss Lee
 */
public class WriteNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new);
    }

    public void onClickSave(View view) {
        CharSequence text = "Your message has been saved.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

        EditText message = (EditText) findViewById(R.id.message);
        String messageText = message.getText().toString();
        Intent intent = new Intent(WriteNewActivity.this, ListActivity.class);
        intent.putExtra("memo message", messageText);
        startActivity(intent);
    }
}
