package com.example.androidphpmysql.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
    })
    @FormUrlEncoded
    @POST("registerUser.php")
    Call<String> loginAPI(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );
}
