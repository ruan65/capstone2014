package com.ruan.managecare.data.repositories;

import com.ruan.managecare.entities.questions.CheckIn;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface CheckInsRepository extends MongoRepository<CheckIn, String> {


}
