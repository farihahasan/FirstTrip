package com.firsttrip.web;

import com.firsttrip.web.pages.ProfilePage;
import com.firsttrip.web.utils.Credential;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MultipleUserTest extends BaseMultipleUserTest {
    Credential defaultCredential = loadDefaultCredential();
    Credential anotherCredential = loadCredential("smoke.admin");

    @Test(groups = {"multi-user"})
    public void verifyMultiUserProfilePageInSingleTestMethodShouldSucceed() {
        ProfilePage profilePage = goTo(ProfilePage.class, defaultCredential);
        assertThat(profilePage.getProfileNameLocator()).containsText("NA");

        profilePage = goTo(ProfilePage.class, anotherCredential);
        assertThat(profilePage.getProfileNameLocator()).containsText("NT");
    }

    @Test(groups = {"multi-user"})
    public void verifyMultiUserProfilePageInSingleTestAgainMethodShouldSucceed() {
        ProfilePage profilePage = goTo(ProfilePage.class, defaultCredential);
        assertThat(profilePage.getProfileNameLocator()).containsText("NA");

        profilePage = goTo(ProfilePage.class, anotherCredential);
        assertThat(profilePage.getProfileNameLocator()).containsText("NT");
    }
}
