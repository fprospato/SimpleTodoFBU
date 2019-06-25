package com.example.simpletodo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items; //for the model
    ArrayAdapter<String> itemsAdapter; //wires the model list to the view
    ListView lvItems; //instance of the list view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize everything
        readItems(); //gets the items
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items); //(reference to the activity, type of item it will wrap, item list)
        lvItems = (ListView) findViewById(R.id.lvitems);
        lvItems.setAdapter(itemsAdapter); //wire to the list view

        //fake data
//        items.add("item 1");
//        items.add("item 2");
//        items.add("item 3");

        //set long press
        setupListViewListener();
    }


    //add item to list
    public void onAddItem(View v) {
        //get text
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem); //local edit text variable
        String itemText = etNewItem.getText().toString(); //get etNewItem text

        //add to todo list
        itemsAdapter.add(itemText);
        etNewItem.setText("");

        //change what we're saving
        writeItems();

        //display a toast (brief notification to user that operation worked)
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    //remove item from list
    public void setupListViewListener() {
        Log.i("MainActivity", "Setting up listener on list view");

        //set method to check for long press
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MainActivity", "Item removed from list at position: " + i);

                //remove for array and update the view
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();

                //change what we're saving
                writeItems();

                return true;
            }
        });
    }


    //get the data
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt"); //(directory, name)
    }


    //read items
    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            //set tp empty array if error
            Log.e("MainActivity", "Error reading file", e);
            items = new ArrayList<>();
        }
    }


    //write items
    private void  writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file", e);
        }
    }
}
