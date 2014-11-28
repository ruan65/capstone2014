package com.ruan.managecare.userinterface.controllers;

import com.ruan.managecare.data.repositories.PatientsRepository;
import com.ruan.managecare.data.repositories.SimpleQuestionsRepository;
import com.ruan.managecare.entities.peronalities.Patient;
import com.ruan.managecare.entities.questions.CheckIn;
import com.ruan.managecare.entities.questions.SimpleQuestion;
import com.ruan.managecare.userinterface.helpers.NetworkOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.String;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class PatientsController {

    @Autowired
    private PatientsRepository pRepo;

    @Autowired
    private SimpleQuestionsRepository sqRepo;

//  action ****************** Requesting all patients ********************

    @RequestMapping("/allpatients")
    public String patients(Model m) {

        List<Patient> all = pRepo.findAll();
        Collections.sort(all, new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        m.addAttribute("patients", all);

        return "patients";
    }

//  action ****************** Requesting all doctor's patients ********************

    @RequestMapping("/patients")
    public String patients(Model m, Principal d) {

        List<Patient> all = pRepo.findByDoctor(d.getName());
        Collections.sort(all, new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        m.addAttribute("patients", all);

        return "patients";
    }

//  action ****************** Requesting patient by _id ******************

    @RequestMapping("/patient")
    public String patient(
            @RequestParam("id") String id,
            Model model) {

        model.addAttribute("patient", pRepo.findById(id));

        return "details_patient";
    }

//  action ****************** Requesting Patient by MRN ******************

    @RequestMapping("/mrn")
    public String patientMrn(@RequestParam("mrn") String mrn, Model model) {

        Patient p = pRepo.findByMrn(mrn.toUpperCase());
        if (p != null) {
            model.addAttribute("patient", p);
            return "details_patient";
        }
        return "errors_page";
    }

//  action ****************** Deleting Patient ***************************

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") String id) {

        Patient patient = pRepo.findById(id);

        if (patient != null) {
            pRepo.delete(patient);
        }

        return "redirect:/patients";
    }

//  action ****************** Adding new Patient *************************

    @RequestMapping("/add")
    public String addPatient(Model m) {

        m.addAttribute("patient", new Patient());

        return "add_patient";
    }

//  action ****************** Update Patient *************************

    @RequestMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") String id, Model m) {

        Patient patient = null;

        System.out.println("Patient in request: " + id);

        if (!id.equals("")) {
            patient = pRepo.findById(id);
        }
        m.addAttribute("patient", (patient == null) ? new Patient() : patient);

        return "add_patient";
    }

//  action ****************** POST for adding and updating Patient *************************

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public String save(@ModelAttribute Patient patient,
                       @PathVariable("id") String id, Model m, Principal principal) {

        if (!id.equals("null")) {
            patient.set_id(id);
        }

        patient.setDoctor(principal.getName());
        patient.setDateOfBirth();
        patient = pRepo.save(patient);

        if (patient != null) {
            m.addAttribute("patient", patient);
            return "details_patient";
        }
        return "errors_page";
    }

    //  action ****************** POST for prescript new medicament *************************

    @RequestMapping(value = "/prescript", method = RequestMethod.POST)
    public String prescript(@RequestParam("pId") String pId,
                            @RequestParam("med") String med, Model m) {

        SimpleQuestion painKiller = sqRepo.findByCode(med).get(0);
        if (painKiller == null) {
            m.addAttribute("error", "Add Question first...");
            return "errors_page";
        }
        Patient patient = pRepo.findById(pId);
        if (patient == null) {
            m.addAttribute("error", "Can't find patient...");
            return "errors_page";
        }
        CheckIn checkIn = patient.getCheckIn();
        if (checkIn == null) {
            m.addAttribute("error", "Add Check-In first...");
            return "errors_page";
        }

        List<SimpleQuestion> simpleQuestions = checkIn.getSimpleQuestions();

        for (SimpleQuestion q : simpleQuestions) {
            if (q.getCode().equals("pain-main")) {
                q.addClarifyingQuestion(painKiller);
            }
        }
        patient.getMedication().add(med);
        patient = pRepo.save(patient);
        m.addAttribute("patient", patient);
        return "redirect:patient?id=" + pId;
    }
}

























