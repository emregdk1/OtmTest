package stepDefinitions;

import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.Page;
import steps.BaseSteps;
import utils.ClassList;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static constants.Constants.DEFAULT_MAX_ITERATION_COUNT;
import static constants.Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT;
import static org.junit.Assert.*;

public class CommonStepDefinitions {
    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Step("Go to <url> address")
    public void goToUrl(String url) {
        logger.info("Entered. Parameters; key: {}", url);
        baseSteps.goToUrl(url);
        logger.info("Gidilen url = " + baseSteps.getCurrentUrl());
    }

    @Step("Check if current URL contains the value <expectedURL>")
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

    @Step("Scroll to the element <key> by js in <pageName>")
    public void scrollToElementWithJs(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement element = baseSteps.findElement(key, pageName);
        if (!baseSteps.isDisplayed(element)) {
            baseSteps.waitForTheElement(key, pageName);
        }
        baseSteps.scrollByJs(element);
        baseSteps.waitForPageToCompleteState();
    }

    @Step("Click to element <key> in <pageName>")
    public void clickElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        logger.info(key + " elementine tıklanacak.");
        baseSteps.hoverElement(baseSteps.findElement(key, pageName));
        waitBySeconds(1);
        baseSteps.javaScriptClicker(baseSteps.findElement(key, pageName));
        baseSteps.waitForPageToCompleteState();
        logger.info(key + " Clicked.");
    }

    @Step("Double click to element <key> in <pagaName>")
    public void doubleClick(String key, String pageName) {
        logger.info("Entered.");
        baseSteps.doubleClick(baseSteps.findElement(key, pageName));
    }

    @Step("User tries to click on the key <key> by javascript on the page name <pageName>")
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

    @Step("Click if the element <key> on the page is clickable <pageName>")
    public void elementClickable(String key, String pageName) {
        baseSteps.clickIfClickable(key, pageName);
    }

    @Step("Wait for the element <key> in <pageName>")
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

    @Step("User waits for and click the element <key> in <pageName> ")
    public void waitForTheElementAfterClick(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        getElementWithKeyIfExists(key, pageName);
        clickElement(key, pageName);
        baseSteps.waitForPageToCompleteState();
        logger.info(key + " clicked.");
    }

    @Step("If exist click to element <key> in <pageName>")
    public void clickElementIfExist(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.ifElementExistsClick(Page.createElement(pageName, key));
    }

    @Step("User clicks as long as visible to the element <key> if exist in the page name <pageName>")
    public void clickElementIfExistMultipleTimes(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.clickElementAsLongAsExist(Page.createElement(pageName, key));
    }

    @Step("User clicks to element <key>, <times> times in the page name <pageName>")
    public void clickElementTimes(String key, int times, String pageName) {
        logger.info("Entered. Parameters; key: {}, times: {}, pageName: {}", key, times, pageName);
        baseSteps.clickElementSpecificTimes(Page.createElement(pageName, key), times);
    }

    @Step("Accept alert popup")
    public void acceptAlertPopup() {
        logger.info("Entered");
        baseSteps.acceptAlertPopup();
    }

    @Step("Switch to iframe key <key> pageName <pageName>")
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

    @Step("User writes a value <text> to element <key> in the page name <pageName>")
    public void sendKeys(String text, String key, String pageName) {
        logger.info("Entered. Parameters; text: {}, key: {}, pageName: {}", text, key, pageName);
        baseSteps.findElement(key, pageName).clear();
        baseSteps.findElement(key, pageName).sendKeys(text);
        logger.info("Text: {} has sent to the key : {} in the page name : {}.", text, key, pageName);
        baseSteps.waitForPageToCompleteState();
    }

    @Step("User writes a value <text> by javascript to element <key> in the page name <pageName>")
    public void writeToKeyWithJavaScript(String key, String text, String pageName) {
        logger.info("Entered. Parameters; key: {}, text: {} pageName: {}", key, text, pageName);
        WebElement element = baseSteps.findElement(key, pageName);
        baseSteps.writeToElementByJs(element, text);
        logger.info(key + " elementine " + text + " değeri js ile yazıldı.");
    }

    @Step("Write random value to element <key> in <pageName>")
    public void writeRandomValueToElement(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        baseSteps.findElement(key, pageName).sendKeys(baseSteps.randomString(15));
    }

    @Step("Write <value> to <attributeName> of element <key> in <pageName>")
    public void setElementAttribute(String value, String attributeName, String key, String pageName) {
        logger.info("Entered. Parameters; value: {}, attributeName: {}, key: {}, pageName: {}", value, attributeName, key, pageName);
        String attributeValue = baseSteps.findElement(key, pageName).getAttribute(attributeName);
        baseSteps.findElement(key, pageName).sendKeys(attributeValue, value);
    }

    @Step("Write <value> to <attributeName> of element <key> with Js in <pageName>")
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
    @Step("Write a text <text> on the <searchFieldKey> and select the option using the <key> in the <pageName>")
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
            logger.info(key + " comboboxından " + savedText + " değeri seçildi");
            waitBySeconds(5);
        }
    }

    @Step("Select a text <text> on the combobox using the <key> in the <pageName>")
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
        for (int i = 0; i < comboBoxElement.size(); i++) {
            String texts = comboBoxElement.get(i).getText();
            if (texts.contains(text)) {
                baseSteps.clickElement(comboBoxElement.get(i));
                result = true;
                break;
            }
        }
        assertTrue("Could not find the text", result);
        logger.info(key + " comboboxından " + text + " değeri seçildi");
    }

    @Step("User scrolls and select a text <text> on the combo box using the <key> in the <pageName>")
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

    @Step("Select <key> element at <index> index in <pageName>")
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

    @Step("Select random element <key> list in the <pageName>")
    public void randomPick(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        List<WebElement> elements = baseSteps.findElements(key, pageName);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        elements.get(index).click();
        logger.info("clicked " + key + " element index is :" + index);
    }

    @Step("Select random element <key> list <times> times in the <pageName>")
    public void randomPickTimes(String key, int times, String pageName) {
        logger.info("Entered. Parameters; key: {}, times: {}, pageName: {}", key, times, pageName);
        for (int i = 0; i < times; i++) {
            List<WebElement> elements = baseSteps.findElements(key, pageName);
            Random random = new Random();
            int index = random.nextInt(elements.size());
            elements.get(index).click();
        }
    }

    @Step("Select <number> days later in the element <key> on the <pageName>")
    public void addASpecificNumberToTheCurrentDay(int number, String key, String pageName) {
        logger.info("Entered. Parameters; number:{}, key: {}, pageName: {}", number, key, pageName);
        baseSteps.selectADayAccordingToRequest(baseSteps.chooseDate(number), key, pageName);
    }

    @Step("Select <number> a specific day in the element <key> on the <pageName>")
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

    @Step("Check if url contains text <key> in <pageName>")
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

    @Step("The <key> should not visible on the the current page")
    public void validateVisibilityOfText(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        assertFalse(text + " was not available on the current page.", baseSteps.shouldSeeText(text));
    }

    @Step("The <key> should visible on the the current page")
    public void validateInVisibilityOfText(String text) {
        logger.info("Entered. Parameters; text: {}", text);
        assertTrue(text + " was available on the current page.", baseSteps.shouldSeeText(text));
    }

    @Step("Check if element <key> exists in <pageName>")
    public WebElement getElementWithKeyIfExists(String key, String pageName) {
        logger.info("Entered. Parameters; key: {}, pageName: {}", key, pageName);
        WebElement webElement;
        int loopCount = 0;
        while (loopCount < 3) {
            try {
                webElement = baseSteps.findElement(key, pageName);
                logger.info(key + " elementi bulundu.");
                return webElement;
            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(60000);
        }
        assertFalse(Boolean.parseBoolean("Element: '" + key + "' doesn't exist."));
        return null;
    }

    @Step("Check if element <key> exists and log the current text in <pageName>")
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

    @Step("Check if current page contains the text <text> or not")
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

    @Step("Wait <value> milliseconds")
    public void waitByMilliSeconds(long milliseconds) {
        logger.info("Entered. Parameters; milliseconds: {}", milliseconds);
        baseSteps.waitByMilliSeconds(milliseconds);
    }

    @Step("Wait <value> seconds")
    public void waitBySeconds(int seconds) {
        logger.info("Entered. Parameters; seconds: {}", seconds);
        try {
            logger.info("Program wait " + seconds + " seconds");
            Thread.sleep(seconds * 1000);
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
