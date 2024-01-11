package com.example.practical5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button basicBtn, toastBtn;
    NotificationChannel channel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basicBtn = findViewById(R.id.basic_btn);
        toastBtn = findViewById(R.id.toast_btn);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("Channel", "notification1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel_desc");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Channel")
                        .setContentTitle("New Notification")
                        .setContentText("This is an example of a notification ~~")
                        .setSmallIcon(R.mipmap.ic_launcher_round);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

                notificationManagerCompat.notify(1, builder.build());

            }
        });

        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "New toast notification!", Toast.LENGTH_SHORT).show();
            }
        });



    }

}