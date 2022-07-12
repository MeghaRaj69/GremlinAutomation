package com.gremlinUI.helper.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.gremlinUI.helper.resource.ResourceHelper;

public class LoggerHelper {
	
private static boolean root=false;
	
	public static Logger getLogger(Class cls)
	{
		//if logger is created by some class or utility
		if(root)
		{
			return Logger.getLogger(cls);
		}
		//If no logger defined by any class then define the logger
		PropertyConfigurator.configure(ResourceHelper.getResourcePath("/src/main/resources/configfile/log4j.properties"));
		root = true;
		return Logger.getLogger(cls);
		
	}

}
