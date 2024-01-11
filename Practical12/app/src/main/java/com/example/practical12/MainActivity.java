package com.example.practical12;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ImageView imgView;
    ConstraintLayout loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        imgView = findViewById(R.id.duck_image);
        loadingView = findViewById(R.id.loading_view);

        imgView.setVisibility(GONE);
        loadingView.setVisibility(GONE);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://random-d.uk/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiService apiService = retrofit.create(ApiService.class);

        Callback<Duck> duckImgCallback = new Callback<Duck>() {
            @Override
            public void onResponse(Call<Duck> call, Response<Duck> response) {
                if (response.isSuccessful()) {

                    Duck duck = response.body();
                    String imgUrl = duck.getUrl();
                    //System.out.println(duck.getURL());
                    Picasso.with(MainActivity.this).load(imgUrl).into(imgView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            imgView.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(GONE);

                        }

                        @Override
                        public void onError() {
                            Log.d("Image", "Image loading error");

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Duck> call, Throwable t) {
                Log.d("API",t.getMessage());

            }
        };


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.setVisibility(View.VISIBLE);
                imgView.setVisibility(GONE);

                Call<Duck> call = apiService.getDuck();
                call.enqueue(duckImgCallback);

            }
        });





    }
}