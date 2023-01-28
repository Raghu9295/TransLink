package tests;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestNG {

	WebDriver driver;
	WebDriverWait wait;
	Actions action;
	JavascriptExecutor js;

	@BeforeClass
	public void beforeClass() {
//		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		action = new Actions(driver);
		js = ((JavascriptExecutor) driver);
		driver.get("https://www.translink.ca/");
	}

	@AfterClass
	public void afterClass() {
		if (!driver.equals(null)) {
			driver.quit();
		}
	}

}
