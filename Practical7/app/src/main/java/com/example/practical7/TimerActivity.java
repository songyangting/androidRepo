package com.example.practical7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    TextView timerTxt;
    Button btn, enterBtn;

    EditText secondsTxt;
    int count = 0;
    int maxseconds;
    boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer2);

        timerTxt = findViewById(R.id.timer_txt);
        btn = findViewById(R.id.run_btn);
        enterBtn = findViewById(R.id.enter_btn);
        secondsTxt = findViewById(R.id.seconds_txt);
        maxseconds = 0;

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxseconds = Integer.parseInt(secondsTxt.getText().toString());
//                secondsTxt.setText("");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maxseconds == 0) {
                    Log.i("maxsecond_value", ""+maxseconds);
                    Toast.makeText(TimerActivity.this, "You have not entered a value for timer", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("maxsecond_value", ""+maxseconds);
                    runTimer();
                }

            }
        });



    }

    private void runTimer() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // task to be done at fixed rate
                // ONLY app logic!!
                // if you update UI here, errors may occur
                count++;
                Log.d("Timmmm","count" + count + ": max" + maxseconds);

                //technicall is finished
                if (count >= maxseconds && !isFinished) {
                    timer.cancel();
                    isFinished = true;
                    maxseconds = 0;
                    count = 0;

                }

                // update UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerTxt.setText("" + count);
                        if (isFinished){
                            Toast.makeText(TimerActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                            timerTxt.setText("0");
                            secondsTxt.setText("");
                            isFinished = false;
                        }
                    }
                });


            }

        }, 1000, 1000);
    }


}

