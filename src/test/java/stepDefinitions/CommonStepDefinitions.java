package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.asynchttpclient.util.Assertions;
import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.Page;
import steps.BaseSteps;
import utils.ClassList;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import static constants.Constants.DEFAULT_MAX_ITERATION_COUNT;
import static constants.Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT;
import static org.asynchttpclient.util.Assertions.*;
import static org.junit.Assert.*;

public class CommonStepDefinitions {
    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Given("Go to {string} address")
    public void goToUrl(String url) {
        logger.info("Entered. Parameters; key: {}", url);
        baseSteps.switchNewWindow();
        baseSteps.goToUrl(url);
        logger.info("Current Url: {}", baseSteps.getCurrentUrl());
        baseSteps.waitForPageToCompleteState();
    }

    @And("Check if current URL contains the value {string}")
    public void checkURLContainsRepeat(String expectedURL) {
        logger.info("Entered. Parameters; expectedURL: {}", expectedURL);
        waitBySeconds(10);
        int loopCount = 0;
        String actualURL = baseSteps.getCurrentUrl();
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {

            if (actualURL != null && actualURL.contains(expectedURL)) {
                logger.info("Şuanki URL: " + expectedURL + " değerini içeriyor.");
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
    }

    @And("Scroll to the element {string} by js in {string}")
    public void scrollToElementWithJs(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement element = baseSteps.findElement(key, pageName);
        if (!baseSteps.isDisplayed(element)) {
            baseSteps.waitForTheElement(key, pageName);
        }
        baseSteps.scrollByJs(element);
        baseSteps.waitForPageToCompleteState();
    }

    @And("Click to element {string} in {string}")
    public void clickElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement element = baseSteps.findElement(key, pageName);
        baseSteps.hoverElement(element);
        baseSteps.javaScriptClicker(element);
        baseSteps.waitForPageToCompleteState();
        logger.info(key + " clicked.");
    }

    @And("Double click to element {string} in {string}")
    public void doubleClick(String key, String pageName) {
        logger.info("Entered.");
        baseSteps.doubleClick(baseSteps.findElement(key, pageName));
    }

    @And("User tries to click on the key {string} by javascript on the page name {string}")
    public void clickToElementByJS(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement element = baseSteps.findElement(key, pageName);
        if (!baseSteps.isDisplayed(element)) {
            baseSteps.waitForTheElement(key, pageName);
        }
        baseSteps.javaScriptClicker(element);
        logger.info("Clicked on the element: {}", element);
        baseSteps.waitForPageToCompleteState();
    }

    @And("Click if the element {string} on the page is clickable {string}")
    public void elementClickable(String key, String pageName) {
        baseSteps.clickIfClickable(key, pageName);
    }

    @And("Wait for the element {string} in {string}")
    public void waitForElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        logger.info("Waiting for the " + key + " on the " + pageName);
        baseSteps.waitForTheElement(key, pageName);
        baseSteps.waitForPageToCompleteState();
        if (baseSteps.isDisplayed(baseSteps.findElement(key, pageName))) {
            logger.info(key + " has found on the " + pageName);
        } else {
            logger.info(key + " has not displayed on the " + pageName);
        }
    }

    @And("User waits for and click the element {string} in {string} ")
    public void waitForTheElementAfterClick(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        getElementWithKeyIfExists(key, pageName);
        clickElement(key, pageName);
        baseSteps.waitForPageToCompleteState();
        logger.info(key + " clicked.");
    }

    @And("If exist click to element {string} in {string}")
    public void clickElementIfExist(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.ifElementExistsClick(Page.createElement(pageName, key));
    }

    @And("User clicks as long as visible to the element {string} if exist in the page name {string}")
    public void clickElementIfExistMultipleTimes(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.clickElementAsLongAsExist(Page.createElement(pageName, key));
    }

    @And("User clicks to element {string}, {int} times in the page name {string}")
    public void clickElementTimes(String key, int times, String pageName) {
        logger.info("Entered. Parameters; key: {}, times: {}, pageName: {}", key, times, pageName);
        baseSteps.clickElementSpecificTimes(Page.createElement(pageName, key), times);
    }

    @And("Accept alert popup")
    public void acceptAlertPopup() {
        logger.info("Entered");
        baseSteps.acceptAlertPopup();
    }

