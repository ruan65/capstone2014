package com.ruan.managecare.entities.peronalities;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Medic {

    @Id
    private String _id;

    private String licence;

    private String deviceNotificationsId;

    private String firstName;
    private String lastName;

    private List<String> patientsMrn;

    public List<String> getPatientsMrn() {
        return patientsMrn;
    }

    public void setPatientsMrn(List<String> patientsMrn) {
        this.patientsMrn = patientsMrn;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDeviceNotificationsId() {
        return deviceNotificationsId;
    }

    public void setDeviceNotificationsId(String deviceNotificationsId) {
        this.deviceNotificationsId = deviceNotificationsId;
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


    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "Medic{" +
                "_id='" + _id + '\'' +
                ", licence='" + licence + '\'' +
                ", deviceNotificationsId='" + deviceNotificationsId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patientsMrn=" + patientsMrn +
                '}';
    }
}
