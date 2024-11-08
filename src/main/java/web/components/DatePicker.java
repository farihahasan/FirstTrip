package com.firsttrip.web.components;

import com.microsoft.playwright.Locator;

public abstract class DatePicker extends BaseComponent {
    public DatePicker(Locator element) {
        super(element);
    }

    public Locator dateLocator = this.element;

    public abstract String currentDateSelector();

    public abstract String dateSelector();

    public abstract String dateRangeSelector(Range range);

    public abstract String nextMonthSelector();

    public abstract String prevMonthSelector();

    public abstract String selectedDateSelector();

    public boolean hasRange() {
        return this.element.locator(dateRangeSelector(Range.FIRST)).first().isVisible()
                && this.element.locator(dateRangeSelector(Range.SECOND)).first().isVisible();
    }

    public void selectDate(String date) {
        dateLocator.locator(dateSelector()).getByText(date).first().click();
    }

    public DatePicker selectRange(Range range) {
        dateLocator = this.element.locator(dateRangeSelector(range));
        return this;
    }

    public void clickCurrentDate() {

        this.element.locator(currentDateSelector()).click();
    }

    public DatePicker clickNextMonth() {
        Locator next = this.element.locator(nextMonthSelector());
        if (next.first().isVisible()) {
            next.click();
        }
        return this;
    }

    public boolean hasPreviousButton() {
        return this.element.locator(prevMonthSelector()).first().isVisible();
    }

    public DatePicker clickPrevious() {
        Locator next = this.element.locator(prevMonthSelector());
        if (next.first().isVisible()) {
            next.click();
        }
        return this;
    }

    public String getSelectedDate() {
        return this.element.locator(selectedDateSelector()).first().textContent();
    }

    public String getCurrentDate() {
        return this.element.locator(currentDateSelector()).first().textContent();
    }

    public enum Range {
        FIRST,
        SECOND
    }
}
