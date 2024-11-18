package com.firsttrip.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SignUpPage extends BasePage {
    public SignUpPage(Page page) {
        super(page);
    }

    @Override
    public String getPageId() {
        return "";
    }

    @Override
    public String getUrl() {
        return "/signup";
    }

    public SignUpPage fillEmailField(String email) {
        page.locator("[type='email']").fill(email);
        return this;
    }

    public SignUpPage fillMobileNumberField(String number) {
        page.locator("[type='number']").fill(number);
        return this;
    }

    public SignUpPage clickSendOtpButton() {
        page.getByText("Send OTP").click();
        return this;
    }

    public Locator getErrorMessageLocator() {
        return page.locator(".w-full.text-error");
    }

    public SignUpPage fillOtp(String otp) {
        page.locator("[data-input-otp='true']").fill(otp);
        return this;
    }

    public void clickContinue() {
        page.getByText("Continue").click();
    }

    public void clickBackButton() {
        page.locator(".mt-5.flex.items-center.justify-center.gap-2.pb-5.text-sm.text-brand-1.py-5").click();
    }

    public boolean hasOtpAttemptFailed() {
        page.locator("h3").getByText("OTP Attempts Failed");
        return true;
    }
}



