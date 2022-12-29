package com.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
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
			myGSLibrary.clickElement(mylocator.getLocator("AdultTravelerDropDown"));
			myGSLibrary.customWait(1);
			List<WebElement> addOneAdult = driver.findElements(By.xpath("//button[@tabindex='-1'][@aria-label='Increment']"));
			addOneAdult.get(0).click();
			myGSLibrary.customWait(3);
			log.info("Selected 2 Travelers");
			
	//First Class Flight
//			myGSLibrary.clickElement(mylocator.getLocator("FlightDropDown"));
//			myGSLibrary.customWait(1);
//			myGSLibrary.clickElement(mylocator.getLocator("FirstClassBtn"));
//			myGSLibrary.customWait(1);
//			log.info("Selected First Class Flight");
			
	//Economy Class FLight
			myGSLibrary.clickElement(mylocator.getLocator("FlightDropDown"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(mylocator.getLocator("EconomyClassBtn"));
			myGSLibrary.customWait(1);
			log.info("Selected Economy Class Flight");
			
	//Checked bags
			myGSLibrary.clickElement(mylocator.getLocator("BagDrop"));
			myGSLibrary.customWait(1);
			myGSLibrary.clickElement(mylocator.getLocator("BagDropDown"));
			myGSLibrary.customWait(1);
			List<WebElement> addCheckBag = driver.findElements(By.xpath("//button[@tabindex='-1'][@aria-label='Increment']"));
			addCheckBag.get(1).click();
			myGSLibrary.customWait(1);
			addCheckBag.get(1).click();
			myGSLibrary.customWait(1);
			//myGSLibrary.clickElement(By.xpath(webUrl));
			log.info("Selected Check Bags");
			
	//From Washington, DC	---> This section has been auto generated
//			myGSLibrary.clickElement(mylocator.getLocator("SelectDCDeparture"));
//			myGSLibrary.customWait(1);
//			log.info("Selected Departure Location");
			
	// To Honolulu, Hawaii
//			myGSLibrary.clickElement(By.cssSelector("input[placeholder='To?']"));
//			myGSLibrary.customWait(2);
//			WebElement Destination = myGSLibrary.waitForElementVisibility(By.xpath("//input[@placeholder='To?']"));
//			Destination.sendKeys("Honolulu, Hawaii");
//	        myGSLibrary.customWait(1);
	        
	//To Denver, Colorado
	        myGSLibrary.clickElement(By.cssSelector("input[placeholder='To?']"));
			myGSLibrary.customWait(2);
			WebElement Destination2 = myGSLibrary.waitForElementVisibility(By.xpath("//input[@placeholder='To?']"));
			Destination2.sendKeys("Denver, Colorado");
			myGSLibrary.customWait(2);
			Destination2.sendKeys(Keys.ENTER);
			log.info("To Destination Inserted");
	        
	  //Departure date
	        myGSLibrary.clickElement(By.xpath("//span[@aria-label='Start date calendar input']"));
	        myGSLibrary.customWait(1);
	        myGSLibrary.clickElement(mylocator.getLocator("NavigateMnthRightArrow"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(mylocator.getLocator("NavigateMnthRightArrow"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(mylocator.getLocator("NavigateMnthRightArrow"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(mylocator.getLocator("NavigateMnthRightArrow"));
	        myGSLibrary.customWait(0.5);
	        myGSLibrary.clickElement(By.xpath("//div[@aria-label='Thursday May 25, 2023']")); //Selecting departure date of 5/25/23
	        myGSLibrary.customWait(3);  
	        log.info("Departure date is selected");
	        
	   //Return date
	        myGSLibrary.clickElement(By.cssSelector("div[class='mkUa mkUa-pres-mcfly mkUa-mod-legend-orange'][aria-label='Friday June 2, 2023']")); //Selecting return date of 6/2/23
	        myGSLibrary.customWait(3);
	        log.info("Return date is selected");
	        
	   //Search Button     
	        myGSLibrary.clickElement(By.xpath("//button[@aria-label='Search']"));
	        myGSLibrary.customWait(7);
	        log.info("Search button clicked...");
	        
	   //print information of tickets
	       
//	        List<WebElement> Results = driver.findElements(By.xpath("//div[@id='searchResultsList']"));
//	        for(int i =0;i<Results.size();i++) {
//	        	 String Data = Results.get(i).getText(); 
//	        	 System.out.println("Results: " +Data); }  
		
	        WebElement searchResultRegion = driver.findElement(By.cssSelector("div.Ui-Flights-Results-Components-ListView-container "));
	        myGSLibrary.highlightWebElement(searchResultRegion);
	        
	       // myGSLibrary.highlightWebElement(By.cssSelector("div.inner-grid.keel-grid"));
	        
	        List<WebElement> resultTickets = searchResultRegion.findElements(By.cssSelector("div.nrc6"));
	        int size = resultTickets.size();
	        System.out.println("Total Tickets: " + size);
	        
	        int counter = 1;
	        for(WebElement ticket : resultTickets) {
	        	
	        	if(counter > 5) {
	        		break;
	        	}
	        	//myGSLibrary.scrollIntoView(ticket);
	        	myGSLibrary.customWait(0.25);
	        	myGSLibrary.highlightWebElement(ticket);
	        	String ticketTxt = ticket.getText();
	        	System.out.println(" ");
	        	System.out.println("<----->");
	        	System.out.println(" ");
	        	System.out.println("Ticket > " + counter);
	        	System.out.println(ticketTxt);
	        	counter ++;
	        }
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}
	@Test(groups = {"Smoke"})
	public void searchTicket2() {
		System.out.println("<-----> SEARCH TICKET TEST 2 'SMOKE' TEST");
	}
	@Test(groups = {"Sanity"})
	public void searchTicket3() {
		System.out.println("<-----> SEARCH TICKET TEST 3 'SANITY' TEST");
	}
	@Test(groups = {"Smoke"})
	public void searchTicket4() {
		System.out.println("<-----> SEARCH TICKET TEST 4 'SMOKE' TEST");
	}
	@Test(groups = {"Sanity"})
	public void searchTicket5() {
		System.out.println("<-----> SEARCH TICKET TEST 5 'SANITY' TEST");
	}
}

