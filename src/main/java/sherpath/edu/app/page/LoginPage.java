package sherpath.edu.app.page;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import sherpath.edu.app.util.Configurator;
import sherpath.edu.app.util.SherpathUtils;


public class LoginPage {

	/**
	 * Page Class modeling Login Page of Sherpath Application
	 * @author Lakshmi Tadepally
	 * @since Nov-2015
	 */
	
	@FindBy(id = "username")
	private WebElement username;
	

	@FindBy(id = "password")
	private WebElement password;
	
	@FindBy(id="registerButton")
	public WebElement SubmitButton;
	
	private WebDriver driver;

	/**
	 * . This is a constructor used to set the driver object
	 * 
	 * @param driver
	 *            - a WebDriver Object
	 */
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
}