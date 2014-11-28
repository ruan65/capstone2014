package com.ruan.managecare.clients_access;


import com.ruan.managecare.data.mongodb.AnswersData;
import com.ruan.managecare.data.mongodb.MongoDBRequests;
import com.ruan.managecare.data.repositories.MedicsRepository;
import com.ruan.managecare.data.repositories.PatientsRepository;
import com.ruan.managecare.entities.peronalities.Medic;
import com.ruan.managecare.entities.peronalities.Patient;
import com.ruan.managecare.entities.questions.CheckIn;
import com.ruan.managecare.userinterface.helpers.NetworkOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruan.managecare.data.mongodb.MongoDBRequests.*;

@RestController
public class PatientsMobileController {

    @Autowired
    PatientsRepository pRepo;

    @Autowired
    MedicsRepository mRepo;

    @RequestMapping("/auth")
    public Map<String, String> login(Principal principal) {

        Map<String, String> map = new HashMap<>();

        Patient p = pRepo.findByMrn(principal.getName());

        if (p != null) {

            map.put("mrn", p.getMrn());
            map.put("fname", p.getFirstName());
            map.put("lname", p.getLastName());
            map.put("id", p.get_id());

            return map;
        } else {

            Medic m = mRepo.findByLicence(principal.getName());

            if (m != null) {
                map.put("mrn", m.getLicence());
                map.put("fname", m.getFirstName());
                map.put("lname", m.getLastName());
                map.put("id", m.get_id());
                return map;
            }
            return map;
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String loginPost(Principal principal) {
        return principal.getName();
    }

    @RequestMapping("/getcheckins")
    public List<CheckIn> getAllCheckins(@RequestParam("pId") String pId) {

        return pRepo.findOne(pId).getAnsweredCheckIns();
    }

    @RequestMapping("/getcheckin")
    public CheckIn getCheckin(@RequestParam("pId") String pId) {

        return pRepo.findOne(pId).getCheckIn();
    }

    /**
     * **************** Action: adding answered Check-In to patient ************
     */
    @RequestMapping(value = "/addanswch", method = RequestMethod.POST)
    public Boolean addAnsweredCheckIn(
            @RequestParam("pId") String pId,
            @RequestBody CheckIn checkIn) {

        Patient p = pRepo.findOne(pId);
        p.addAnsweredCheckIn(checkIn);

        sendAlarmGCMessageToDoctor(p, isEatingOk(pId), "can't eat more than 12 hours");
        sendAlarmGCMessageToDoctor(p, isPainNotSevere(pId), "has severe pain more than 12 hours");
        sendAlarmGCMessageToDoctor(p, isPainNotSevereOrModerate(pId),
                "has severe or moderate pain more than 16 hours");

        return pRepo.save(p) != null ? true : false;
    }

    /**
     * **************** Action: send GCMessage to doctor if something went wrong ************
     */

    private void sendAlarmGCMessageToDoctor(Patient patient, boolean ok, String msg) {

        if (!ok) {

            String doctorDeviceId = mRepo.findByLicence(patient
                    .getDoctor())
                    .getDeviceNotificationsId();

            sendGcm(patient, msg, doctorDeviceId);
        }
    }

    private boolean sendGcm(Patient patient, String msg, String doctorDeviceId) {
        return NetworkOperations.sendGCMessage(patient.getFirstName() + " " +
                patient.getLastName() + " " + msg, patient.get_id(), doctorDeviceId);
    }

    /**
     * **************** Action: send GCMessage to doctor from mobile ************
     */
    @RequestMapping(value = "/sendmsg", method = RequestMethod.POST)
    private boolean sendAlarmGCMessageToDoctor(Principal principal,
                                            @RequestBody String msg) {

        Patient patient = pRepo.findByMrn(principal.getName());
        String doctorDeviceId = mRepo.findByLicence(patient.getDoctor()).getDeviceNotificationsId();

        return sendGcm(patient, msg, doctorDeviceId);
    }

    /**
     * **************** Action: saving mobile device id for GCMessaging ************
     */
    @RequestMapping(value = "/mobileid/{userId}", method = RequestMethod.POST)
    public boolean saveMobileId(@PathVariable("userId") String userId,
                                @RequestBody Map<String, String> deviceId) {

        Patient p = pRepo.findById(userId);
        if (p != null) {
            p.setDeviceNotificationsId(deviceId.get("deviceId"));
            return pRepo.save(p) != null ? true : false;
        } else {
            Medic m = mRepo.findById(userId);
            if (m != null) {
                m.setDeviceNotificationsId(deviceId.get("deviceId"));
                return mRepo.save(m) != null ? true : false;
            }
            return false;
        }
    }

    /**
     * **************** Action: sending answered Check-Ins data for info-graphics ************
     */

    @RequestMapping("/getdata/{userId}")
    public List<AnswersData> sendDataForInfoG(@PathVariable("userId") String userId) {
        int count = 3;
        try {
            return MongoDBRequests.getAnsweredCheckInsData(userId, 10);
        } catch (Error error) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (--count > 0) {
                sendDataForInfoG(userId);
            }

        }
        return null;
    }

    /**
     * **************** Action: sending doctor's patients list ************
     */

    @RequestMapping("/getpatients")
    public List<Map<String, String>> sendPatientsList(Principal doctor) {

        List<Patient> patients = pRepo.findByDoctor(doctor.getName());

        List<Map<String, String>> pInfos = new ArrayList<>();

        if (patients != null && patients.size() > 0) {
            for (Patient p : patients) {
                Map<String, String> m = new HashMap<>();
                m.put("fname", p.getFirstName());
                m.put("lname", p.getLastName());
                m.put("_id", p.get_id());
                pInfos.add(m);
            }
        }
        return pInfos;
    }


}
































