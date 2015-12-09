package sherpath.edu.app.util;

import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sherpath.edu.app.testng.*;


public class SherpathUtils 
{
		
		/**
	 * This is method to launch the specific url.
	 * @author Lakshmi Tadepally
	 * @since nov 2015
	 **/
	public void launchURL(WebDriver driver, String url)
	{
		driver.get(url);
		Logger.info("Navigated to:\t"+url);
	
	}
	
	 /**
	 * The method to switch to new window 
	 * @return  none
	 * @author Lakshmi Tadepally
	 * @since nov 2015
	 **/
		public void switchWindow(WebDriver driver) {
		  for(String winHandle : driver.getWindowHandles()){
		       driver.switchTo().window(winHandle);
		       Logger.info("Switched to new window ="+driver.getTitle());
		   }
		}
	
	/**
	 * Generate random email address as a String.
	 * @param None
	 * @return AlphaNumeric String - a random AlphaNumeric String in email address format
	 * * @author Lakshmi Tadepally
	 * @since nov 2015
	 * */
	public final String generateEmailAddressWithDomain(String type,String domain) {
		UUID email_id = UUID.randomUUID();
		String email_address = email_id.toString().substring(0, 7)+"SherpathAuto"+"@"+domain+"."+type;
		return email_address;
	}

	/**
	 * Generic method to check whether the element is present or not on the page
	 * @param WebElement
	 * @return boolean
	 * @author Lakshmi Tadepally
	 * @since nov 2015
	 * */
	public boolean isElementPresent(WebElement element)
	{
		try
		{
			element.isDisplayed();
		//	Logger.info("Element Present on the page");
			return true;
		}
		catch(Exception e)
		{
			Logger.info(e.getMessage());
			return false;
		}
	
				
	}
	
	/**
	 * Generic method to wait for a web element
	 * @param WebElement and WebDriver
	 * @return void 
	 * @author Lakshmi Tadepally
	 * @since nov 2015
	 * */
	public void  waitForMyElement(WebDriver driver, WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**
	* This method is used to check the expected text is displayed on the page or not
	* @return  Boolean value
	* @throws	java.lang.Exception Exception
	* @author Lakshmi Tadepally
	* @since nov 2015
	* */
	public boolean findText(WebDriver driver, String textToBeVerified)
	{
		Logger.info("textToBeVerified == //*[contains(.,'" + textToBeVerified + "')]");
		if (driver.findElement(By.xpath("//*[contains(.,'" + textToBeVerified + "')]")) != null){
		Logger.info("Returning True");
		return true;
		}
		Logger.info("Returning False");
		return false;
	}
	
}