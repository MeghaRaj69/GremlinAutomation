package com.gremlinUI.testbase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.gremlinUI.helper.browserConfigurations.BrowserType;
import com.gremlinUI.helper.browserConfigurations.ChromeBrowser;
import com.gremlinUI.testbase.TestBase;
//import com.webMethodsUI.flow.helper.browserConfigurations.FireFoxBrowser;
//import com.webMethodsUI.flow.helper.browserConfigurations.IEexplorerBrowser;
import com.gremlinUI.helper.browserConfigurations.config.ObjectReader;
import com.gremlinUI.helper.browserConfigurations.config.PropertyReader;
import com.gremlinUI.helper.resource.ResourceHelper;
import com.gremlinUI.utils.ExtentManager;
import com.gremlinUI.helper.logger.LoggerHelper;

public class TestBase {
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public WebDriver driver;
	private Logger log = LoggerHelper.getLogger(TestBase.class);
	public static File reportDirectory; 
	
	public static BrowserType currentBrowserType;
	
	//File seprator variable 
	public static String fileSeperator = System.getProperty("file.separator");
	
	@BeforeSuite
	public void beforeSuite() 
	{ 
		extent = ExtentManager.getInstance();
	} 
	
	// Before running all the tests this beforeTest will be called and browser will get intilialized
	@BeforeTest
	public void beforeTest() throws Exception 
	{
		ObjectReader.reader = new PropertyReader();
		reportDirectory = new File(ResourceHelper.getResourcePath("/src/main/resources/screenshots"));
		setUpDriver(ObjectReader.reader.getBrowserType()); 
	} 
	@BeforeClass
	public void beforeClass() 
	{
		test = extent.createTest(getClass().getSimpleName());
	} 
	@BeforeMethod
	public void beforeMethod(Method method) {
		test.log(Status.INFO, "<span style='font-weight:bold;style='font-size:30pt'>"+ method.getName() + "  test started</span>");
	} 
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception 
	{
		if (result.getStatus() == ITestResult.FAILURE) 
		{
			captureConsoleMessages(driver,"\""+result.getMethod().getDescription()+"\"",currentBrowserType); 
			TestBase.getNavigationScreenOnFailure(driver);
			test.log(Status.FAIL, result.getThrowable());
			TestBase.logFailedExtentReport(result.getName());
		}
		else if (result.getStatus() == ITestResult.SUCCESS) 
		{
			test.log(Status.PASS, "<span style='font-weight:bold;style='font-size:30pt'>"+ result.getName() + "  is passed</span>");
			TestBase.logExtentReport(result.getName());
	//		TestBase.getNavigationScreen(driver);
		} 
		else if (result.getStatus() == ITestResult.SKIP) 
		{
			test.log(Status.SKIP, result.getThrowable());
			TestBase.logSkipExtentReport(result.getName());
		}
		extent.flush();
	} 
	public WebDriver getBrowserObject(BrowserType browserType) throws Exception 
	{
		TestBase.currentBrowserType = browserType;
		try 
		{ 
			switch (browserType) 
			{
				case Chrome:
					// get Object of Chrome browser class
					@SuppressWarnings("deprecation") ChromeBrowser chrome = ChromeBrowser.class.newInstance();
					// set options for chrome browser
					ChromeOptions option = chrome.getChromeOptions();
					option.setAcceptInsecureCerts(true);
					return chrome.getChromeDriver(option);
					
//				case Firefox:
//					@SuppressWarnings("deprecation") FireFoxBrowser firefox = FireFoxBrowser.class.newInstance();
//					FirefoxOptions options = firefox.getFireFoxOptions();
//					return firefox.getFireDriver(options); 
//				case IEexplorer:
//					@SuppressWarnings("deprecation") IEexplorerBrowser ie = IEexplorerBrowser.class.newInstance();
//					InternetExplorerOptions cap = ie.getIExplorerCapabilities();
//					return ie.getIExplorerDriver(cap); 
					
			default:
				throw new Exception("Driver not found: " + browserType.name()); 
				} 
			}   catch (Exception e) {
				log.info(e.getMessage());
				throw e; 
				} 
		} 
	
	// This method will launch the browser
	public void setUpDriver(BrowserType browserType) throws Exception 
	{
		driver = getBrowserObject(browserType);
		log.info("Initilize Web Driver: " + driver.hashCode());
		driver.manage().window().maximize(); 
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	} 
	
