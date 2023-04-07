package utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {
    public static WebDriver getChromeDriver() throws MalformedURLException {
        //ChromeDriverManager.getInstance().setup("110.0.5481.30");
        /*ChromeDriverManager.getInstance().setup("111");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless"); //!!!should be enabled for Jenkins
        chromeOptions.addArguments("--disable-dev-shm-usage");*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.UNIX);
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("111");
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    }
}
