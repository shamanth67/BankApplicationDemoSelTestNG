package com.bankingaccount.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.bankingaccount.base.BaseClass;
import com.bankingaccount.pages.AddCustomer;
import com.bankingaccount.pages.HomePage;
import com.bankingaccount.pages.OpenAccount;
import com.bankingaccount.utilities.DataProviders;
import com.bankingaccount.utilities.ExtentManager;

public class OpenAccountTest extends BaseClass {

	private HomePage homePage;
	private AddCustomer addCustomer;
	private OpenAccount openAccount;
	
	@BeforeMethod
	public void setUpPages() {
		homePage = new HomePage(getDriver());
		addCustomer = new AddCustomer(getDriver());
		openAccount = new OpenAccount(getDriver());
	}
	@Test(dataProvider = "customerDetails", dataProviderClass = DataProviders.class)
	public void verifyOpenAccountPageElements(String firstName, String lastName, String postCode) {
		ExtentManager.startTest("Verify Open Account Page Elements"); //-- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		addCustomer.openAddCustomerForm();
		addCustomer.addCustomer(firstName, lastName, postCode);
		addCustomer.handleAlertAndVerifyMessage("Customer added successfully with customer id");
		openAccount.openOpenAccountForm();
		openAccount.verifyOpenAccountButtonIsDisplayed();
		openAccount.verifyCustomerLabelIsDisplayed();
			openAccount.verifyCurrencyLabelIsDisplayed();
		openAccount.verifyProcessButtonIsDisplayed();
		
	}
	@Test(dataProvider = "customerDetails", dataProviderClass = DataProviders.class)
	public void errorMessageIsDisplayedWhenSubmittingEmptyForm(String firstName, String lastName, String postCode) {
		ExtentManager.startTest("Verify the error message is displayed when submitting empty form");//    -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		addCustomer.openAddCustomerForm();
		addCustomer.addCustomer(firstName, lastName, postCode);
		addCustomer.handleAlertAndVerifyMessage("Customer added successfully with customer id");
		openAccount.openOpenAccountForm();
		openAccount.validationOfOpenAccountForm();
		
	}
	@Test(dataProvider = "openAccountDetails", dataProviderClass = DataProviders.class)
	public void openAccountSuccessfully(String firstName, String lastName, String postCode, String fullName, String currency) throws Exception{
		ExtentManager.startTest("Verify Open Account Successfully");//  -- This method has been implemented in Listeners
		homePage.verifyHomePageTitle();
		homePage.openBankManagerLoginPage();
		addCustomer.openAddCustomerForm();
		addCustomer.addCustomer(firstName, lastName, postCode);
		addCustomer.handleAlertAndVerifyMessage("Customer added successfully with customer id");
		openAccount.openOpenAccountForm();
		openAccount.openAccount(fullName, currency);
		openAccount.handleAlertAndVerifyMessage("Account created successfully with account Number");
		
	}
	
}
