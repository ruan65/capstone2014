package com.ruan.managecare.data.repositories;

import com.ruan.managecare.entities.peronalities.Medic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface MedicsRepository extends MongoRepository<Medic, String> {

    Medic findByLicence(String licence);

    @Query("{ '_id' : ?0 }")
    Medic findById(String _id);

}
