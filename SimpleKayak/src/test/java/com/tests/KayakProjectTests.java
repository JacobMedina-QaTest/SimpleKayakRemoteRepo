package com.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.library.Base;

public class KayakProjectTests extends Base{
//This test was created by a another student in class
	
	public static final Logger log = LogManager.getLogger(KayakProjectTests.class);

	
	@Test
	public void searchTicket() {
		try {
			String webUrl = "https://www.kayak.com/";
			
			log.info("Go to Website: " + webUrl);
			driver.get(webUrl);
			myGSLibrary.customWait(2);
			
	
	// Round Trip	
			log.info("Click on 'Round Trip' dropdown element");
			//myGSLibrary.clickElement(By.xpath("//div[@role='button']//span[@class='svg wIIH-chevron-icon-wrapper']//*[name()='svg']"));
			myGSLibrary.clickElement(By.xpath("//div[@class='wIIH wIIH-mod-size-small wIIH-mod-state-default wIIH-pres-default wIIH-mod-variant-default wIIH-mod-radius-none wIIH-mod-full-width wIIH-mod-dirty']"));
			myGSLibrary.customWait(2);
			log.info("");
			myGSLibrary.clickElement(By.xpath("//li[@aria-label='Round-trip']//span[contains(text(),'Round-trip')]"));
			myGSLibrary.customWait(1);

	//Two Adults
			myGSLibrary.clickElement(By.xpath("//div[@class='zcIg']//div[2]//div[1]//span[2]//*[name()='svg']"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(By.xpath("//div[@class='UKFa UKFa-mod-variant-default']//div[1]//div[1]//button[2]//*[name()='svg']"));
			myGSLibrary.customWait(1);
			
			
	//Economy Flight
			myGSLibrary.clickElement(By.cssSelector("div[class='wIIH wIIH-mod-size-small wIIH-mod-state-default wIIH-pres-default wIIH-mod-variant-default wIIH-mod-radius-none wIIH-mod-full-width'] span[class='svg wIIH-chevron-icon-wrapper']"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(By.xpath("//li[@id='e']"));
			myGSLibrary.customWait(1);
			
			
	//Checked bags
			myGSLibrary.clickElement(By.xpath("//span[normalize-space()='0 bags']"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(By.xpath("//div[@role='dialog']//div[2]//div[1]//button[2]"));
			myGSLibrary.customWait(1);
			
	//From Washington, DC	
			myGSLibrary.clickElement(By.xpath("//div[@class='vvTc-item-value']"));
			myGSLibrary.customWait(1);
			
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
