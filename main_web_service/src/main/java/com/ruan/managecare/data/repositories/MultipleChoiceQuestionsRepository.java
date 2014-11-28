package com.ruan.managecare.data.repositories;

import com.ruan.managecare.entities.questions.MultipleChoiceQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MultipleChoiceQuestionsRepository  extends MongoRepository <MultipleChoiceQuestion, String> {

    List<MultipleChoiceQuestion> findBySubCategory(String rubric);

    List<MultipleChoiceQuestion> findByCategory(String category);

    List<MultipleChoiceQuestion> findByCategoryAndSubCategory(String category, String subCategory);

    MultipleChoiceQuestion findByCode(String code);
}
