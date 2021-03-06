package com.sdsmdg.pulkit.pollsfrontend;

import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pulkit on 26/3/17.
 */

public interface api {
    String baseurl = " http://10.42.0.1:8080/";

    @POST("polls/register")
    Call<String> register(@Body Register reg);

    @POST("polls/login")
    Call<Token> login(@Body Register reg);

    @POST("polls/questions")
    Call<Questions> getQuestions(@Header("Authorization") String token);

    @POST("polls/vote")
    Call<String> setVote(@Header("Authorization") String token,@Body Vote v);

    @POST("polls/results")
    Call<Results> getResult(@Header("Authorization") String token);

    @POST("polls/logout")
    Call<String> logout(@Header("Authorization") String token);

    class Factory {
        public static api services = null;

        public static api getInstance() {
            if (services == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(baseurl).build();
                services = retrofit.create(api.class);
                return services;
            } else {
                return services;
            }
        }
    }
}
