/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.khr.pm.tests.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
	}
	
	@Before
	public void setUpBeforeTest()
	{
		prgsc_lookup = (PositionReportGroupSubCategoryLookup) loadPage(PositionReportGroupSubCategoryLookup.class);
		prgsc_lookup.gotoPage();
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
		loadPage(prgsc_lookup.getClass());
		
		assertNotNull(prgsc_lookup.ResultsActionsLabel);
		assertNotNull(prgsc_lookup.ResultsEffectiveDateLabel);
		assertNotNull(prgsc_lookup.ResultsPositionReportGroupSubCategoryLabel);
	}
	
	@Test
	public void test()
    {
		prgsc_lookup.lookupGroupKey("1");
	}

	@Test
	public void findByPositionReportGroupSubCategory()
    {
		prgsc_lookup.lookupPositionReportGroupSubCategory("Sr Exec Off");
		verifyResults(prgsc_lookup);
	}

	@Test
	public void checkGroupKeyLookup() 
	{
		prgsc_lookup.GroupKeyLookupButton.click();
		loadPage(prgsc_lookup.getClass());
		prgsc_lookup.switchFrame("fancybox-frame");
		
		assertNotNull(prgsc_lookup.GroupKeyLookupTitle);
		assertNotNull(prgsc_lookup.GroupKeyIdLabel);
		prgsc_lookup.lookupGroupKey("ISU-IA");
		verifyResults(prgsc_lookup);
		prgsc_lookup.closeLookupLightbox();
		loadPage(prgsc_lookup.getClass());
		prgsc_lookup.switchToDefaultFrame();
		
		// verify you can access objects on main Position Report Group Sub Category Lookup screen 
		assertNotNull(prgsc_lookup.GroupKeyLabel);
	}
	
	@Test
	public void checkInstitutionLookup() 
	{
		prgsc_lookup.InstitutionLookupButton.click();
		loadPage(PositionReportGroupSubCategoryLookup.class);
		prgsc_lookup.switchFrame("fancybox-frame");
		
		assertNotNull(prgsc_lookup.InstitutionLookupTitle);
		assertNotNull(prgsc_lookup.InstitutionCodeLabel);
		prgsc_lookup.lookupInstitution("ISU");
		verifyResults(prgsc_lookup);
		prgsc_lookup.closeLookupLightbox();
		loadPage(prgsc_lookup.getClass());
		prgsc_lookup.switchToDefaultFrame();
		
		// verify you can access objects on main Position Report Group Sub Category Lookup screen 
		assertNotNull(prgsc_lookup.GroupKeyLabel);
	}
	

	private void verifyResults(PositionReportGroupSubCategoryLookup position_report_group_subcategory_lookup) 
	{
		assertEquals(position_report_group_subcategory_lookup.checkResults(),
				"Showing 1 to 1 of 1 entries");
	}
	

	
	

	

}
