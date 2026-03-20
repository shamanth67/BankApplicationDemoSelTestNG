package com.bankingaccount.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.bankingaccount.actionDriver.ActionDriver;
import com.bankingaccount.base.BaseClass;

public class OpenAccount {

	private ActionDriver actionDriver;
	
	private By openAccountButton = By.xpath("//button[contains(text(),'Open Account')]");
	private By customerDropdown = By.id("userSelect");
	private By currencyDropdown = By.id("currency");
	private By processButton = By.xpath("//button[contains(text(),'Process')]");
	private By customerLabel = By.xpath("//label[contains(text(),'Customer')]");
	private By currencyLabel = By.xpath("//label[contains(text(),'Currency')]");
	
	/*
	public OpenAccount(WebDriver driver) {
		this.actionDriver =new ActionDriver(driver);
	}
	*/
	
	public OpenAccount(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	public void verifyOpenAccountButtonIsDisplayed() {
		if (!actionDriver.isElementDisplayed(openAccountButton)) {
			throw new AssertionError("Open Account button is not Displayed on the Open Account Page.");
		}else {
			System.out.println("Open Account button is Displayed on the Open Account Page.");
		}
	}
	
	public void openOpenAccountForm() {
		actionDriver.click(openAccountButton);
	}
	
	public void verifyCustomerLabelIsDisplayed() {
		if (!actionDriver.isElementDisplayed(customerLabel)) {
			throw new AssertionError("Customer label is not Displayed on the Open Account Page.");
		}else {
			System.out.println("Customer label is Displayed on the Open Account Page.");
		}
	}
	
	public void verifyCurrencyLabelIsDisplayed() {
		if (!actionDriver.isElementDisplayed(currencyLabel)) {
			throw new AssertionError("Currency label is not Displayed on the Open Account Page.");
		}else {
			System.out.println("Currency label is Displayed on the Open Account Page.");
		}
	}
	
	public void verifyProcessButtonIsDisplayed() {
		if (!actionDriver.isElementDisplayed(processButton)) {
			throw new AssertionError("Process button is not Displayed on the Open Account Page.");
		}else {
			System.out.println("Process button is Displayed on the Open Account Page.");
		}
	}
	
	public void openAccount(String customerName, String currency) throws Exception {
		actionDriver.click(customerDropdown);
		actionDriver.selectDropdownByVisibleText(customerDropdown, customerName);
		actionDriver.click(currencyDropdown);
		actionDriver.waitForElementToBePresent(currencyDropdown);
		actionDriver.selectDropdownByValue(currencyDropdown, currency);
		actionDriver.click(currencyLabel);
			actionDriver.click(processButton);
		
		
	}
	
	public void validationOfOpenAccountForm() {
		actionDriver.click(processButton);
		String errorMessage = actionDriver.getAttribute(customerDropdown, "validationMessage");
		Assert.assertEquals("Please select an item in the list.", errorMessage);
		System.out.println("Validation message is displayed as expected: " + errorMessage);
	}

	public void handleAlertAndVerifyMessage(String expectedMessage) throws Exception {
		String actualMessage = actionDriver.getAlertText();
		System.out.println("Alert message received: " + actualMessage);
		String accountNumber = actualMessage.split(":")[1].trim(); // Extract the account ID from the alert message
		String alertMessageWithoutNumber = actualMessage.split(":")[0].trim(); // Get the alert message without the account ID
		Assert.assertEquals(alertMessageWithoutNumber, expectedMessage, "Alert message does not match expected value.");// Accept the alert
		System.out.println("Alert message is displayed as expected: " + actualMessage);
		actionDriver.handleAlertAcept(true);
	}
	
	public void clickCurrencyLabel() {
		actionDriver.click(currencyLabel);
	}
}
