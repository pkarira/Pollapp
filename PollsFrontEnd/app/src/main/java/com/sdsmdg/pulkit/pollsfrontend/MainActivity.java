package com.sdsmdg.pulkit.pollsfrontend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        final Register register =new Register("p","p","p");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.Factory.getInstance().register(register).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
            }
        });
}
}
