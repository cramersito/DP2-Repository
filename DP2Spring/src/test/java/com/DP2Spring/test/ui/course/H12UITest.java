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
public class H12UITest {

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

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\carlo\\Desktop\\chrome\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


	//Caso positivo

	@Test
	public void testHU12UI() throws Exception {
		driver.get("http://localhost/");
		driver.findElement(By.linkText("Iniciar sesion")).click();
		 driver.findElement(By.id("phone-form1-z")).click();
		    driver.findElement(By.id("phone-form1-z")).clear();
		    driver.findElement(By.id("phone-form1-z")).sendKeys("owner3");
		    driver.findElement(By.id("message-form1-z")).click();
		    driver.findElement(By.id("message-form1-z")).clear();
		    driver.findElement(By.id("message-form1-z")).sendKeys("owner3");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("Cursos para inscribirse")).click();
		assertEquals("Precio Rebajado", driver.findElement(By.xpath("//th[3]")).getText());
		driver.findElement(By.linkText("Cerrar sesion")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
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



