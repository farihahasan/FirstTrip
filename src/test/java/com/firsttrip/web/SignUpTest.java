package com.firsttrip.web;

import com.firsttrip.web.pages.SignUpPage;
import com.firsttrip.web.utils.Credential;
import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class SignUpTest extends BaseTest {

    @Test
    public void errorMessageForSignUpWithInvalidEmailAndValidNotRegisteredMobileNumberShouldSucceed() {
        String email = "iftekhar.test@retest";
        String number = "1715820274";
        SignUpPage signUpPage = goTo(new SignUpPage(page));
        signUpPage
                .fillEmailField(email)
                .fillMobileNumberField(number)
                .clickSendOtpButton();
        assertThat(signUpPage.getErrorMessageLocator()).containsText("Invalid email address");
    }

    @Test
    public void errorMessageForSignUpWithInvalidEmailAndValidRegisteredMobileNumberShouldSucceed() {
        Credential credential = loadCredential("mobile.user");

        String email = "iftekhar.test@retest.com";
        SignUpPage signUpPage = goTo(new SignUpPage(page));
        signUpPage
                .fillEmailField(email)
                .fillMobileNumberField(credential.getMobile())
                .clickSendOtpButton();
        assertThat(signUpPage.getErrorMessageLocator()).containsText("This Phone Number is Already Used by Another User");
    }

    @Test
    public void userBlockedFor24HoursAndErrorMessageDisplayedShouldSucceed() {
        SignUpPage signUpPage = goTo(new SignUpPage(page));
        LoremIpsum loremIpsum = LoremIpsum.getInstance();
        String email = LoremIpsum.getInstance().getEmail();
        for (int i = 0; i < 5; i++) {
            signUpPage
                    .fillEmailField(email)
                    .clickSendOtpButton()
                    .clickBackButton();
        }
        assertTrue(signUpPage.hasOtpAttemptFailed());
    }

    @Test
    public void signupWithBlankEmailFieldShouldFail() {
        SignUpPage signUpPage = goTo(new SignUpPage(page));
        signUpPage.clickSendOtpButton();
        assertThat(signUpPage.getErrorMessageLocator()).containsText("The field cannot be empty");
    }

    @Test
    public void verifyUserBlockForInvalidOptShouldSucceed() {
        SignUpPage signUpPage = goTo(new SignUpPage(page));
        LoremIpsum loremIpsum = LoremIpsum.getInstance();

        do {
            signUpPage
                    .fillEmailField(loremIpsum.getEmail())
                    .clickSendOtpButton();
        } while (signUpPage.getErrorMessageLocator().count() != 0);

        for (int i = 0; i <= 4; i++) {
            signUpPage
                    .fillOtp(String.valueOf(generateRandomSixDigitNumber()))
                    .clickContinue();
        }
        assertThat(signUpPage.getErrorMessageLocator()).containsText("Your account has been temporarily blocked.");
    }

    public int generateRandomSixDigitNumber() {
        return (int) (Math.random() * 900000) + 100000; // Generates a number between 100000 and 999999
    }
}
