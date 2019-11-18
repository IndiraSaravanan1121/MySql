package com.atmecs.testscript;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.config.Constants;
import com.atmecs.helper.MyException;
import com.atmecs.pages.Pages;
import com.atmecs.utils.ExcelReader;
import com.atmecs.utils.MysqlConnection;
import com.atmecs.utils.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TutorialsNinja extends TestBase {
	static ExtentTest test;
	ExtentReports extent;
	ExtentHtmlReporter reports;
	public ExcelReader readExcel = new ExcelReader(Constants.TESTDATA_PATH);
	private Pages pages = new Pages();
	public MysqlConnection mysqlreader=new MysqlConnection();

	@BeforeClass
	public void startBrowser() throws Exception {
		reports = new ExtentHtmlReporter("extentreport.html");
		extent = new ExtentReports();
		extent.attachReporter(reports);
		try {
			openBrowser();
			driver.get(property.properties("ninjaurl", Constants.CONFIG_PATH));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} catch (MyException ex) {
			log.error("exception occur: " + ex.getMessage());
		}
	}

	@Test(priority = 1)
	public void validateHomepage() {
		try {
			log.info("1.HomePage Validation");
			String actualTitle = driver.getTitle();
			String expectedTitle = mysqlreader.readTestdataFromMysql("select title from tutorialsninja where id=1",1);
			Assert.assertEquals(actualTitle, expectedTitle);
			log.info("Homepage is validated");
		} catch (Exception e) {
			log.error("Homepage not validated" + e);
		}
	}

	@Test(priority = 2)
	public void addToCart() throws Exception {
		log.info("2.Add product to the cart");
		helper.clickElement(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.sendValues(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH),
				mysqlreader.readTestdataFromMysql("select macbookdetail from tutorialsninja where id=1",1));
		helper.clickElement(driver, property.properties("loc_macbook_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clearValues(driver,
				property.properties("loc_productsquantity_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		String macbookQuantity = mysqlreader.readTestdataFromMysql("select macbookdetail from tutorialsninja where id=2",1);
		helper.sendValues(driver, property.properties("loc_productsquantity_lbl", Constants.TUTORIALSNINJALOCATOR_PATH),
				macbookQuantity);
		helper.clickElement(driver, property.properties("loc_addtocart_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		log.info("macbook added into the cart");
		helper.clickElement(driver, property.properties("loc_homepage_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clickElement(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.sendValues(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH),
				mysqlreader.readTestdataFromMysql("select iphonedetail from tutorialsninja where id=1",1));
		helper.sendEnter(driver, property.properties("loc_search_txtbx", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clickElement(driver, property.properties("loc_iphone_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clearValues(driver,
				property.properties("loc_productsquantity_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.sendValues(driver, property.properties("loc_productsquantity_lbl", Constants.TUTORIALSNINJALOCATOR_PATH),
				mysqlreader.readTestdataFromMysql("select iphonedetail from tutorialsninja where id=2",1));
		helper.clickElement(driver, property.properties("loc_addtocart_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		log.info("iphone added into the cart");

	}

	@Test(priority = 3)
	public void verifyProductInCart() throws Exception {
		log.info("3.Verify product available in the cart");
		helper.clickElement(driver, property.properties("loc_carttotal_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		helper.clickElement(driver, property.properties("loc_viewcart_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		String macbookQuantity = helper.getText(driver,
				property.properties("loc_macbookquantity_txt", Constants.TUTORIALSNINJALOCATOR_PATH));
		System.out.println(macbookQuantity);
		String iphoneQuantity = helper.getText(driver,
				property.properties("loc_iphonequantity_txt", Constants.TUTORIALSNINJALOCATOR_PATH));
		System.out.println(iphoneQuantity);

	}

	@Test(priority = 4)
	public void validateProductDetail() throws Exception {
		log.info("4.Validate product details");
		helper.clickElement(driver, property.properties("loc_macbook_lbl", Constants.TUTORIALSNINJALOCATOR_PATH));
		String actualAvailability = helper.getText(driver,
				property.properties("loc_productavailability_txt", Constants.TUTORIALSNINJALOCATOR_PATH));
		String expectedAvailability = readExcel.getData("validateproductdetail", 1, 0);
		Assert.assertEquals(actualAvailability, expectedAvailability);
		String actualPriceAndTax = helper.getText(driver,
				property.properties("loc_productpriceandtax_txt", Constants.TUTORIALSNINJALOCATOR_PATH));
		String expectedPriceAndTax = readExcel.getData("validateproductdetail", 1, 0);
		Assert.assertEquals(actualPriceAndTax, expectedPriceAndTax);

	}

	@Test(priority = 5)
	public void verifyGrandTotal() throws Exception {
		log.info("5.Verify grant total and its update");
		String grandTotal = helper.getText(driver,
				property.properties("loc_grandtotal_txt", Constants.TUTORIALSNINJALOCATOR_PATH));
		System.out.println(grandTotal);
		helper.clickElement(driver,
				property.properties("loc_removecartitem_btn", Constants.TUTORIALSNINJALOCATOR_PATH));
		String updatedGrandTotal = helper.getText(driver,
				property.properties("loc_grandtotal_txt", Constants.TUTORIALSNINJALOCATOR_PATH));
		System.out.println(updatedGrandTotal);
	}

	@AfterTest
	public void endBrowser() {
		try {
			closeBrowser();
		} catch (MyException ex) {
			log.error("exception occur: " + ex.getMessage());
		}
	}
}
