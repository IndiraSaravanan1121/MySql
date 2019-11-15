package com.atmecs.report;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.atmecs.config.Constants;

public class LogReport {
	
	Logger logger = null;

	public void info(String message) {
		PropertyConfigurator.configure(Constants.LOG_PATH);
		logger = Logger.getLogger(LogReport.class.getName());
		logger.info(message);
	}
	public void error(String message) {
		PropertyConfigurator.configure(Constants.LOG_PATH);
		logger = Logger.getLogger(LogReport.class.getName());
		logger.error(message);
	}
	public void debug(String message) {
		PropertyConfigurator.configure(Constants.LOG_PATH);
		logger = Logger.getLogger(LogReport.class.getName());
		logger.debug(message);
	}

}
