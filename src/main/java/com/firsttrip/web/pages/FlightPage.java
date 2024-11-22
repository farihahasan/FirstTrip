package com.firsttrip.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;

public class FlightPage extends BasePage {
    private static final String PAGE_ID = "flight-page";

    public FlightPage(Page page) {
        super(page);
    }

    @Override
    public String getPageId() {
        return PAGE_ID;
    }

    @Override
    public String getUrl() {
        return "/flight";
    }

    public void clickViewDetails() {
        page.getByTestId("show-hide-button").first().click();
    }

    public FlightPage clickFlight() {
        page.getByTestId("search-form-tab-Flight").click();
        return this;
    }

    public Locator getDepartureAirportLocator() {
        return page.locator("div.col-span-1 .text-brand-7.lg\\:text-start").first();
    }

    public FlightPage selectOneWay() {
        page.getByTestId("One Way-input").click();
        return this;
    }

    public FlightPage selectRoundTrip() {
        page.getByTestId("Round Trip-input").click();
        return this;
    }

    public Locator getDestinationAirportLocator() {
        return page.locator("div.relative.col-span-1 .text-brand-7.md\\:truncate").first();
    }

    public FlightPage selectFromAirport(String departure) {
        Locator fromAirport = page.getByTestId("airport-city-input").first();
        fromAirport.click();
        fromAirport.fill(departure);
        page.getByText(departure).nth(0).click();
        return this;
    }

    public void selectToAirport(String departure) {
        Locator fromAirport = page.getByTestId("airport-city-input").last();
        fromAirport.click();
        fromAirport.fill(departure);
        page.getByText(departure).nth(0).click();
    }

    public Locator getDepartureDateLocator() {
        return page.locator("div.col-span-1 .text-brand-7").first();
    }

    public Locator getAdultLocator() {
        return page.locator("div.space-y-4 .col-span-2.text-brand-8").nth(0);
    }

    public Locator getChildLocator() {
        return page.locator("div.space-y-4 .col-span-2.text-brand-8").nth(1);
    }

    public Locator getInfantLocator() {
        return page.locator("div.space-y-4 .col-span-2.text-brand-8").nth(2);
    }

    public FlightPage clickTravellerClass() {
        page.getByText("1 Traveller").click();
        return this;
    }

    public Locator getNoFlightsFoundText() {
        return page.getByText("No flights found");
    }

    public FlightPage selectChildFromTravelers() {
        page.getByTestId("children-number-add-button").click();
        return this;
    }

    public FlightPage chooseChildAge() {
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

    public FlightPage selectBookingClass(BookingClass bookingClass) {
        page.locator("[data-testid='booking-class-selector']").nth(bookingClass.getId()).click();
        return this;
    }

    public void clickSearchButton() {
        page.getByTestId("search-flight-button").click();
    }

    public Locator getOneWayLocator() {
        return page.getByText("One Way").first();
    }

    public Locator getRoundTripLocator() {
        return page.getByText("Round Trip").first();
    }

    public Locator verifyTotalTravellersForOneWayCount() {
        return page.locator(".font-medium.text-brand-8").nth(3);
    }

    public Locator verifyTotalTravellersForRoundTripCount() {
        return page.locator(".font-medium.text-brand-8").last();
    }

    public void clickTravellerToVerify() {
        page.locator(".font-medium.text-brand-8").last().click();
    }

    public Locator verifyAdultTravellerCount() {
        return page.locator(".flex.gap-4").nth(0);
    }

    public Locator verifyChildTravellerCount() {
        return page.locator(".flex.gap-4").nth(1);
    }

    public Locator verifyInfantTravellerCount() {
        return page.locator(".flex.gap-4").nth(2);
    }

    public Locator getBusinessClassLocator() {
        return page.getByText("Business").first();
    }

    public Locator getPremiumEconomyClassLocator() {
        return page.getByText("Premium Economy").first();
    }

    public Locator getFlightsCountLocator() {
        return page.locator(".flex.items-center.gap-4.text-brand-8");
    }

    public Locator getDepartureAndDestinationLocator(String Departure, String Destination) {
        return page.locator(".flex.w-full.gap-4.py-4");
    }

    @Getter
    public enum BookingClass {
        ECONOMY(0),
        PREMIUM_ECONOMY(1),
        BUSINESS(2),
        FIRST_CLASS(3);

        private final int id;

        BookingClass(int id) {
            this.id = id;
        }
    }
}
