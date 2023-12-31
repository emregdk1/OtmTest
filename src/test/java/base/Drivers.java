package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Drivers {
    CHROME {
        @Override
        public RemoteWebDriver getLocale(DesiredCapabilities capabilities) {
            ChromeOptions chromeOptions = new ChromeOptions();
            Map<String, String> prefs = new HashMap<>();
            //prefs.put("profile.default_content_setting_values.notifications", 2);
            chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            //chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking", "enable-automation"));
            // chromeOptions.addArguments("--start-fullscreen");
            // chromeOptions.addArguments("start-maximized");
            //chromeOptions.addArguments("-AppleLanguages","(tr)");
            chromeOptions.addArguments("--lang=tr");
            chromeOptions.addArguments("--disable-popup-blocking", "disable-automation", "--disable-blink-features", "--disable-blink-features=AutomationControlled"
                    , "--disable-gpu", "--no-sandbox", "disable-infobars", "disable-plugins", "ignore-certificate-errors",
                    "disable-translate", "disable-extensions", "--disable-notifications", "--remote-allow-origins=*");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("credentials_enable_service", false);
            parameters.put("password_manager_enabled", false);
            chromeOptions.setExperimentalOption("prefs", parameters);
            chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            //System.setProperty("webdriver.chrome.driver", "Web_Driver/chromedriver");
            //chromeOptions.merge(capabilities);
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(chromeOptions);
        }

    },
    FIREFOX {
        @Override
        public RemoteWebDriver getLocale(DesiredCapabilities capabilities) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            firefoxOptions.addArguments("--kiosk");
            FirefoxProfile profile = new FirefoxProfile();
            firefoxOptions.setProfile(profile);
            //firefoxOptions.setCapability("marionette", true);
            //firefoxOptions.setCapability("prefs", prefs);
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(firefoxOptions);

        }
    };

    public RemoteWebDriver getLocale(DesiredCapabilities capabilities) {
        return getLocale(capabilities);
    }

    public static Drivers getDriver(String driverName) {
        for (Drivers drivers : Drivers.values()) {
            if (drivers.toString().equalsIgnoreCase(driverName)) {
                return drivers;
            }
        }
        throw new RuntimeException();
    }
}