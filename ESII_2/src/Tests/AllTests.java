package Tests;

import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import monitoring.Test;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
	static WebDriver driver;
	
	@BeforeClass
	public void getReady() {
	System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
	driver = new ChromeDriver();
	}
	
	@AfterClass
	public void exit() {
		driver.close();
		driver.quit();
	}
	
	@org.junit.Test
	public void checkErrorOcurred() throws EmailException {
		Test.errorOcurred("site abaixo");
	}

}
