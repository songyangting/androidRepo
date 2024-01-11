package com.example.practical2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button resetBtn, minusBtn, addBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetBtn = (Button) findViewById(R.id.resetBtn);
        minusBtn = (Button) findViewById(R.id.minusBtn);
        addBtn = (Button) findViewById(R.id.addBtn);

        textView = (TextView) findViewById(R.id.textView);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(textView.getText().toString());
                textView.setText(""+ (count+1));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(textView.getText().toString());
                textView.setText(""+(count-1));
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(""+0);
            }
        });




    }
}