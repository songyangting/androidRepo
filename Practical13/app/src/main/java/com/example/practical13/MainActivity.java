package com.example.practical13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends AppCompatActivity {

    EditText englishTxt;
    TextView chinese;
    Button translate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        englishTxt = findViewById(R.id.editText);
        chinese = findViewById(R.id.textView);
        translate_btn = findViewById(R.id.button);


        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.CHINESE)
                .build();

        Translator englishChineseTranslator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        englishChineseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(

                // Model downloaded successfully. Now translation can be done offline
                unused -> {
                    System.out.println("Model downloaded successfully");
                }
        ).addOnFailureListener(
                // Model couldn't be downloaded or other error. Handle appropriately.
                e -> {
                    System.out.println("Error downloading model");}
        );

        translate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english_txt = englishTxt.getText().toString();

                englishChineseTranslator.translate(english_txt).addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String s) {
                                String chinese_txt = s;
                                chinese.setText(chinese_txt);
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failure");
                    }
                });
            }
        });


    }
}