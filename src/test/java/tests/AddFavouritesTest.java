package tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.DateUtil;

public class AddFavouritesTest extends BaseTestNG {

	@Test
	public void test_1() throws InterruptedException, ParseException {

		//2.
		WebElement schedulesAndMapsLink = driver.findElement(By.linkText("Schedules and Maps"));
		action.moveToElement(schedulesAndMapsLink).build().perform();
		Thread.sleep(3000);
		WebElement busLink = driver.findElement(By.linkText("Bus"));
		wait.until(ExpectedConditions.elementToBeClickable(busLink)).click();

		//3, 4 & 5
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("find-schedule-searchbox"))).sendKeys("99");
		driver.findElement(By.xpath("//button[normalize-space()='Find Schedule']")).click();
		Thread.sleep(5000);
		
		//6
		WebElement BlineLink = driver.findElement(By.linkText("#99 - UBC B-Line"));
		wait.until(ExpectedConditions.elementToBeClickable(BlineLink)).click();
		
		//7.
		WebElement startDateElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("startDate")));
		String currentStartDate = startDateElement.getAttribute("value");
		String startDate = DateUtil.getDate(currentStartDate, 1);
		startDateElement.sendKeys(startDate);
		driver.findElement(By.name("startTime")).sendKeys("07:00 AM");
		driver.findElement(By.name("endTime")).sendKeys("08:30 AM");
		driver.findElement(By.xpath("//a[normalize-space()='View route on map']//following-sibling::button[normalize-space()='Search']")).click();
		Thread.sleep(5000);

		//8.
		String[] stops = { "50913", "50916", "58613" };
		for (String stop : stops) {
			WebElement element = driver.findElement(By.xpath("//span[normalize-space()='" + stop + "']//preceding-sibling::input"));
			js.executeScript("arguments[0].scrollIntoView();", element);
			Thread.sleep(2000);
			action.moveToElement(element).build().perform();
			element.click();
		}
		js.executeScript("window.scrollTo(0, 0);");
		Thread.sleep(2000);
		driver.findElement(By.name("ShowSelectedStops")).click();
		
		//Test Scenario for bonus points if time allowed. Point 2.
		List<String> times = new LinkedList<>();
		List<WebElement> timeStops = driver.findElements(By.xpath("//strong[normalize-space()='58613']/parent::th/following-sibling::td"));
		for(WebElement timeStop : timeStops) {
			if(timeStop.isDisplayed()) {
				times.add(timeStop.getText());
//				System.out.println(timeStop.getText());
			}
		}
		for (int i = 0; i < 4; i++) {
			long actualTimeDiff = DateUtil.timeDiff(times.get(i), times.get(i + 1));
//			System.out.println(actualTimeDiff);
			Assert.assertTrue(actualTimeDiff < 1800000);
		}
		
		//9.
		WebElement addtoFavouitesElement = driver.findElement(By.xpath("//div[normalize-space()='Add to favourites']/parent::button"));
		js.executeScript("arguments[0].click();", addtoFavouitesElement);
		
		//10.
		String favouriteName = "99 UBC B-Line – Morning Schedule";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("newfavourite"))).clear();
		driver.findElement(By.id("newfavourite")).sendKeys(favouriteName);
		driver.findElement(By.xpath("//button[normalize-space()='Add to favourites']")).click();
		Thread.sleep(2000);
		
		//11.
		driver.findElement(By.partialLinkText("Manage my favourites")).click();
		List<WebElement> favouritesList = driver.findElements(By.xpath("//ul[@class='GTFSFavouritesList']//a"));
		List<String> favourties = new ArrayList<>();
		for(WebElement favourite : favouritesList) {
			favourties.add(favourite.getText());
		}
		Assert.assertTrue(favourties.contains(favouriteName));

	}

}
