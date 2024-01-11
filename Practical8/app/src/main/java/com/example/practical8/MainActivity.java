package com.example.practical8;

import static android.widget.GridLayout.HORIZONTAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button addTaskBtn;
    EditText editTask;
    RecyclerView taskRecycler;
    ArrayList<Task> taskList = new ArrayList<Task>();
    MyCustomAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTaskBtn = (Button) findViewById(R.id.btn_addTask);
        editTask = (EditText) findViewById(R.id.editText_task);
        taskRecycler = (RecyclerView) findViewById(R.id.recyclerView);

        // doesnt work without linearlayoutmanager because onCreateViewHolder requires viewtype?
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); //LinearLayoutManager.HORIZONTAL, false);
        taskRecycler.setLayoutManager(linearLayoutManager);


        // adapter auto updates when taskList changes, no need to create new instance everytime you update the list
        myAdapter = new MyCustomAdapter(getApplicationContext(), taskList);

        taskRecycler.setItemAnimator(new DefaultItemAnimator());
        taskRecycler.setAdapter(myAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTxt = editTask.getText().toString();

                Task task = new Task(taskTxt);
                taskList.add(0, task);
                editTask.setText("");

                myAdapter.notifyDataSetChanged(); //to change adapter everytime dataset is changed, no need to keep setting adapter

            }
        });



    }
}