package com.gremlinUI.helper.browserConfigurations.config;

import com.gremlinUI.helper.browserConfigurations.BrowserType;

public interface ConfigReader {
	
	public int getExplicitWait();
	
	public int getImplicitWait();

	public int getPageLoadTime();

	public BrowserType getBrowserType();

	public String getURL();

	public String getUserName();

	public String getPassword();

	public String GetdeployURL();

	public String GetdeployUserName();

	public String GetdeployPassword();
}
