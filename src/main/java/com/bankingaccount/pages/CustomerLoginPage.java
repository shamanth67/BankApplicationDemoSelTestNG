package com.bankingaccount.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bankingaccount.actionDriver.ActionDriver;
import com.bankingaccount.base.BaseClass;

public class CustomerLoginPage {

private ActionDriver actionDriver;
	
	private By yourNameLabel = By.xpath("//label[contains(text(),'Your Name')]");
	private By customerDropdown = By.id("userSelect");
	/*
	public CustomerLoginPage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
	*/
	
	public CustomerLoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	public void verifyYourNameLabeIsDisplayed() {
		if (!actionDriver.isElementDisplayed(yourNameLabel)) {
			throw new AssertionError("Customer Login button is not Displayed on the login Page.");
		}else {
			System.out.println("Customer Login button is Displayed on the login Page.");
		}
	}
	
	public void selectCustomerFromDropdown(String customerName) {
		actionDriver.click(customerDropdown);
		actionDriver.selectDropdownByVisibleText(customerDropdown, customerName);
	}
}