	public static String captureScreen(String fileName, WebDriver driver) 
	{
		String imgstr = null;
		try 
		{
			if (driver == null)
			{
				//log.info("driver is null");
				return null;
			}
			if (fileName == "") {
				fileName = "blank";
			}
			File destFile = null;
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); 
			destFile = new File(reportDirectory + "/" + fileName + "_" + formatter.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(srcFile, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
			+ "'height='100' width='100'/></a>"); 
			imgstr = imageToBase64String(destFile.getAbsolutePath());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return imgstr; 
	}
	
	public static void logExtentReport(String logInfo)
	{
		test.log(Status.INFO, logInfo); 
	
	}
	
	public static void logSkipExtentReport(String logInfo)
	{
		test.log(Status.SKIP, logInfo); 
	}
	
	public static void logFailedExtentReport(String logInfo)
	{
		test.log(Status.FAIL, logInfo); 
	}
	
	//Convert image to base64 string
		public static String imageToBase64String(String filePath)
		{
			String base64 = "";
	
			try
			{
				InputStream iSteamReader = new FileInputStream(filePath);
				byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
				base64 = Base64.getEncoder().encodeToString(imageBytes);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return base64;
		}
	public void getApplicationUrl(String url) 
	{
		log.info("Navigating to URL" +url);
		//logExtentReport("Navigating to......." +url);
		driver.get(url); 
		//driver.get("http://www.google.com");
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	}
	
	public static void getNavigationScreen(WebDriver driver) 
	{
		//log.info("capturing ui navigation screen");
		String imgstr = captureScreen("", driver); 
		try {
			test.info("Click on below button to expand screenshot: ",MediaEntityBuilder.createScreenCaptureFromBase64String(imgstr).build());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void getNavigationScreenOnFailure(WebDriver driver) 
	{
		//log.info("capturing ui navigation screen");
		String imgstr = captureScreen("", driver); 
		try {
			test.fail("Click on below button to expand screenshot: ",MediaEntityBuilder.createScreenCaptureFromBase64String(imgstr).build());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//Method to return absolute file path based on environment
	public static File absoluteFilePath(String relativeFilePath)
	{
		File file = null; 
	
		if(getCurrentPlatform().contains("Windows"))
		{
			file = new File(System.getProperty("user.dir")+relativeFilePath);
		}
		else if(getCurrentPlatform().contains("nix")||getCurrentPlatform().contains("nux")||getCurrentPlatform().contains("aix"))
		{
			file = new File(System.getProperty("user.dir")+relativeFilePath);
		}
		else if(getCurrentPlatform().contains("mac"))
		{
			file = new File(System.getProperty("user.home")+relativeFilePath);
		}
	
		return file;
	}
	
	//Method to fetch current platform
	public static String getCurrentPlatform()
	{
		return System.getProperty("os.name");
	}
	
//	 private int count = 0; 
//
//	  private int maxCount = 1;
//
//	  @Override
//	  public boolean retry(ITestResult result) { 
//
//	    if(count < maxCount) {  
//
//	       count++;
//
//	       return true;        
//
//	    }        
//
//	    return false; 
//	  }
//	  
	  	//Capture all JS console messages
		public static void captureConsoleMessages(WebDriver driver,String testCaseName,BrowserType browser) throws Exception
		{ 
		try
		{
			switch (browser) 
			{
				case Chrome:
					    //Collect console errors if any
						LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

						if(logEntries.getAll().size()!=0)
						{
							//Create file
							//File file = new File(System.getProperty("user.dir")+"/ConsoleMessages/allConsoleMessages.html");
							File file = new File(absoluteFilePath(fileSeperator+"ConsoleMessages"+fileSeperator+"allConsoleMessages.html").getAbsolutePath());
							FileWriter fileWrite = new FileWriter(file, true);
							BufferedWriter bufferedWrite = new BufferedWriter(fileWrite);

							//Create printwriter object
							PrintWriter write = new PrintWriter(bufferedWrite);
							file.createNewFile();
							write.flush();
							write.println("<TABLE border=1 width = 100%><TR><TH bgcolor="+"#644987"+"  width="+"5%"+"><font color="+"white"+">Log Level</font></TH><TH bgcolor="+"#644987"+" width="+"95%"+"><font color="+"white"+">Console messages after executing "+testCaseName+" test case</font></TH></TR>");

							for (LogEntry entry : logEntries) 
							{
								write.println("<TR><TD Align=Center><PRE>"+entry.getLevel()+"</PRE></TD><TD><PRE>"+entry.getMessage()+"</PRE></TD></Tr>");  
							}

							write.println("</TABLE><br><hr><br>");
							write.close(); 
							test.log(Status.FAIL,"ConsoleError Link : <a href='"+file+"'>ConsoleError Link</a>");
						}
						else
						{
							//File file = new File(System.getProperty("user.dir")+"/ConsoleMessages/allConsoleMessages.html");
							File file = new File(absoluteFilePath(fileSeperator+"ConsoleMessages"+fileSeperator+"allConsoleMessages.html").getAbsolutePath());
							FileWriter fileWrite = new FileWriter(file, true);
							BufferedWriter bufferedWrite = new BufferedWriter(fileWrite);

							//Create printwriter object
							PrintWriter write = new PrintWriter(bufferedWrite);
							file.createNewFile();
							write.flush();
							write.println("<TABLE border=1 width = 100%><TR><TH bgcolor="+"#644987"+" width="+"5%"+"><font color="+"white"+">Log Level</font></TH><TH bgcolor="+"#644987"+" width="+"95%"+"><font color="+"white"+">Console messages after executing "+testCaseName+" test case</font></TH></TR>");
							write.println("<TR><TD Align=Center colspan="+"2"+"><PRE>NO CONSOLE MESSAGES FOUND</PRE></TD></Tr>");
							write.println("</TABLE><br><hr><br>");
							write.close();
							
							test.log(Status.FAIL,"ConsoleError Link : <a href='"+file+"'>ConsoleError Link</a>");
						}
						
				  default:	throw new Exception("Driver not found: " + browser.name()); 
				}
			}
				catch(Exception e)
				{
					System.out.println("Unable to write console error messages in file" +e.getMessage());
				}  
			}
				
		//Clear console error text file
		public static void clearTheFile() throws IOException 
		{
			FileWriter fwOb = new FileWriter(System.getProperty("user.dir")+fileSeperator+"ConsoleMessages"+fileSeperator+"allConsoleMessages.html", false); 
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		}
//		
//		@AfterTest
//		public void afterTest() 
//		{		
//			driver.manage().deleteAllCookies();
//			driver.close();
//			driver.quit();	;
//		}

}

