package com.bankingaccount.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.bankingaccount.actionDriver.ActionDriver;
import com.bankingaccount.base.BaseClass;

public class AddCustomer {

	private ActionDriver actionDriver;
	
	private By addCustomer = By.xpath("//button[contains(text(),'Add Customer')]");
	private By firstName = By.xpath("//input[@placeholder='First Name']");
	private By lastName = By.xpath("//input[@placeholder='Last Name']");
	private By postCode = By.xpath("//input[@placeholder='Post Code']");
	private By addCustomerButton = By.xpath("//button[text()='Add Customer']");
	/*
	public AddCustomer(WebDriver driver) {
		this.actionDriver =new ActionDriver(driver);
	}*/
	
	public AddCustomer(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	public void verifyAddCustomerButtonIsDisplayed() {
		if (!actionDriver.isElementDisplayed(addCustomer)) {
			throw new AssertionError("Add Customer button is not Displayed on the Create Account Page.");
		}else {
			System.out.println("Add Customer button is Displayed on the Create Account Page.");
		}
	}
	
	public void openAddCustomerForm() {
		actionDriver.click(addCustomer);
	}
	public void validationOfAddCustomerForm(String firstNameText, String lastNameText, String postCodeText) {

		actionDriver.enterText(firstName, firstNameText);
		actionDriver.enterText(lastName, lastNameText	);
		actionDriver.enterText(postCode, postCodeText);
			actionDriver.click(addCustomerButton);
			// TODO Auto-generated catch block
		String errorMessage = actionDriver.getAttribute(firstName, "validationMessage");
		Assert.assertEquals("Please fill out this field.", errorMessage);
		System.out.println("Validation message is displayed as expected: " + errorMessage);
	}
	
	public void addCustomer(String firstNameText, String lastNameText, String postCodeText) throws UnhandledAlertException {
		actionDriver.enterText(firstName, firstNameText);
		actionDriver.enterText(lastName, lastNameText	);
		actionDriver.enterText(postCode, postCodeText);
			actionDriver.click(addCustomerButton);
		actionDriver.waitForAlerttoBepresent();

	}
	
	public void verifyFirstNameIsDisplayed() {
		if (!actionDriver.isElementDisplayed(firstName)) {
			throw new AssertionError("First Name field is not Displayed on the Create Account Page.");
		}else {
			System.out.println("First Name field is Displayed on the Create Account Page.");
		}
	}
	
	public void verifyLastNameIsDisplayed() {
		if (!actionDriver.isElementDisplayed(lastName)) {
			throw new AssertionError("Last Name field is not Displayed on the Create Account Page.");
		}else {
			System.out.println("Last Name field is Displayed on the Create Account Page.");
		}
	}
	
	public void verifyPostCodeIsDisplayed() {
		if (!actionDriver.isElementDisplayed(postCode)) {
			throw new AssertionError("Post Code field is not Displayed on the Create Account Page.");
		}else {
			System.out.println("Post Code field is Displayed on the Create Account Page.");
		}
	}
	
	public void verifyAddCustomerSubmitButtonIsDisplayed() {
		if (!actionDriver.isElementDisplayed(addCustomerButton)) {
			throw new AssertionError("Add Customer submit button is not Displayed on the Create Account Page.");
		}else {
			System.out.println("Add Customer submit button is Displayed on the Create Account Page.");
		}
	}
	
	public void handleAlertAndVerifyMessage(String expectedMessage) {
		String actualMessage = actionDriver.getAlertText();
		System.out.println("Alert message received: " + actualMessage);
		String accountId = actualMessage.split(":")[1].trim(); // Extract the account ID from the alert message
		String alertMessageWithoutId = actualMessage.split(":")[0].trim(); // Get the alert message without the account ID
		Assert.assertEquals(alertMessageWithoutId, expectedMessage, "Alert message does not match expected value.");
		actionDriver.handleAlertAcept(true); // Accept the alert
		System.out.println("Alert message is displayed as expected: " + actualMessage);
	}
}
