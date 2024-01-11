package com.example.practical1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button followBtn, messageBtn;
    int followColor = 0xFF6200EE;
    int unfollowColor = 0xFFBB86FC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User newUser = new User("John Doe", "Some person", 1, false);

        followBtn = (Button)findViewById(R.id.btn_follow);
        messageBtn = (Button)findViewById(R.id.btn_msg);
        //set initial state
        followBtn.setText(newUser.followed ? "UNFOLLOW" : "FOLLOW");
        followBtn.setBackgroundColor(newUser.followed ? unfollowColor : followColor);


        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser.followed = !newUser.followed;
                followBtn.setText(newUser.followed ? "UNFOLLOW" : "FOLLOW");
                followBtn.setBackgroundColor(newUser.followed ? unfollowColor : followColor);
//                if (!newUser.followed) {
//                    //change to unfollow
//                    newUser.followed = true;
//
//                    followBtn.setText("UNFOLLOW");
//                    followBtn.setBackgroundColor(unfollowColor);
//
//                } else {
//                    // change to follow
//                    newUser.followed = false;
//
//                    followBtn.setText("FOLLOW");
//                    followBtn.setBackgroundColor(followColor);
//
//                }
            }
        });


    }
}