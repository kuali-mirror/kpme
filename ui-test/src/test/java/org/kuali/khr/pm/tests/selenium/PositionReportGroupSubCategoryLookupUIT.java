package org.kuali.khr.pm.tests.selenium;

import static org.junit.Assert.assertEquals;
import static org.kuali.khr.hub.tests.selenium.SeleniumTestSuite.waitHere;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.khr.hub.tests.selenium.KhrBase;
import org.kuali.khr.hub.tests.selenium.SeleniumTestSuite;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.pages.GroupKeyLookup;
import org.kuali.khr.pm.pages.PositionReportGroupSubCategoryLookup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PositionReportGroupSubCategoryLookupUIT extends KhrBase {
	private static WebDriver driver = SeleniumBase.getDriver();
	private static PositionReportGroupSubCategoryLookup prgsc_lookup;
	private static final String USERNAME = "admin";

	@BeforeClass
	public static void setUpBeforeTest() {
		login(USERNAME);
		prgsc_lookup = PageFactory.initElements(driver, PositionReportGroupSubCategoryLookup.class);
		prgsc_lookup.gotoPage();
	}

	@Test
	public void checkLabels() 
	{
		assertEquals("Position Report Group Sub Category Lookup", prgsc_lookup.PositionReportGroupSubCategoryLookupTitle.getText());
		assertEquals("Effective Date:", prgsc_lookup.EffectiveDateLabel.getText());
		assertEquals("Group Key:", prgsc_lookup.GroupKeyLabel.getText());
		assertEquals("Institution:", prgsc_lookup.InstitutionLabel.getText());
		assertEquals("Location:", prgsc_lookup.LocationLabel.getText());
		assertEquals("Position Report Group Sub Category:", prgsc_lookup.PositionReportGroupSubCategoryLabel.getText());
		assertEquals("Position Report Group:", prgsc_lookup.PositionReportGroupLabel.getText());
		assertEquals("Position Report Sub Category:", prgsc_lookup.PositionReportSubCategoryLabel.getText());
		assertEquals("Show History:", prgsc_lookup.ShowHistoryLabel.getText());
		assertEquals("Yes", prgsc_lookup.ShowHistoryYesLabel.getText());
		assertEquals("No", prgsc_lookup.ShowHistoryNoLabel.getText());
		
		
		assertEquals("Active:", prgsc_lookup.ActiveLabel.getText());
		assertEquals("Yes", prgsc_lookup.ActiveYesLabel.getText());
		assertEquals("No", prgsc_lookup.ActiveNoLabel.getText());
		assertEquals("Both", prgsc_lookup.ActiveBothLabel.getText());
		assertEquals("Search", prgsc_lookup.SearchButton.getText());
		
		
		prgsc_lookup.search();
//		assertEquals("Actions", prgsc_lookup.getActionsLabel());

	}
	

	@Ignore()
	@Test
	public void checkGroupKeyLookup() 
	{
		GroupKeyLookup gkl = prgsc_lookup.gotoGroupKeyLookup();
		waitHere();
		assertEquals("Group Key Id:", gkl.GroupKeyIdLabel.getText());
		waitHere();
		gkl.CloseBtn.click();
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

	private void verifyResults(PositionReportGroupSubCategoryLookup position_report_group_subcategory_lookup) 
	{
		assertEquals(position_report_group_subcategory_lookup.checkResults(),
				"Showing 1 to 1 of 1 entries");
	}

}
