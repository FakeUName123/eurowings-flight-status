package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class FlightStatusPage {

    @FindBy(css = ".cookie-consent--cta-accept")
    public
    WebElement cookieConsent;

    @FindBy(css = "button.o-compact-search__cta-button-button")
    List<WebElement> departureButtons;

    @FindBy(css = "div[data-component-name=input-text] input")
    WebElement departureSearchInput;

    @FindBy(css = "div.o-station-select__new-station-list__station")
    WebElement firstStationSelect;

    @FindBy(css = "body")
    WebElement bodyElement;

    @FindBy(css = "div[name=departureDate]")
    WebElement datePicker;

    @FindBy(css = ".o-search-flight-status__flight-search--criteria button[type=submit]")
    WebElement submitButton;

    @FindBy(css = "button.o-search-flight-status__date-navigation__date--active div.o-search-flight-status__date-navigation__date-day")
    WebElement currentDate;

    @FindBy(css = "div.o-search-flight-status__card-airports")
    List<WebElement> routes;

    private final WebDriver driver;

    public FlightStatusPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectDeparture(String airport) {
        departureButtons.get(0).click();
        departureSearchInput.sendKeys(airport);
        firstStationSelect.click();
        bodyElement.click();
    }

    public void selectDestination(String airport) {
        departureButtons.get(1).click();
        departureSearchInput.sendKeys(airport);
        firstStationSelect.click();
        bodyElement.click();
    }

    public void selectDate(String date) {
        datePicker.click();
        driver.findElement(By.cssSelector("input[value='"+date+"']")).click();
        bodyElement.click();
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void verifyFlightRoutesStatuses(String expectedDeparture, String expectedDestination, String expectedDate) {
        Assert.assertTrue(currentDate.getText().contains(expectedDate));
        routes.forEach(webElement -> {
            Assert.assertEquals(webElement.findElements(By.cssSelector("p")).get(0).getText(), expectedDeparture);
            Assert.assertEquals(webElement.findElements(By.cssSelector("p")).get(1).getText(), expectedDestination);
        });
    }

    public void verifySubmitButtonEnabled(boolean isEnabled) {
        if (isEnabled) {
            Assert.assertTrue(submitButton.isEnabled());
        } else {
            Assert.assertFalse(submitButton.isEnabled());
        }
    }


}
