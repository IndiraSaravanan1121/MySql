package com.atmecs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public FileInputStream fileReader;
	public Workbook book;
	public Sheet sheets;
	public Properties properties = new Properties();
	public String stringData;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;

	public Object[][] readExcel(String sheetName, String path) { // sheetname to fetch data from particular sheet
		try {
			fileReader = new FileInputStream(path);// set test data excel file as testData_path in constants class
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(fileReader);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheets = book.getSheet(sheetName);

		// Read Every rows,columns and pass value to data provider
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}

	public ExcelReader(String filePath) {
		try {
			File file = new File(filePath);
			FileInputStream fileInput = new FileInputStream(file);
			try {
				workbook = new XSSFWorkbook(fileInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getData(String sheetName, int rowNum, int cellNum) {

		sheet = workbook.getSheet(sheetName);
		String data = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
		return data;
	}

	public int totalRowsinSheet(String sheetName) {
		int rowCount = workbook.getSheet(sheetName).getLastRowNum();
		return rowCount;
	}

	public int totalColsinSheet(String sheetName) {
		int colCount = workbook.getSheet(sheetName).getRow(1).getLastCellNum();
		return colCount;
	}

}