    @Given("Switch to iframe key {string} pageName {string}")
    public void switchToIframe(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.chromeFocusFrameWithWebElement(key, pageName);
    }

//    @And("User save the element's text {string} to slot {string} at {string}")
//    public void saveTextToSlot(String key, String saveSlot, String pageName) {
//        logger.info("Entered. Parameters; key: {}, saveSlot: {}, pageName: {}", key, saveSlot, pageName);
//        String elementText = "";
//        elementText = baseSteps.findElement(key, pageName).getText();
//        if (elementText.trim().equals("")) {
//            elementText = baseSteps.findElement(key, pageName).getAttribute("value");
//        }
//        SavedValue.getInstance().putValue(saveSlot, elementText);
//        logger.info(key + " text: " + elementText + " is saved into the " + saveSlot + " at the " + pageName);
//    }

    @And("User writes a value {string} to element {string} in the page name {string}")
    public void sendKeys(String text, String key, String pageName) {
        logger.info("Entered. Parameters; text: {}, key: {}, pageName: {}", text, key, pageName);
        baseSteps.findElement(key, pageName).clear();
        baseSteps.findElement(key, pageName).sendKeys(text);
        logger.info("Text: {} has sent to the key : {} in the page name : {}.", text, key, pageName);
        baseSteps.waitForPageToCompleteState();
    }

    @And("User writes a value {string} by javascript to element {string} in the page name {string}")
    public void writeToKeyWithJavaScript(String key, String text, String pageName) {
        logger.info("Entered. Parameters; key: {}, text: {} pageName: {}", key, text, pageName);
        WebElement element = baseSteps.findElement(key, pageName);
        baseSteps.writeToElementByJs(element, text);
        logger.info(key + " elementine " + text + " değeri js ile yazıldı.");
    }

    @And("Write random value to element {string} in {string}")
    public void writeRandomValueToElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.findElement(key, pageName).sendKeys(baseSteps.randomString(15));
    }

    @And("User writes a value {string} to the attribute {string} of element {string} in the page name {string}")
    public void setElementAttribute(String value, String attributeName, String key, String pageName) {
        logger.info("Entered. Parameters; value: {}, attributeName: {}, key: {}, pageName: {}", value, attributeName, key, pageName);
        String attributeValue = baseSteps.findElement(key, pageName).getAttribute(attributeName);
        baseSteps.findElement(key, pageName).sendKeys(attributeValue, value);
    }

    @And("User writes a value {string} to the attribute {string} of element {string} with Js in the page name {string}")
    public void setElementAttributeWithJs(String value, String attributeName, String key, String pageName) {
        logger.info("Entered. Parameters; value: {}, attributeName: {}, key: {}, pageName: {}", value, attributeName, key, pageName);
        WebElement webElement = baseSteps.findElement(key, pageName);
        baseSteps.setAttribute(webElement, attributeName, value);
    }

