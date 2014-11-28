package com.ruan.managecare.data.repositories;

import com.ruan.managecare.entities.medication.Medicament;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MedicamentRepository extends MongoRepository <Medicament, String> {

    Medicament findByCode(String code);

    List<Medicament> findByName(String name);
}
