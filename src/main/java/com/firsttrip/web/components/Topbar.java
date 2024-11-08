package com.firsttrip.web.components;

import com.firsttrip.web.pages.LoginPage;
import com.microsoft.playwright.Locator;

public class Topbar extends BaseComponent {
    public Topbar(Locator element) {
        super(element);
    }

    public LoginPage clickSignIn() {
        element.getByText("Sign In").first().click();
        return new LoginPage(element.page());
    }

    public Locator getProfileDropdownButton() {
        return element.locator(".hidden > div[aria-hidden='true'] > div").first();
    }
}
