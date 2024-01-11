package com.example.practical6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button ans_btn;
    String feelings;
    TextView feelingsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feelingsTxt = findViewById(R.id.text_day);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("How are you feeling today?")
                .setTitle("Feelings today")
                .setPositiveButton("Good", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        feelings = "Good";
                        feelingsTxt.setText(feelings);
                        feelingsTxt.setTextColor(0xFF02e107);

                    }
                })
                .setNegativeButton("Bad", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        feelings = "Bad";
                        feelingsTxt.setText(feelings);
                        feelingsTxt.setTextColor(0xFFbe3228);

                    }
                });

        AlertDialog alert = builder.create();

        ans_btn = (Button) findViewById(R.id.button);

        ans_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();

            }
        });

    }
}