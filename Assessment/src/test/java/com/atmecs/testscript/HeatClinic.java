package com.atmecs.testscript;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.config.Constants;
import com.atmecs.helper.MyException;
import com.atmecs.pages.Pages;
import com.atmecs.utils.ExcelReader;
import com.atmecs.utils.MysqlConnection;
import com.atmecs.utils.TestBase;

public class HeatClinic extends TestBase {
	public ExcelReader readExcel = new ExcelReader(Constants.TESTDATA_PATH);
	private Pages pages = new Pages();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	public MysqlConnection mysqlreader=new MysqlConnection();

	@BeforeTest
	public void startBrowser() throws Exception {
		try {
			openBrowser();
			driver.get(property.properties("heatclinicurl", Constants.CONFIG_PATH));
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (MyException ex) {
			log.error("exception occur" + ex.getMessage());
		}
	}

	@Test
	public void verifyUserOnRespectivePage() throws Exception {
		try {
			log.info("1.Verify user redirect to respective page");
			String homepage= mysqlreader.readTestdataFromMysql("select expectedurl from heatclinic where id=1",1);
			pages.userOnRespectivePage(driver,homepage);
			log.info("user is on homepage");
			helper.clickElement(driver, property.properties("loc_hotsauce_lbl", Constants.HEATCLINICLOCATOR_PATH));
			String hotsauce= mysqlreader.readTestdataFromMysql("select expectedurl from heatclinic where id=2",1);
			pages.userOnRespectivePage(driver,hotsauce);			
			log.info("user is on hotsauce page");
			helper.clickElement(driver, property.properties("loc_merchandise_lbl", Constants.HEATCLINICLOCATOR_PATH));
			String merchandise= mysqlreader.readTestdataFromMysql("select expectedurl from heatclinic where id=3",1);
			pages.userOnRespectivePage(driver,merchandise);			
			log.info("user is on merchandise page");
			helper.clickElement(driver, property.properties("loc_clearance_lbl", Constants.HEATCLINICLOCATOR_PATH));
			String clearance= mysqlreader.readTestdataFromMysql("select expectedurl from heatclinic where id=4",1);
			pages.userOnRespectivePage(driver,clearance);			
			log.info("user is on clearance page");
			helper.clickElement(driver, property.properties("loc_newtohotsauce_lbl", Constants.HEATCLINICLOCATOR_PATH));
			String newtohotsauce= mysqlreader.readTestdataFromMysql("select expectedurl from heatclinic where id=5",1);
			pages.userOnRespectivePage(driver,newtohotsauce);			
			log.info("user is on newtohotsauce page");
			helper.clickElement(driver, property.properties("loc_faq_lbl", Constants.HEATCLINICLOCATOR_PATH));
			String faq= mysqlreader.readTestdataFromMysql("select expectedurl from heatclinic where id=6",1);
			pages.userOnRespectivePage(driver,faq);			
			log.info("user is on faq page");
		}
		catch (MyException ex) {
			log.error("exception occur" + ex.getMessage());
		}
	}

	@Test(priority = 1)
	public void verifyViewingMensText() throws Exception {
		try {
			log.info("2.Verify viewing mens text is displayed");
			helper.moveToElements(driver, property.properties("loc_merchandise_lbl", Constants.HEATCLINICLOCATOR_PATH));
			helper.clickElement(driver, property.properties("loc_mens_lbl", Constants.HEATCLINICLOCATOR_PATH));
			helper.isDisplay(driver, property.properties("loc_viewingmenu_lbl", Constants.HEATCLINICLOCATOR_PATH));
			log.info("Viewing men's text is displayed");
		} catch (Exception e) {
			log.error("Viewing men's text is not displayed");
		}
	}

	@Test(priority = 2)
	public void buyHabaneroShirt() throws Exception {
		log.info("3.Buy Habanero shirt with specified color and size");
		try {
			helper.clickElement(driver, property.properties("loc_buynow_lbl", Constants.HEATCLINICLOCATOR_PATH));
			Thread.sleep(2000);
			helper.clickElement(driver, property.properties("loc_redcolor_btn", Constants.HEATCLINICLOCATOR_PATH));
			log.info("color is clicked");
			helper.clickElement(driver, property.properties("loc_shirtsize_btn", Constants.HEATCLINICLOCATOR_PATH));
			log.info("size is clicked");
			String personalizedName = mysqlreader.readTestdataFromMysql("select personalizename from heatclinic where id=1",1);
			helper.sendValues(driver, property.properties("loc_personalizedname_txt", Constants.HEATCLINICLOCATOR_PATH),
					personalizedName);
			log.info("personalized name is sended");
			driver.findElement(By.name(property.properties("loc_buynow_btn", Constants.HEATCLINICLOCATOR_PATH)))
					.sendKeys(Keys.TAB, Keys.ENTER);
			log.info("BuyNow is clicked");
		} catch (MyException ex) {
			log.error("exception occur:" + ex.getMessage());
		}
	}

	@Test(priority = 3)
	public void validateCartItem() throws Exception {
		try {
			log.info("4.Validate item in the cart");
			helper.clickElement(driver, property.properties("loc_carttotal_btn", Constants.HEATCLINICLOCATOR_PATH));
			String actualShirtSize = helper.getText(driver,
					property.properties("loc_shirtsize_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedShirtSize = mysqlreader.readTestdataFromMysql("select productdetail from heatclinic where id=1",1);
			Assert.assertEquals(actualShirtSize, expectedShirtSize);
			log.info("shirt size is verified");
			String actualPersonalizeName = helper.getText(driver,
					property.properties("loc_personalizename_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedPersonalizeName = mysqlreader.readTestdataFromMysql("select productdetail from heatclinic where id=2",1);
			Assert.assertEquals(actualPersonalizeName, expectedPersonalizeName);
			log.info("personalized name is verified");
			String actualShirtColor = helper.getText(driver,
					property.properties("loc_shirtcolor_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedShirtColor = mysqlreader.readTestdataFromMysql("select productdetail from heatclinic where id=3",1);
			Assert.assertEquals(actualShirtColor, expectedShirtColor);
			log.info("shirt color is verified");
			log.info("validated cart item");
		} catch (Exception e) {
			log.error("iten in the cart not validated");
		}
	}

	@Test(priority = 4)
	public void verifyTotalPriceUpdate() throws MyException, IOException {
		try {
			log.info("verify total price update");
			helper.clickElement(driver,
					property.properties("loc_productquantity_txt", Constants.HEATCLINICLOCATOR_PATH));
			helper.clearValues(driver,
					property.properties("loc_productquantity_txt", Constants.HEATCLINICLOCATOR_PATH));
			helper.sendValues(driver, property.properties("loc_productquantity_txt", Constants.HEATCLINICLOCATOR_PATH),
					mysqlreader.readTestdataFromMysql("select productquantity from heatclinic where id=1",1));
			helper.clickElement(driver, property.properties("loc_update_btn", Constants.HEATCLINICLOCATOR_PATH));
			String actualUpdatedPrice = helper.getText(driver,
					property.properties("loc_totalprice_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedUpdatedPrice = mysqlreader.readTestdataFromMysql("select productquantity from heatclinic where id=2",1);
			Assert.assertEquals(actualUpdatedPrice, expectedUpdatedPrice);
			log.info("Price updated");
		} catch (Exception e) {
			log.error("Price not updated");
		}
	}

	@AfterTest
	public void endBrowser() {
		try {
			closeBrowser();
		} catch (MyException ex) {
			log.error("exception occur" + ex.getMessage());
		}
	}
}
