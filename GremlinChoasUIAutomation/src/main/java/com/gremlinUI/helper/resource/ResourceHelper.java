package com.gremlinUI.helper.resource;

public class ResourceHelper {
	
	//This method is designed to ensure
	//changing the location of framework should not fail the test scripts.
	
	//This class helps to log from logger .hence do not create log.info here
	public static String getResourcePath(String path) {
		
		System.out.println("Relative path for property file is --->" +path);
		//This will provide location till the project
		String basePath = System.getProperty("user.dir");
		System.out.println("Base path for property file is--->" +basePath);
		
		return basePath + path;
		
		
		
	}
	
}
