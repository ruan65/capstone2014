package com.ruan.managecare.data.repositories;

import com.ruan.managecare.entities.questions.SimpleQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SimpleQuestionsRepository extends MongoRepository <SimpleQuestion, String> {

    List<SimpleQuestion> findBySubCategory(String rubric);

    List<SimpleQuestion> findByCategory(String category);

    List<SimpleQuestion> findByCategoryAndSubCategory(String category, String subCategory);

    List<SimpleQuestion> findByCode(String code);
}
