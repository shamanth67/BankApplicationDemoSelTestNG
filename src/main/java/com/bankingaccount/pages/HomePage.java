package com.bankingaccount.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bankingaccount.actionDriver.ActionDriver;
import com.bankingaccount.base.BaseClass;

public class HomePage {

	private ActionDriver actionDriver;
	
	private By customerLoginButton = By.xpath("//button[text()='Customer Login']");
	private By bankManagerLoginButton = By.xpath("//button[text()='Bank Manager Login']");
	
	
/*	public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
	*/
	
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	public void openCustomerLoginPage() {
		actionDriver.click(customerLoginButton);
	}
	
	public void openBankManagerLoginPage() {
			actionDriver.click(bankManagerLoginButton);
	}
	
	public void verifyHomePageTitle() {
		String expectedTitle = "Protractor practice website - Banking App";
		String actualTitle = actionDriver.getTitle();
		if (!actualTitle.equals(expectedTitle)) {
			throw new AssertionError("Page title does not match expected value. Expected: " + expectedTitle + ", Actual: " + actualTitle);
		}
	}
	
	public void verifyCustomerLoginButtonIsDisplayed() {
		if (!actionDriver.isElementDisplayed(customerLoginButton)) {
			throw new AssertionError("Customer Login button is not displayed on the Home Page.");
		}else {
			System.out.println("Customer Login button is displayed on the Home Page.");
		}
	}
	
	public void verifyBankManagerLoginButtonIsDisplayed() {
		if (!actionDriver.isElementDisplayed(bankManagerLoginButton)) {
			throw new AssertionError("Bank Manager Login button is not displayed on the Home Page.");
		}else {
			System.out.println("Bank Manager Login button is displayed on the Home Page.");
		}
	}
	

}
