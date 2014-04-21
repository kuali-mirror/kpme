package org.kuali.khr.pm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GroupKeyLookup 
{
	private WebDriver driver;
	
	public GroupKeyLookup(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(id = "u100012_control")
	public WebElement GroupKeyIdLabel;
	
	@FindBy(id = "u100009")
	public WebElement CloseBtn;
	
	


}
