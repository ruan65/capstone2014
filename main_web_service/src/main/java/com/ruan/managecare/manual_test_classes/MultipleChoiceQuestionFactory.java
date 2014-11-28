package com.ruan.managecare.manual_test_classes;

import com.ruan.managecare.entities.questions.MultipleChoiceQuestion;

public class MultipleChoiceQuestionFactory {

    public static MultipleChoiceQuestion get() {

        MultipleChoiceQuestion question= new MultipleChoiceQuestion();

        question.setCode("Ca-Ch-Mo-007");
        question.setCategory("Oncology");
        question.setSubCategory("Check-In");
        question.setText("How do you feel today?");

        question.addAnswer("good");
        question.addAnswer("moderate");
        question.addAnswer("bad");

        return question;
    }
}
