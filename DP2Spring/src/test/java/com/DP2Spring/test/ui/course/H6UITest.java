package com.DP2Spring.test.ui.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class H6UITest {


	@LocalServerPort
	private int port;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {


		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jesus\\Desktop\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


	 //Caso positivo
	
	 @Test
	  public void testHU6UIPositivo() throws Exception {
		driver.get("http://localhost:"+port+"/login");
	    driver.findElement(By.linkText("Iniciar sesion")).click();
	    driver.findElement(By.id("phone-form1-z")).click();
	    driver.findElement(By.id("phone-form1-z")).clear();
	    driver.findElement(By.id("phone-form1-z")).sendKeys("clerk1");
	    driver.findElement(By.id("message-form1-z")).clear();
	    driver.findElement(By.id("message-form1-z")).sendKeys("clerk1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Publicar curso")).click();
	    driver.findElement(By.id("entity")).click();
	    driver.findElement(By.id("entity")).clear();
	    driver.findElement(By.id("entity")).sendKeys("test 2");
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("test 2");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.id("description")).click();
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("test 2");
	    driver.findElement(By.xpath("//form[@id='myForm']/div[2]")).click();
	    driver.findElement(By.id("price")).clear();
	    driver.findElement(By.id("price")).sendKeys("13");
	    driver.findElement(By.id("startDate")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.linkText("6")).click();
	    driver.findElement(By.id("endDate")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.linkText("20")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("test 2", driver.findElement(By.xpath("//td")).getText());
	  }
	 
	 
	 //Caso negativo con fecha inicio pasada
	 
	 @Test
	  public void testHU6UINegativo() throws Exception {
		driver.get("http://localhost:"+port+"/login");
	    driver.findElement(By.linkText("Iniciar sesion")).click();
	    driver.findElement(By.xpath("//section[@id='form1-z']/div[2]/div")).click();
	    driver.findElement(By.id("phone-form1-z")).clear();
	    driver.findElement(By.id("phone-form1-z")).sendKeys("clerk1");
	    driver.findElement(By.id("message-form1-z")).clear();
	    driver.findElement(By.id("message-form1-z")).sendKeys("clerk1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Publicar curso")).click();
	    driver.findElement(By.id("entity")).click();
	    driver.findElement(By.id("entity")).clear();
	    driver.findElement(By.id("entity")).sendKeys("test negativo");
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("test negativo");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.id("description")).click();
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("test negativo con fecha inicio pasada");
	    driver.findElement(By.xpath("//form[@id='myForm']/div[2]")).click();
	    driver.findElement(By.id("price")).clear();
	    driver.findElement(By.id("price")).sendKeys("12");
	    driver.findElement(By.id("startDate")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/div")).click();
	    driver.findElement(By.linkText("1")).click();
	    driver.findElement(By.id("endDate")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.linkText("1")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("La fecha de inicio debe ser futura.", driver.findElement(By.xpath("//form[@id='myForm']/div[5]/p")).getText());
	  }

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}



