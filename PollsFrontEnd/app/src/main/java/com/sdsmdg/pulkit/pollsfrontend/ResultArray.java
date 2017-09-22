package com.sdsmdg.pulkit.pollsfrontend;

/**
 * Created by pulkit on 22/9/17.
 */

public class ResultArray {
    private String id;

    private Choice[] choice;

    private String question;

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
        return choice;
    }

    public void setChoices (Choice[] choices)
    {
        this.choice = choices;
    }

    public String getQuestions ()
    {
        return question;
    }

    public void setQuestions (String question)
    {
        this.question = question;
    }

}
