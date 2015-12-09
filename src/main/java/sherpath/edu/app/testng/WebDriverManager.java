package sherpath.edu.app.testng;

import org.openqa.selenium.WebDriver;


public class WebDriverManager {

	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	 
    public static WebDriver getDriver() {
    	String	browserName = System.getProperty("browserName");
    	if (browserName == null){
    		browserName = "firefox";
    	}
    	 WebDriver driver = WebDriverFactory.createInstance(browserName);
    	 driver.manage().window().maximize();
         setWebDriver(driver);
        return webDriver.get();
    }
 
    static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
    
    static void quitDriver() {
	  if (webDriver.get() != null) {
	      webDriver.get().quit();
	  }
    }    
}
