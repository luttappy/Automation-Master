package sherpath.edu.app.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import sherpath.edu.app.util.Configurator;

class WebDriverFactory 
{
	static WebDriver createInstance(String browserName) {
		WebDriver driver = null;
		if (browserName.toLowerCase().contains("firefox")) {
			driver = new FirefoxDriver();
			return driver;
		}
		if (browserName.toLowerCase().contains("safari")){
			driver = new SafariDriver();
			return driver;
		}
		if (browserName.toLowerCase().contains("internet")) {
			System.setProperty("webdriver.ie.driver", Configurator.getConfig().get("IE_DRIVER_PATH"));
			driver = new InternetExplorerDriver();
			return driver;
		}
		if (browserName.toLowerCase().contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", Configurator.getConfig().get("CHROME_DRIVER_PATH"));
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("test-type","star-maximized");
			driver = new ChromeDriver(chromeOptions);
			return driver;
		}
		return driver;
	}
}
