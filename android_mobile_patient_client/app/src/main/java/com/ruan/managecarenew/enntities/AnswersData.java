package com.ruan.managecarenew.enntities;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnswersData {

    private Date timeStamp;
    private Map<String, String> answers = new HashMap<String, String>();

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

}
