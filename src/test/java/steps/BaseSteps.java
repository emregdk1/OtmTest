package steps;

import base.BaseTest;
import com.github.javafaker.Faker;
import constants.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.Page;
import utils.ClassList;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseSteps {
    public WebDriver driver;
    public Actions actions;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public BaseSteps() {
        this.driver = BaseTest.getDriver();
        this.actions = new Actions(this.driver);
        ClassList.getInstance().put(this);
    }

    public WebElement findElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        By infoParam = Page.createElement(pageName, key);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public WebElement findElement(By infoParam) {
        logger.info("Entered. Parameters; infoParam: {}", infoParam);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public List<WebElement> findElements(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        return driver.findElements(Page.createElement(pageName, key));
    }

    public List<WebElement> findElements(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        return driver.findElements(by);
    }

    public void clickElement(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        try {
            clickElement(findElement(by));
        } catch (ElementClickInterceptedException exception) {
            scrollByJs(by);
            clickElement(findElement(by));
        }

    }

    public void clickElement(WebElement element) {
        logger.info("Entered. Parameters; element: {}", element);
        try {
            element.click();
            logger.info("Clicks on the element. Parameters; element: {}", element);
        } catch (ElementClickInterceptedException exception) {
            javaScriptClicker(element);
            logger.info("Caught. Clicks on the element with JS ");
        }
    }

    public void hoverOnTheElementAndClick(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        try {
            hoverElement(findElement(by));
            clickElement(by);
            waitForPageToCompleteState();
        } catch (ElementClickInterceptedException exception) {
            logger.info(exception + " has caught");
            scrollByJs(by);
            hoverElement(findElement(by));
            clickElement(by);
            waitForPageToCompleteState();
        }
    }

    public void clickByJs(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", webElement);
        waitForPageToCompleteState();
    }

    public void clickByJs(By by) {
        logger.info("Entered. Parameters; webElement: {}", by);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", findElement(by));
        waitForPageToCompleteState();
    }

    public void scrollAndClickByJs(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        executor.executeScript("arguments[0].click();", webElement);
    }

    public void doubleClick(WebElement elementLocator) {
        logger.info("Entered. Parameters; elementLocator: {}", elementLocator);
        actions.doubleClick(elementLocator).perform();
    }


    public void hoverElement(WebElement element) {
        logger.info("Entered. Parameters; element: {}", element);
        actions.moveToElement(element).build().perform();
    }

    public void hoverElementBy(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement webElement = findElement(key, pageName);
        actions.moveToElement(webElement).build().perform();
    }

    public void sendKeys(By by, String text) {
        logger.info("Entered. Parameters; by: {}, text: {}", by, text);
        findElement(by).sendKeys(text);
    }

    public void sendKeyESC(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        findElement(key, pageName).sendKeys(Keys.ESCAPE);
    }

    public void sendKeyESCByAction() {
        logger.info("Entered");
        actions.sendKeys(Keys.ESCAPE);
        logger.info("Clicked to the Escape Key");
    }

    public void sendKeysJavascript(String text, String key, String pageName) {
        logger.info("Entered. Parameters; text: {}, key: {}, pageName: {}", text, key, pageName);
        WebElement webElement = driver.findElement(Page.createElement(pageName, key));
        JavascriptExecutor ex = (JavascriptExecutor) driver;
        ex.executeScript("arguments[0].value='" + text + "';", webElement);
    }

    public void sendKeysJavascript(String text, By by) {
        logger.info("Entered. Parameters; text: {}, by: {}", text, by);
        WebElement webElement = driver.findElement(by);
        JavascriptExecutor ex = (JavascriptExecutor) driver;
        ex.executeScript("arguments[0].value='" + text + "';", webElement);
    }


    public void sendKeysWithoutElement(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        actions.sendKeys(text).perform();
    }

    public void writeToElementByJs(WebElement webElement, String text) {
        logger.info("Entered. Parameters; webElement: {}, text: {}", webElement, text);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=arguments[1]", webElement, text);
    }

    public void javaScriptClicker(WebDriver driver, WebElement element) {
        logger.info("Entered. Parameters; driver: {}, element: {}", driver, element);
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    public void javaScriptClicker(WebElement element) {
        logger.info("Entered. Parameters; element: {}", element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public boolean isDisplayed(WebElement element) {
        logger.info("Entered. Parameters; element: {}", element);
        return element.isDisplayed();
    }

    public boolean isDisplayedBy(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        try {
            waitByMilliSeconds(2000);
            return driver.findElement(by).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            logger.info(e + " caught");
            return false;
        }
    }

    public boolean isEnabledBy(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        try {
            return driver.findElement(by).isEnabled();
        } catch (TimeoutException | NoSuchElementException e) {
            logger.info(e + " caught");
            return false;
        }
    }

    public String getElementAttributeValue(String key, String attribute, String pageName) {
        logger.info("Entered. Parameters; key: {}, attribute: {}, pageName: {}", key, attribute, pageName);
        return findElement(key, pageName).getAttribute(attribute);
    }

    public String getElementAttributeValue(By by, String attribute) {
        logger.info("Entered. Parameters; By: {}, attribute: {}", by, attribute);
        return findElement(by).getAttribute(attribute);
    }


    public boolean checkElementContainsAttribute(By by, String attribute) {
        String attributeValue = findElement(by).getAttribute(attribute);
        return attributeValue != null;
    }


    public void waitByMilliSeconds(long milliseconds) {
        logger.info("Entered. Parameters; milliseconds: {}", milliseconds);
        try {
            logger.info(milliseconds + " milisaniye bekleniyor.");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ZrandomPick(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        List<WebElement> elements = findElements(key, pageName);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        elements.get(index).click();
    }

    public void randomPick(By by) {
        logger.info("Entered. Parameters; key: {}", by);
        List<WebElement> elements = findElements(by);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        elements.get(index).click();
    }

    public void randomPickTimes(By by, int times) {
        logger.info("Entered. Parameters; by: {},times: {}", by, times);
        for (int i = 0; i < times; i++) {
            List<WebElement> elements = findElements(by);
            Random random = new Random();
            int index = random.nextInt(elements.size());
            hoverElement(elements.get(index));
            clickElement(elements.get(index));
            waitByMilliSeconds(500);
        }
    }

    public JavascriptExecutor getJSExecutor() {
        logger.info("Entered.");
        return (JavascriptExecutor) driver;
    }

    public Object executeJS(String script, boolean wait) {
        logger.info("Entered. Parameters; script: {}, wait: {}", script, wait);
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }

    public void scrollTo(int x, int y) {
        logger.info("Entered. Parameters; x: {}, y: {}", x, y);
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(script, true);
    }

    //?
    public boolean shouldSeeText(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='" + text + "']")));
            return true;
        } catch (TimeoutException e) {
            logger.warn(text + " yazısı görünmedi.");
        }
        return false;
    }

    //?
    public boolean shouldSeeContainingText(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(.,'" + text + "')]")));
            return true;
        } catch (TimeoutException e) {
            logger.warn(text + " yazısı görünmedi.");
        }
        return false;
    }

    public WebElement scrollToElementToBeVisible(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        return scrollToElementToBeVisible(Page.createElement(pageName, key));
    }

    public WebElement scrollToElementToBeVisible(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        return scrollToElementToBeVisible(findElement(by));
    }

    public WebElement scrollToElementToBeVisible(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        if (webElement != null) {
            scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 200);
        }
        waitByMilliSeconds(500);
        return webElement;
    }

    public Long getTimestamp() {
        logger.info("Entered.");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getTime());
    }

    public int chooseDate(int number) {
        logger.info("Entered. Parameters; number: {}", number);
        LocalDateTime date = LocalDateTime.now().plusDays(number);
        return date.getDayOfMonth();
    }

    // FIXME: 1.09.2023  kontrol edilecek yukarıdaki alanla
    public void writeATextToTheFieldAndSelectTheText(String text, By comboBoxSelection, By searchField, By comboBox) {
        logger.info("Entered. Parameters; text: {},comboBoxSelection: {} ,searchField: {}, comboBox: {}", text, comboBoxSelection, searchField, comboBox);
        findElement(comboBoxSelection).click();
        findElements(searchField).clear();
        findElement(searchField).sendKeys(text);
        waitByMilliSeconds(1000);
        List<WebElement> comboBoxElement = findElements(comboBox);
        if (!(isDisplayed(comboBoxElement.get(0)))) {
            waitForTheElement(comboBoxElement.get(0));
        }
        if (comboBoxElement.size() != 0) {
            String savedText = comboBoxElement.get(0).getText();
            clickElement(comboBoxElement.get(0));
            logger.info("Text: {} has selected on the key: {}", savedText, comboBox);
        }
    }

    // FIXME: 1.09.2023  kontrol edilecek yukarıdaki alanla
    public void writeATextToTheFieldAndSelectTheText(String text, By searchField, By comboBox) {
        logger.info("Entered. Parameters; text: {}, searchField: {}, comboBox: {}", text, searchField, comboBox);
        findElements(searchField).clear();
        findElement(searchField).sendKeys(text);
        waitByMilliSeconds(1000);
        List<WebElement> comboBoxElement = findElements(comboBox);
        if (!(isDisplayed(comboBoxElement.get(0)))) {
            waitForTheElement(comboBoxElement.get(0));
        }
        if (comboBoxElement.size() != 0) {
            String savedText = comboBoxElement.get(0).getText();
            clickElement(comboBoxElement.get(0));
            logger.info("Text: {} has selected on the key: {}", savedText, comboBox);
        }
    }

    public void selectADayAccordingToRequest(int number, String key, String pageName) {
        logger.info("Entered. Parameters; number: {}, key: {}, pageName: {}", number, key, pageName);
        List<WebElement> elements = findElements(key, pageName);
        if (!isDisplayed(findElement(key, pageName))) {
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(Page.createElement(pageName, key)));
        }
        if (elements.isEmpty()) {
            throw new RuntimeException("Tarihler gözükmemekte");
        }
        for (WebElement element : elements) {
            if (element.getText().equalsIgnoreCase(String.valueOf(number))) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
                logger.info("clicked to the date");
                break;
            }
        }
    }

    public void waitForTheElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.presenceOfElementLocated(Page.createElement(pageName, key)));
    }

    public void waitForTheElement(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForTheElement(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForInvisibilityOfTheElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Page.createElement(pageName, key)));
    }

    public void waitForInvisibilityOfTheElement(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForInvisibilityOfTheElement(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void smallWaitForTheElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(Page.createElement(pageName, key)));
    }

    public void goToUrl(String url) {
        logger.info("Entered. Parameters; url: {}", url);
        driver.get(url);
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignore) {
            // This is the way to know it wasn't there
        }
        logger.info(url + " adresine gidiliyor.");
    }

    public String getCurrentUrl() {
        logger.info("Entered.");
        return driver.getCurrentUrl();
    }

    public void setAttribute(WebElement webElement, String attributeName, String value) {
        logger.info("Entered. Parameters; webElement: {}, attributeName: {}, value: {}", webElement, attributeName, value);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + value + "')",
                webElement);
    }

    public void refreshPage() {
        logger.info("Entered.");
        driver.navigate().refresh();
    }


    public void chromeZoomOut(String value) {
        logger.info("Entered. Parameters; value: {}", value);
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("document.body.style.zoom = '" + value + "%'");
    }

    public void chromeOpenNewTab() {
        logger.info("Entered.");
        ((JavascriptExecutor) driver).executeScript("window.open()");
        switchNewWindow();
    }

    public void chromeFocusTabWithNumber(int number) {
        logger.info("Entered. Parameters; number: {}", number);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(number - 1));
    }


    public void acceptAlertPopup() {
        logger.info("Entered.");
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert
        } catch (NoAlertPresentException e) {
            logger.info("No alert was here");
        }
    }

    public void scrollByJs(WebElement element) {
        logger.info("Entered. Parameters; element: {}", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        waitForPageToCompleteState();
    }

    public void scrollByJs(By by) {
        logger.info("Entered. Parameters; element: {}", by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findElement(by));
        waitForPageToCompleteState();
    }


    public boolean waitUntilElementClickable(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
            logger.info("Element tıklanabilir duruma geçti");
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element tıklanabilir duruma geçmedi");
        }
        return false;
    }

    public boolean waitUntilElementClickable(WebElement webElement) {
        logger.info("Entered. Parameters; by: {}", webElement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            logger.info("Element tıklanabilir duruma geçti");
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element tıklanabilir duruma geçmedi");
        }
        return false;
    }

    public boolean ifElementExistsClick(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            javaScriptClicker(driver.findElement(by));
            logger.info(by.toString() + " elementine javascript ile tıklandı");
            waitForPageToCompleteState();
            return true;
        } catch (TimeoutException e) {
            logger.info("Element bulunamadı");
        }
        return false;
    }

    public void clickElementAsLongAsExist(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        while (isDisplayedBy(by)) {
            clickByJs(by);
            waitForPageToCompleteState();
        }
        if (!isDisplayedBy(by)) {
            logger.info("Parameters: By {} has not visible", by);
        }
    }

    public boolean ifElementExistsClick(WebElement element) {
        logger.info("Entered. Parameters; by: {}", element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            javaScriptClicker(element);
            waitForPageToCompleteState();
            return true;
        } catch (TimeoutException e) {
            logger.info("Element bulunamadı");
        }
        return false;
    }


    public void clearByJs(WebElement webElement) {
        logger.info("Entered. Parameters; webElement: {}", webElement);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value ='';", webElement);
    }


    public void waitForPageToCompleteState() {
        logger.info("Entered.");
        int counter = 0;
        int maxNoOfRetries = Constants.DEFAULT_MAX_ITERATION_COUNT;
        while (counter < maxNoOfRetries) {
            waitByMilliSeconds(Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT);
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                if (js.executeScript("return document.readyState").toString().equals("complete")) {
                    break;
                }
            } catch (Exception ignored) {
            }
            counter++;
        }
    }


    //?
    public void onBeforeUnloadByJs() {
        logger.info("Entered.");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.onbeforeunload = function(e){};");
    }

    public void navigateBack() {
        logger.info("Entered.");
        driver.navigate().back();
    }

    public void getRecordsOnTheNetworkFromTheUrl() {
        logger.info("Entered.");
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        String netData = ((JavascriptExecutor) driver).executeScript(scriptToExecute).toString();
        logger.info(netData);
    }

    public void switchNewWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    public void switchTo() {
        logger.info("Entered.");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    public void chromeFocusLastTab() {
        logger.info("Entered.");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    public void chromeFocusFrameWithWebElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement webElement = findElement(key, pageName);
        driver.switchTo().frame(webElement);
    }

    public void chromeFocusFrameWithBy(By by) {
        logger.info("Entered. Parameters; by: {}", by);
        WebElement webElement = findElement(by);
        driver.switchTo().frame(webElement);
    }

    public void switchDefaultContent() {
        logger.info("Entered.");
        driver.switchTo().defaultContent();
    }

    public String getPageSource() {
        logger.info("Entered.");
        return driver.switchTo().alert().getText();
    }

    public void clickElementSpecificTimes(By by, int times) {
        for (int i = 0; i < times; i++) {
            clickElement(by);
            waitByMilliSeconds(1000);
        }
    }


    public void closeTheLastTabAndSwitchToNewWindow() {
        logger.info("Current window will close and switch to the new window");
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        driver.close();
        executor.executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void clearBrowserCache() {
        logger.info("All caches will delete.");
        driver.manage().deleteAllCookies();
    }

    public static String generateRandom(int count, boolean letters, boolean numbers) {
        return RandomStringUtils.random(count, letters, numbers);
    }


    public WebElement returnElementIfEqualText(String text, By by) {
        logger.info("Entered.");
        List<WebElement> elements = findElements(by);
        WebElement searchedElement = null;
        for (WebElement element : elements) {
            if (text.equalsIgnoreCase(element.getText())) {
                searchedElement = element;
                break;
            }
        }
        return searchedElement;
    }

    // sayfa yüklenme kontrolu payment için kontrol edilecek düzenlenecek
    public boolean elementVisibilityCheck(By by) {
        logger.info("Entered.");
        int counter = 0;
        int maxNoOfRetries = Constants.DEFAULT_MAX_ITERATION_COUNT;
        while (counter < maxNoOfRetries) {
            waitByMilliSeconds(Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT);
            try {
                if (!findElement(by).isDisplayed()) break;
                break;
            } catch (Exception ignored) {
            }
            counter++;
        }
        return findElement(by).isDisplayed();
    }

    public void chooseElementFromComboBox(String text, By by) {
        logger.info("Entered. Parameters; text: {}, by: {}", text, by);
        List<WebElement> comboBoxElement = findElements(by);
        boolean result;
        for (int i = 0; i < comboBoxElement.size(); i++) {
            if (comboBoxElement.get(i).getText().equalsIgnoreCase(text)) {
                clickByJs(comboBoxElement.get(i));
                result = true;
                break;
            } else result = false;

            Assert.assertTrue("Cound find text", result);
            logger.info("Comboboxdan " + text + " değeri seçildi");
        }
    }

    public double stringToDouble(String priceCurrency) {
        double price = Double.parseDouble(priceCurrency.replace(" TL", "").replace(" EUR", "").replace(" USD", "").replace(",", ""));
        return price;
    }

    public long doubleToLong(double decimalNumber) {
        long price = Math.round(decimalNumber);
        return price;
    }

    public String trimTextAfterCharacter(String text, char character) {
        int dashIndex = text.indexOf(character);
        return (dashIndex != -1) ? text.substring(dashIndex + 1).trim() : text;
    }

    public boolean canConvertToInteger(String stringValue) {
        try {
            Integer.parseInt(stringValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String generateRandomEmail() {
        return Faker.instance().name().firstName() + "@test.com";
    }

    public String generatePhoneNumber(String areaCode) {
        return areaCode.concat(generateRandom(9, false, true)).trim();
    }

    public void selectTextFromDropDown(String text, By by) {
        logger.info("Entered. Parameters; text: {}, by: {}", text, by);
        if (!isDisplayed(findElement(by))) {
            waitForTheElement(by);
        }
        Select select = new Select(findElement(by));
        select.selectByVisibleText(text);
    }

    public void selectATextFromTheList(By by, String text) {
        logger.info("Entered. Parameters; by: {}, text: {}", by, text);
        List<WebElement> comboBoxElement = findElements(by);
        if (!isDisplayed(findElement(by))) {
            waitForTheElement(by);
        }
        if (comboBoxElement.isEmpty()) {
            throw new RuntimeException("Couldn't find the element");
        }
        boolean result = false;
        for (int i = 0; i < comboBoxElement.size(); i++) {
            String texts = comboBoxElement.get(i).getText();
            if (texts.contains(text)) {
                clickElement(comboBoxElement.get(i));
                result = true;
                break;
            }
        }
        Assert.assertTrue("Could not find the text", result);
        logger.info(by + " comboboxından " + text + " değeri seçildi");
    }
}


