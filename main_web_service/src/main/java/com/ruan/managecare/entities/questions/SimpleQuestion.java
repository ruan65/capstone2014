package com.ruan.managecare.entities.questions;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SimpleQuestion {

    @Id
    private String _id;

    private String category;
    private String subCategory;
    private String code;
    private String text;

    private boolean answered;
    private boolean yesAnswer;
    private boolean timeMarkNeeded;
    private boolean clarifyingQuestionsNeeded;

    private List<SimpleQuestion> clarifyingQuestions = new LinkedList<>();

    private Date timeMark;
    private Date answerTimeStamp;

    public SimpleQuestion() {}

    public void addClarifyingQuestion(SimpleQuestion q) {
        clarifyingQuestions.add(q);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isYesAnswer() {
        return yesAnswer;
    }

    public void setYesAnswer(boolean yesAnswer) {
        this.yesAnswer = yesAnswer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Date getAnswerTimeStamp() {
        return answerTimeStamp;
    }

    public void setAnswerTimeStamp(Date answerTimeStamp) {
        this.answerTimeStamp = answerTimeStamp;
    }

    public boolean isTimeMarkNeeded() {
        return timeMarkNeeded;
    }

    public void setTimeMarkNeeded(boolean timeMarkNeeded) {
        this.timeMarkNeeded = timeMarkNeeded;
    }

    public Date getTimeMark() {
        return timeMark;
    }

    public void setTimeMark(Date timeMark) {
        this.timeMark = timeMark;
    }

    public boolean isClarifyingQuestionsNeeded() {
        return clarifyingQuestionsNeeded;
    }

    public void setClarifyingQuestionsNeeded(boolean clarifyingQuestionsNeeded) {
        this.clarifyingQuestionsNeeded = clarifyingQuestionsNeeded;
    }

    public List<SimpleQuestion> getClarifyingQuestions() {
        return clarifyingQuestions;
    }

    public void setClarifyingQuestions(List<SimpleQuestion> clarifyingQuestions) {
        this.clarifyingQuestions = clarifyingQuestions;
    }

    @Override
    public String toString() {
        return "SimpleQuestion{" +
                "_id='" + _id + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", code='" + code + '\'' +
                ", text='" + text + '\'' +
                ", answered=" + answered +
                ", yesAnswer=" + yesAnswer +
                ", timeMarkNeeded=" + timeMarkNeeded +
                ", clarifyingQuestionsNeeded=" + clarifyingQuestionsNeeded +
                ", clarifyingQuestions=" + clarifyingQuestions +
                ", timeMark=" + timeMark +
                ", answerTimeStamp=" + answerTimeStamp +
                '}';
    }
}
