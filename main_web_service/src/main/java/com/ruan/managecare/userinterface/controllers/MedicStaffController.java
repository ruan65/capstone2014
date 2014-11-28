package com.ruan.managecare.userinterface.controllers;

import com.ruan.managecare.data.repositories.PatientsRepository;
import com.ruan.managecare.entities.peronalities.Patient;
import com.ruan.managecare.userinterface.helpers.NetworkOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MedicStaffController {

    @Autowired
    private PatientsRepository pRepo;

    /**
     * **************** Action: send message to the patient ************
     */

    @RequestMapping(value = "/notify/{pId}", method = RequestMethod.POST)
    public String notifyPatient(@PathVariable("pId") String pId, @RequestParam("msg") String msg, Model m) {

        Patient patient = pRepo.findById(pId);
        System.out.println("Notify: " + msg);

        String deviceId = patient.getDeviceNotificationsId();

        if (deviceId != null && !deviceId.isEmpty()) {
            NetworkOperations.sendGCMessage("Message from ManageCare to " + patient.getFirstName(), msg, deviceId);
            return "main";
        }
        m.addAttribute("error", "There is no device GCM Id");
        return "errors_page";
    }
}
