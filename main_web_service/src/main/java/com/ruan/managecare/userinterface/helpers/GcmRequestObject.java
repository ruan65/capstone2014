package com.ruan.managecare.userinterface.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcmRequestObject {

    private Map<String, String> data;
    private List<String> registration_ids;


    public GcmRequestObject() {
        this.data = new HashMap<>();
        this.registration_ids = new ArrayList<>();
        setTitle("");
        setMessage("");
    }

    public void setTitle(String title) {
        data.put("title", title);
    }

    public void setMessage(String msg) {
        data.put("message", msg);
    }

    public void addRegistrationId(String id) {
        registration_ids.add(id);
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }
}
