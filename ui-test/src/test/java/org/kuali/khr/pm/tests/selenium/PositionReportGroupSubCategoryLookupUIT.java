package org.kuali.khr.pm.tests.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.kuali.khr.hub.tests.selenium.SeleniumTestSuite.waitHere;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.khr.hub.tests.selenium.KhrBase;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.pages.PositionReportGroupSubCategoryLookup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PositionReportGroupSubCategoryLookupUIT extends KhrBase {
	private static WebDriver driver = SeleniumBase.getDriver();
	private static PositionReportGroupSubCategoryLookup prgsc_lookup;
	private static final String USERNAME = "admin";

	@BeforeClass
	public static void setUp() 
	{
		login(USERNAME);
		waitHere();
	}
	
	@Before
	public void setUpBeforeTest()
	{
		prgsc_lookup = loadPage();
		prgsc_lookup.gotoPage();
		waitHere();
		
	}

	@Test
	public void checkLabels() 
	{
		assertNotNull(prgsc_lookup.PositionReportGroupSubCategoryLookupTitle);
		assertNotNull(prgsc_lookup.EffectiveDateLabel);
		assertNotNull(prgsc_lookup.GroupKeyLabel);
		assertNotNull(prgsc_lookup.InstitutionLabel);
		assertNotNull(prgsc_lookup.LocationLabel);
		assertNotNull(prgsc_lookup.PositionReportGroupSubCategoryLabel);
		assertNotNull(prgsc_lookup.PositionReportGroupLabel);
		assertNotNull(prgsc_lookup.PositionReportSubCategoryLabel);
		assertNotNull(prgsc_lookup.ShowHistoryLabel);
		
		// TODO must fix code for finding radio buttons
//		assertEquals("Yes", prgsc_lookup.ShowHistoryYesLabel.getText());
//		assertEquals("No", prgsc_lookup.ShowHistoryNoLabel.getText());
//		
		assertNotNull(prgsc_lookup.ActiveLabel);
//		assertEquals("Yes", prgsc_lookup.ActiveYesLabel.getText());
//		assertEquals("No", prgsc_lookup.ActiveNoLabel.getText());
		assertNotNull(prgsc_lookup.ActiveBothLabel);
		assertEquals("Search", prgsc_lookup.SearchButton.getText());
		
		
		prgsc_lookup.search();
		prgsc_lookup = loadPage();
		
		assertNotNull(prgsc_lookup.ResultsActionsLabel);
		assertNotNull(prgsc_lookup.ResultsEffectiveDateLabel);
		assertNotNull(prgsc_lookup.ResultsPositionReportGroupSubCategoryLabel);
		

	}
	
	@Test
	public void test() {
		prgsc_lookup.lookupGroupKey("1");
	}

	@Test
	public void findByPositionReportGroupSubCategory() {
		prgsc_lookup.lookupPositionReportGroupSubCategory("Sr Exec Off");
		verifyResults(prgsc_lookup);
	}


	@Test
	public void checkGroupKeyLookup() 
	{
		prgsc_lookup.GroupKeyLookupButton.click();
		waitHere();
		loadPage();
		prgsc_lookup.switchFrame("fancybox-frame");
		
		assertNotNull(prgsc_lookup.GroupKeyLookupTitle);
		assertNotNull(prgsc_lookup.GroupKeyIdLabel);
		prgsc_lookup.lookupGroupKey("ISU-IA");
		waitHere();
		verifyResults(prgsc_lookup);
		prgsc_lookup.closeLookupLightbox();
		loadPage();
		prgsc_lookup.switchToDefaultFrame();
		
		// verify you can access objects on main Position Report Group Sub Category Lookup screen 
		assertNotNull(prgsc_lookup.GroupKeyLabel);
	}
	
	@Test
	public void checkInstitutionLookup() 
	{
		prgsc_lookup.InstitutionLookupButton.click();
		waitHere();
		loadPage();
		prgsc_lookup.switchFrame("fancybox-frame");
		
		assertNotNull(prgsc_lookup.InstitutionLookupTitle);
		assertNotNull(prgsc_lookup.InstitutionCodeLabel);
		prgsc_lookup.lookupInstitution("ISU");
		waitHere();
		verifyResults(prgsc_lookup);
		prgsc_lookup.closeLookupLightbox();
		loadPage();
		prgsc_lookup.switchToDefaultFrame();
		
		// verify you can access objects on main Position Report Group Sub Category Lookup screen 
		assertNotNull(prgsc_lookup.GroupKeyLabel);
	}
	

	private void verifyResults(PositionReportGroupSubCategoryLookup position_report_group_subcategory_lookup) 
	{
		assertEquals(position_report_group_subcategory_lookup.checkResults(),
				"Showing 1 to 1 of 1 entries");
	}
	
	private static PositionReportGroupSubCategoryLookup loadPage() {
		return PageFactory.initElements(driver, PositionReportGroupSubCategoryLookup.class);
	}
	
	

	

}
