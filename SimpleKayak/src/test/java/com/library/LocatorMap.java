package com.library;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class LocatorMap {

	Properties prop;
	
	public LocatorMap(String filepath) {
		
		prop = new Properties();
		
		try {
			FileInputStream file = new FileInputStream(filepath);
			prop.load(file);
			file.close();
			
		} catch (IOException e) { //IOException means input & output exception.
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public By getLocator(String strElement) throws Exception {
		// Retrieve the specified object from the LocatorMap
		String locator = prop.getProperty(strElement);
		
		String locatorType = locator.split(":")[0];
		String locatorValue = locator.split(":")[1];
		
		//System.out.println("Retrieving locators from locator map file, locatorType:" + locatorType);
		//System.out.println("Retrieving locators from locator map file, locatorValue:" + locatorValue);
		
		if(locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if(locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if(locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class"))
			return By.className(locatorValue);
		else if(locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag"))
			return By.tagName(locatorValue);
		else if(locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css"))
			return By.cssSelector(locatorValue);
		else if(locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);
		else
			throw new Exception("unknown locator type passed" + locatorType);
	}
	
	public static void main(String[] args) {
		String filePath = "src/test/resources/locatormap.properties";
		LocatorMap myLocators = new LocatorMap(filePath);
		try {
			By locator = myLocators.getLocator("LoginButton");
			System.out.println(locator);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
