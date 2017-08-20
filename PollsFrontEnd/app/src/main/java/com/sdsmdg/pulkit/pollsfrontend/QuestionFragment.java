package com.sdsmdg.pulkit.pollsfrontend;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private Questions questions;
    int c=0;
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
        api.Factory.getInstance().getQuestions(MainActivity.token).enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, retrofit2.Response<Questions> response) {
                questions=response.body();
                Toast.makeText(getContext(),questions+" ",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_question, container, false);
        final TextView textView=(TextView)view.findViewById(R.id.question);
        final Button choice1=(Button)view.findViewById(R.id.choice);
        final Button choice2=(Button)view.findViewById(R.id.choice2);
        Button next =(Button)view.findViewById(R.id.next);
        textView.setText(questions.getData().getQuestion().get(0).getQuestion());
        choice1.setText(questions.getData().getQuestion().get(0).getChoices().get(0).getText());
        choice2.setText(questions.getData().getQuestion().get(0).getChoices().get(1).getText());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c<questions.getData().getQuestion().size())
                {
                    c++;
                    textView.setText(questions.getData().getQuestion().get(c).getQuestion());
                    choice1.setText(questions.getData().getQuestion().get(c).getChoices().get(0).getText());
                    choice2.setText(questions.getData().getQuestion().get(c).getChoices().get(1).getText());
                }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}