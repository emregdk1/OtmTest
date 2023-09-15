package StepImplementation;

import Driver.BaseDriver;
import Constant.Constants;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static Constant.Constants.*;
import static org.junit.Assert.assertFalse;

public class StepImplementation extends BaseDriver {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Step("Go to <url>")
    public void GoUrl(String url) {
        webDriver.get(url);
    }

    @Step({"Focus on last tab",
            "Son sekmeye odaklan"})
    public void chromeFocusLastTab() {
        logger.info("Entered.");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    @Step("<elementName> Elementini sayfada bul")
    public WebElement findElement(String elementName) {
        return webDriver.findElement(By.xpath(byMap.get(elementName)));

    }

    @Step("<int> saniye Bekle")
    public void waitBySeconds(int seconds) throws InterruptedException {
        logger.info("Entered.");
        Thread.sleep(seconds * 1000L);
    }


    @Step("<elementName> Elementine tıklanılır")
    public void clickElement(String elementName) {
        logger.info("Entered.");
        findElement(elementName).click();
    }

    @Step("<elementName> Elementine <text> değerini yaz")
    public void sendKeys(String elementName, String text) throws InterruptedException {
        logger.info("Entered.");
        waitBySeconds(1);
        findElement(elementName).sendKeys(text);
    }

    @Step("<elementName> Elementine scroll et")
    public void scrollToElement(String elementName) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", findElement(elementName));
    }

    @Step("url <text> textini içeriyor mu")
    public void checkIfUrlContainsText(String text) {
        Assert.assertTrue(webDriver.getCurrentUrl().contains(text));
    }

    @Step("js ile tikla <key>")
    public void jsclick(String key) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].click();", webDriver.findElement(By.xpath(byMap.get(key))));
    }

    @Step("<elementName> Elementi görülürse tıklanılır")
    public void ifElementExistsClick(String key) {
        try {
            jsclick(key);
        } catch (TimeoutException e) {
            System.out.println("Element bulunamadı");
        }
    }

    @Step("Select <key> element at <index> index in")
    public void selectElementAtIndex(String key, String index) {
        logger.info("Entered.");
        try {
            int x = Integer.parseInt(index) - 1;
            List<WebElement> elements = webDriver.findElements(By.xpath(byMap.get(key)));

            if (x >= 0 && x < elements.size()) {
                logger.info("Entered.");
                WebElement selectedElement = elements.get(x);
                scrollIntoKeyByJs(selectedElement);
                waitBySeconds(2);
                selectedElement.click();
            } else {
                logger.error("Index is out of bounds: {}", x);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid index value: {}", index);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step({"Check if element <key> exists in",
            "Element var mı kontrol et <key> in"})
    public WebElement getElementWithKeyIfExists(String key) throws InterruptedException {
        logger.info("Entered. Parameters; key: {}", key);
        WebElement webElement;
        int loopCount = 0;
        while (loopCount < 3) {
            try {
                webElement = webDriver.findElement(By.xpath(byMap.get(key)));
                logger.info(key + " elementi bulundu.");
                return webElement;
            } catch (WebDriverException e) {
            }
            loopCount++;
            waitBySeconds(60);
        }
        assertFalse(Boolean.parseBoolean("Element: '" + key + "' doesn't exist."));
        return null;
    }

    public WebElement findElement(By by) {
        logger.info("Entered. Parameters; key: {}", by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(by));
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public void javaScriptClicker(WebElement element) {
        logger.info("Entered. Parameters; element: {}", element);
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoKeyByJs(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
    }
}
