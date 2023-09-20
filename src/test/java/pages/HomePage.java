package pages;

import java.util.HashMap;


public class HomePage {

    public static HashMap<String, String> byMap = new HashMap<>();
    public static final String PAGE_NAME = "Home Page";

    static {
        byMap.put("USERNAME_INPUT", "//input[@id='username']");
        byMap.put("PASSWORD_INPUT", "//input[@id='password']");
        byMap.put("LOGIN_BUTTON", "//input[@id='idlogon']");
        byMap.put("test", "//a[@id='pt1:pt_t1:4::di']");
        byMap.put("test1", "//a[@id='pt1:pt_t1:10::di']");
        byMap.put("test2", "//a[@id='pt1:pt_t1:20:c11']");
        byMap.put("test3", "//a[@id='pt1:r1:1:r1:0:cb1']");
        byMap.put("test4", "//input[@id='pt1:r1:1:r1:1:s1:it2::content']");
        byMap.put("test5", "//button[@id='pt1:r1:1:r1:1:s1:btnSearchInd']");
        byMap.put("ICON", "//div[@class='bolbol-header-logo']");
    }
}
