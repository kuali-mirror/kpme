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
package org.kuali.khr.lm.tests.selenium;

import static org.kuali.khr.hub.util.Helper.waitHere;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.khr.hub.tests.selenium.KhrBase;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.lm.pages.LeaveCalendar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LeaveCalendarUIT extends KhrBase
{

	private static WebDriver driver = SeleniumBase.getDriver();
	private static LeaveCalendar leave_calendar;
//	private static final String USERNAME = "inlmclockdetail1";
	private static final String USERNAME = "inlmdetail1";

	@BeforeClass
	public static void setUp() 
	{
		login(USERNAME);
		waitHere();
	}
	
	@Before
	public void setUpBeforeTest()
	{
		leave_calendar = loadPage();
		leave_calendar.gotoPage();
		waitHere();
		
	}

	@Test
	public void checkLabels() 
	{
		// TODO create tests
	}
	
	private static LeaveCalendar loadPage() {
		return PageFactory.initElements(driver, LeaveCalendar.class);
	}
	
}
