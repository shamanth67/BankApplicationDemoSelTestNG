package com.bankingaccount.utilities;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

public class LoggerManager {

	//This method returns a Logger instance for the specified class, allowing you to log messages with the context of that class.
	public static Logger getLogger(Class<?> clazz) {
		return LogManager.getLogger();
	}
}
