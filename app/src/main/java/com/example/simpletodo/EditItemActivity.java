package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.simpletodo.MainActivity.ITEM_POSITION;
import static com.example.simpletodo.MainActivity.ITEM_TEXT;

public class EditItemActivity extends AppCompatActivity {

    /* variables */
    EditText etItemText; //track edit text
    int position; //position of edit item


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //resolve edit text from layout
        etItemText = (EditText) findViewById(R.id.etItemText);

        //set text value
        etItemText.setText(getIntent().getStringExtra(ITEM_TEXT));

        //update the position
        position = getIntent().getIntExtra(ITEM_POSITION, 0);

        //update title bar
        getSupportActionBar().setTitle("Edit Item");
    }

    //handler for save button
    public void onSaveItem(View v) {
        //prepare for intent
        Intent intent = new Intent();

        //pass updated item and position
        intent.putExtra(ITEM_TEXT, etItemText.getText().toString());
        intent.putExtra(ITEM_POSITION, position);

        //set intent as result of activity
        setResult(RESULT_OK, intent);

        //close activity and go to main
        finish();
    }
}
