package base;

import base.Drivers;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected static WebDriver driver;
    public static Actions actions;
    //protected Logger utils.logger = LoggerFactory.getLogger(getClass());
    DesiredCapabilities capabilities;
    String browserName = System.getProperty("browserName");
    String javaVersion = System.getProperty("java.version");
    String selectPlatform = "win";

    @Given("Setup Driver \"(chrome|firefox|safari)\"$")
    public void setUp(String browserNameLocale) {
        logger.info("************************************  BeforeScenario  ************************************");
        logger.info("Local cihazda " + selectPlatform + " ortamında " + browserNameLocale + " browserında test ayağa kalkacak");
        logger.info("Java version: " + javaVersion);
        if ("win".equalsIgnoreCase(selectPlatform)) {
            driver = Drivers.getDriver(browserNameLocale).getLocale(capabilities);
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        } else if ("mac".equalsIgnoreCase(selectPlatform)) {
            driver = Drivers.getDriver(browserNameLocale).getLocale(capabilities);
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        actions = new Actions(driver);
    }


    @After
    public void closeDriver() {
        //webDriver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }

}

