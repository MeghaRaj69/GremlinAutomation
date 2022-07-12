package com.gremlinUI.helper.wait;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {
	
	private WebDriver driver;
	
	public WaitHelper(WebDriver driver) {
		super();
		this.driver = driver;
		//log.info("WaitHelper Object created....");
	}
	
	/**
	 * Wait for untill frame is switched
	 * @param element
	 * @param timeOutInSeconds
	 */
	
	public void waitForElement(WebElement element,int timeOutInSeconds)
	{
		//log.info("waiting for :" +element.toString() +" for :" +timeOutInSeconds +"seconds");
		WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		//log.info("element is visible now.....");
	}



}
