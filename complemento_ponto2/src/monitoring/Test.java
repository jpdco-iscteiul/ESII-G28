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
	private static Date date = new Date();
	// Alterar as variáveis abaixo conforme o ambiente no qual está a ser usado.
	private static String Username = "root";
	private static String Password = "root";
	private static String Website = "http://192.168.99.100:8000/";
	private static String EmailAdministrador = "esiigrupo28@gmail.com";
	private static String PassAdministrador = "MekieTasFixe28";
	private static String TitleWebsite = "ESII – Covid – 19";

	public static void main(String[] args) throws InterruptedException, EmailException{	//Este método é o main que vai executar todas as funções pretendidas pelo trabalho
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
		driver.quit();
	}

	public static void checkWebsite() {		//Neste método vai iniciar o browser e vai direto ao nosso site do wordpress verificando se este está ativo através do título da página
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
		}
	}

	public static void checkWebPages() throws EmailException {		// Neste método vamos verificar se as páginas web do site estão disponiveis
		try {
			driver.findElement(By.linkText("Home")).click();
			driver.findElement(By.linkText("Covid Scientific Discoveries")).click();
			driver.findElement(By.linkText("Covid Spread")).click();
			driver.findElement(By.linkText("Covid Queries")).click();
			driver.findElement(By.linkText("Covid Evolution")).click();
			driver.findElement(By.linkText("Covid Wiki")).click();
			driver.findElement(By.linkText("FAQ")).click();
			driver.findElement(By.linkText("Contact Us")).click();
			driver.findElement(By.linkText("Join Us")).click();
			driver.findElement(By.linkText("About Us")).click();
			driver.findElement(By.linkText("Covid Scientific Discoveries Repository")).click();
			driver.findElement(By.linkText("Website Analytics")).click();
			WebAnalytics = "<strong>More statistics from Website Analytics:</strong>"+driver.findElement(By.className("entry-content")).getText();
			System.out.println(WebAnalytics);
			driver.findElement(By.linkText("Log In")).click();
		} catch (Exception e) {
			errorOcurred("Web Page is down");
			WebPages = "Unavailable";
			LogIn = "Inconclusive";
			Repository = "Inconclusive";
			Forms = "Inconclusive";
			Email = "Inconclusive";
			Availability = "Some errors detected";
		}
	}

	public static void Login() throws EmailException { // Neste método fazemos o Login na página para ver se está tudo funcional
		try {
			driver.findElement(By.linkText("Log In")).click();
			Thread.sleep(1000);
			WebElement username = driver.findElement(By.id("user_login"));
			username.sendKeys(Username);
			WebElement password = driver.findElement(By.id("user_pass"));
			password.sendKeys(Password);
			driver.findElement(By.id("wppb-submit")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			errorOcurred("Log in is not possible");
			LogIn = "Unavailable";
			Repository = "Inconclusive";
			Forms = "Inconclusive";
			Email = "Inconclusive";
			Availability = "Some errors detected";

		}
	}

	public static void checkRepositories() throws EmailException { // Neste método verificamos se os repositórios têem os ficheiros e abrimos 1 para testar se tem conteúdo
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
			errorOcurred("Repository down");
			Repository = "Unavailable";
			Forms = "Inconclusive";
			Email = "Inconclusive";
			Availability = "Some errors detected";
		}
	}

	public static void checkForms() throws EmailException { // Neste método verificamos se os formulários do site estão funcionais testando com dados gerados provisóriamente, verificando se recebemos resposta do site no email
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
			Actions submitAction = new Actions(driver);
			WebElement submitElement = driver.findElement(By.cssSelector("input[value='Submit']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", submitElement);
			Thread.sleep(5000);
			submitAction.moveToElement(driver.findElement(By.cssSelector("input[value='Submit']"))).click().perform();
			Thread.sleep(5000);
			Actions joinUsAction = new Actions(driver);
			WebElement joinUsElement = driver.findElement(By.linkText("Join Us"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", joinUsElement);
			Thread.sleep(5000);
			joinUsAction.moveToElement(driver.findElement(By.linkText("Join Us"))).click().perform();
			Thread.sleep(5000);
			driver.findElement(By.linkText("Join Us")).click();
			Actions logOutAction = new Actions(driver);
			WebElement logOutElement = driver.findElement(By.linkText("Log out"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", logOutElement);
			Thread.sleep(1000);
			logOutAction.moveToElement(driver.findElement(By.linkText("Log out"))).click().perform();
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
			Actions privacyAction = new Actions(driver);
			WebElement privacyElement = driver.findElement(By.cssSelector("input[value='1']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", privacyElement);
			Thread.sleep(1000);
			privacyAction.moveToElement(driver.findElement(By.cssSelector("input[value='1']"))).click().perform();
			driver.findElement(By.id("um-submit-btn")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			errorOcurred("Error forms");
			Forms = "Unavailable";
			Email = "Inconclusive";
			Availability = "Some errors detected";
		}
	}

	public static void checkEmail() throws EmailException { //Verificamos se o nosso email provisório recebeu o email do site a confirmar o registo
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
		} catch (Exception e) {
			errorOcurred("Error registration");
			Email = "Unavailable";
			Availability = "Some errors detected";
		}
	}

	public static void html() throws EmailException { //Gera o html com os dados recolhidos ao longo da classe
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Time = formatter.format(date);
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
		} catch (Exception e) {
			errorOcurred("html error");
		}
	}

	public static void errorOcurred(String error) throws EmailException { //Caso algum dos métodos anteriores dê erro, envia email ao Administrador a informar o erro.
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
