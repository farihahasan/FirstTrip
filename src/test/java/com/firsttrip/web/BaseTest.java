package com.firsttrip.web;

import com.firsttrip.web.pages.BasePage;
import com.firsttrip.web.utils.Credential;
import com.firsttrip.web.utils.CredentialStore;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public abstract class BaseTest {
    private static final String KEY_CREDENTIALS_FILE = "external.credentials.file";
    private static final String DEFAULT_CREDENTIALS_FILE = "credentials.properties";

    private static final String KEY_SUFFIX_BASE_URL = ".baseUrl";
    private static final String KEY_SUFFIX_EMAIL = ".email";
    private static final String KEY_SUFFIX_MOBILE = ".mobile";
    private static final String KEY_SUFFIX_PASSWORD = ".password";

    private static final String DEFAULT_CREDENTIAL = "default";
    private static final String DEFAULT_SOME_CREDENTIAL = "default.smoke";

    private static final String RECORD_VIDEO_DIR = "build/reports/video";

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected PlaywrightConfig config;

    protected Credential loadDefaultCredential() {
        return loadCredential(DEFAULT_CREDENTIAL);
    }

    protected Credential loadSmokeCredential() {
        return loadCredential(DEFAULT_SOME_CREDENTIAL);
    }

    protected Credential loadCredential(String name) {
        CredentialStore store = CredentialStore.instance();

        if (store.isNotPopulated()) {
            populateCredentialStore();
        }

        String baseUrl = store.get(name + KEY_SUFFIX_BASE_URL);
        String email = store.get(name + KEY_SUFFIX_EMAIL);
        String mobile = store.get(name + KEY_SUFFIX_MOBILE);
        String password = store.get(name + KEY_SUFFIX_PASSWORD);
        if (StringUtils.isNoneBlank(baseUrl)) {
            return new Credential(baseUrl, email, mobile, password);
        }
        throw new IllegalArgumentException("Credential not found: " + name);
    }

    private void populateCredentialStore() {
        try {
            Properties properties = new Properties();
            String externalCredentialsFile = System.getProperty(KEY_CREDENTIALS_FILE);
            if (StringUtils.isNoneBlank(externalCredentialsFile)) {
                if (new File(externalCredentialsFile).exists()) {
                    properties.load(getClass().getClassLoader().getResourceAsStream(externalCredentialsFile));
                } else {
                    System.out.println("External credentials file not found: " + externalCredentialsFile);
                }
            } else {
                properties.load(getClass().getClassLoader().getResourceAsStream(DEFAULT_CREDENTIALS_FILE));
            }
            CredentialStore store = CredentialStore.instance();
            properties.forEach((key, value) -> store.put(key.toString(), value.toString()));
            store.setPopulated(true);
        } catch (Exception e) {
            System.out.println("Failed to populate credential store: " + e.getMessage());
        }
    }

    public <P extends BasePage> P goTo(P page, String absoluteUrl) {
        this.page.navigate(absoluteUrl);
        return page;
    }

    public <P extends BasePage> P goTo(P page) {
        String url = page.getUrl();
        if (StringUtils.isBlank(url)) {
            url = config.getBaseUrl();
        } else if (!url.startsWith("http")) {
            url = config.getBaseUrl() + url;
        }
        return goTo(page, url);
    }

    public <P extends BasePage> P goTo(Page page, P otherPage) {
        page.navigate(otherPage.getUrl());
        return otherPage;
    }

    @BeforeClass(alwaysRun = true)
    public void globalSetup() {
        playwright = Playwright.create();
        config = new PlaywrightConfig();
        browser = config.createBrowser(playwright);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        if (config.videoRecordingEnabled()) {
            contextOptions
                    .setRecordVideoDir(Paths.get(RECORD_VIDEO_DIR));
        }
        context = browser.newContext(contextOptions);
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String testName = result.getName();
                String screenShotsDir = "build/reports/screenshots";

                String screenshotPath = screenShotsDir + "/" + testName + "_" + timeStamp + ".png";

                // Ensure directory exists
                Path path = Paths.get(screenShotsDir);
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }

                // Take screenshot
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true));

                System.out.println("Screenshot saved at: " + screenshotPath);
            } catch (Exception e) {
                System.out.println("Failed to take screenshot: " + e.getMessage());
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void recordVideoOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            if (page.video() != null) {
                Path videoPath = page.video().path();
                Path targetPath = Paths.get(RECORD_VIDEO_DIR, result.getName() + ".webm");
                try {
                    if (Files.notExists(targetPath.getParent())) {
                        Files.createDirectories(targetPath.getParent());
                    }
                    Files.move(videoPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Video saved at: " + targetPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (page != null && !page.isClosed()) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
    }

    @AfterClass(alwaysRun = true)
    public void globalTeardown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
