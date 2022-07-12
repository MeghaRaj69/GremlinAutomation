package com.gremlinUI.pageObjects.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gremlinUI.helper.browserConfigurations.config.ObjectReader;
import com.gremlinUI.helper.wait.WaitHelper;
import com.gremlinUI.pageObjects.project.HomePage;

//import com.webMethodsUI.flow.helper.wait.WaitHelper;

public class LoginPage {
	
	private WebDriver driver;
	
	WaitHelper waitHelper;
	
	@FindBy(xpath = "//*[@id='username']")
	@CacheLookup
	WebElement userName;
	
	@FindBy(xpath = "//*[@id='password']")
	@CacheLookup
	WebElement password;
	
	@FindBy(xpath = "//*[@id='kc-login']")
	@CacheLookup
	WebElement loginButton;
	
	public LoginPage(WebDriver driver) {
		super();
		this.driver = driver;
		// All webelements defined in this page are initialized with below line at
		// runtime
		PageFactory.initElements(driver, this);
		// Whenever any test calls loginPage ensure login page is loaded
		waitHelper = new WaitHelper(driver);
		// ensure login page loaded or not based on any UI element . here it is
		// 'userName'
		// Systax waitHelper.waitForElement(element, timeOutInSeconds,
		// pollingEveryMiliSec)
		waitHelper.waitForElement(userName, ObjectReader.reader.getExplicitWait());
//		log.info("element is visible now....");
//		TestBase.logExtentReport("LoginPage Object created");
		// new TestBase().getNavigationScreen(driver);
	}
	
	public void enterUserName(String userNametxt) {
		//log.info("Entering user Name:  " +userName);
		//logExtentReport("enter userName");
		//TestBase.logExtentReport("Entering user Name: " +userNametxt);
		this.userName.sendKeys(userNametxt);
		

	}
	
	
	public void enterPassword(String password)
	{
		//log.info("Entering password " +password);
		//TestBase.logExtentReport("Entering password " +password);
		this.password.sendKeys(password);
	}
		
	
	public HomePage loginToApplication(String userName, String password)
	{
		
		
		enterUserName(userName);
		enterPassword(password);
		
		
		clickOnLoginButton();
		return new HomePage(driver);
		
	}
	
	public HomePage clickOnLoginButton()
	{
		//log.info("Clicking on Login button....");
		//logExtentReport("Clicked on Login Button");
		//TestBase.logExtentReport("Clicked on Login Button");
		loginButton.click();
		return new HomePage(driver);
	}

}
