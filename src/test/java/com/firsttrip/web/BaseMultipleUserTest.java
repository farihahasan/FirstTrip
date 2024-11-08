package com.firsttrip.web;

import com.firsttrip.web.pages.BasePage;
import com.firsttrip.web.utils.Credential;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.BeforeMethod;

import java.util.Objects;

public class BaseMultipleUserTest extends BaseAuthenticatedTest {
    private Credential currentCredential;

    @Override
    protected Credential credential() {
        if (Objects.isNull(currentCredential)) {
            throw new RuntimeException("Credential is null");
        } else {
            return currentCredential;
        }
    }

    protected void setCredential(Credential credential) {
        if (!Objects.equals(this.currentCredential, credential)) {
            this.currentCredential = credential;
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setUp() {
        System.out.println("BaseMultipleUserTest.setUp");
    }

    public <P extends BasePage> P goTo(Class<P> clazz, Credential credential) {
        setCredential(credential);
        Page playwrightPage;
        try {
            playwrightPage = getAuthStateContext().newPage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up authentication context", e);
        }

        P newPage = createInstanceWithPage(clazz, playwrightPage);
        String url = getUrl(newPage);

        playwrightPage.navigate(url);
        playwrightPage.waitForLoadState(LoadState.DOMCONTENTLOADED);

        if (playwrightPage.url().contains("login") || playwrightPage.url().contains("sign_in") || playwrightPage.url().contains("signin")) {
            authenticate(playwrightPage);
            playwrightPage.navigate(url);
        }
        playwrightPage.waitForURL(url);
        this.page = playwrightPage;
        return newPage;
    }

    private <P> P createInstanceWithPage(Class<P> clazz, Page page) {
        try {
            return clazz.getDeclaredConstructor(Page.class).newInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create instance of " + clazz.getName(), e);
        }
    }
}
