package com.bankingaccount.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bankingaccount.base.BaseClass;
import com.bankingaccount.pages.AddCustomer;
import com.bankingaccount.pages.HomePage;
import com.bankingaccount.utilities.DataProviders;
import com.bankingaccount.utilities.ExtentManager;

public class AddAccountTest extends BaseClass {

	private HomePage homePage;
	private AddCustomer createAccount;
	
	@BeforeMethod
	public void setUpPages() {
		homePage = new HomePage(getDriver());
		createAccount = new AddCustomer(getDriver());
	}
	
	@Test
	public void VerifyAddCustomersPageElements() {
		ExtentManager.startTest("Verify Add Customers Page Elements"); // -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		createAccount.openAddCustomerForm();
		createAccount.verifyAddCustomerButtonIsDisplayed();
		createAccount.verifyFirstNameIsDisplayed();
		createAccount.verifyLastNameIsDisplayed();
		createAccount.verifyPostCodeIsDisplayed();
		createAccount.verifyAddCustomerSubmitButtonIsDisplayed();
	}
	@Test
	public void verifyTheErrorMessageIsDisplayedWhenSubmittingEmptyForm() { 
		ExtentManager.startTest("Verify the error message is displayed when submitting empty form"); // -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		createAccount.openAddCustomerForm();
		createAccount.verifyAddCustomerButtonIsDisplayed();
		createAccount.validationOfAddCustomerForm("", "", "");
	}
	@Test(dataProvider = "customerDetails", dataProviderClass = DataProviders.class)
	public void verfyAddCustomerSuccessfully(String firstName, String lastName, String postCode) {
		ExtentManager.startTest("Verify Add Customer Successfully");//  -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		createAccount.openAddCustomerForm();
		createAccount.verifyAddCustomerButtonIsDisplayed();
		createAccount.addCustomer(firstName, lastName, postCode);
		createAccount.handleAlertAndVerifyMessage("Customer added successfully with customer id");
	}
}
