package com.ruan.managecare.data.configviews;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categories {

    public final static Map<String, List<String>> categories;
    public final static List<String> medicationTypes = Arrays.asList(

            "Diabeties", "Pain", "Cancer", "Allergies", "Asthma", "Depression", "Diabetes"
    );

    static {

        categories = new HashMap<>();

        categories.put(
                "Oncology", Arrays.asList(
                        "Chemotherapy",
                        "Causes of cancer pain",
                        "Diet and cancer",
                        "Check-In",
                        "Pain"
                ));

        categories.put(
                "Immunology", Arrays.asList(
                        "Immunologic tests",
                        "Vaccine",
                        "Check-In",
                        "Pain"
                ));

        categories.put(
                "Pediatrics", Arrays.asList(
                        "Baby colic",
                        "Birth weight",
                        "Growth chart",
                        "Delayed puberty",
                        "Pain in babies",
                        "Check-In",
                        "Pain"
                ));

        categories.put(
                "Cardiology", Arrays.asList(
                        "Heart rate variability",
                        "Pulse",
                        "Check-In",
                        "Diet",
                        "Pain"
                ));
    }
}
