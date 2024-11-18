package com.firsttrip.web;

import com.firsttrip.web.pages.FlightPage;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.Test;


import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlightTest extends BaseTest{
    @Test
    public void verifyOneWayFlightSearchForBusinessClassWith1Adult1ChildOf5_11YAnd1InfantShouldSucceed(){
        FlightPage flightPage =goTo(new FlightPage(page));
        String Date = "22";
        flightPage
                .clickFlight()
                .selectOneWay()
                .selectFromAirport("DAC")
                .selectToAirport("CXB");
        flightPage
                .clickDepartureDate()
                .clickNextMonth()
                .selectDate(Date);

        flightPage
                .clickTravellerClass()
                .selectChildFromTravelers();
        Random random = new Random();
        int randomChildAge = random.nextInt(5,11);
        System.out.println("childAges:" + randomChildAge);
        flightPage
                .chooseChildAge()
                .selectChildAge(randomChildAge)
                .selectInfantsFromTravelers()
                .selectBusinessClass()
                .clickSearchButton();

        assertThat(flightPage.getOneWayLocator()).isVisible();
        assertThat(flightPage.getBusinessClassLocator()).isVisible();
        this.page.waitForLoadState(LoadState.NETWORKIDLE);
        if (flightPage.getNoFlightsLocator().isVisible()) {
            assertThat(flightPage.getNoFlightsLocator()).isVisible();
            System.out.println("No flights found.");
        } else {
            System.out.println("Flights are available.");
            String flightSummary = flightPage.getFlightsCountLocator().textContent();
            System.out.println("Flight search successful! Summary of the Flight: " + flightSummary);
            assertThat(flightPage.getFlightsCountLocator()).isVisible();
        }

        }
}

