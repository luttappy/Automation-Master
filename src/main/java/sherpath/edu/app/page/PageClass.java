package sherpath.edu.app.page;

import org.openqa.selenium.WebDriver;

public class PageClass {
	
	public void launchUrl(WebDriver driver, String url) {
		//binu print
		System.out.println("url in page class is " +url);
		driver.get(url);
	}

}
