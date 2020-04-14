package com.DP2Spring.test.ui.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class H1UITest {
	
//	El secretario debe poder solicitar transportes de animales
//	consultando en la base de datos animales por transportar.
	@LocalServerPort
	private int port;
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();
	  
		@BeforeEach
		public void setUp() throws Exception {

			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			baseUrl = "https://www.google.com/";
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	  @Test
	  public void testH1UI() throws Exception {
	    driver.get("http://localhost:"+port+"/login");
	    driver.findElement(By.id("phone-form1-z")).click();
	    driver.findElement(By.id("phone-form1-z")).clear();
	    driver.findElement(By.id("phone-form1-z")).sendKeys("clerk1");
	    driver.findElement(By.id("message-form1-z")).click();
	    driver.findElement(By.id("message-form1-z")).clear();
	    driver.findElement(By.id("message-form1-z")).sendKeys("clerk1");
	    driver.findElement(By.id("message-form1-z")).sendKeys(Keys.ENTER);
	    driver.findElement(By.linkText("Transportes pendientes")).click();
	    driver.findElement(By.linkText("Solicitar transporte")).click();
	    driver.findElement(By.id("txtcompany1")).click();
	    driver.findElement(By.id("txtcompany1")).clear();
	    driver.findElement(By.id("txtcompany1")).sendKeys("SEUR");
	    driver.findElement(By.xpath("//html")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("Transportes Pendientes", driver.findElement(By.xpath("//h2")).getText());
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

