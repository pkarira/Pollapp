package com.sdsmdg.pulkit.pollsfrontend;

/**
 * Created by pulkit on 21/8/17.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Question {

        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("choices")
        @Expose
        private List<Choice> choices = null;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

    }
