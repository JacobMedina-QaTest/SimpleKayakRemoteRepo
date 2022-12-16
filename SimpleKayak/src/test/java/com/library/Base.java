package com.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.library.GlobalSelenium.Browser;

public class Base {

	public static final Logger log = LogManager.getLogger(Base.class);

	public WebDriver driver;
	public static GlobalSelenium myGSLibrary = new GlobalSelenium();

	private String configPath = "src/tes"
			+ "t/resources/config.properties";
	private String browserType;
	private String isDemoMode;
	private String isHeadlessMode;
	private String isRemoteMode;
	// we need to read these variables and we do that below in the beforeAllTest
	// method
	private String hubURLString;
	

	@BeforeClass
	public void beforeAllTest() {
		// this method will only run one time right before any method/test in this class
		// runs
		log.info("Running before all test.");
		// we are reading these variables below in the config.properties file
		JavaPropertiesManager readProp = new JavaPropertiesManager(configPath);
		browserType = readProp.readProperty("browser").toLowerCase();
		isDemoMode = readProp.readProperty("isDemoMode").toLowerCase();
		isHeadlessMode = readProp.readProperty("isHeadless").toLowerCase();
		isRemoteMode = readProp.readProperty("isRemote").toLowerCase();
		hubURLString = readProp.readProperty("hubURL").toLowerCase();
	}

	@AfterClass
	public void afterAllTest() {
		// this method will only run one time right after all tests/other methods are
		// executed
		log.info("Running after all tests");
	}

	@BeforeMethod
	public void setUp() {
// this method is checking if our test/config is remote or not. If it is, it'll run the remote browsers in 
// first 'if' section. If its not remote, we run the 'else' in the below section for local run

//first if-else checks if remote is on or not. Inner ifs are checking browser type.
		if(isHeadlessMode.contains("yes")) {
			myGSLibrary.setIsHeadless(true);
		}
		
		if(isDemoMode.contains("on")) {
			myGSLibrary.setIsDemo(true);
		}
		
		if (isRemoteMode.contains("on")) {
			myGSLibrary.setIsRemote(true);
			if(browserType.contains("chrome")) {
				driver = myGSLibrary.startARemoteBrowser(hubURLString, Browser.CHROME);
			} else if (browserType.contains("edge")) {
				driver = myGSLibrary.startARemoteBrowser(hubURLString, Browser.EDGE_CHROMIUM);
			} else if (browserType.contains("firefox")) {
				driver = myGSLibrary.startARemoteBrowser(hubURLString, Browser.FIREFOX);
			}
			
		} else {

			if (browserType.contains("chrome")) {
				driver = myGSLibrary.startBrowser(Browser.CHROME);
			} else if (browserType.contains("edge")) {
				driver = myGSLibrary.startBrowser(Browser.EDGE_CHROMIUM);
			} else if (browserType.contains("firefox")) {
				driver = myGSLibrary.startBrowser(Browser.FIREFOX);
			} else if (browserType.contains("safari")) {
				driver = myGSLibrary.startBrowser(Browser.SAFARI);
			} else {
				log.info("Currently the framework does not support " + browserType + " type of browser!");
				log.info("Default browser set to 'CHROME'");
				driver = myGSLibrary.startBrowser(Browser.CHROME);
			}
		}

		// driver = myLibGS.startBrowser(Browser.EDGE_CHROMIUM);
		// driver = myLibGS.startBrowser(Browser.FIREFOX);

	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.FAILURE == result.getStatus()) {
			// capture screenshot when test fails
			myGSLibrary.takeScreenshot(result.getName());
		}

		myGSLibrary.tearDown();
	}

}
