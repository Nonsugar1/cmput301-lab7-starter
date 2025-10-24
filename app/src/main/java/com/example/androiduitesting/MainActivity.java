package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    EditText newName;
    LinearLayout nameField;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        LinearLayout nameField = findViewById(R.id.field_nameEntry);
        EditText newName = findViewById(R.id.editText_name);
        ListView cityList = findViewById(R.id.city_list);

        // Data source for the ListView
        ArrayList<String> dataList = new ArrayList<>();

        // Use a built-in row layout that contains a single TextView
        ArrayAdapter<String> cityAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        cityList.setAdapter(cityAdapter);

        // Add button: show input field
        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> nameField.setVisibility(View.VISIBLE));

        // Confirm button: add the typed city to the adapter
        Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(v -> {
            String cityName = newName.getText().toString();
            cityAdapter.add(cityName);
            newName.getText().clear();
            nameField.setVisibility(View.INVISIBLE);
        });

        // Clear button: clear all items
        Button clearButton = findViewById(R.id.button_clear);
        clearButton.setOnClickListener(v -> cityAdapter.clear());

        // >>> New: open ShowActivity when a list item is clicked
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String city = cityAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
            intent.putExtra(ShowActivity.EXTRA_CITY, city);
            startActivity(intent);
        });
    }
}