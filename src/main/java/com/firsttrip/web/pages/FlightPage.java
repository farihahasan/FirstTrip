package com.firsttrip.web.pages;

import com.firsttrip.web.components.ReactDatePicker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FlightPage extends BasePage {
    public FlightPage(Page page) {
        super(page);
    }

    @Override
    public String getPageId() {
        return "";
    }

    @Override
    public String getUrl() {
        return "/flight";
    }


    public FlightPage clickFlight() {
        page.getByTestId("search-form-tab-Flight").click();
        return this;
    }

    public FlightPage selectOneWay() {
        page.getByTestId("One Way-input").click();
        return this;
    }

    public FlightPage selectFromAirport(String departure) {
        Locator fromAirport = page.getByTestId("airport-city-input").first();
        fromAirport.click();
        fromAirport.fill(departure);
        page.getByText(departure).nth(0).click();
        return this;

    }

    public FlightPage selectToAirport(String departure) {
        Locator fromAirport = page.getByTestId("airport-city-input").last();
        fromAirport.click();
        fromAirport.fill(departure);
        page.getByText(departure).nth(0).click();
        return this;
    }

    public ReactDatePicker clickDepartureDate() {
        page.locator(".one-way-date-picker .react-datepicker__input-container").first().click();
        return getDatePicker();
    }

    public ReactDatePicker getDatePicker() {
        return new ReactDatePicker(page.locator(".react-datepicker"));
    }
    public FlightPage clickTravellerClass() {
        page.getByText("1 Traveller").click();
        return this;
    }
    public FlightPage selectChildFromTravelers() {
       page.getByTestId("children-number-add-button").click();
        return this;
    }
    public FlightPage chooseChildAge(){
        page.locator(".cursor-pointer.rounded-md").click();
        return this;
    }

    public FlightPage selectChildAge(int childAge) {
        page.locator("div.bg-brand-3.rounded-md >> text=" + childAge).click();
        return this;
    }
    public FlightPage selectInfantsFromTravelers() {
        page.getByTestId("infant-number-add-button").click();
        return this;
    }
    public FlightPage selectBusinessClass() {
        page.getByTestId("booking-class-selector").nth(2).click();
        return this;
    }
    public FlightPage clickSearchButton() {
        page.getByTestId("search-flight-button").click();
        return this;
    }
    public Locator getOneWayLocator(){
        return page.getByText("One Way").first();
    }
    public Locator getBusinessClassLocator(){
        return page.getByText("Business").first();
    }
    public Locator getNoFlightsLocator() {
        return page.getByText("No flights found");
    }
    public Locator getFlightsCountLocator() {
        return page.locator(".flex.items-center.gap-4.text-brand-8");
    }

}
