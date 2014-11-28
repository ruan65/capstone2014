package com.ruan.managecare.data.mongodb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AnswersData {

    private Date timeStamp;
    private Map<String, String> answers = new HashMap<>();

    public void addQuestionAndAnswer(String question, String answer) {
        answers.put(question, answer);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "AnswersData{" +
                "timeStamp=" + timeStamp +
                ", answers=" + answers +
                '}';
    }
}
