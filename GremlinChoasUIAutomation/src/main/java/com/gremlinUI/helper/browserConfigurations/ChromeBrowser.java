package com.gremlinUI.helper.browserConfigurations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gremlinUI.helper.resource.ResourceHelper;

public class ChromeBrowser {

	// when we create chrome driver we need to provide some options to chrome
	// This method only gives chrome options capability
	public ChromeOptions getChromeOptions() {

//		ChromeOptions option = new ChromeOptions();
//
//		option.addArguments("--test-type");
//		option.addArguments("--disable-popup-blocking");
//
//		// get Chrome capabilities
//		DesiredCapabilities chrome = DesiredCapabilities.chrome();
//
//		chrome.setJavascriptEnabled(true);
//		option.setCapability(ChromeOptions.CAPABILITY, chrome);

		ChromeOptions option = new ChromeOptions();
        option.addArguments("--test-type");
        option.addArguments("--disable-popup-bloacking");
        //option.addArguments("--incognito");
        DesiredCapabilities chrome = DesiredCapabilities.chrome();
        /*Error
        Multiple markers at this line - DesiredCapabilities cannot be resolved -
        * DesiredCapabilities cannot be resolved to a type*/
        chrome.setJavascriptEnabled(true);
        chrome.setCapability(ChromeOptions.CAPABILITY, option);
		// Linux
		if (System.getProperty("os.name").contains("Linux")) {
			option.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
		}

		return option;
	}

	// call this method with chrome options
	// set the location of chrome driver
	public WebDriver getChromeDriver(ChromeOptions cap) {

		if (System.getProperty("os.name").contains("Mac")) {
			// need to add chrome jar path for Mac
			System.setProperty("webdriver.chrome.driver", ResourceHelper.getResourcePath(""));
			return new ChromeDriver(cap);
		} else if (System.getProperty("os.name").contains("Window")) {
			System.setProperty("webdriver.chrome.driver",
					ResourceHelper.getResourcePath("/src/main/resources/Driver/chromedriver.exe"));
			return new ChromeDriver(cap);
		} // while using add linux path
		else if (System.getProperty("os.name").contains("Linux")) {
			System.setProperty("webdriver.chrome.driver",
					ResourceHelper.getResourcePath("/src/main/resources/Driver/chromedriver.exe"));
			return new ChromeDriver(cap);
		}

		return null;
	}
}
