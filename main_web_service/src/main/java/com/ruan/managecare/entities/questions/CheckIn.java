package com.ruan.managecare.entities.questions;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckIn {

    @Id
    private String _id;

    private String tag;
    private String description;
    private String patientMRN;

    private boolean checked;

    private Date responseDateTime;

    private List<SimpleQuestion> simpleQuestions = new ArrayList<>();
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

    public CheckIn(){}

    public void addQuestion(SimpleQuestion sq) {
        simpleQuestions.add(sq);
    }

    public void addMultipleChoiceQuestion(MultipleChoiceQuestion mq) {
        multipleChoiceQuestions.add(mq);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatientMRN() {
        return patientMRN;
    }

    public void setPatientMRN(String patientMRN) {
        this.patientMRN = patientMRN;
    }

    public Date getResponseDateTime() {
        return responseDateTime;
    }

    public void setResponseDateTime(Date responseDateTime) {
        this.responseDateTime = responseDateTime;
    }


    public List<SimpleQuestion> getSimpleQuestions() {
        return simpleQuestions;
    }

    public void setSimpleQuestions(List<SimpleQuestion> simpleQuestions) {
        this.simpleQuestions = simpleQuestions;
    }

    public List<MultipleChoiceQuestion> getMultipleChoiceQuestions() {
        return multipleChoiceQuestions;
    }

    public void setMultipleChoiceQuestions(List<MultipleChoiceQuestion> multipleChoiceQuestions) {
        this.multipleChoiceQuestions = multipleChoiceQuestions;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                "_id='" + _id + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", patientMRN='" + patientMRN + '\'' +
                ", checked=" + checked +
                ", responseDateTime=" + responseDateTime +
                ", simpleQuestions=" + simpleQuestions +
                ", multipleChoiceQuestions=" + multipleChoiceQuestions +
                '}';
    }
}
