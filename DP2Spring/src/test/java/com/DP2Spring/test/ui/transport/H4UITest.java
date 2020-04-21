package com.DP2Spring.test.ui.transport;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class H4UITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
	@LocalServerPort
	private int port;
	@BeforeEach
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\carlo\\Desktop\\chrome\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

  @Test
  public void testH4UI() throws Exception {
	    driver.get("http://localhost:"+port+"/login");
    driver.findElement(By.linkText("Iniciar sesion")).click();
    driver.findElement(By.id("phone-form1-z")).click();
    driver.findElement(By.id("phone-form1-z")).clear();
    driver.findElement(By.id("phone-form1-z")).sendKeys("clerk1");
    driver.findElement(By.id("message-form1-z")).click();
    driver.findElement(By.id("message-form1-z")).clear();
    driver.findElement(By.id("message-form1-z")).sendKeys("clerk1");
    driver.findElement(By.xpath("//html")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Transportes acabados")).click();
    try {
      assertEquals("Transportes acabados", driver.findElement(By.xpath("//h2")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
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
