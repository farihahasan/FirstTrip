package com.firsttrip.web.utils;

import lombok.Getter;

@Getter
public class Credential {
    private final String baseUrl;
    private final String email;
    private final String mobile;
    private final String password;

    public Credential(String baseUrl, String username, String mobile, String password) {
        this.baseUrl = baseUrl;
        this.email = username;
        this.mobile = mobile;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (!baseUrl.equals(that.baseUrl)) return false;
        if (!email.equals(that.email)) return false;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = baseUrl.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
