package com.gremlinUI.testsuits.chaosTest;

import org.testng.annotations.Test;

import com.gremlinUI.helper.browserConfigurations.config.ObjectReader;
import com.gremlinUI.pageObjects.common.LoginPage;
import com.gremlinUI.pageObjects.project.HomePage;
import com.gremlinUI.testbase.TestBase;
//import com.gremlinUI.helper.assertion.AssertionHelper;

public class ChaosTest extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	
	//Login

		@Test(groups = "sanity", description = "ensure login workd fine", priority = 1)
		public void loginTest() throws Exception {
            System.out.println("get url");
			getApplicationUrl(ObjectReader.reader.getURL());
			loginPage = new LoginPage(driver);
			homePage = loginPage.loginToApplication(ObjectReader.reader.getUserName(), ObjectReader.reader.getPassword());

		}

}

