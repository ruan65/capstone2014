package com.ruan.managecarenew.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ruan.managecarenew.enntities.AnswersData;
import com.ruan.managecarenew.enntities.CheckIn;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NetworkOperations {

    final static String LOG_TAG = "ml";

    public final static int URL_TYPE_GET = 100;
    public final static int URL_TYPE_POST = 200;
    public final static int RECONNECT_TIMES = 5;

    public static Map<String, String> getUserInfo(String url, String base64, Context ctx) {

        try {
            return getRestTemplate()
                    .exchange(url, HttpMethod.GET,
                            new HttpEntity<String>(createHeaders(base64)), Map.class)
                    .getBody();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    public static CheckIn downloadCheckIn(String url, String base64, Context ctx) {

        try {
            return getRestTemplate()
                    .exchange(url, HttpMethod.GET,
                            new HttpEntity<String>(createHeaders(base64)), CheckIn.class)
                    .getBody();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    public static List<AnswersData> getData(String urlPost, String base64, Context ctx) {

        int count = RECONNECT_TIMES;
        try {
            return getRestTemplate()
                    .exchange(urlPost, HttpMethod.GET,
                            new HttpEntity<String>(createHeaders(base64)), List.class)
                    .getBody();
        } catch (Exception e) {
            while (--count > 0) {
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                getData(urlPost, base64, ctx);
            }
            Log.d(LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    public static List<Map<String, String>> downloadDoctorPatientsList(String url, String base64, Context ctx) {

        int count = RECONNECT_TIMES;
        try {
            return getRestTemplate()
                    .exchange(url, HttpMethod.GET,
                            new HttpEntity<String>(createHeaders(base64)), List.class)
                    .getBody();
        } catch (Exception e) {
            while (--count > 0) {
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                downloadDoctorPatientsList(url, base64, ctx);
            }
            Log.d(LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    public static boolean uploadCheckin(String urlPost, String base64, CheckIn checkIn, Context ctx) {

        try {
            if (checkIn != null) {
                HttpEntity<CheckIn> request = new HttpEntity<CheckIn>(checkIn, createHeaders(base64));
                return getRestTemplate().postForObject(urlPost, request, Boolean.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean SendMessageToDoctor(String url, String base64, String msg, Context ctx) {

        try {

            if (msg != null && !msg.isEmpty()) {
                HttpEntity<String> request = new HttpEntity<String>(msg, createHeaders(base64));

                Boolean result = getRestTemplate().postForObject(url, request, Boolean.class);

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendUserDeviceGcmId(String urlPost, String base64, String deviceId, Context ctx) {

        try {
            if (deviceId != null && !deviceId.isEmpty()) {

                Map<String, String> map = new HashMap<String, String>();

                map.put("deviceId", deviceId);

                HttpEntity<Map<String, String>> request =
                        new HttpEntity<Map<String, String>>(map, createHeaders(base64));

                return getRestTemplate().postForObject(urlPost, request, Boolean.class);
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        }
        return false;
    }

    private static MultiValueMap<String, String> createHeaders(String base64) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Authorization", "Basic " + base64);
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private static RestTemplate getRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(

                new EasyHttpClient()

        ));

        return restTemplate;
    }
}
