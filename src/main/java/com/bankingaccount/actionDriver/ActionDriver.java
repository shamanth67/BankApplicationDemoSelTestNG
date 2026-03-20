package com.bankingaccount.actionDriver;

import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bankingaccount.base.BaseClass;
import com.bankingaccount.utilities.ExtentManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int waitTime = Integer.parseInt(BaseClass.getProperties().getProperty("explicitWait")); // Default wait time in seconds
		wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime)); // Default wait time of 10 seconds
		logger.info("ActionDriver initialized with WebDriver: " + driver);
	}

	//wait for an element to be clickable

	private void waitForElementToBeClickable(By by) {
		// Implement the logic to wait for an element to be clickable
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			logger.info("Element is clickable: " + by);
			ExtentManager.logStep("Element is clickable: " + by);
		} catch (Exception e) {
			//System.out.println("Element not clickable: " + e.getMessage());
			logger.error("Element not clickable: " + by, e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click Element", by.toString());
			// Rethrow the exception to fail the test
		}
	}

	//wait for an element to be visible

	private void waitForElementToBeVisible(By by) {
		// Implement the logic to wait for an element to be visible
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			logger.info("Element is visible: " + by);
			 ExtentManager.logStep("Element is visible: " + by);
		} catch (Exception e) {
			//System.out.println("Element not visible: " + e.getMessage());
			logger.error("Element not visible: " + by, e);
			// Rethrow the exception to fail the test
			 ExtentManager.logFailure(BaseClass.getDriver(), "Element not visible: ", by.toString());
			 // Rethrow the exception to fail the test
		}
	}

	//click an element
	public void click(By by) {

		// Implement the logic to click an element
		try {
			applyBorder(by, "green");
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("Clicked element: " + by);
			 ExtentManager.logStep("Clicked element: " + by);
		//	 ExtentManager.logStepWithScreenshot(driver, "Clicked the Expected Element", by.toString());
		} catch (Exception e) {
			applyBorder(by, "red");
			//	System.out.println("Failed to click element: " + e.getMessage());
			logger.error("Failed to click element: " + by, e);
			 ExtentManager.logFailure(BaseClass.getDriver(), "Failed to click element: ", by.toString());
			// Rethrow the exception to fail the test
		}
	}
	//enter text into an element

	public void enterText(By by, String text) {
		// Implement the logic to enter text into an element
		try {
			applyBorder(by, "green");
			waitForElementToBeVisible(by);
			driver.findElement(by).sendKeys(text);
			logger.info("Entered text '" + text + "' into element: " + by);
			 ExtentManager.logStep("Entered text '" + text + "' into element: " + by);
		//	 ExtentManager.logStepWithScreenshot(driver, "Clicked the Expected Element", by.toString());
			 // Rethrow the exception to fail the test
		} catch (Exception e) {
			applyBorder(by, "red");
			//System.out.println("Failed to enter text: " + e.getMessage());
			logger.error("Failed to enter text '" + text + "' into element: " + by, e);
			 ExtentManager.logFailure(BaseClass.getDriver(), "Failed to enter text: ", by.toString());
			// Rethrow the exception to fail the test
		}
	}

	//Get text from an element

	public String getText(By by) {
		// Implement the logic to get text from an element
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			logger.info("Getting text from element: " + by);
			ExtentManager.logStep("Getting text from element: " + by);
			return driver.findElement(by).getText();

		} catch (Exception e) {
			applyBorder(by, "red");
			//	System.out.println("Failed to get text: " + e.getMessage());
			logger.error("Failed to get text from element: " + by, e);
			 ExtentManager.logFailure(BaseClass.getDriver(), "Failed to get text from element: ", by.toString());
			return null; // Return null or throw an exception based on your preference

		}
	}
	//Compare text of an element with expected text

	public boolean compareText(By by, String expectedText) {
		// Implement the logic to compare text of an element with expected text
		try {
			waitForElementToBeVisible(by);
			String actualText = getText(by);
			logger.info("Comparing actual text '" + actualText + "' with expected text '" + expectedText + "' for element: " + by);
			 ExtentManager.logStep("Comparing actual text '" + actualText + "' with expected text '" + expectedText + "' for element: " + by);
		//	 ExtentManager.logStepWithScreenshot(driver, expectedText,by.toString());
			return actualText.equals(expectedText);
		} catch (Exception e) {
			//	System.out.println("Failed to compare text: " + e.getMessage());
			logger.error("Failed to compare text for element: " + by, e);
			 ExtentManager.logFailure(BaseClass.getDriver(), "Failed to compare text for element: ", by.toString());
			return false; // Return false or throw an exception based on your preference
		}
	}
	//Element is displayed or not
	public boolean isElementDisplayed(By by) {
		// Implement the logic to check if an element is displayed
		try {
			waitForElementToBeVisible(by);
			logger.info("Checking if element is displayed: " + by);
			 ExtentManager.logStep("Checking if element is displayed: " + by);
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			//System.out.println("Failed to check if element is displayed: " + e.getMessage());
			logger.error("Failed to check if element is displayed: " + by, e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Failed to check if element is displayed: ", by.toString());
			return false; // Return false or throw an exception based on your preference
		}
	}

	//Element is enabled or not
	public boolean isElementEnabled(By by) {
		// Implement the logic to check if an element is enabled
		try {
			waitForElementToBeVisible(by);
			logger.info("Checking if element is enabled: " + by);
			ExtentManager.logStep("Checking if element is enabled: " + by);
		//	ExtentManager.logStepWithScreenshot(driver, "Is Element Enabled in Page", by.toString());
			return driver.findElement(by).isEnabled();
		} catch (Exception e) {
			//System.out.println("Failed to check if element is enabled: " + e.getMessage());
			logger.error("Failed to check if element is enabled: " + by, e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Failed to check if element is enabled: ", by.toString());
			return false; // Return false or throw an exception based on your preference
		}
	}

	//Element is selected or not
	public boolean isElementSelected(By by) {
		// Implement the logic to check if an element is selected
		try {
			waitForElementToBeVisible(by);
			logger.info("Checking if element is selected: " + by);
			ExtentManager.logStep("Checking if element is selected: " + by);
			return driver.findElement(by).isSelected();
		} catch (Exception e) {
			//System.out.println("Failed to check if element is selected: " + e.getMessage());
			logger.error("Failed to check if element is selected: " + by, e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Failed to check if element is selected: ", 	by.toString());
			return false; // Return false or throw an exception based on your preference
		}
	}
	//scroll to an element
	public void scrollToElement(By by) {
		// Implement the logic to scroll to an element
		try {
			waitForElementToBeVisible(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
			ExtentManager.logStep("Scrolled to element: " + by);
			logger.info("Scrolled to element: " + by);
		} catch (Exception e) {
			//System.out.println("Failed to scroll to element: " + e.getMessage());
			logger.error("Failed to scroll to element: " + by, e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Failed to scroll to element: ", by.toString());
			// Rethrow the exception to fail the test
		}
	}

	//wait for page to load completely
	public void waitForPageToLoad() {
		// Implement the logic to wait for the page to load completely
		try {
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			 ExtentManager.logStep("Page loaded completely.");
			logger.info("Page loaded completely.");
		} catch (Exception e) {
			//	System.out.println("Failed to wait for page to load: " + e.getMessage());
			logger.error("Failed to wait for page to load", e);
			 ExtentManager.logFailure(BaseClass.getDriver(), "Failed to wait for page to load: ", e.getMessage());
			// Rethrow the exception to fail the test
		}
	}

	//handle alerts
	public void handleAlertAcept(boolean accept) {
		Alert alert =  driver.switchTo().alert();
		// Implement the logic to handle alerts
		
		
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			if (accept) {
				alert.accept();
			//	logger.info("Alert accepted.");
			} else {
				alert.dismiss();
			//	logger.info("Alert dismissed.");
			}
		} catch (Exception e) {
			//	System.out.println("Failed to handle alert: " + e.getMessage());
		logger.error("Failed to handle alert", e);
			// Rethrow the exception to fail the test
		}
	}
	//get text from an alert
	public String getAlertText() {
		Alert alert =  driver.switchTo().alert();
		// Implement the logic to get text from an alert
		try {
			wait.until(ExpectedConditions.alertIsPresent());
		//	logger.info("Getting text from alert.");
		//	ExtentManager.logStep("Getting text from alert.");
			return alert.getText();
		} catch (Exception e) {
			System.out.println("Failed to get alert text: " + e.getMessage());
			return null; // Return null or throw an exception based on your preference
		}
	}

	public By getByFromString(String text) {

		String locatorType = "//td[contains(text(),'"+text+"')]/following-sibling::td/button";
		return new ByXPath(locatorType);
	}

	//Get the title of the current page
	public String getTitle() {
		// Implement the logic to get the title of the current page
		try {
			logger.info("Getting title of the current page.");
			ExtentManager.logStep("Getting title of the current page.");
			return driver.getTitle();
		} catch (Exception e) {
			// System.out.println("Failed to get page title: " + e.getMessage());
			logger.error("Failed to get page title", e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Failed to get page title: ", e.getMessage());
			return null; // Return null or throw an exception based on your preference
		}
	}

	//select an option from a dropdown by visible text
	public void selectDropdownByVisibleText(By by, String visibleText) {
		// Implement the logic to select an option from a dropdown by visible text
		try {
			waitForElementToBeVisible(by);
			Select dropdown = new Select(driver.findElement(by));
			dropdown.selectByVisibleText(visibleText);
			logger.info("Selected dropdown option '" + visibleText + "' from element: " + by);
		} catch (Exception e) {
			//	System.out.println("Failed to select dropdown option: " + e.getMessage());
			logger.error("Failed to select dropdown option '" + visibleText + "' from element: " + by, e);
			// Rethrow the exception to fail the test
		}
	}

	//Get the value of an attribute from an element
	public String getAttribute(By by, String attributeName) {
		// Implement the logic to get the value of an attribute from an element
		try {
			waitForElementToBeVisible(by);
			logger.info("Getting value of attribute '" + attributeName + "' from element: " + by);
			return driver.findElement(by).getAttribute(attributeName);
		} catch (Exception e) {
			// System.out.println("Failed to get attribute value: " + e.getMessage());
			logger.error("Failed to get value of attribute '" + attributeName + "' from element: " + by, e);
			return null; // Return null or throw an exception based on your preference
		}
	}

	//wait for an element to be present in the DOM
	public void waitForElementToBePresent(By by) {
		// Implement the logic to wait for an element to be present in the DOM
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			logger.info("Element is present in the DOM: " + by);
		} catch (Exception e) {
			// System.out.println("Failed to wait for element to be present: " + e.getMessage());
			logger.error("Failed to wait for element to be present: " + by, e);
			// Rethrow the exception to fail the test
		}
	}
	//select an option from a dropdown by Value
	public void selectDropdownByValue(By by, String value) {
		// Implement the logic to select an option from a dropdown by value
		try {
			waitForElementToBeVisible(by);
			Select dropdown = new Select(driver.findElement(by));
			dropdown.selectByValue(value);
			logger.info("Selected dropdown option with value '" + value + "' from element: " + by);
		} catch (Exception e) {
			//	System.out.println("Failed to select dropdown option: " + e.getMessage());
			logger.error("Failed to select dropdown option with value '" + value + "' from element: " + by, e);
			// Rethrow the exception to fail the test
		}
	}
	//Method to get the description of an element for logging purposes
	public String getElementDescription(By by) {

		if(driver == null) {
			logger.warn("WebDriver instance is null. Cannot get element description for: " + by);
			return null; // Return null or throw an exception based on your preference
		}
		if(by == null) {
			logger.warn("By locator is null. Cannot get element description.");
			return null; // Return null or throw an exception based on your preference
		}

		// Implement the logic to get a description of an element for logging purposes
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String className = element.getDomAttribute("class");
			String text = element.getText();
			String placeholder = element.getDomAttribute("placeholder");

			if(isNullOrEmpty(name)) {
				return "Element located by: " + by.toString();
			}else if(isNullOrEmpty(id)) {
				return "Element located by: " + by.toString();
			}else if(isNullOrEmpty(className)) {
				return "Element located by: " + by.toString();
			}else if(isNullOrEmpty(text)) {
				return "Element located by: " + by.toString();
			}else if(isNullOrEmpty(placeholder)) {
				return "Element located by: " + by.toString();
			}

		} catch (Exception e) {
			// System.out.println("Failed to get element description: " + e.getMessage());
			logger.error("Failed to get description of element: " + by, e);
			return null; // Return null or throw an exception based on your preference
		}
		return null; }
	//utility method to check a String is null or empty and log the result
	private boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			logger.warn("String is null or empty: '" + str + "'");
			return true;
		}
		return false;
	}
	
	//Utility Method to border an Element
	public void applyBorder(By by, String color) {
		
		try {
			WebElement element =driver.findElement(by);
			
			//apply border
			
			String script = "arguments[0].style.border='3px solid "+color+"'";
			JavascriptExecutor  executor =(JavascriptExecutor) driver;
			executor.executeScript(script, element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Handle the alert
	public void handleAlertpop() {
		// Implement the logic to handle alerts
		try {
				driver.switchTo().alert();
			//	logger.info("Alert accepted.");
				ExtentManager.logStep("Alert accepted.");
				} catch (Exception e) {
			//	System.out.println("Failed to handle alert: " + e.getMessage());
		//	logger.error("Failed to handle alert", e);
			ExtentManager.logFailure(BaseClass.getDriver(), "Failed to handle alert: ", e.getMessage());
			// Rethrow the exception to fail the test
		}
	}
	
	public boolean waitForAlerttoBepresent() {
		 try {
		        driver.switchTo().alert();
		        return true;
		    } catch (NoAlertPresentException e) {
		        return false;
		    }
		}
}
