package com.library;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaPropertiesManager {

	public static final Logger log = LogManager.getLogger(JavaPropertiesManager.class);
	
	private String propertiesFile;
	private Properties prop;
	private FileInputStream input; // inputstream is when you read the file
	private FileOutputStream output; // outputstream is writing out a file
	
	public JavaPropertiesManager (String propertiesFilePath) {
		try {
			if(propertiesFilePath.length() > 0) {
				propertiesFile = propertiesFilePath;
				prop = new Properties();
			}
			
			
		}catch (Exception e) {
			log.error("Error: ", e);
		}
	}
	
	public String readProperty(String key) {
		String value = null;
		try {
			input = new FileInputStream(propertiesFile);
			prop.load(input);
			value = prop.getProperty(key);
			log.info("Reading properties file ---> " + propertiesFile);
		}catch (Exception e) {
			log.error("Error: ", e);
		}
		
		
		return value;
	}
	
	public void setProperty(String key, String value) {
		try {
			output = new FileOutputStream(propertiesFile);
			prop.setProperty(key, value);
			prop.store(output, null);
			log.info("Properties file is updated/created ---> " + propertiesFile);
		}catch (Exception e) {
			log.error("Error: ", e);
		}
		
	}
	
	
	/*
	 * // this is main method used only for testing the class public static void
	 * main(String[] args) {
	 * 
	 * String filePath = "src/test/resources/config.properties";
	 * JavaPropertiesManager propManager = new JavaPropertiesManager(filePath); //
	 * update or creating java properties file // propManager.setProperty("browser",
	 * "Chrome"); // propManager.setProperty("demoMode", "Off");
	 * 
	 * // reading existing java properties file for property key & value String key
	 * = "DemoMode"; String propertyValue = propManager.readProperty(key);
	 * log.info("key:" + key + ", value: " + propertyValue); }
	 */
	
	
	
	
}
