package utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static ChromeDriver getChromeDriver(){
        ChromeDriverManager.getInstance().setup("111");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless"); //!!!should be enabled for Jenkins
        chromeOptions.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
        return new ChromeDriver(chromeOptions);
    }
}
