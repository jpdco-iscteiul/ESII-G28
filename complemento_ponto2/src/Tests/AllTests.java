package Tests;

import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
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
	WebDriver driver;
	
	@BeforeClass
	public void set() {
	System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@org.junit.Test
	public void checkErrorOcurred() throws EmailException {
		Test.errorOcurred("site abaixo");
	}

}
