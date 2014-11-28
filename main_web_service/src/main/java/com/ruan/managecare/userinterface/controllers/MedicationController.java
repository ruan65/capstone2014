package com.ruan.managecare.userinterface.controllers;


import com.ruan.managecare.data.configviews.Categories;
import com.ruan.managecare.data.repositories.MedicamentRepository;
import com.ruan.managecare.entities.medication.Medicament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class MedicationController {

    @Autowired
    MedicamentRepository medicamentRepo;

    @RequestMapping("/medication")
    public String medication(Model m) {

        List<Medicament> drugs = medicamentRepo.findAll();
        Collections.sort(drugs, new Comparator<Medicament>() {
            @Override
            public int compare(Medicament o1, Medicament o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        m.addAttribute("drugs", drugs);
        return "medication/medication";
    }

    @RequestMapping("/newmedicament")
    public String newMedicament(Model m) {

        m.addAttribute("medicament", new Medicament());
        m.addAttribute("types", Categories.medicationTypes);

        return "medication/add_medicament_to_db";
    }


    @RequestMapping(value = "/newmedicament", method = RequestMethod.POST)
    public String saveMedicament(@ModelAttribute Medicament medicament) {

        System.out.println(medicament);

        if (medicament != null) {
            medicament = medicamentRepo.save(medicament);
        }
        if (medicament != null) {
            return "redirect:medication";
        }

        return "errors_page";
    }
}
