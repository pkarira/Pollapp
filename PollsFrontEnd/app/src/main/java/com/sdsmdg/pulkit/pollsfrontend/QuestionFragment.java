package com.sdsmdg.pulkit.pollsfrontend;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private Questions questions;
    int c = 0;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_question, container, false);
        final TextView textView = (TextView) view.findViewById(R.id.question);
        final Button choice1 = (Button) view.findViewById(R.id.choice);
        final Button choice2 = (Button) view.findViewById(R.id.choice2);
        final Button next = (Button) view.findViewById(R.id.next);
        api.Factory.getInstance().getQuestions(MainActivity.token).enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, retrofit2.Response<Questions> response) {
                questions = response.body();
                Toast.makeText(getActivity(), response.body().getData().getQuestion().get(0).getQuestion().toString(), Toast.LENGTH_SHORT).show();
                textView.setText(questions.getData().getQuestion().get(0).getQuestion());
                choice1.setText(questions.getData().getQuestion().get(0).getChoices().get(0).getText().toString());
                choice2.setText(questions.getData().getQuestion().get(0).getChoices().get(1).getText().toString());
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (c < questions.getData().getQuestion().size()) {
                            c++;
                            List<Choice> choices=questions.getData().getQuestion().get(c).getChoices();
                            choice1.setVisibility(View.VISIBLE);
                            choice2.setVisibility(View.VISIBLE);
                            textView.setText(questions.getData().getQuestion().get(c).getQuestion());
                            choice1.setText(choices.get(0).getText());
                            choice2.setText(choices.get(1).getText());
                        }
                    }
                });
                choice1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vote vote = new Vote(questions.getData().getQuestion().get(c).getId(), questions.getData().getQuestion().get(c).getChoices().get(0).getId());
                        choice1.setVisibility(View.INVISIBLE);
                        choice2.setVisibility(View.INVISIBLE);
                        api.Factory.getInstance().setVote(MainActivity.token,vote).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                if(response.body().toString()=="Done")
                                Toast.makeText(getActivity(),"Thanks for Voting",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(),response.body().toString(),Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                });
                choice2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vote vote = new Vote(questions.getData().getQuestion().get(c).getId(), questions.getData().getQuestion().get(c).getChoices().get(1).getId());
                        choice1.setVisibility(View.INVISIBLE);
                        choice2.setVisibility(View.INVISIBLE);
                        api.Factory.getInstance().setVote(MainActivity.token,vote).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                if(response.body().toString()=="Done")
                                    Toast.makeText(getActivity(),"Thanks for Voting",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(),response.body().toString(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
