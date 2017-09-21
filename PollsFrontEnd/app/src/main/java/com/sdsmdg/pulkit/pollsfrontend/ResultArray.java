package com.sdsmdg.pulkit.pollsfrontend;

/**
 * Created by pulkit on 22/9/17.
 */

public class ResultArray {
    private String id;

    private Choice[] choices;

    private String questions;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Choice[] getChoices ()
    {
        return choices;
    }

    public void setChoices (Choice[] choices)
    {
        this.choices = choices;
    }

    public String getQuestions ()
    {
        return questions;
    }

    public void setQuestions (String questions)
    {
        this.questions = questions;
    }

}
