package com.firsttrip.web;

import com.firsttrip.web.pages.BasePage;
import com.firsttrip.web.pages.LoginPage;
import com.firsttrip.web.utils.Credential;
import com.firsttrip.web.utils.FileUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseAuthenticatedTest extends BaseTest {
    final String AUTH_DIRECTORY = ".auth";

    protected abstract Credential credential();

    protected Path getUserAuthStatePath() {
        try {
            String username = credential().getEmail().split("@")[0];
            String authFileName = AUTH_DIRECTORY + "/" + username + "-" + "state.json";
            return Paths.get(authFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create auth state file", e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setUp() {
        try {
            page = getAuthStateContext().newPage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up authentication context", e);
        }
    }

    @Override
    public <P extends BasePage> P goTo(P page) {
        this.page.navigate(getUrl(page));
        this.page.waitForLoadState();
        if (this.page.url().contains("login") || this.page.url().contains("sign_in")) {
            authenticate(this.page);
        }
        return page;
    }

    protected void authenticate(Page authPage) {
        Credential credential = credential();
        if (credential == null) {
            throw new RuntimeException("Credential is null");
        }
        if (Files.exists(getUserAuthStatePath())) {
            try {
                Files.delete(getUserAuthStatePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        authPage.navigate(credential.getBaseUrl());

        LoginPage loginPage = new LoginPage(authPage);
        loginPage.getTopbar().clickSignIn()
                .clickEmailAddress()
                .fillEmail(credential.getEmail())
                .clickSignInWithPassword()
                .fillPassword(credential.getPassword())
                .clickSignIn();
        authPage.waitForLoadState();
    }

    protected String getUrl(BasePage page) {
        String url = page.getUrl();

        if (StringUtils.isBlank(url)) {
            url = credential().getBaseUrl();
        } else {
            if (!url.startsWith("http")) {
                url = credential().getBaseUrl() + url;
            }
        }
        return url;
    }

    protected BrowserContext getAuthStateContext() {
        BrowserContext context;
        if (Files.exists(getUserAuthStatePath())) {
            context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(getUserAuthStatePath()));
        } else {
            context = browser.newContext();
            Page authPage = context.newPage();
            authenticate(authPage);
            System.out.println(getUserAuthStatePath().toString());
            FileUtil.waitForFileExists(getUserAuthStatePath(), 10000, 500);
            context.storageState(new BrowserContext.StorageStateOptions().setPath(getUserAuthStatePath()));
        }

        return context;
    }
}
