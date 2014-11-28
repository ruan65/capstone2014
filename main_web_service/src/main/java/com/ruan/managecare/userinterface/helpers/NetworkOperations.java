package com.ruan.managecare.userinterface.helpers;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class NetworkOperations {

    public static final String GOOGLE_GSM_API = "https://android.googleapis.com/gcm/send";
    public static final String SERVER_API_KEY = "key=" + "AIzaSyDo_tRwnR263OcNYusSHCeCjarOe1_EKf0";
    public static final String CONTENT_TYPE = "application/json";

    public static boolean sendGCMessage(String title, String msg, String... ids) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", SERVER_API_KEY);
        headers.add("Content-Type", CONTENT_TYPE);

        GcmRequestObject gcmRequest = new GcmRequestObject();

        for (String id : ids) {
            gcmRequest.addRegistrationId(id);
        }

        gcmRequest.setTitle(title);
        gcmRequest.setMessage(msg);

        HttpEntity<GcmRequestObject> request = new HttpEntity<>(gcmRequest, headers);

        try {
            ResponseEntity<String> resp = getRestTemplate().postForEntity(GOOGLE_GSM_API, request, String.class);
            System.out.println("response uri: " + resp);
            return true;
        } catch (Exception e) {
            System.err.println("GCM post error: " + e);
            return false;
        }
    }

    private static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }
}
