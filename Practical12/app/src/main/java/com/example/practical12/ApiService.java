package com.example.practical12;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("random")
    Call<Duck> getDuck();
}
