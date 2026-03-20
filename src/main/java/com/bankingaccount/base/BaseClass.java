package com.bankingaccount.base;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.annotations.BeforeMethod;

import com.bankingaccount.actionDriver.ActionDriver;
import com.bankingaccount.utilities.ExtentManager;
import com.bankingaccount.utilities.LoggerManager;

public class BaseClass {

	protected static Properties properties;
	//protected static WebDriver driver;
	//private static ActionDriver actionDriver;
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	

	@BeforeSuite
	public void loadConfiguration() throws Exception {
		//Load the configuration properties
		properties = new Properties();
		FileInputStream inputStream = new FileInputStream("src/main/resources/config.properties");
		properties.load(inputStream);
		logger.info("Configuration properties loaded successfully.");
		
		//Start the extent report
		//ExtentManager.getExtentReporter(); -- This has been implemented in Listener
		
	}
	@BeforeMethod
	public synchronized void setUp() throws Exception {

		System.out.println("Setting up the test environment..."+this.getClass().getSimpleName());
		loadConfiguration();
		launchBrowser();
		configureBrowser();
		//staticWait(); // Static wait to ensure the browser is fully loaded before proceeding
		logger.info("Browser launched and configured successfully.");
		//Intialize ActionDriver only once
	/*	if(actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActionDriver initialized successfully." +Thread.currentThread().getId());
			logger.trace("ActionDriver instance created for the first time.");
			logger.debug("ActionDriver instance created with WebDriver: " + driver);
			logger.error("This is a debug message to verify logging configuration.");
			logger.warn("This is a warning message to verify logging configuration.");
			logger.fatal("This is a fatal message to verify logging configuration.");
		}*/
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialized successfully for thread: " + Thread.currentThread().getId());
	}
	private synchronized void launchBrowser() throws Exception {
		//Initialize the WebDriver based on the configuration
		String browser = properties.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
		//	driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("Chrome browser launched successfully.");
		} else if (browser.equalsIgnoreCase("firefox")) {
		//	driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("Firefox browser launched successfully.");
		}
		else if(browser.equalsIgnoreCase("edge")) {
		//	driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("Edge browser launched successfully.");
		}
		else {
			throw new Exception("Unsupported browser: " + browser);
		}
	}
	private void configureBrowser() {
		// Maximize the browser window
		getDriver().manage().window().maximize();

		// Navigate to the base URL
		String baseUrl = properties.getProperty("baseUrl");
		try {
			getDriver().get(baseUrl);
			logger.info("Navigated to the base URL: " + baseUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error navigating to the base URL: " + e.getMessage());
		}

		// Implicit wait for elements to be found
		int implicitWaitTime = Integer.parseInt(properties.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(implicitWaitTime));
	}



	@AfterMethod
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
				logger.info("Browser closed successfully.");
			} catch (Exception e) {
				System.out.println("Error closing the browser: " + e.getMessage());
			}
		}
		driver.remove(); // Remove the WebDriver instance from ThreadLocal to prevent memory leaks
		actionDriver.remove(); // Remove the ActionDriver instance from ThreadLocal to prevent memory leaks
		//driver = null; // Ensure driver reference is cleared after quitting
	//	actionDriver = null; // Clear ActionDriver reference to avoid stale references in future tests
	//	ExtentManager.endTest(); // End the extent test for the current test method  -- This method has been implemented in Listeners
	}
	// Utility method for static wait
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			System.out.println("Static wait interrupted: " + e.getMessage());
		}
	}
	// Getters and setters for WebDriver
	public  static WebDriver getDriver() {
		return driver.get();
	}
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}
	
	public static Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public static ActionDriver getActionDriver() {
		
		if(actionDriver.get() == null) {
			throw new IllegalStateException("ActionDriver has not been initialized. Please ensure that the setUp method has been called before accessing ActionDriver.");
		}
		return actionDriver.get();
	}
}
