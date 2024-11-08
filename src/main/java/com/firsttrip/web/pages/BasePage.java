package com.firsttrip.web.pages;

import com.firsttrip.web.components.Topbar;
import com.microsoft.playwright.Page;

import java.util.Objects;

public abstract class BasePage {
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public abstract String getPageId();

    public abstract String getUrl();

    public String currentPageId() {
        return page.locator("body").getAttribute("data-test-id");
    }

    public boolean isPage(String pageId) {
        return Objects.equals(pageId, currentPageId());
    }

    public boolean isAtPage() {
        return isPage(getPageId());
    }

    public String getPageTitle() {
        return page.title();
    }

    public Topbar getTopbar() {
        return new Topbar(page.locator("#header"));
    }
}
