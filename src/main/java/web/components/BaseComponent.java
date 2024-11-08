package com.firsttrip.web.components;

import com.microsoft.playwright.Locator;

public abstract class BaseComponent {
    protected Locator element;

    public BaseComponent(Locator element) {
        this.element = element;
    }
}
