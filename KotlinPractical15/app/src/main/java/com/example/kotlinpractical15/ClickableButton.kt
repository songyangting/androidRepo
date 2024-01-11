package com.example.kotlinpractical15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ClickableButton : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clickable_button)

        val button = findViewById<Button>(R.id.button_click)
        val text_result = findViewById<TextView>(R.id.text_result)

        button.setOnClickListener {
            text_result.text = "Button Clicked"
        }

    }
}