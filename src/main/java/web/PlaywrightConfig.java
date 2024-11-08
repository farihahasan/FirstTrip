package com.firsttrip.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Properties;

public class PlaywrightConfig {
    private final Properties config = new Properties();

    public PlaywrightConfig() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/config.properties");
            if (inputStream == null) {
                throw new RuntimeException("Failed to load config.properties");
            }
            config.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public Browser createBrowser(Playwright playwright) {
        BrowserType browserType = switch (getBrowserType().toLowerCase()) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(isHeadless());
        if (StringUtils.isNotBlank(getChannel())) {
            launchOptions.setChannel(getChannel());
        }
        if (getSlowMo() != null) {
            launchOptions.setSlowMo(getSlowMo());
        }
        if (getTimeout() != null) {
            launchOptions.setTimeout(getTimeout());
        }
        return browserType.launch(launchOptions);
    }

    public String getBrowserType() {
        return StringUtils.isBlank(config.getProperty("browserType")) ? "chromium" : config.getProperty("browserType");
    }

    public String getChannel() {
        return config.getProperty("channel");
    }

    public Double getSlowMo() {
        return StringUtils.isNotBlank(config.getProperty("slowMo")) ? Double.valueOf(config.getProperty("slowMo")) : null;
    }

    public Double getTimeout() {
        return StringUtils.isNotBlank(config.getProperty("timeout")) ? Double.valueOf(config.getProperty("timeout")) : null;
    }

    public Boolean videoRecordingEnabled() {
        return Boolean.parseBoolean(config.getProperty("videoRecording"));
    }

    public String getBaseUrl() {
        return config.getProperty("baseUrl");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(config.getProperty("headless"));
    }
}
