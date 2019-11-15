package com.atmecs.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.atmecs.report.LogReport;

/**
 * This class
 * 
 * @author indira.saravanan
 *
 */
public class Helper {
	public WebElement webElement = null;
	private LogReport log = new LogReport();

	public WebElement findLocators(WebDriver driver, String locatorType) {
		String[] locator = locatorType.split(":",2);
		try {
			switch (locator[0]) {
			case "class":
				webElement = driver.findElement(By.className(locator[1]));
				break;
			case "css":
				webElement = driver.findElement(By.cssSelector(locator[1]));
				break;
			case "id":
				webElement = driver.findElement(By.id(locator[1]));
				break;
			case "linktext":
				webElement = driver.findElement(By.linkText(locator[1]));
				break;
			case "name":
				webElement = driver.findElement(By.name(locator[1]));
				break;
			case "partiallinktext":
				webElement = driver.findElement(By.partialLinkText(locator[1]));
				break;
			case "tagname":
				webElement = driver.findElement(By.tagName(locator[1]));
				break;
			case "xpath":
				webElement = driver.findElement(By.xpath(locator[1]));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webElement;

	}

	public void clickElement(WebDriver driver, String element) throws MyException {
		if (element != null) {
			webElement = findLocators(driver, element);
			webElement.click();
		} else {
			throw new MyException("element not clicked");
		}
	}

	public String getText(WebDriver driver, String element) throws MyException {
		if (element != null) {
			webElement = findLocators(driver, element);
			return webElement.getText();
		} else {
			throw new MyException("no such element");
		}
	}

	public void moveToElements(WebDriver driver, String element) throws MyException {
		Actions actions = new Actions(driver);
		if (element != null) {
			webElement = findLocators(driver, element);
			actions.moveToElement(webElement).build().perform();
		} else {
			throw new MyException("no such element");
		}
	}

	public void isDisplay(WebDriver driver, String element) throws MyException {
		webElement = findLocators(driver, element);
		if (webElement.isDisplayed()) {
		} else
			throw new MyException("element not displayed");
	}

	public void sendValues(WebDriver driver, String element, String values) throws MyException {
		if (element != null) {
			webElement = findLocators(driver, element);
			webElement.sendKeys(values);
		} else {
			throw new MyException("values not sended");
		}
	}

	public void clearValues(WebDriver driver, String element) throws MyException {
		if (element != null) {
			webElement = findLocators(driver, element);
			webElement.sendKeys(Keys.BACK_SPACE);
		} else {
			throw new MyException("no such element");
		}
	}

	public void sendEnter(WebDriver driver, String element) throws MyException {
		if (element != null) {
			webElement = findLocators(driver, element);
			webElement.sendKeys(Keys.ENTER);
		} else {
			throw new MyException("no such element");
		}
	}

}
