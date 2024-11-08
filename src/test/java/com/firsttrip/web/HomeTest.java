package com.firsttrip.web;

import com.firsttrip.web.components.DatePicker;
import com.firsttrip.web.pages.HomePage;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertTrue;

public class HomeTest extends BaseTest {
    @Test
    public void searchShouldSucceed() {
        String selectDate = "21";
        HomePage homePage = goTo(new HomePage(page));
        //homePage.clickDepartureDate().clickCurrentDate();

        homePage.clickDepartureDate().clickNextMonth().selectDate(selectDate);
        // assertEquals(homePage.clickDepartureDate().getSelectedDate(), selectDate);

        homePage.clickReturnDate().clickNextMonth();
        assertTrue(homePage.getDatePicker().hasRange());

        homePage.getDatePicker().selectRange(DatePicker.Range.SECOND).selectDate(selectDate);
        homePage.clickReturnDate();


        homePage.clickDepartureDate();
        DatePicker datePicker = homePage.getDatePicker();
        assertNotSame(datePicker.getSelectedDate(), datePicker.getCurrentDate());

        System.out.println("Selected Date: " + datePicker.getSelectedDate());
        System.out.println("Current Date: " + datePicker.getCurrentDate());

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
        System.out.println("Screenshot taken");

    }
    @Test
    public void redirectedToTheSignUpPage(){
        HomePage homePage = goTo(new HomePage(page));
        homePage.clickSignUp();
        assertThat(homePage.getSignUpTextLocatorForVerify()).isVisible();
    }

}
