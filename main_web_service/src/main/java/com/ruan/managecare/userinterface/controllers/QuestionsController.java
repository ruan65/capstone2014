package com.ruan.managecare.userinterface.controllers;


import com.ruan.managecare.data.configviews.Categories;
import com.ruan.managecare.data.repositories.MultipleChoiceQuestionsRepository;
import com.ruan.managecare.data.repositories.SimpleQuestionsRepository;
import com.ruan.managecare.entities.questions.MultipleChoiceQuestion;
import com.ruan.managecare.entities.questions.SimpleQuestion;
import com.ruan.managecare.manual_test_classes.MultipleChoiceQuestionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionsController {

    @Autowired
    SimpleQuestionsRepository qRepo;

    @Autowired
    MultipleChoiceQuestionsRepository mcqRepo;

    @RequestMapping("/questions")
    public String questions() {
        return "checkins/questions";
    }

    /**
     * action ****************** Adding new SimpleQuestion ************************
     */

    @RequestMapping("/category")
    public String category(Model m) {

        m.addAttribute("categories", Categories.categories.keySet());

        return "checkins/category";
    }

    @RequestMapping(value = "/newq")
    public String createQuestion(@RequestParam("categ") String categ, Model m) {


        SimpleQuestion question = new SimpleQuestion();
        question.setCategory(categ);
        m.addAttribute("question", question);
        m.addAttribute("sCategory", Categories.categories.get(categ));

        return "checkins/create_question";
    }

    /**
     * action ****************** POST for adding and updating SimpleQuestion ************************
     */

    @RequestMapping(value = "/newq", method = RequestMethod.POST)
    public String saveQ(@ModelAttribute SimpleQuestion question,
                        Model model) {

        System.out.println(question);

        question = qRepo.save(question);

        if (question != null) {
            model.addAttribute("question", question);
            return "checkins/details_question";
        }
        return "errors_page";
    }

    /**
     * action ****************** POST for MANUALLY adding MultipleChoiceQuestion *********************
     */

    @RequestMapping(value = "/manaddmcq", method = RequestMethod.POST)
    public String saveMCQManually(@RequestBody MultipleChoiceQuestion question, Model model) {

        question = mcqRepo.save(question);

        if (question != null) {
            model.addAttribute("question", question);
            return "checkins/details_question";
        }
        return "errors_page";
    }
}
