package pages;


import java.util.HashMap;


public class Constants {

    public static HashMap<String, String> byMap = new HashMap<>();

    static {
        byMap.put("Giris_Yap_Butonu", "(//div[@class='login-register-wrapper']//a)[1]");
        byMap.put("Google_ile_Giriş_Yap", "//button[@id='btnLoginWithGoogle']");
        byMap.put("E_Posta_Input", "//input[@name='identifier']");
        byMap.put("Email_Next_Button", "//div[@id='identifierNext']//button");
        byMap.put("Password_Input", "//input[@name='Passwd']");
        byMap.put("Password_Next_Button", "//div[@id='passwordNext']//button");
        byMap.put("Search_Input", "//div[contains(@class,'search-input')]//input");
        byMap.put("Search_Button", "//button[@class='search-button']");
        byMap.put("Car_List", "//td[@class='listing-modelname pr']");
        byMap.put("Car_Price_Text", "//div[@class='product-price-container']");
        byMap.put("Icon", "//div[@class='bolbol-header-logo']");
    }
}
