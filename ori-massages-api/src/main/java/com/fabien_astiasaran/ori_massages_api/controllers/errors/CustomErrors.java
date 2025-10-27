package com.fabien_astiasaran.ori_massages_api.controllers.errors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomErrors {

    private final Map<String, Collection<String>> fieldsErrors = new HashMap<>();
    private final Collection<String> globalErrors = new ArrayList<>();

    public void addFieldError(String fieldName, String message) {
        fieldsErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(message);
    }

    public void addGlobalError(String message) {
        globalErrors.add(message);
    }

    public Map<String, Collection<String>> getFieldsErrors() {
        return fieldsErrors;
    }

    public Collection<String> getGlobalErrors() {
        return globalErrors;
    }

    @Override
    public String toString() {
        return "CustomErrors{" +
                "fieldsErrors=" + fieldsErrors +
                ", globalErrors=" + globalErrors +
                '}';
    }
}
