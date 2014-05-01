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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.khr.hub.tests.selenium.KhrBase;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.pages.PositionLookup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PositionLookupUIT extends KhrBase
{
	private static WebDriver driver = SeleniumBase.getDriver();
	private static PositionLookup position_lookup;
	private static final String USERNAME = "admin";

	@BeforeClass
	public static void setUp()
	{
		login(USERNAME);
	}

    @Before
    public void setUpBeforeTest()
    {
        position_lookup = (PositionLookup) loadPage(PositionLookup.class);
        position_lookup.gotoPage();
    }

    @Test
    public void lookupPositionUsingNoSearchValues() {
        position_lookup.lookupAny();
        loadPage(position_lookup.getClass());
        assertEquals(position_lookup.checkResults(), "Showing 1 to 25 of 53 entries");

        // Verify result (based on... what, number of results, number of results > 0, or look at a specific result?
    }

	@Test
	public void lookupPositionUsingPositionNumber()
    {
        loadPage(position_lookup.getClass());
		position_lookup.lookupPositionNumber("10001");
		verifyResults(position_lookup);
		
		// Verify result (based on... what, number of results, number of results > 0, or look at a specific result?
	}
	
	private void verifyResults(PositionLookup position_lookup) {
		assertEquals(position_lookup.checkResults(), "Showing 1 to 1 of 1 entries");

	}

	


}
