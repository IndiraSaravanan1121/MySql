package com.atmecs.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.atmecs.config.Constants;
import com.atmecs.helper.Helper;
import com.atmecs.helper.MyException;
import com.atmecs.report.LogReport;
import com.atmecs.utils.ExcelReader;
import com.atmecs.utils.ReadProperties;

public class Pages {
	public ExcelReader readExcel = new ExcelReader(Constants.TESTDATA_PATH);
	private ReadProperties property = new ReadProperties();
	private Helper helper = new Helper();
	private LogReport log = new LogReport();

	String url;
	String expectedUrl;

	public void userOnRespectivePage(WebDriver driver,String expectedUrl) throws InterruptedException, MyException {
		try{
			Thread.sleep(2000);
	    url = driver.getCurrentUrl();
		Assert.assertEquals(url, expectedUrl);
		}catch(Exception e) {
			throw new MyException("user on respective page not validated");
		}
		
	}
	public void searchAndAddToCart(WebDriver driver, String searchProduct, String product, String productQuantity)
			throws MyException, IOException {
		helper.clickElement(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.sendValues(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH),
				searchProduct);
		helper.sendEnter(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clickElement(driver, property.properties(product, Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clearValues(driver,
				property.properties("loc_productsquantity_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.sendValues(driver, property.properties("loc_productsquantity_lbl", Constants.TUTORIALSNINJALOCATOR_PATH),
				productQuantity);
		helper.clickElement(driver, property.properties("loc_addtocart_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		driver.navigate().back();
		log.info("product is added");
	}
}
