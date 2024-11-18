package com.firsttrip.web.pages;

import com.firsttrip.web.components.DatePicker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.commons.lang3.StringUtils;

public class LoginPage extends BasePage {
    private static final String PAGE_ID = "login-page";

    public LoginPage(Page page) {
        super(page);
    }

    @Override
    public String getPageId() {
        return PAGE_ID;
    }

    @Override
    public String getUrl() {
        return StringUtils.EMPTY;
    }

    public LoginPage clickEmailAddress() {
        page.getByText("Email Address").click();
        return this;
    }

    public LoginPage fillEmail(String email) {
        Locator emailField = page.locator("[type='email']");
        emailField.fill(email);
        return this;
    }

    public LoginPage clickSignInWithPassword() {
        page.getByText("Sign In with Password").click();
        return this;
    }

    public LoginPage fillPassword(String password) {
        Locator passwordField = page.locator("#password");
        passwordField.fill(password);
        return this;
    }

    public void clickSignIn() {
        Locator loginButton = page.getByText("Sign In").last();
        loginButton.click();
    }
}