//    @And("User writes the saved text {string} to the element {string} at page name{string}")
//    public void writeSavedTextToTheKey(String savedKey, String key, String pageName) {
//        logger.info("Entered. Parameters; savedKey: {}, key: {}, pageName: {}", savedKey, key, pageName);
//        String savedText = SavedValue.getInstance().getSavedValue(String.class, savedKey);
//        sendKeys(savedText, key, pageName);
//        logger.info("Text: " + savedText + " wrote into the " + key + " at the " + pageName);
//    }
//
//    @And("User writes by js the saved text {string} to the element {string} at page name {string}")
//    public void writeTextToTheKeyByJs(String savedKey, String key, String pageName) {
//        logger.info("Entered. Parameters; savedKey: {}, key: {}, pageName: {}", savedKey, key, pageName);
//        String savedText = SavedValue.getInstance().getSavedValue(String.class, savedKey);
//        writeToKeyWithJavaScript(key, savedText, pageName);
//        logger.info("Text: " + savedText + " wrote into the " + key + " at the " + pageName);
//    }

    // FIXME: 21.07.2023 Önemli kod tekrarlı aynısı basetestte var
    @When("Write a text {string} on the {string} and select the option using the {string} in the {string}")
    public void writeATextToTheFieldAndSelectTheText(String text, String searchFieldKey, String key, String pageName) {
        logger.info("Entered. Parameters; text: {}, searchFieldKey: {}, key: {}, pageName: {}", text, searchFieldKey, key, pageName);
        baseSteps.findElement(searchFieldKey, pageName).click();
        baseSteps.findElements(searchFieldKey, pageName).clear();
        baseSteps.findElement(searchFieldKey, pageName).sendKeys(text);
        waitBySeconds(5);
        List<WebElement> comboBoxElement = baseSteps.findElements(key, pageName);
        if (!(baseSteps.isDisplayed(comboBoxElement.get(0)))) {
            baseSteps.waitForTheElement(comboBoxElement.get(0));
        }
        if (comboBoxElement.size() != 0) {
            String savedText = comboBoxElement.get(0).getText();
            baseSteps.clickElement(comboBoxElement.get(0));
            logger.info("Text: {} has selected on the key: {}", savedText, key);
            waitBySeconds(5);
        }
    }

    @When("User selects a text {string} on the combo box using the {string} in the page name {string}")
    public void chooseATextFromTheList(String text, String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        List<WebElement> comboBoxElement = baseSteps.findElements(key, pageName);
        if (!baseSteps.isDisplayed(baseSteps.findElement(key, pageName))) {
            baseSteps.waitForTheElement(key, pageName);
        }
        if (comboBoxElement.isEmpty()) {
            throw new RuntimeException("Couldn't find the element");
        }
        boolean result = false;
        for (WebElement webElement : comboBoxElement) {
            String texts = webElement.getText();
            if (texts.contains(text)) {
                baseSteps.clickByJs(webElement);
                result = true;
                break;
            }
        }
        assertTrue("Could not find text: " + text, result);
        logger.info("Text: {} has selected in the key: {} combo box.", text, key);
    }

    @When("User scrolls and select a text {string} on the combo box using the {string} in the {string}")
    public void scrollChooseATextFromTheList(String text, String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        List<WebElement> comboBoxElement = baseSteps.findElements(key, pageName);
        if (!baseSteps.isDisplayed(baseSteps.findElement(key, pageName))) {
            baseSteps.waitForTheElement(key, pageName);
        }
        if (comboBoxElement.isEmpty()) {
            throw new RuntimeException("Couldn't find the element");
        }
        boolean result = false;
        for (WebElement webElement : comboBoxElement) {
            String texts = webElement.getText();
            if (texts.contains(text)) {
                baseSteps.scrollAndClickByJs(webElement);
                result = true;
                break;
            }
        }
        assertTrue("Could not find text: " + text, result);
        logger.info("Text: {} has selected in the key: {} combo box.", text, key);
    }

    @And("Select {string} element at {string} index in {string}")
    public void selectElementAtIndex(String key, String index, String pageName) {
        logger.info("Entered. Parameters: key={}, index={}, pageName={}", key, index, pageName);
        try {
            int x = Integer.parseInt(index) - 1;
            Optional.ofNullable(baseSteps.findElements(key, pageName))
                    .map(List::stream)
                    .flatMap(stream -> stream.skip(x).findFirst())
                    .ifPresentOrElse(baseSteps::javaScriptClicker,
                            () -> logger.error("Index is out of bounds: {}", x));
        } catch (NumberFormatException e) {
            logger.error("Invalid index value: {}", index);
        }
    }

    @And("Select random element {string} list in the {string}")
    public void randomPick(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        List<WebElement> elements = baseSteps.findElements(key, pageName);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        elements.get(index).click();
        logger.info("clicked " + key + " element index is :" + index);
    }

    @And("Select random element {string} list {int} times in the {string}")
    public void randomPickTimes(String key, int times, String pageName) {
        logger.info("Entered. Parameters; key: {}, times: {}, pageName: {}", key, times, pageName);
        for (int i = 0; i < times; i++) {
            List<WebElement> elements = baseSteps.findElements(key, pageName);
            Random random = new Random();
            int index = random.nextInt(elements.size());
            elements.get(index).click();
        }
    }

    @When("User selects {int} days later in the element {string} on the {string}")
    public void addASpecificNumberToTheCurrentDay(int number, String key, String pageName) {
        logger.info("Entered. Parameters; number:{}, key: {}, pageName: {}", number, key, pageName);
        baseSteps.selectADayAccordingToRequest(baseSteps.chooseDate(number), key, pageName);
    }

    @When("User selects {int} a specific day in the element {string} on the {string}")
    public void daySelection(int number, String key, String pageName) {
        logger.info("Entered. Parameters; number: {}, key: {}, pageName: {}", number, key, pageName);
        baseSteps.selectADayAccordingToRequest(number, key, pageName);
    }

