package com.ruan.managecare.userinterface.controllers;

import com.ruan.managecare.data.configviews.Categories;
import com.ruan.managecare.data.repositories.CheckInsRepository;
import com.ruan.managecare.data.repositories.MultipleChoiceQuestionsRepository;
import com.ruan.managecare.data.repositories.PatientsRepository;
import com.ruan.managecare.data.repositories.SimpleQuestionsRepository;
import com.ruan.managecare.entities.peronalities.Patient;
import com.ruan.managecare.entities.questions.CheckIn;
import com.ruan.managecare.entities.questions.MultipleChoiceQuestion;
import com.ruan.managecare.entities.questions.SimpleQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckInsController {

    @Autowired
    CheckInsRepository chRepo;

    @Autowired
    PatientsRepository pRepo;

    @Autowired
    SimpleQuestionsRepository qRepo;

    @Autowired
    MultipleChoiceQuestionsRepository mcqRepo;

    /**
     * ************** To CheckIn creation page **********
     */

    @RequestMapping("/crchin")
    public String createCheckIn(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "categ", required = false) String cat,
            @RequestParam(value = "s_categ", required = false) String sCat,
            @RequestParam(value = "qtype", required = false) String qType,
            @RequestParam(value = "qid", required = false) String qId,
            Model m) {


        Patient patient = pRepo.findById(id);
        m.addAttribute("category", cat);
        m.addAttribute("categories", Categories.categories.keySet());

        if (patient != null) {
            m.addAttribute("patient", patient);
        }

        if (qType != null && qId != null) {
            addQuestionToCheckIn(qId, qType, patient);
        }

        if (cat != null) {
            m.addAttribute("sCategory", Categories.categories.get(cat));
        }

        if (sCat != null) {

            m.addAttribute("sub_category", sCat);
            m.addAttribute("questions",
                    qRepo.findByCategoryAndSubCategory(cat, sCat));

            m.addAttribute("mch_questions",
                    mcqRepo.findByCategoryAndSubCategory(cat, sCat));

            return "checkins/questions";
        }

        return "checkins/create_checkin";
    }


    /**
     * **************** Save CheckIn *****************
     */

    @RequestMapping(value = "/crchin", method = RequestMethod.POST)
    public String SaveCheckIn(@ModelAttribute CheckIn checkIn, Model m) {


        checkIn = chRepo.save(checkIn);
        m.addAttribute(checkIn);

        return "checkin";
    }

    /**
     * **************** Helpers   *****************
     */

    private void addQuestionToCheckIn(String qId, String qType, Patient patient) {
        if (patient != null && qId != null) {

            SimpleQuestion q = qRepo.findOne(qId);
            MultipleChoiceQuestion mchq = mcqRepo.findOne(qId);

            if (patient.getCheckIn() == null) patient.setCheckIn(new CheckIn());

            if (qType.equals("sq") && q != null) {
                patient.getCheckIn().addQuestion(q);
            } else if (qType.equals("mchq") && mchq != null) {
                patient.getCheckIn().addMultipleChoiceQuestion(mchq);
            }

            pRepo.save(patient);
        }
    }
}


















