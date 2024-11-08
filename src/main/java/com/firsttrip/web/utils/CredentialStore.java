package com.firsttrip.web.utils;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class CredentialStore {
    private static CredentialStore instance;

    private final Map<String, String> credentials;

    @Setter
    private boolean populated;

    private CredentialStore() {
        credentials = new HashMap<>();
        populated = false;
    }

    public static CredentialStore instance() {
        if (instance == null) {
            instance = new CredentialStore();
        }
        return instance;
    }

    public String get(String key) {
        return credentials.get(key);
    }

    public void put(String key, String value) {
        credentials.put(key, value);
    }

    private boolean isPopulated() {
        return populated;
    }

    public boolean isNotPopulated() {
        return !isPopulated();
    }
}
