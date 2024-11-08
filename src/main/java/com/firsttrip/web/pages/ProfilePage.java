package com.firsttrip.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProfilePage extends BasePage {
    public ProfilePage(Page page) {
        super(page);
    }

    @Override
    public String getPageId() {
        return "";
    }

    @Override
    public String getUrl() {
        return "/profile";
    }


    public Locator getProfileNameLocator() {
        return page.locator(".text-brand-1.text-6xl").first();
    }
}
