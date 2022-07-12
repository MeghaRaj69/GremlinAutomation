package com.gremlinUI.helper.browserConfigurations.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.gremlinUI.helper.browserConfigurations.BrowserType;
import com.gremlinUI.helper.resource.ResourceHelper;

public class PropertyReader implements ConfigReader {
	private static FileInputStream file;
	public static Properties OR;

	// property file will be loaded into memory whenever object of this file is
	// created
	public PropertyReader() {
		System.out.println("property");
		String filePath = ResourceHelper.getResourcePath("/src/main/resources/configfile/config.properties");
		try {
			file = new FileInputStream(new File(filePath));
			OR = new Properties();
			OR.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getImplicitWait() {

		return Integer.parseInt(OR.getProperty("implicitWait"));
	}

	@Override
	public int getExplicitWait() {
		return Integer.parseInt(OR.getProperty("explicitWait"));

	}

	@Override
	public int getPageLoadTime() {

		return Integer.parseInt(OR.getProperty("pageLoadTime"));

	}

	// here string converted into enum
	@Override
	public BrowserType getBrowserType() {

		return BrowserType.valueOf(OR.getProperty("browserType"));
	}

	@Override
	public String getURL() {
		System.out.println("url");
		if(System.getProperty("url")!=null) {
			return System.getProperty("url");
			
		}
		//this return will execute only when the pom property is null
		return OR.getProperty("applicationUrl");
	}

	@Override
	public String getUserName() {
		if(System.getProperty("userName")!=null) {
			return System.getProperty("userName");
			
		}
		
		return OR.getProperty("userName");
	}

	@Override
	public String getPassword() {
		if(System.getProperty("password")!=null) {
			return System.getProperty("password");
			
		}
		
		return OR.getProperty("password");
	}

	@Override
	public String GetdeployURL() {
		if(System.getProperty("DeployedtenantURL")!=null) {
			return System.getProperty("DeployedtenantURL");

		}
		//this return will execute only when the pom property is null
		return OR.getProperty("DeployedtenantURL");
	}

	@Override
	public String GetdeployUserName() {
		if(System.getProperty("DeployedtenantuserName")!=null) {
			return System.getProperty("DeployedtenantuserName");

		}

		return OR.getProperty("DeployedtenantuserName");
	}

	@Override
	public String GetdeployPassword() {
		if(System.getProperty("Deployedpassword")!=null) {
			return System.getProperty("Deployedpassword");

		}

		return OR.getProperty("Deployedpassword");
	}

}
