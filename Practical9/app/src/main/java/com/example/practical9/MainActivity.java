package com.example.practical9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ConstraintLayout layout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        btn = findViewById(R.id.btn);

        sharedPreferences = getSharedPreferences("ColorSchemePrefs", MODE_PRIVATE);
        // a file is created with the name "ColorSchemePrefs"

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColorScheme();
            }
        });

        int currentColour = sharedPreferences.getInt("backgroundColour", Color.WHITE);
        layout.setBackgroundColor(currentColour);
    }

    public void changeColorScheme() {
        int currentColour = sharedPreferences.getInt("backgroundColour", Color.WHITE);

        int bgcolour1 = Color.parseColor("#FFEED9");
        int bgcolour2 = Color.parseColor("#E0F4FF");

        if (currentColour == bgcolour1) {
            currentColour = bgcolour2;
        } else {
            currentColour = bgcolour1;
        }

        layout.setBackgroundColor(currentColour);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("backgroundColour", currentColour); //identifying value
        editor.apply(); //to store the value

    }


}