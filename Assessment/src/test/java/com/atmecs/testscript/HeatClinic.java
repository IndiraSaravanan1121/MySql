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
import com.atmecs.utils.TestBase;

public class HeatClinic extends TestBase {
	public ExcelReader readExcel = new ExcelReader(Constants.TESTDATA_PATH);
	private Pages pages = new Pages();
	JavascriptExecutor js = (JavascriptExecutor) driver;

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
			pages.userOnRespectivePage(driver, "heatclinic", 1, 0);
			log.info("user is on homepage");
			helper.clickElement(driver, property.properties("loc_hotsauce_lbl", Constants.HEATCLINICLOCATOR_PATH));
			pages.userOnRespectivePage(driver, "heatclinic", 2, 0);
			log.info("user is on hotsauce page");
			helper.clickElement(driver, property.properties("loc_merchandise_lbl", Constants.HEATCLINICLOCATOR_PATH));
			pages.userOnRespectivePage(driver, "heatclinic", 3, 0);
			log.info("user is on merchandise page");
			helper.clickElement(driver, property.properties("loc_clearance_lbl", Constants.HEATCLINICLOCATOR_PATH));
			pages.userOnRespectivePage(driver, "heatclinic", 4, 0);
			log.info("user is on clearance page");
			helper.clickElement(driver, property.properties("loc_newtohotsauce_lbl", Constants.HEATCLINICLOCATOR_PATH));
			pages.userOnRespectivePage(driver, "heatclinic", 5, 0);
			log.info("user is on newtohotsauce page");
			helper.clickElement(driver, property.properties("loc_faq_lbl", Constants.HEATCLINICLOCATOR_PATH));
			pages.userOnRespectivePage(driver, "heatclinic", 6, 0);
			log.info("user is on faq page");
		} catch (MyException ex) {
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
			String personalizedName = readExcel.getData("heatclinic", 1, 1);
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
			String expectedShirtSize = readExcel.getData("heatclinic", 1, 2);
			Assert.assertEquals(actualShirtSize, expectedShirtSize);
			log.info("shirt size is verified");
			String actualPersonalizeName = helper.getText(driver,
					property.properties("loc_personalizename_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedPersonalizeName = readExcel.getData("heatclinic", 2, 2);
			Assert.assertEquals(actualPersonalizeName, expectedPersonalizeName);
			log.info("personalized name is verified");
			String actualShirtColor = helper.getText(driver,
					property.properties("loc_shirtcolor_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedShirtColor = readExcel.getData("heatclinic", 3, 2);
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
					readExcel.getData("heatclinic", 1, 3));
			helper.clickElement(driver, property.properties("loc_update_btn", Constants.HEATCLINICLOCATOR_PATH));
			String actualUpdatedPrice = helper.getText(driver,
					property.properties("loc_totalprice_txt", Constants.HEATCLINICLOCATOR_PATH));
			String expectedUpdatedPrice = readExcel.getData("heatclinic", 2, 3);
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
