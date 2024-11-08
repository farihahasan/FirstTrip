package com.firsttrip.web;

import com.firsttrip.web.pages.HomePage;
import com.firsttrip.web.pages.LoginPage;
import com.firsttrip.web.utils.Credential;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {
    @Test
    public void loginShouldSucceed() {
        Credential credential = loadDefaultCredential();
        LoginPage loginPage = goTo(new LoginPage(page), credential.getBaseUrl());

        loginPage.getTopbar()
                .clickSignIn()
                .clickEmailAddress()
                .fillEmail(credential.getEmail())
                .clickSignInWithPassword()
                .fillPassword(credential.getPassword())
                .clickSignIn();
        this.page.waitForLoadState(LoadState.NETWORKIDLE);
        HomePage homePage = new HomePage(page);
        assertThat(homePage.getTopbar().getProfileDropdownButton()).isAttached();
    }
}
