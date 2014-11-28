package com.ruan.managecare.entities.peronalities;

import com.ruan.managecare.entities.questions.CheckIn;
import org.springframework.data.annotation.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Patient {

    @Id
    private String _id;

    private String mrn; // Medical record number
    private String doctor;
    private String firstName;
    private String lastName;
    private String diagnosis;
    private String description;
    private String gender;
    private String deviceNotificationsId; // android app id in the GCMessage system

    private Date dateOfBirth;

    private String dob;



    private int age;

    private CheckIn checkIn;

    private List<String> medication = new LinkedList<>();
    private List<CheckIn> answeredCheckIns = new LinkedList<>();

    public boolean addAnsweredCheckIn(CheckIn checkIn) {
        return answeredCheckIns.add(checkIn);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth() {

        try {
            this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    .parse( getDob() );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public List<CheckIn> getAnsweredCheckIns() {
        return answeredCheckIns;
    }

    public void setAnsweredCheckIns(List<CheckIn> answeredCheckIns) {
        this.answeredCheckIns = answeredCheckIns;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(List<String> medications) {
        this.medication = medications;
    }

    public String getDeviceNotificationsId() {
        return deviceNotificationsId;
    }

    public void setDeviceNotificationsId(String deviceNotificationsId) {
        this.deviceNotificationsId = deviceNotificationsId;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "_id='" + _id + '\'' +
                ", mrn='" + mrn + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", description='" + description + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", checkIn=" + checkIn +
                ", medication=" + medication +
                ", answeredCheckIns=" + answeredCheckIns +
                '}';
    }
}
