package org.kuali.khr.pm.tests.selenium;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.khr.hub.tests.selenium.KhrBase;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.pages.PositionLookup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LookupPositionUIT extends KhrBase
{
	private static WebDriver driver = SeleniumBase.getDriver();
	private static PositionLookup position_lookup;
	private static final String USERNAME = "admin";

	@BeforeClass
	public static void setUpBeforeTest()
	{
		login(USERNAME);
	}
	
	@Test
	public void lookupPositionUsingNoSearchValues() {
		position_lookup = PageFactory.initElements(driver, PositionLookup.class);
		position_lookup.gotoPage();
		position_lookup.lookupPositionNumber("1");
		verifyResults(position_lookup);
		
		// Verify result (based on... what, number of results, number of results > 0, or look at a specific result?
	}
	
	private void verifyResults(PositionLookup position_lookup) {
		assertEquals(position_lookup.checkResults(), "Showing 1 to 1 of 1 entries");

		
	}

	@Test
	public void lookupPositionUsingPositionNumber() {
//		fail("Not yet implemented");
		
		//lookup based on Position Number
		// Verify result (based on... what, number of results, number of results > 0, or look at a specific result?
	}
	


}
