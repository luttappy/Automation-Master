package sherpath.edu.app.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import sherpath.edu.app.page.PageClass;
import sherpath.edu.app.testng.Logger;
import sherpath.edu.app.testng.WebDriverManager;
import sherpath.edu.app.util.Configurator;

public class TestClass {
	
	@Test
	public void sampleTest(){
		WebDriver driver = WebDriverManager.getDriver();
		Logger.debug("webdriver instance created");

		
		PageClass pclass = PageFactory.initElements(driver, PageClass.class);
		pclass.launchUrl(driver, Configurator.getConfig().get("HOME_PAGE"));
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
