package com.example.kotlinpractical14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemList = listOf(Item("Item 1"), Item("Item 2"), Item("Item 3"), Item("Item 4"), Item("Item 5"))

        val adapter = MyAdapter(itemList)

        recyclerView.adapter = adapter

    }
}