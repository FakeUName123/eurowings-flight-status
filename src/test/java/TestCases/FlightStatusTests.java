import PageObjects.FlightStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class FlightStatusTests {
    private WebDriver driver;
    private FlightStatusPage fsp;
    private String todayDate;
    private String todayDateCalendarValue;
    private String departureAirport;
    private String destinationAirport;

    // Given
    @BeforeSuite
    public void setUpSuite() {
        departureAirport = "CGN";
        destinationAirport = "BER";
        todayDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        todayDateCalendarValue = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.setProperty("webdriver.chrome.driver", "C:\\ITmagination\\repos\\eurowings-flight-status\\src\\test\\utils\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        new WebDriverWait(driver, Duration.ofSeconds(5));
        fsp = PageFactory.initElements(driver, FlightStatusPage.class);
        driver.manage().window().maximize();
        driver.get("https://www.eurowings.com/en/information/at-the-airport/flight-status.html");
        // consent for cookies
        fsp.cookieConsent.click();
    }

    @BeforeMethod
    public void setUpMethod() {
        driver.navigate().refresh();
    }

    @Test
    public void testFlightStatusSunnyPath() {
        // When
        fsp.selectDeparture(departureAirport);
        fsp.selectDestination(destinationAirport);
        fsp.selectDate(todayDateCalendarValue);
        fsp.clickSubmit();
        // Then
        fsp.verifyFlightRoutesStatuses(departureAirport, destinationAirport, todayDate.substring(0, todayDate.length() - 5));
    }

    @Test
    public void testFlightStatusButtonDisabledWhenNoDeparture() {
        // When
        fsp.selectDestination(destinationAirport);
        fsp.selectDate(todayDateCalendarValue);
        // Then
        fsp.verifySubmitButtonEnabled(false);
    }

    @Test
    public void testFlightStatusButtonDisabledWhenNoDestination() {
        // When
        fsp.selectDeparture(departureAirport);
        fsp.selectDate(todayDateCalendarValue);
        // Then
        fsp.verifySubmitButtonEnabled(false);
    }

    @Test
    public void testFlightStatusButtonDisabledWhenNoDate() {
        // When
        fsp.selectDestination(destinationAirport);
        fsp.selectDeparture(departureAirport);
        // Then
        fsp.verifySubmitButtonEnabled(false);
    }

    @Test
    public void testFlightStatusButtonEnabledWhenFormFilled() {
        // When
        fsp.selectDestination(destinationAirport);
        fsp.selectDeparture(departureAirport);
        fsp.selectDate(todayDateCalendarValue);
        // Then
        fsp.verifySubmitButtonEnabled(true);
    }

    @AfterSuite()
    public void cleanUp(){
        driver.quit();
    }
}
