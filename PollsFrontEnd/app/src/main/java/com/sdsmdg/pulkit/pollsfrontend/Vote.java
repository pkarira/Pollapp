package com.sdsmdg.pulkit.pollsfrontend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pulkit on 22/9/17.
 */

public class Vote {
    private String question_id, choice_id;

    Vote(String question_id, String choice_id) {
        this.question_id = question_id;
        this.choice_id = choice_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(String choice_id) {
        this.choice_id = choice_id;
    }
}
