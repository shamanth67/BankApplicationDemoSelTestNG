package com.bankingaccount.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtility {

	public static List<String[]> getSheetData(String filePath, String sheetName) throws Exception{
		//
		List<String[]> data = new ArrayList<String[]>();
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetName);
		if(sheet==null) {
			throw new IllegalArgumentException("sheet "+sheetName+" doesn't exixts");
		}

		for(Row row: sheet) {
			if(row.getRowNum()==0) {
				continue;
			}
			//Read the Excel data in row wise
			List<String> rowData = new ArrayList<String>();
			for(Cell cell:row) {
				rowData.add(getCellValue(cell));
			}
			//convert rowdata to array
			data.add(rowData.toArray(new String[0]));
		}


		return data;

	}

	private static  String getCellValue(Cell cell) {

		if(cell == null) {
			return "";
		}
		switch(cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			}
			return String.valueOf((int) cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return null;
		}



	}
}
