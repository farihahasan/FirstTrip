package com.firsttrip.web.components;

import com.microsoft.playwright.Locator;

public class ReactDatePicker extends DatePicker {
    public ReactDatePicker(Locator element) {
        super(element);
    }

    @Override
    public String currentDateSelector() {
        return "div.react-datepicker__day--today";
    }

    @Override
    public String dateSelector() {
        return "div.react-datepicker__day";
    }

    @Override
    public String dateRangeSelector(Range range) {
        return range == Range.FIRST
                ? ".react-datepicker__month-container:first-of-type"
                : ".react-datepicker__month-container:last-of-type";
    }

    @Override
    public String nextMonthSelector() {
        return "button.react-datepicker__navigation--next";
    }

    @Override
    public String prevMonthSelector() {
        return "button.react-datepicker__navigation--previous";
    }

    public String selectedDateSelector() {
        return "div.react-datepicker__day--selected";
    }
}
