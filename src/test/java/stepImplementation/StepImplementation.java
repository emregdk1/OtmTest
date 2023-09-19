package stepImplementation;

import base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ClassList;

import java.time.Duration;

import static pages.Constants.*;
import static org.junit.Assert.assertFalse;

public class StepImplementation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    WebDriver webDriver;
    Action action;

    public StepImplementation() {
        this.webDriver = BaseTest.getDriver();
        ClassList.getInstance().put(this);
    }

    @And("I go to {string}")
    public void goToUrl(String url) {
        logger.info("Entered. parameters; url{}:", url);
        webDriver.get(url);
    }
//
//    @Given("I focus on the last tab")
//    public void focusOnLastTab() {
//        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
//        webDriver.switchTo().window(tabs.get(tabs.size() - 1));
//    }
//
//    public WebElement findElement(String elementName) {
//        return webDriver.findElement(By.xpath(byMap.get(elementName)));
//    }
//
    @Given("I wait for {string} seconds")
    public void waitBySeconds(String seconds) throws InterruptedException {
        waitBySeconds(Integer.parseInt(seconds));
    }

    @And("I click the {string} element")
    public void clickTheElement(String elementName) {
        findElement(By.xpath(byMap.get(elementName))).click();
    }
//
////    @When("I send the text {String} to the {String} element")
////    public void sendKeys(String elementName, String text) {
////        //waitBySeconds(1);
////        findElement(elementName).sendKeys(text);
////    }
//
//    @When("I scroll to the {String} element")
//    public void scrollToElement(String elementName) {
//        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", findElement(elementName));
//    }
//
//    @Then("the URL should contain the text {String}")
//    public void checkIfUrlContainsText(String text) {
//        Assert.assertTrue(webDriver.getCurrentUrl().contains(text));
//    }
//
////    @When("I click the {String} element using JavaScript")
////    public void jsclick(String key) {
////        JavascriptExecutor js = (JavascriptExecutor) webDriver;
////        js.executeScript("arguments[0].click();", webDriver.findElement(By.xpath(byMap.get(key))));
////    }
////
////    @When("I click the {String} element if it exists")
////    public void ifElementExistsClick(String key) {
////        try {
////            jsclick(key);
////        } catch (TimeoutException e) {
////            System.out.println("Element bulunamadı");
////        }
////    }
//
////    @When("I select the {String} element at the {String} index")
////    public void selectElementAtIndex(String key, String index) throws InterruptedException {
////        int x = Integer.parseInt(index) - 1;
////        List<WebElement> elements = webDriver.findElements(By.xpath(byMap.get(key)));
////
////        if (x >= 0 && x < elements.size()) {
////            WebElement selectedElement = elements.get(x);
////            scrollIntoKeyByJs(selectedElement);
////            waitBySeconds(2);
////            selectedElement.click();
////        } else {
////            System.out.println("Index is out of bounds: " + x);
////        }
////    }
//
//    @Then("the {String} element should exist")
//    public WebElement getElementWithKeyIfExists(String key) throws InterruptedException {
//        WebElement webElement;
//        int loopCount = 0;
//        while (loopCount < 3) {
//            try {
//                webElement = findElement(By.xpath(byMap.get(key)));
//                System.out.println(key + " elementi bulundu.");
//                return webElement;
//            } catch (WebDriverException e) {
//            }
//            loopCount++;
//            waitBySeconds(60);
//        }
//        assertFalse(Boolean.parseBoolean("Element: '" + key + "' doesn't exist."));
//        return null;
//    }
//
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
//
//    public void javaScriptClicker(WebElement element) {
//        utils.logger.info("Entered. Parameters; element: {}", element);
//        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
//        executor.executeScript("arguments[0].click();", element);
//    }
//
//    public void scrollIntoKeyByJs(WebElement webElement) {
//        utils.logger.info("Entered. Parameters; webElement: {}", webElement);
//        ((JavascriptExecutor) webDriver).executeScript(
//                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
//                webElement);
//    }
//
    public void waitBySeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }
}
