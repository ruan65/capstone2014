package com.ruan.managecare.data.repositories;

import com.ruan.managecare.entities.peronalities.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientsRepository extends MongoRepository<Patient, String> {

    Patient findByMrn(String mrn);

    List<Patient> findByDoctor(String doctor);

    @Query("{ '_id' : ?0 }")
    Patient findById(String _id);
}