//    @When("Select a random value on the combo box from the {string} in the {string}")
//    public void comboBoxRandom(String key, String pageName) {
//        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
//        List<WebElement> comboBoxElement = baseSteps.findElements(key, pageName);
//        System.out.println(comboBoxElement.size());
//        int randomIndex = new Random().nextInt(comboBoxElement.size() - 1);
//        System.out.println(randomIndex);
//        if (!baseSteps.isDisplayed(baseSteps.findElement(key, pageName))) {
//            baseSteps.waitForTheElement(key, pageName);
//        }
//        SavedValue.getInstance().putValue(SavedValue.DROPDOWN_TEXT, comboBoxElement.get(randomIndex).getText());
//        logger.info(SavedValue.getInstance().getSavedValue(SavedValue.DROPDOWN_TEXT) + " has selected as random value");
//        System.out.println(SavedValue.getInstance().getSavedValue(SavedValue.DROPDOWN_TEXT));
//        baseSteps.clickByJs(comboBoxElement.get(randomIndex));
//        logger.info("Random value has selected in the key: {}", key);
//        baseSteps.waitForPageToCompleteState();
//    }

//    @When("User selects a specific index {string} on the combo box from the {string} in the <pageName>")
//    public void comboBoxWithSpecificIndexSelection(int index, String key, String pageName) {
//        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
//        List<WebElement> comboBoxElement = baseSteps.findElements(key, pageName);
//        int listSize = comboBoxElement.size();
//        logger.info("Size of the list: " + listSize);
//        if (!baseSteps.isDisplayed(baseSteps.findElement(key, pageName))) {
//            baseSteps.waitForTheElement(key, pageName);
//        }
//        if (listSize > index - 1) {
//            SavedValue.getInstance().putValue(SavedValue.DROPDOWN_TEXT, comboBoxElement.get(index - 1).getText());
//            String savedText = SavedValue.getInstance().getSavedValue(SavedValue.DROPDOWN_TEXT).toString();
//            logger.info(savedText + " has selected according to the index");
//            System.out.println(SavedValue.getInstance().getSavedValue(SavedValue.DROPDOWN_TEXT));
//            baseSteps.clickByJs(comboBoxElement.get(index - 1));
//            logger.info("The " + savedText + " has selected on the " + key + " Combo box.");
//            baseSteps.waitForPageToCompleteState();
//        } else {
//            Assert.fail("Size of list was less than the selected index. Selected was: " + index + " Actual was: " + listSize);
//        }
//    }

    @Then("Check if url contains text {string} in {string}")
    public void checkElementContainsText(String expectedText, String pageName) {
        logger.info("Entered. Parameters; expectedText: {}, pageName: {}", expectedText, pageName);
        boolean kontrol = false;
        if (baseSteps.getCurrentUrl().equalsIgnoreCase(expectedText)) {
            kontrol = true;
        } else if (baseSteps.getCurrentUrl().contains(expectedText)) {
            kontrol = true;
        } else {
            logger.info(baseSteps.getCurrentUrl());
        }
        Assert.assertTrue("Expected text is not contained. It was " + baseSteps.getCurrentUrl(), kontrol);
        logger.info("Current Url: {} contains expectedText: {}", baseSteps.getCurrentUrl(), expectedText);
    }

    @Then("The {string} should not visible on the the current page")
    public void validateVisibilityOfText(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        assertFalse(text + " was not available on the current page.", baseSteps.shouldSeeText(text));
    }

    @Then("The {string} should visible on the the current page")
    public void validateInVisibilityOfText(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        assertTrue(text + " was available on the current page.", baseSteps.shouldSeeText(text));
    }

    @Then("Check if element {string} exists in {string}")
    public WebElement getElementWithKeyIfExists(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement webElement;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            try {
                webElement = baseSteps.findElement(key, pageName);
                logger.info(key + " has found.");
                return webElement;
            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        assertFalse(Boolean.parseBoolean("Element: '" + key + "' doesn't exist."));
        return null;
    }

    @And("Check if element {string} exists and log the current text in {string}")
    public void checkIfElementExistLogCurrentText(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (baseSteps.findElements(key, pageName).size() > 0) {
                logger.info("Key: {}, text: {}", key, baseSteps.findElement(key, pageName).getText());
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        logger.warn(key + " was not visible on the " + pageName);
    }

//    @Then("User should check that {string} element's text and {string} saved text are same at {string}")
//    public void compareTexts(String key, String savedKey, String pageName) {
//        logger.info("Entered. Parameters; key: {}, savedKey: {}, pageName: {}", key, savedKey, pageName);
//        String savedText = SavedValue.getInstance().getSavedValue(savedKey).toString();
//        String elementText = baseSteps.findElement(key, pageName).getText();
//        logger.info(savedText + " and " + elementText + " have compared. They have to equal to each other.");
//        Assert.assertEquals("Texts are not same. Expected text was " + savedText + " but it was " + elementText, savedText, elementText);
//    }

//    @Then("User should check that {string} element's text and {string} saved text are not same at {string}")
//    public void compareTextsNotSame(String key, String savedKey, String pageName) {
//        logger.info("Entered. Parameters; key: {}, savedKey: {}, pageName: {}", key, savedKey, pageName);
//        String savedText = SavedValue.getInstance().getSavedValue(savedKey).toString();
//        String elementText = baseSteps.findElement(key, pageName).getText();
//        logger.info(savedText + " and " + elementText + " have compared. They have to different from each other.");
//        Assert.assertNotEquals("Texts are same and they were " + savedText, savedText, elementText);
//    }
//
//    @Then("The saved key {string} should not visible on the the current page")
//    public void savedTextShouldNotVisibleOnThePage(String savedKey) {
//        String savedText = SavedValue.getInstance().getSavedValue(String.class, savedKey);
//        logger.info("Entered. Parameters; savedKey: {} , savedText: {}", savedKey, savedText);
//        assertFalse(savedText + " was on the current page", baseSteps.shouldSeeText(savedText));
//    }
//
//    @Then("The saved key {string} should visible on the the current page")
//    public void savedTextShouldVisibleOnThePage(String savedKey) {
//        String savedText = SavedValue.getInstance().getSavedValue(String.class, savedKey);
//        logger.info("Entered. Parameters; savedKey: {} , savedText: {}", savedKey, savedText);
//        assertTrue(savedText + " wasn't on the current page", baseSteps.shouldSeeText(savedText));
//    }

    @Then("Check if current page contains the text {string} or not")
    public void whetherCurrentPageContainsTheTextOrNot(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        assertTrue(text + "wasn't available on the current page", baseSteps.shouldSeeContainingText(text));
        logger.info("The element visible on the current page , text {}", text);
    }

//    @Then("Check if current page contains the saved key {string} or not")
//    public void whetherCurrentPageContainsTheSavedTextOrNot(String savedKey) {
//        String savedText = SavedValue.getInstance().getSavedValue(String.class, savedKey);
//        logger.info("Entered. Parameters; saved key: {} , saved text: {}", savedKey, savedText);
//        assertTrue("Saved Text: " + savedText + " wasn't available on the current page", baseSteps.shouldSeeContainingText(savedText));
//        logger.info("The element visible on the current page saved key: {}, saved text: {}", savedKey, savedText);
//    }

//    @Then("The saved keys should visible on the the current page \"([^\"]*)\"$")
//    public void savedTextShouldVisibleOnThePage(String pageName, List<String> savedKeys) {
//        for (String key : savedKeys) {
//            String savedText = SavedValue.getInstance().getSavedValue(String.class, key);
//            assertTrue("Expected saved text: " + savedText + "  is not visible on the " + pageName, baseSteps.shouldSeeText(savedText));
//        }
//    }
//
//    @And("Check if current URL contains the saved value {string}")
//    public void checkIfURLContainsSavedValue(String savedKey) {
//        logger.info("Entered. Parameters; expectedURL: {}", savedKey);
//        waitBySeconds(10);
//        String actualURL = baseSteps.getCurrentUrl();
//        String expectedDomain = SavedValue.getInstance().getSavedValue(String.class, savedKey).toUpperCase(new Locale("tr", "TR"));
//        String[] actualUrlArray = actualURL.split("\\.");
//        String actualDomain = actualUrlArray[1].toUpperCase(new Locale("tr", "TR"));
//        Assert.assertEquals("Expected domain: " + expectedDomain + "didn't equal to actual domain: " + actualDomain, expectedDomain, actualDomain);
//        logger.info("Parameters: expectedDomain: {}, actualDomain: {}", expectedDomain, actualDomain);
//    }

    @And("User waits {long} milliseconds")
    public void waitByMilliSeconds(long milliseconds) {
        logger.info("Entered. Parameters; milliseconds: {}", milliseconds);
        baseSteps.waitByMilliSeconds(milliseconds);
    }

    @And("Wait {int} seconds")
    public void waitBySeconds(int seconds) {
        logger.info("Entered. Parameters; seconds: {}", seconds);
        try {
            logger.info(seconds + " saniye bekleniyor.");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @When("Go to manage my booking with PNR {string} and surname {string}")
//    public void goToManageMyBookingWithPnrAndSurname(String pnrNo, String pnr_surname) {
//        String savedPnr = SavedValue.getInstance().getSavedValue(String.class, pnrNo);
//        String savedSurname = SavedValue.getInstance().getSavedValue(String.class, pnr_surname);
//        goToUrl("https://prepweb.flypgs.com/manage-booking?currency=TL&language=tr&pnrNo=" + savedPnr + "&surname=" + savedSurname);
//    }

}
