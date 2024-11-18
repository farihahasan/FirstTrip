package com.firsttrip.web.pages;

import com.firsttrip.web.components.DatePicker;
import com.firsttrip.web.components.ReactDatePicker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage {
    public HomePage(Page page) {
        super(page);
    }

    public String getPageId() {
        return "";
    }

    public String getUrl() {
        return "http://13.229.20.77:6100";
    }

    public ReactDatePicker clickDepartureDate() {
        page.locator(".one-way-date-picker .react-datepicker__input-container").first().click();
        return getDatePicker();
    }

    public ReactDatePicker clickReturnDate() {
        page.locator(".one-way-date-picker + div").locator(".react-datepicker__input-container").first().click();
        return getDatePicker();
    }

    public ReactDatePicker getDatePicker() {
        return new ReactDatePicker(page.locator(".react-datepicker"));
    }

    public HomePage clickSignUp() {
        page.getByText("Sign Up").last().click();
        return new HomePage(page);
    }

    public Locator getSignUpTextLocatorForVerify() {

        return page.getByText("Sign up").nth(1);
    }


}
