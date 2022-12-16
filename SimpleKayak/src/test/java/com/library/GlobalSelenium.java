package com.library;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;
import com.library.GlobalSelenium.Browser;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GlobalSelenium {
//this code below is for logging info. The parameter should be different for each class e.i. base.class
	public static final Logger log = LogManager.getLogger(GlobalSelenium.class);
//these below are variables/fields/class instance variables.
	private WebDriver driver;
	private int waitTimeInSeconds = 30;
	private boolean isHeadless = false; // boolean variables usually always start with 'is'
	private boolean isDemo = false;
	private boolean isRemote = false; // by default the boolean values are false

	public boolean getIsRemote() {
		return isRemote;
	}

	public void setIsRemote(boolean _isRemote) {
		this.isRemote = _isRemote;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver _driver) {
		this.driver = _driver;
	}

	public boolean getIsHeadless() {
		return isHeadless;
	}

	public void setIsHeadless(boolean _isHeadless) {
		this.isHeadless = _isHeadless;
	}

	public boolean getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(boolean _isDemo) {
		this.isDemo = _isDemo;
	}

	public enum Browser {
		CHROME, FIREFOX, SAFARI, EDGE_CHROMIUM
	}

	public WebDriver startBrowser(Browser browser) {
		try {
			switch (browser) {

			case CHROME:
				driver = startChromeBrowser();
				break;

			case FIREFOX:
				driver = startFireFoxBrowser();
				break;

			case SAFARI:
				driver = startSafariBrowser();
				break;

			case EDGE_CHROMIUM:
				driver = startEdgeBrowser();
				break;

			default:
				log.info("Currently the framework does not support this type of browser!");
				log.info("Default browser set to 'CHROME'");
				driver = startChromeBrowser();
				break;
			}
			driver.manage().deleteAllCookies();
			log.info("Starting browser ---> " + browser);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return driver;
	}

	private WebDriver startEdgeBrowser() {
		try {
			WebDriverManager.edgedriver().setup();
			EdgeOptions ops = new EdgeOptions();
			if (isHeadless) {
				ops.setHeadless(true);
			}
			driver = new EdgeDriver(ops);
			pageSync();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return driver;
	}

	private WebDriver startSafariBrowser() {
		try {
			WebDriverManager.safaridriver().setup();
			SafariOptions ops = new SafariOptions();
			// headless mode does not exist in Safari
			driver = new SafariDriver(ops);
			pageSync();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return driver;
	}

	private WebDriver startFireFoxBrowser() {
		try {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions ops = new FirefoxOptions();
			if (isHeadless) {
				ops.setHeadless(true);
			}
			driver = new FirefoxDriver(ops);
			pageSync();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return driver;
	}

	private WebDriver startChromeBrowser() {
		try {
			WebDriverManager.chromedriver().setup();
			ChromeOptions ops = new ChromeOptions();
			if (isHeadless) {
				ops.setHeadless(true);
			}
			driver = new ChromeDriver(ops);
			pageSync();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return driver;
	}

	private void pageSync() {
		customWait(5);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTimeInSeconds));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(waitTimeInSeconds));
	}

	/*
	 * public WebDriver setUp() { try { // Third party library to check browser
	 * version and downloads the matching // "Chrome" driver file
	 * WebDriverManager.chromedriver().setup();
	 * 
	 * // We don't need to use system.setProperty when we are using the //
	 * WebDriverManager --> which automatically checks the browser and downloads the
	 * // matching driver file // System.setProperty("webdriver.chrome.driver", //
	 * "src/test/resources/browser_drivers/chromedriver.exe"); // start chrome
	 * browser driver = new ChromeDriver(); customWait(5);
	 * driver.manage().window().maximize();
	 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	 * driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	 * 
	 * } catch (Exception e) { log.error("Error: ", e); } return driver; }
	 */

	public void tearDown() {
		try {
			customWait(5);
			// close the browser
			driver.close();
			driver.quit();

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

	public void highlightWebElement(WebElement element) {
		try {
			if (isDemo) { // this highlight method will run only if demomode is on.
				WrapsDriver wrappedElement = (WrapsDriver) element;
				JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();

				for (int i = 1; i < 4; i++) {

					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow");
					customWait(1);

					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
					customWait(1);
				}

				customWait(3);
			}
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

	public void highlightWebElement(By by) {
		try {
			if (isDemo) {
				WebElement element = driver.findElement(by);
				WrapsDriver wrappedElement = (WrapsDriver) element;
				JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();

				for (int i = 1; i < 4; i++) {

					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow");
					customWait(1);

					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
					customWait(1);
				}

				customWait(3);
			}
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

	public void handleCheckBox(By by, boolean isChecked) {

		WebElement checkBox = driver.findElement(by);
		highlightWebElement(checkBox);
		// user wants to check the box
		if (isChecked == true) {

			if (checkBox.isSelected()) {
				// box is checked ----------> (user should do nothing)
			} else {
				// or box is empty(Not checked) ---------> (user should check the box)
				checkBox.click();
				highlightWebElement(checkBox);
			}
		} else {
			// user wants to un-check the box
			if (checkBox.isSelected()) {
				// box is checked ----------> (user should un-check the box)
				checkBox.click();
				highlightWebElement(checkBox);
			} else {
				// or box is empty(Not checked) --------> (user should do nothing)
			}

		}

	}

	public String takeScreenshot(String screenshotName) {
		String finalScreenshotPath = null;

		try {
			String fileLocation = "target/" + screenshotName + "_" + getCurrentTime() + ".png";
			File absFilePath = new File(fileLocation);
			String newPath = absFilePath.getAbsolutePath();

			if (isRemote) {
				driver = new Augmenter().augment(driver); // this line of code transfers the data from the node machine
															// back to the execution machine in JSON Y protocol
			}
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(newPath));
			finalScreenshotPath = newPath;
			log.info("Screenshot Location --->  " + newPath);

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return finalScreenshotPath;
	}

	public void enterText(By by, String inputString) {

		try {
			WebElement element = driver.findElement(by);
			highlightWebElement(element);
			element.clear();
			element.sendKeys(inputString);

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

	}

	public void enterText(By by, Keys keys) {

		try {
			WebElement element = driver.findElement(by);
			highlightWebElement(element);
			element.clear();
			element.sendKeys(keys);

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

	public void selectDropDown(By by, String visibleOptionText) {
		try {
			WebElement dropDownElem = driver.findElement(by);
			highlightWebElement(dropDownElem);
			Select select = new Select(dropDownElem);
			select.selectByVisibleText(visibleOptionText);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

	}

	public void selectDropDown(By by, int index) {
		try {
			WebElement dropDownElem = driver.findElement(by);
			highlightWebElement(dropDownElem);
			Select select = new Select(dropDownElem);
			select.selectByIndex(index);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

	}

	public void selectDropDown(String valueString, By by) {
		try {
			WebElement dropDownElem = driver.findElement(by);
			highlightWebElement(dropDownElem);
			Select select = new Select(dropDownElem);
			select.selectByValue(valueString);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

	}

	public void clickElement(By by) {
		try {
			WebElement element = driver.findElement(by);
			highlightWebElement(element);
			element.click();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

	}

	public void clickElement(WebElement element) {
		try {
			highlightWebElement(element);
			element.click();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

	}

	public void hoverOver(By mainMenuBy, By subMenuBy) {

		try {
			WebElement mainMenuElem = driver.findElement(mainMenuBy);
			highlightWebElement(mainMenuElem);
			// moving mouse to main elem
			Actions action = new Actions(driver);
			action.moveToElement(mainMenuElem).build().perform();

			// delay or pause 0.5 second for sub-main menu to display/open
			customWait(0.5);

			WebElement subMenuElem = driver.findElement(subMenuBy);
			highlightWebElement(subMenuElem);
			action.moveToElement(subMenuElem).build().perform();
			subMenuElem.click();

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

	public WebElement waitForElementVisibility(By by) {
		WebElement elem = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
			elem = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			highlightWebElement(elem);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

		return elem;
	}

	public WebElement fluentWait(final By by) { // You can use this method anywhere you use driver.findElement method
		WebElement elem = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(waitTimeInSeconds))
					.pollingEvery(Duration.ofSeconds(3)) // this pulls the html every 3 seconds until the element is located
					.ignoring(NoSuchElementException.class); // watch Jenkins Class (10/22/2022) @ around 2:45:00

			elem = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			});
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return elem;
	}

	public void clickHiddenElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}
	}
	
	public String getTitleByJS(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String title = js.executeScript("return document.title;").toString();
		return title;
	}
	
	public void enterTxtByAction(By by, String text) {
		try {
			
			WebElement element = driver.findElement(by);
			Actions action = new Actions(driver);
			action.sendKeys(element, text).build().perform();
			
		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}
	}
	
	public void UpLoadFileRobot(String RootFilePath) {
		try {
			Robot rb = new Robot();
			rb.delay(2000);
			
			// put the file in a clipboard
			StringSelection ss = new StringSelection(RootFilePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			
			//Ctrl+V --> Paste
			rb.keyPress(KeyEvent.VK_CONTROL); // Press on ctrl key
			rb.keyPress(KeyEvent.VK_V); // Press on V key
			rb.delay(2000);
			
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_V);
			rb.delay(2000);
			
			// Enter
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		
		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}
	}

	public void scrollIntoView(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.scrollToElement(element).build().perform();
			customWait(.25);
		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}
	}

	public void scrollIntoView(By by) {
		try {
			WebElement element = driver.findElement(by);
			Actions action = new Actions(driver);
			action.scrollToElement(element).build().perform();
			customWait(.25);
		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}
	}

	public void scrollIntoCenter(WebElement element) {
		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);

		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}

	}

	public void scrollIntoCenter(By by) {
		try {

			WebElement element = driver.findElement(by);
			scrollIntoCenter(element);

		} catch (Exception e) {
			e.getStackTrace();
			assertEquals(true, false);
		}
	}

	public WebDriver startARemoteBrowser(String hubURL, Browser browser) {

		try {
			switch (browser) {
			case CHROME:
				driver = startRemoteChromeBrowser(hubURL);
				break;
			case FIREFOX:
				driver = startRemoteFirefoxBrowser(hubURL);
				break;
			case EDGE_CHROMIUM:
				driver = startRemoteEdgeBrowser(hubURL);
				break;
			default:
				log.info("Currently framework does not support this type of remote browser!");
				log.info("Default remote browser set to 'Remote Chrome' browser");
				driver = startRemoteChromeBrowser(hubURL);
			}

			driver.manage().deleteAllCookies();
			// the delete all cookies method is going to delete the browsers history/cache
			// in
			// between each test ran
			pageSync();
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

		return driver;
	}

	private WebDriver startRemoteChromeBrowser(String hubURL) {

		try {
			ChromeOptions chromeOps = new ChromeOptions();
			if (isHeadless == true) {
				chromeOps.setHeadless(true);
			}
			chromeOps.setCapability("browserName", "chrome");
			chromeOps.setCapability("platformName", "Windows");
			chromeOps.setCapability("se:name", "test name");
			chromeOps.setCapability("se:sampleMetadata", "Sample metadata value");

			log.info("Starting remote chrome browser...");
			driver = new RemoteWebDriver(new URL(hubURL), chromeOps);

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

		return driver;
	}

	private WebDriver startRemoteEdgeBrowser(String hubURL) {

		try {
			EdgeOptions edgeOps = new EdgeOptions();
			if (isHeadless == true) {
				edgeOps.setHeadless(true);
			}
			edgeOps.setCapability("browserName", "edge");
			edgeOps.setCapability("platformName", "Windows");
			edgeOps.setCapability("se:name", "test name");
			edgeOps.setCapability("se:sampleMetadata", "Sample metadata value");

			log.info("Starting remote edge browser...");
			WebDriver driver = new RemoteWebDriver(new URL(hubURL), edgeOps);

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

		return driver;
	}

	private WebDriver startRemoteFirefoxBrowser(String hubURL) {

		try {
			FirefoxOptions firefoxOps = new FirefoxOptions();
			if (isHeadless == true) {
				firefoxOps.setHeadless(true);
			}
			firefoxOps.setCapability("browserName", "firefox");
			firefoxOps.setCapability("platformName", "Windows");
			firefoxOps.setCapability("se:name", "test name");
			firefoxOps.setCapability("se:sampleMetadata", "Sample metadata value");

			log.info("Starting remote firefox browser...");
			WebDriver driver = new RemoteWebDriver(new URL(hubURL), firefoxOps);

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

		return driver;
	}

	public String fileUpload(By by, String filePath) {
		String fileFullPath = null;

		try {
			// first thing first, we locate the element(btn) for uploading the file
			WebElement uploadElem = driver.findElement(by);
			highlightWebElement(uploadElem); // this line of code is for demo mode if needed
			File file = new File(filePath);
			fileFullPath = file.getAbsolutePath();
			// Remote file upload for selenium Grid/Hub/Node setup
			if (isRemote) {
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			}
			uploadElem.sendKeys(fileFullPath);
			log.info("Uploading file ---> " + fileFullPath);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
		return fileFullPath;
	}
	
	public void findLinksWithText() { // Must know this code for interview and real-life application
		
		List<WebElement> allLinks = driver.findElements(By.tagName("a"));
		int totalLinks = allLinks.size();
		
		List<String> LinksWithTexts = new ArrayList<String>();
		List<String> LinksNoTexts = new ArrayList<String>();
		
		int counter = 1;
		
		for(WebElement link : allLinks) {
			String linkTxt = link.getText();
			
			// if(linkTxt.length() > 0) {
			if(!linkTxt.isEmpty()) {
				// tag contains text
				LinksWithTexts.add(linkTxt);
			} else {
				// tag does not contain text
				LinksNoTexts.add(linkTxt);
			}
			
			log.info("(" + counter + ") link text is: [" + linkTxt + "]");
			
			// counter++
			counter = counter + 1;
		}
		log.info("Total Links: " + totalLinks);
		log.info("Links with texts: " + LinksWithTexts.size());
		log.info("Links with No text: " + LinksNoTexts.size());
	}
	
	public void selectOptionFromDropDown(List<WebElement> options, String value) {
		try {
			for(WebElement option : options) {
				if(option.getText().equals(value)) {
					option.click();
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

///////////////// Helper Methods --------------
//if within the same class, helper method should be declared in private
//
	public void customWait(double inSeconds) {
		try {
// casting / converting data type from Double to Long --> Thread.sleep method
// takes in a long data type so we must cast
			long seconds = (long) (inSeconds * 1000);

			Thread.sleep(seconds);
// below line of code is same 2 steps above merging into one line
// Thread.sleep((long) inSeconds * 1000);
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

	public String getCurrentTime() {
		String finalTimeStamp = null;

		try {

			Date date = new Date();
			// log.info("Current Date1: " + date);

			String tempTime = new Timestamp(date.getTime()).toString();
			// log.info("Current Date2: " + tempTime);

			String newTempTime = tempTime.replace(':', '_').replace('.', '_').replace(' ', '_').replace('-', '_');
			// log.info("Current Date3: " + newTempTime);

			String newtempTime2 = newTempTime.replaceAll("_", "");
			// log.info("Current Date4: " + newtempTime2);

			finalTimeStamp = newtempTime2;

		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}

		return finalTimeStamp;
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0,300)");
	}

	public static void main(String[] args) {
		GlobalSelenium myLibrary = new GlobalSelenium();
		myLibrary.getCurrentTime();
	}
	
	////// USE BELOW ERROR MESSAGE CODE IF STRUGGLING WITH ASSERTIONS //////
	
	public void captureErrorMsg(By by, String ErrorExpectedMsg) { //Mariam's work
		try {
			WebElement errorElem = driver.findElement(by);
			String actualErrorTxt = errorElem.getText();
			
			if(actualErrorTxt.contains(ErrorExpectedMsg)) {
				log.info("Error detected and negative test passed");
			} else {
				log.info("Error did NOT detect and negative test failed");
			}
		} catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}
	
	public void captureSuccessMsg(By by, String SuccessExpectedMsg) { //Mariam's work
		try {
			WebElement successMsgElem = driver.findElement(by);
			String actualSuccessTxt = successMsgElem.getText();

			
			if(SuccessExpectedMsg.contains(actualSuccessTxt)) {
				log.info("Success message detected and test passed");
			} else {
				log.info("Success message was NOT detected and test failed");
			}
		}  catch (Exception e) {
			log.error("Error: ", e);
			assertEquals(true, false);
		}
	}

}
