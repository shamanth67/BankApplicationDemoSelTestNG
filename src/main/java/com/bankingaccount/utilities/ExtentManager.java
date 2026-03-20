package com.bankingaccount.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ExtentTest test;
	private static	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap();

	//Initializing the extent report
	public synchronized static ExtentReports getExtentReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport_"+getTimeStamp()+".html";
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setReportName("Banking Account Test Automation Report");
			sparkReporter.config().setDocumentTitle("Banking Account Test");
			sparkReporter.config().setTheme(Theme.DARK);
			//initializing the extent report	
			extent = new ExtentReports();	
			
		extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Operating System", "Windows 11");
			extent.setSystemInfo("Java Version", "java 25");
			extent.setSystemInfo("Tester Name", "Shamanth");
		}
		return extent;
	}

	//start test and create extent test instance for the current thread
	public synchronized static ExtentTest startTest(String testName) {
		ExtentTest test = getExtentReporter().createTest(testName);
		extentTest.set(test);
		return test;
	}
	
	//End test and remove the extent test instance for the current thread
	public static void endTest() {
		getExtentReporter().flush();
	}
	//Get current thread
	
	public synchronized static ExtentTest getTest() {
		return extentTest.get();
	}
	
	
	//Method to get the name of the current test
	public synchronized static String getCurrentTestName() {
			
		ExtentTest currentTest = getTest();
		if (currentTest != null) {
			return currentTest.getModel().getName();
		}else {
			return "Unknown Test";
		}
		
		
	}
	
	//Log a step in the current test
	public synchronized static void logStep(String stepDescription) {
		getTest().info(stepDescription);
	}
	
	//Log a step with screenshot in the current test
	public synchronized static void logStepWithScreenshot(WebDriver driver,String stepDescription, String screenshotMessage) {
		getTest().pass(stepDescription);
		attachScreenshotToReport(driver, screenshotMessage);
	}
	
	//Log Failure
	public static void logFailure(WebDriver driver,String failureDescription,String screenshotMessage) {
		getTest().fail(failureDescription);
		attachScreenshotToReport(driver, screenshotMessage);
	}
	
	//log a Skip
	public static void logSkip(WebDriver driver,String skipDescription,String screenshotMessage) {
		getTest().skip(skipDescription);
		attachScreenshotToReport(driver, screenshotMessage);
	}
	
	
	//Take screenshot with date and time in the file
	public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
		String screenshotPath = System.getProperty("user.dir") + "/src/test/resources/Screenshots/"+screenshotName+"_" + timeStamp + ".png";
		File dest = new File(screenshotPath);
		try {
			org.apache.commons.io.FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Coverting the screenshot to base64 string and attaching it to the report
		String base64Screenshot =convertScreenshotToBase64(src);
		return base64Screenshot;
	}
	//Covert screenshot to base64 format
	public synchronized static String convertScreenshotToBase64(File screenshotFile) {
		String base64Screenshot = "";
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
			// Convert the byte array to a Base64 string
			base64Screenshot = java.util.Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Screenshot;
	}
	
	//Attach screenshot to the report using base64 string
	public synchronized static void attachScreenshotToReport(WebDriver driver,String message) {
		try {
			String base64Screenshot = takeScreenshot(driver, getCurrentTestName());
			getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			getTest().addScreenCaptureFromBase64String(base64Screenshot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getTest().fail("Failed to attach screenshot: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Register web driver for the current thread
	public synchronized static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}
	
	//create a method of timestamp to generate unique screenshot names
	public static String getTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
	}
}
