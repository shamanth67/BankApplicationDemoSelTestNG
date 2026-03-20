package com.bankingaccount.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.bankingaccount.base.BaseClass;
import com.bankingaccount.utilities.ExtentManager;

public class TestListener implements ITestListener{

	//Trigger When Test Starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.startTest(testName);
		//Starts logging steps in Extent Report
		ExtentManager.logStep(testName);
		
	}

	//Trigger when a Test Succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test passes Sucessfully", testName);
	}
//Trigger when a Test Fail
	
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMsg =result.getThrowable().getMessage();
		ExtentManager.logStep(failureMsg);
		ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed :", testName);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip(BaseClass.getDriver(), "Test Skipped", testName);
	}

	//Triggered when a suite starts
	@Override
	public void onStart(ITestContext context) {
		ExtentManager.getExtentReporter();
	}

	//Triggered when a Suite start
	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.endTest();
	}

	
}
