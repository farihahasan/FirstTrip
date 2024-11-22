package com.firsttrip.web;
import com.firsttrip.web.pages.FlightPage;
import com.firsttrip.web.pages.HomePage;
import org.testng.annotations.Test;
import java.util.Random;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlightTest extends BaseTest {
    @Test
    public void verifyOneWayFlightSearchForBusinessClassWith1Adult1Child5To11Years1InfantShouldSucceed() {
        String Date = "22";
        String Departure = "DAC";
        String Destination = "CXB";
        FlightPage.BookingClass bookingClass = FlightPage.BookingClass.BUSINESS;
        Random random = new Random();
        int randomChildAge = random.nextInt(5, 12);

        FlightPage flightPage = goTo(new FlightPage(page));
        flightPage
                .clickFlight()
                .selectOneWay()
                .selectFromAirport("DAC")
                .selectToAirport("CXB");

        HomePage homePage = new HomePage(page);
        homePage
                .clickDepartureDate()
                .clickNextMonth()
                .selectDate(Date);

        flightPage
                .clickTravellerClass()
                .selectChildFromTravelers()
                .chooseChildAge()
                .selectChildAge(randomChildAge)
                .selectInfantsFromTravelers()
                .selectBookingClass(bookingClass)
                .clickSearchButton();

        assertThat(flightPage.getOneWayLocator()).isVisible();
        assertThat(flightPage.verifyTotalTravellersForOneWayCount()).hasText("3 Travellers");
        flightPage
                .clickTravellerToVerify();
        assertThat(flightPage.verifyAdultTravellerCount()).hasText("1");
        assertThat(flightPage.verifyChildTravellerCount()).hasText("1");
        assertThat(flightPage.verifyInfantTravellerCount()).hasText("1");
        assertThat(flightPage.getBusinessClassLocator()).isVisible();
        try {
            assertThat(flightPage.getNoFlightsFoundText()).isVisible();
        } catch (AssertionError e) {
            assertThat(flightPage.getFlightsCountLocator()).isVisible();
            flightPage
                    .clickViewDetails();
            assertThat(flightPage.getDepartureAndDestinationLocator(Departure, Destination)).isVisible();
        }
    }

    @Test
    public void verifyRoundTripFlightSearchForPremiumEconomyClassWith1Adult1ChildOf5To11YearsShouldSucceed() {
        String Date = "20";
        String returnDate = "25";
        String Departure = "DAC";
        String Destination = "CXB";
        FlightPage.BookingClass bookingClass = FlightPage.BookingClass.PREMIUM_ECONOMY;
        Random random = new Random();
        int randomChildAge = random.nextInt(5, 12);

        FlightPage flightPage = goTo(new FlightPage(page));
        flightPage
                .clickFlight()
                .selectRoundTrip()
                .selectFromAirport("DAC")
                .selectToAirport("CXB");

        HomePage homePage = new HomePage(page);
        homePage
                .clickDepartureDate()
                .clickNextMonth()
                .selectDate(Date);
        homePage
                .clickReturnDate()
                .selectDate(returnDate);

        flightPage
                .clickTravellerClass()
                .selectChildFromTravelers()
                .chooseChildAge()
                .selectChildAge(randomChildAge)
                .selectBookingClass(bookingClass)
                .clickSearchButton();

        assertThat(flightPage.getRoundTripLocator()).isVisible();
        System.out.println(flightPage.verifyTotalTravellersForRoundTripCount());
        assertThat(flightPage.verifyTotalTravellersForRoundTripCount()).hasText("2 Travellers");
        flightPage
                .clickTravellerToVerify();
        assertThat(flightPage.verifyAdultTravellerCount()).hasText("1");
        assertThat(flightPage.verifyChildTravellerCount()).hasText("1");
        assertThat(flightPage.getPremiumEconomyClassLocator()).isVisible();
        try {
            assertThat(flightPage.getNoFlightsFoundText()).isVisible();
        } catch (AssertionError e) {
            assertThat(flightPage.getFlightsCountLocator()).isVisible();
            flightPage
                    .clickViewDetails();
            assertThat(flightPage.getDepartureAndDestinationLocator(Departure, Destination)).isVisible();
        }
    }

    @Test
    public void verifyRoundTripFlightSearchForFirstClassWith1Adult2ChildOf2To4And5To11Years1InfantShouldSucceed() {
        String Date = "20";
        String returnDate = "25";
        String Departure = "DAC";
        String Destination = "CXB";
        FlightPage.BookingClass bookingClass = FlightPage.BookingClass.FIRST_CLASS;
        Random random = new Random();
        int[] randomChildAges = {random.nextInt(2, 5), random.nextInt(5, 12)};

        FlightPage flightPage = goTo(new FlightPage(page));
        flightPage
                .clickFlight()
                .selectRoundTrip()
                .selectFromAirport("DAC")
                .selectToAirport("CXB");

        HomePage homePage = new HomePage(page);
        homePage
                .clickDepartureDate()
                .clickNextMonth()
                .selectDate(Date);
        homePage
                .clickReturnDate()
                .selectDate(returnDate);

        flightPage
                .clickTravellerClass()
                .addMultipleChildrenWithAges(randomChildAges)
                .selectInfantsFromTravelers()
                .selectBookingClass(bookingClass)
                .clickSearchButton();

        assertThat(flightPage.getFirstClassLocator()).isVisible();
        System.out.println(flightPage.verifyTotalTravellersForRoundTripCount());
        assertThat(flightPage.verifyTotalTravellersForRoundTripCount()).hasText("4 Travellers");
        flightPage
                .clickTravellerToVerify();
        assertThat(flightPage.verifyAdultTravellerCount()).hasText("1");
        assertThat(flightPage.verifyChildTravellerCount()).hasText("2");
        assertThat(flightPage.verifyInfantTravellerCount()).hasText("1");
        assertThat(flightPage.getPremiumEconomyClassLocator()).isVisible();
        try {
            assertThat(flightPage.getNoFlightsFoundText()).isVisible();
        } catch (AssertionError e) {
            assertThat(flightPage.getFlightsCountLocator()).isVisible();
            flightPage
                    .clickViewDetails();
            assertThat(flightPage.getDepartureAndDestinationLocator(Departure, Destination)).isVisible();
        }
    }
}
