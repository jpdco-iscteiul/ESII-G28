package monitoring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Test {
	public static WebDriver driver;
	private static String Time;
	private static String WebsiteIsDown = "Available";
	private static String WebPages = "Available";
	private static String LogIn = "Available";
	private static String Repository = "Available";
	private static String Forms = "Available";
	private static String Email = "Available";
	private static String WebAnalytics;
	private static String Availability = "Everything working";
	// Alterar as variáveis abaixo conforme os dados a serem utilizados para o programa correr como é suposto.
	private static String Username = "Visitor";
	private static String Password = "esii2020";
	private static String Website = "http://192.168.99.100:8000/";
	private static String EmailAdministrador = "esiigrupo28@gmail.com";
	private static String PassAdministrador = "MekieTasFixe28";
	private static String TitleWebsite = "ESII – Covid – 19";
	private static Date date = new Date();

	public static void main(String[] args) throws InterruptedException, EmailException{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Time = formatter.format(date);
		System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		checkWebsite();
		if(WebsiteIsDown.equals("Available")) 
			checkWebPages();
		if(WebPages.equals("Available"))
			Login();
		if(LogIn.equals("Available"))
			checkRepositories();
		if(Repository.equals("Available"))
			checkForms();
		if(Forms.equals("Available"))
			checkEmail();
		html();
		driver.close();
	}

	public static void checkWebsite() {
		try {
			driver.navigate().to(Website);
			driver.manage().window().maximize();
			String title = driver.getTitle();
			System.out.println(title);	
			if(title.equals(TitleWebsite)) 
				System.out.println("Website is Up");
			else {
				errorOcurred("Website is down");
				WebsiteIsDown = "Unavailable";
				WebPages = "Inconclusive";
				LogIn = "Inconclusive";
				Repository = "Inconclusive";
				Forms = "Inconclusive";
				Email = "Inconclusive";
				Availability = "Some errors detected";
			}		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Website is down");
		}
	}

	public static void checkWebPages() {
		try {
			driver.findElement(By.linkText("Home"));
			driver.findElement(By.linkText("Covid Scientific Discoveries"));
			driver.findElement(By.linkText("Covid Spread"));
			driver.findElement(By.linkText("Covid Queries"));
			driver.findElement(By.linkText("Covid Evolution"));
			driver.findElement(By.linkText("Covid Wiki"));
			driver.findElement(By.linkText("FAQ"));
			driver.findElement(By.linkText("Contact Us"));
			driver.findElement(By.linkText("Join Us"));
			driver.findElement(By.linkText("About Us"));
			driver.findElement(By.linkText("Covid Scientific Discoveries Repository"));
			driver.findElement(By.linkText("Website Analytics"));
			driver.findElement(By.linkText("Log In"));
		} catch (Exception e) {
			WebPages = "Unavailable";
			LogIn = "Inconclusive";
			Repository = "Inconclusive";
			Forms = "Inconclusive";
			Email = "Inconclusive";
			Availability = "Some errors detected";
		}
	}

	public static void Login() throws EmailException {
		try {
			//			Actions act = new Actions(driver);
			//			WebElement element = driver.findElement(By.linkText("Log in"));
			//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			//			Thread.sleep(1000);
			//			act.moveToElement(driver.findElement(By.linkText("Log in"))).click().perform();
			driver.findElement(By.linkText("Log In")).click();
			Thread.sleep(1000);
			WebElement username = driver.findElement(By.id("user_login"));
			username.sendKeys(Username);
			WebElement password = driver.findElement(By.id("user_pass"));
			password.sendKeys(Password);
			driver.findElement(By.id("wppb-submit")).click();
			Thread.sleep(1000);
			//			driver.findElement(By.id("welcome-panel"));
		} catch (Exception e) {
			//			e.printStackTrace();
			errorOcurred("Log in not possible");
			LogIn = "Unavailable";
			Repository = "Inconclusive";
			Forms = "Inconclusive";
			Email = "Inconclusive";
			Availability = "Some errors detected";

		}
	}

	public static void checkRepositories() {
		try {
			driver.findElement(By.linkText("Covid Scientific Discoveries Repository")).click();
			driver.findElement(By.linkText("biology-09-00097-2"));
			driver.findElement(By.linkText("178-1-53-2"));
			driver.findElement(By.linkText("biology-09-00094-2"));
			driver.findElement(By.linkText("1-s2.0-S1755436517301135-main-2")).click();
			Thread.sleep(1000);
			if(driver.getCurrentUrl().equals("http://192.168.99.100:8000/wp-content/uploads/2020/06/1-s2.0-S1755436517301135-main-2.pdf"))
				driver.navigate().back();
			else Repository = "Unavailable";
		} catch (Exception e) {
			Repository = "Unavailable";
			Forms = "Inconclusive";
			Email = "Inconclusive";
			Availability = "Some errors detected";
		}
	}

	public static void checkForms() throws EmailException {
		try {
			driver.findElement(By.linkText("Contact Us")).click();
			((JavascriptExecutor)driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.get("https://generator.email/");
			Thread.sleep(5000);
			String mail = driver.findElement(By.id("email_ch_text")).getText();
			String[] aux = mail.split("@");
			String username = aux[0];
			System.out.println(username);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.id("nf-field-5")).sendKeys(username);
			driver.findElement(By.id("nf-field-9")).sendKeys(mail);
			driver.findElement(By.id("nf-field-7")).sendKeys(username);
			Thread.sleep(1000);
			Actions act2 = new Actions(driver);
			WebElement element2 = driver.findElement(By.cssSelector("input[value='Submit']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element2);
			Thread.sleep(5000);
			act2.moveToElement(driver.findElement(By.cssSelector("input[value='Submit']"))).click().perform();
			Thread.sleep(5000);
			Actions act3 = new Actions(driver);
			WebElement element3 = driver.findElement(By.linkText("Join Us"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element3);
			Thread.sleep(5000);
			act3.moveToElement(driver.findElement(By.linkText("Join Us"))).click().perform();
			Thread.sleep(5000);
			driver.findElement(By.linkText("Join Us")).click();
			Actions act = new Actions(driver);
			WebElement element = driver.findElement(By.linkText("Log out"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			Thread.sleep(1000);
			act.moveToElement(driver.findElement(By.linkText("Log out"))).click().perform();
			Thread.sleep(1000);
			driver.findElement(By.linkText("← Back to ESII")).click();
			driver.findElement(By.linkText("Join Us")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("user_login-169")).sendKeys(username);
			driver.findElement(By.id("user_password-169")).sendKeys("T"+username+"1");
			driver.findElement(By.id("confirm_user_password-169")).sendKeys("T"+username+"1");
			driver.findElement(By.id("first_name-169")).sendKeys(username);
			driver.findElement(By.id("last_name-169")).sendKeys(username);
			driver.findElement(By.id("_-169")).sendKeys(username);
			driver.findElement(By.id("user_email-169")).sendKeys(mail);
			Actions act1 = new Actions(driver);
			WebElement element1 = driver.findElement(By.cssSelector("input[value='1']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element1);
			Thread.sleep(1000);
			act1.moveToElement(driver.findElement(By.cssSelector("input[value='1']"))).click().perform();
			//			driver.findElement(By.cssSelector("input[value='1']")).click();
			driver.findElement(By.id("um-submit-btn")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			//			e.printStackTrace();
			e.printStackTrace();
			Forms = "Unavailable";
			Email = "Inconclusive";
			Availability = "Some errors detected";
			errorOcurred("Error forms");
			System.out.println("Error forms");
		}
	}

	public static void checkEmail() throws EmailException {
		try {
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(1000);
			Actions act = new Actions(driver);
			WebElement element = driver.findElement(By.linkText("Login to our site"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			Thread.sleep(1000);
			act.moveToElement(driver.findElement(By.linkText("Login to our site"))).click().perform();
			Thread.sleep(2000);
			//			driver.switchTo().window(tabs.get(2));
			//			driver.findElement(By.linkText("Home"));
			//			driver.close();
			//			driver.get("https://www.google.com/intl/pt-PT/gmail/about/#");
			//			Thread.sleep(100000);
			//			driver.findElement(By.linkText("Iniciar sessão")).click();
			//			Thread.sleep(5000);
			//			driver.findElement(By.linkText("Email ou telemóvel")).sendKeys(EmailAdministrador);
			//			driver.findElement(By.linkText("Seguinte")).click();
			//			Thread.sleep(1000);
			//			driver.findElement(By.id("Introduza a palavra-passe")).sendKeys(PassAdministrador);
			//			Thread.sleep(1000);
			//			driver.findElement(By.linkText("Seguinte")).click();
			//			driver.findElement(By.id("1f"));
			//			driver.findElement(By.id("2r"));
		} catch (Exception e) {
			//			e.printStackTrace();
			e.printStackTrace();
			Email = "Unavailable";
			Availability = "Some errors detected";
			System.out.println("Error registration");
			errorOcurred("Error registration");
		}
	}
	public static void WebsiteAnalytics() {
		
	}

	public static void html() throws EmailException {
		try {
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(0));
			driver.get("https://www.cs.iupui.edu/~ajharris/webprog/jsTester.html");
			WebElement textarea = driver.findElement(By.id("txtInput"));
			textarea.click();
			textarea.sendKeys(Keys.CONTROL, "a");
			textarea.sendKeys(Keys.RETURN);
			String c ="<html><head><title>Monitoring Tool</title></head><body><h2>Monitoring Tool</h2><table border=\"1px\" height=\"auto\" length=\"auto\" align=\"left\"bgcolor=\"lightgrey\"><tr bgcolor=\"lightblue\"><th>WP-CMS</th><th>Web Pages</th><th>Login</th><th>Repositories</th><th>Forms</th><th>Email</th><th>Date</th><th>Availability</th></tr><tr><td>"+WebsiteIsDown+"</td><td>"+WebPages+"</td><td>"+LogIn+"</td><td>"+Repository+"</td><td>"+Forms+"</td><td>"+Email+"</td><td>"+Time+"</td><td>"+Availability+"</td></tr></table><p>"+WebAnalytics+"</p></body></html>";
			textarea.sendKeys(c);
			Thread.sleep(3000);
			driver.findElement(By.cssSelector("input[value='show the output']")).click();
			Thread.sleep(10000);
			System.out.println("Content-type: text/html\n");
			System.out.println("<html>\n<head>\n<title>Monitoring Tool</title>\n</head>\n<body>\n<h2>Monitoring Tool</h2>\n<table border=\"1px\" height=\"auto\" length=\"auto\" align=\"left\"bgcolor=\"lightgrey\">\n<tr bgcolor=\"lightblue\">\n<th>WP-CMS</th>\n<th>Web Pages</th>\n<th>Login</th>\n<th>Repositories</th>\n<th>Forms</th>\n<th>Email</th>\n<th>Date</th>\n<th>Availability</th>\n</tr>");
			System.out.println("<tr>\n<td>"+WebsiteIsDown+"</td>\n<td>"+WebPages+"</td>\n<td>"+LogIn+"</td>\n<td>"+Repository+"</td>\n<td>"+Forms+"</td>\n<td>"+Email+"</td>\n<td>"+Time+"</td>");
			System.out.println("<td>"+Availability+"</td>");
			System.out.println("</tr>\n</table>\n<p>"+WebAnalytics+"</p>\n</body>\n</html>");
		} catch (Exception e) {
			errorOcurred("html error");
		}
	}

	public static void errorOcurred(String error) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(EmailAdministrador, PassAdministrador));
		email.setSSLOnConnect(true);
		email.setFrom("esiigrupo28@gmail.com");
		email.setSubject("ERROR FOUND");
		email.setMsg("Olá administrador, detetámos um erro do tipo: "+error+".\n"+"Recomendamos que verifique e corrija este erro o mais rápido possível para um melhor uso dos seus clientes.\n"+"Cumprimentos,\n"+"A sua monitorização de confiança :)");
		email.addTo(EmailAdministrador);
		email.send();
	}
}
