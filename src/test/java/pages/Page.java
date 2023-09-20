package pages;

import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Locale;

public class Page {

    public static HashMap<String, HashMap<String, String>> pageByMap = new HashMap<>();

    static {
        pageByMap.put(HomePage.PAGE_NAME, (HomePage.byMap));
    }

    public static By createElement(String pageName, String key) {
        HashMap<String, String> list = pageByMap.get(pageName);
        return By.xpath(list.get(key.toUpperCase(Locale.ENGLISH)));
    }
}
