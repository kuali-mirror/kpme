package org.kuali.khr.tk.time_detail.pages;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.kuali.khr.hub.util.KhrTestConstants.Urls.*;

public class TimeDetail {

	private WebDriver driver;

	public TimeDetail(WebDriver driver) {
		this.driver = driver;
	}
		
		
	public void goToTimeDetail() {
		// go to Time Detail page
		WebElement timedetailtab = driver.findElement(By.id("timeDetail"));
		String timeDetailtabtext = timedetailtab.getText();
		assertEquals(timeDetailtabtext, "Time Detail");

		timedetailtab.getAttribute("href");
		WebElement link = timedetailtab.findElement(By.xpath(".//a"));
		link.click();
		String urltime = driver.getCurrentUrl();
		assertEquals(urltime, TIME_DETAIL_PAGE_URL);

	}
	
	public boolean checkTimeBlock(List<Map<String, String>> clockinInfos)
	{
		// TODO: this method is a quick and dirty way of comparing the assignments on the Clock 
		// page to the title on the time block on the Time Detail page
		// This method will need to be built out properly to check time blocks for specific 
		// blocks on specific days, check Summaries, etc
		boolean found = false;
		// get timeblocks
		List<WebElement> timeblocks = driver.findElements(By.className("event-title-true"));
		int count = 0;

		for (WebElement we : timeblocks)
		{
			String tbassignment = we.getText();
			
			for (Map<String, String> hm : clockinInfos)
			{
				String clockinText = hm.get("assignment");
				
				String dept_wa = clockinText.substring(0, clockinText.indexOf(":")).trim();
				
				String task = clockinText.substring(clockinText.indexOf("Task"));
				
				
				if(tbassignment.contains(dept_wa) && tbassignment.contains(task))
				{
					count++;
					if (count == clockinInfos.size())
					{
						found = true;						
					}
				}
			}
		}
		return found;
	}


	
}
