package com.sdsmdg.pulkit.pollsfrontend;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    public static String token;
    EditText name, pass, email;
    Register register, login;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button3);
        name = (EditText) findViewById(R.id.editText4);
        pass = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.editText6);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register = new Register(name.getText().toString(), pass.getText().toString(), email.getText().toString());
                api.Factory.getInstance().register(register).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "asf", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                login = new Register(name.getText().toString(), pass.getText().toString(), email.getText().toString());
                api.Factory.getInstance().login(login).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
                        if (response.body().getStatus() == 1) {
                            token = "Token " + response.body().getToken();
                            Toast.makeText(getApplicationContext(), response.body().getToken(), Toast.LENGTH_LONG).show();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Fragment newFragment = new QuestionFragment();
                            transaction.replace(R.id.fragment, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else
                        {
                            Toast.makeText(getApplicationContext(),response.body().getToken(),Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                    }
                });
            }
        });
    }
}
