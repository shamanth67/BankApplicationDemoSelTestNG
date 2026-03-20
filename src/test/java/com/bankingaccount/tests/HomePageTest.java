package com.bankingaccount.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bankingaccount.base.BaseClass;
import com.bankingaccount.pages.AddCustomer;
import com.bankingaccount.pages.CustomerLoginPage;
import com.bankingaccount.pages.HomePage;
import com.bankingaccount.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

	private HomePage homePage;
	private AddCustomer createAccount;
	private CustomerLoginPage customerLoginPage;
	
	@BeforeMethod
	public void setUpPages() {
		homePage = new HomePage(getDriver());
		createAccount = new AddCustomer(getDriver());
		customerLoginPage = new CustomerLoginPage(getDriver());
	}
	@Test
	//Verify that the home page title is correct and both buttons are displayed
	public void verifyHomePageButtons() {
		ExtentManager.startTest("Verify Home Page Buttons"); //  -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.verifyBankManagerLoginButtonIsDisplayed();
		homePage.verifyCustomerLoginButtonIsDisplayed();
		
	}
	@Test
	//Verify that clicking the "Bank Manager Login" button navigates to the Bank Manager Login page and the "Add Customer" button is displayed
	public void verifyBankManagerLoginButtonFunctionality() {
		ExtentManager.startTest("Verify Bank Manager Login Button Functionality");  //-- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		createAccount.verifyAddCustomerButtonIsDisplayed();
	}
	@Test
	//Verify that clicking the "Customer Login" button navigates to the Customer Login page and the "Your Name" label is displayed
	public void verifyCustomerLoginButtonFunctionality() {
	ExtentManager.startTest("Verify Customer Login Button Functionality"); //  -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openCustomerLoginPage();
		
		customerLoginPage.verifyYourNameLabeIsDisplayed();
	}
}
