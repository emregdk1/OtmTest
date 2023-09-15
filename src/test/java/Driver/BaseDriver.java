package Driver;

import com.thoughtworks.gauge.AfterStep;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeStep;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaseDriver {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected static WebDriver webDriver;

    @Step("Setup Driver <browserName>")
    public void initializeDriver(String driverName) {
        webDriver = DriverFactory.getDriver(driverName);
    }

    @AfterStep
    public void beforeSteps() throws InterruptedException {
        Thread.sleep(1500);

    }

    @AfterSuite
    public void closeDriver() {
        //webDriver.quit();
    }

    }

