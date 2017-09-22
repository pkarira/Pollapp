package com.sdsmdg.pulkit.pollsfrontend;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {
    Button button1, button2, results, logout;
    public static String token;
    boolean isLoggedIn = false;
    EditText name, pass, email;
    Register register, login;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button3);
        results = (Button) findViewById(R.id.results);
        name = (EditText) findViewById(R.id.editText4);
        pass = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.editText6);
        logout = (Button) findViewById(R.id.logout);
        SharedPreferences logIn = getSharedPreferences("LogIn", 0);
        isLoggedIn = logIn.getBoolean("isloggedin", false);
        token = logIn.getString("Key", null);
        if (isLoggedIn == true) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment newFragment = new QuestionFragment();
            transaction.replace(R.id.fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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
                            SharedPreferences settings = getSharedPreferences("login", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isloggedin", true);
                            editor.putString("token", token);
                            editor.commit();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getToken(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                    }
                });
            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.Factory.getInstance().getResult(token).enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(Call<Results> call, retrofit2.Response<Results> response) {
                        ResultArray[] resultArray = response.body().getResults();
                        int size = resultArray.length;
                        String s = null;
                        for (int i = 0; i < size; i++) {
                            s += "question ";
                            s += resultArray[i].getQuestions().toString() + "\n";
                            s += "votes:" + "\n";
                            s += resultArray[i].getChoices()[0].getText().toString() + "  " + resultArray[i].getChoices()[0].getVotes().toString() + "\n";
                            s += resultArray[i].getChoices()[1].getText().toString() + "  " + resultArray[i].getChoices()[1].getVotes().toString() + "\n";
                        }
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Results> call, Throwable t) {

                    }
                });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.Factory.getInstance().logout(token).enqueue(new Callback<String>() {
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
