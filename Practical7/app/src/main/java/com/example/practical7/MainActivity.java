package com.example.practical7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button enterBtn, startBtn;
    Button btn;
    TextView timerTxt;
    EditText secondsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterBtn = (Button) findViewById(R.id.enter_button);
        startBtn = (Button) findViewById(R.id.start_button);
        timerTxt = (TextView) findViewById(R.id.timer);
        secondsTxt = (EditText) findViewById(R.id.editText);



        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int seconds = Integer.parseInt(secondsTxt.getText().toString());

                // set initial value of timer
                timerTxt.setText(""+seconds);

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start timer

                int seconds =Integer.parseInt(timerTxt.getText().toString());


                CountDownTimer countdown = new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerTxt.setText(""+ (millisUntilFinished/1000));

                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();

                    }
                };

                countdown.start();


            }
        });


        btn = findViewById(R.id.button);
        Intent intent = new Intent(this, TimerActivity.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
}