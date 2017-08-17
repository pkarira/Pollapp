package com.sdsmdg.pulkit.pollsfrontend;

import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    class Factory {
        public static api services = null;

        public static api getInstance() {
            if (services == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseurl).build();
                services = retrofit.create(api.class);
                return services;
            } else {
                return services;
            }
        }
    }
}
