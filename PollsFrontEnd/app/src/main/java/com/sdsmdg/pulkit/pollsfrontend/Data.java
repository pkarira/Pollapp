package com.sdsmdg.pulkit.pollsfrontend;

/**
 * Created by pulkit on 21/8/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("question")
    @Expose
    private List<Question> question = null;

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
