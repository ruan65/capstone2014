package com.ruan.managecare.entities.questions;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultipleChoiceQuestion {

    @Id
    private String _id;

    private String category;
    private String subCategory;
    private String code;
    private String text;
    private String answer;

    private List<String> answers = new ArrayList<>();

    private boolean answered;

    private Date answerTimeStamp;

    public MultipleChoiceQuestion(){}

    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public void clearAnswers() {
        answers.clear();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
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

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "_id='" + _id + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", code='" + code + '\'' +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                ", answers=" + answers +
                ", answered=" + answered +
                ", answerTimeStamp=" + answerTimeStamp +
                '}';
    }
}
