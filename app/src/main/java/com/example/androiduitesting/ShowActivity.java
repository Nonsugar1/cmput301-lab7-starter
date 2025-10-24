package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that displays the clicked city name and a BACK button.
 */
public class ShowActivity extends AppCompatActivity {

    // Intent extra key used by MainActivity to pass the city name
    public static final String EXTRA_CITY = "extra_city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityText = findViewById(R.id.text_city);
        Button back = findViewById(R.id.button_back);

        // Read the city name from the launching intent
        String city = getIntent().getStringExtra(EXTRA_CITY);
        cityText.setText(city == null ? "" : city);

        // Finish this activity and go back to MainActivity
        back.setOnClickListener(v -> finish());
    }
}
