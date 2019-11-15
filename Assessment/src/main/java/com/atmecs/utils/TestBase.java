package com.atmecs.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.atmecs.config.Constants;
import com.atmecs.helper.Helper;
import com.atmecs.helper.MyException;
import com.atmecs.report.LogReport;

public class TestBase {

	public WebDriver driver;
	public ReadProperties property = new ReadProperties();
	public  Helper helper = new Helper();
	public LogReport log = new LogReport();

	public void openBrowser() throws MyException, Exception {

		switch (property.properties("browser", Constants.CONFIG_PATH)) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_PATH);
			driver = new FirefoxDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", Constants.IE_PATH);
			driver = new InternetExplorerDriver();
			break;
		default:
			throw new MyException("browser not matched");
		}
	}

	public void closeBrowser() throws MyException {
		if (driver != null) {
			driver.quit();
		} else {
			throw new MyException("driver not closed");
		}
	}
}
