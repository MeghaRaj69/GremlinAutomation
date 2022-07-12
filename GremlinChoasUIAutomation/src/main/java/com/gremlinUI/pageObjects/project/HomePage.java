package com.gremlinUI.pageObjects.project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gremlinUI.helper.wait.WaitHelper;
import com.gremlinUI.helper.browserConfigurations.config.ObjectReader;

public class HomePage {

	WebDriver driver;
	//private Logger log = LoggerHelper.getLogger(HomePage.class);
	WaitHelper waitHelper;
	
	@FindBy(xpath = "//*[@class='project-greet']")
	@CacheLookup
	WebElement HomePageGreetMessageDescription;
	
	public HomePage(WebDriver driver) {

		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);

		waitHelper = new WaitHelper(driver);
		waitHelper.waitForElement(HomePageGreetMessageDescription, ObjectReader.reader.getExplicitWait());

		//log.info("element is visible now....");
		// new TestBase().getNavigationScreen(driver);

	}
}
