package com.bankingaccount.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final String FILE_PATH =System.getProperty("user.dir")+ "/src/test/resources/testData/testData.xlsx";
	
	@DataProvider(name="customerDetails")
	public static Object[][] customerDetails() throws Exception{
		
		return getSheetData("customerDetails");
	}
	
	@DataProvider(name="openAccountDetails")
	public static Object[][] openAccountDetails() throws Exception{
		
		return getSheetData("openAccountDetails");
	}
	
	private static Object[][] getSheetData(String sheetName) throws Exception{
		
	List<String[]> sheetData =	ExcelReaderUtility.getSheetData(FILE_PATH, sheetName);
		Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
		
		for(int i=0;i<sheetData.size();i++) {
			data[i]= sheetData.get(i);
		}
	return data; 
	}
}
