package com.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.library.Base;
import com.library.LocatorMap;

public class KayakProjectTests extends Base{
//This test was created by a another student in class
	
	public static final Logger log = LogManager.getLogger(KayakProjectTests.class);
	String filePath = "src/test/resources/locatormap.properties";
	LocatorMap mylocator = new LocatorMap(filePath);
	
	@Test
	public void searchTicket() {
		try {
			String webUrl = "https://www.kayak.com/";
			
			log.info("Go to Website: " + webUrl);
			driver.get(webUrl);
			myGSLibrary.customWait(2);
	
	// Round Trip	
			myGSLibrary.clickElement(mylocator.getLocator("RoundTripElem"));
			myGSLibrary.customWait(2);
			myGSLibrary.clickElement(mylocator.getLocator("RoundTripDropElem"));
			myGSLibrary.customWait(1);
			log.info("Click on 'Round Trip' dropdown element");

	//Two Adults
			myGSLibrary.clickElement(mylocator.getLocator("AdultDropSelection"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(mylocator.getLocator("AddTravelorBtn"));
			myGSLibrary.customWait(1);
			log.info("Selected 2 Travelers");
			
	//First Flight
			myGSLibrary.clickElement(mylocator.getLocator("FlightDropDown"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(mylocator.getLocator("FirstClassBtn"));
			myGSLibrary.customWait(1);
			log.info("Selected First Class Flight");
			
	//Checked bags
			myGSLibrary.clickElement(mylocator.getLocator("BagDrop"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(mylocator.getLocator("BagDropDown"));
			myGSLibrary.customWait(1);
			log.info("Selected Check Bags");
			
	//From Washington, DC	
			myGSLibrary.clickElement(mylocator.getLocator("SelectDCDeparture"));
			myGSLibrary.customWait(1);
			log.info("Selected Departure Location");
			
	// To Honolulu, Hawaii
			myGSLibrary.clickElement(By.cssSelector("input[placeholder='To?']"));
			myGSLibrary.customWait(2);
			WebElement Destination = myGSLibrary.waitForElementVisibility(By.xpath("//input[@placeholder='To?']"));
			Destination.sendKeys("Honolulu, Hawaii");
	        myGSLibrary.customWait(1);
	        
	  //Departure date
	        myGSLibrary.clickElement(By.xpath("//span[@aria-label='Start date calendar input']"));
	        myGSLibrary.customWait(1);
	        myGSLibrary.clickElement(By.xpath("//button[@aria-label='Next Month']//span[@class='svg tUEz-icon']//*[name()='svg']"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(By.xpath("//button[@aria-label='Next Month']//span[@class='svg tUEz-icon']//*[name()='svg']"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(By.xpath("//button[@aria-label='Next Month']//span[@class='svg tUEz-icon']//*[name()='svg']"));
	        myGSLibrary.customWait(0.5);
	        //myGSLibrary.clickElement(By.xpath("//button[@aria-label='Next Month']//span[@class='svg tUEz-icon']//*[name()='svg']"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(By.xpath("//div[@aria-label='February 21, 2023']"));
	        myGSLibrary.customWait(1);      
	   //return date
	        myGSLibrary.clickElement(By.xpath("//div[@aria-label='March 7, 2023']")); 
	        myGSLibrary.customWait(1);
	        myGSLibrary.clickElement(By.xpath("//button[@aria-label='Search']"));
	        myGSLibrary.customWait(30);
	        
	        
	   //print information of tickets
	       
//	        List<WebElement> Results = driver.findElements(By.xpath("//div[@id='searchResultsList']"));
//	        for(int i =0;i<Results.size();i++) {
//	        	 String Data = Results.get(i).getText(); 
//	        	 System.out.println("Results: " +Data); }  
		
	        WebElement searchResultRegion = driver.findElement(By.cssSelector("#searchResultsList"));
	        myGSLibrary.highlightWebElement(searchResultRegion);
	        
	       // myGSLibrary.highlightWebElement(By.cssSelector("div.inner-grid.keel-grid"));
	        
	        List<WebElement> resultTickets = searchResultRegion.findElements(By.cssSelector("div.inner-grid.keel-grid"));
	        int size = resultTickets.size();
	        System.out.println("Total TIckets: " + size);
	        
	        int counter = 1;
	        for(WebElement ticket : resultTickets) {
	        	
	        	if(counter > 5) {
	        		break;
	        	}
	        	
	        	//myGSLibrary.scrollIntoView(ticket);
	        	
	        	myGSLibrary.customWait(0.25);
	        	
	        	
	        	myGSLibrary.highlightWebElement(ticket);
	        	String ticketTxt = ticket.getText();
	        	System.out.println("TIcket > " + counter);
	        	System.out.println(ticketTxt);
	        	counter ++;
	        }
	        
	        // List<WebElement> resultWrapperItems = searchResultRegion.findElements(By.className("resultWrapper"));
	        
	        
	       	
			
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}
	
}
