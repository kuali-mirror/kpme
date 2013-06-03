/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.approval;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

public class TimeApproveServiceTest extends KPMETestCase {
	
	@Test
	public void testGetTimePrincipalIdsWithSearchCriteria() throws ParseException {
		List<String> workAreaList = new ArrayList<String>();
		String calendarGroup = "payCal";
		DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
		LocalDate beginDate = LocalDate.fromDateFields(DATE_FORMAT.parse("03/01/2012"));
		LocalDate endDate = LocalDate.fromDateFields(DATE_FORMAT.parse("03/30/2012"));
		
		List<String> idList = TkServiceLocator.getTimeApproveService()
								.getTimePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);		
		Assert.assertTrue("There should be 0 principal ids when searching with empty workarea list, not " + idList.size(), idList.isEmpty());
		
		workAreaList.add("1111");
		workAreaList.add("2222");
		idList = TkServiceLocator.getTimeApproveService()
					.getTimePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);	
		// there's an principal id '1033' in setup that does not have jobs with flsaStatus = NE, so it should not be in the search results
		Assert.assertTrue("There should be 2 principal ids when searching with both workareas, not " + idList.size(), idList.size() == 2);
		for(String anId : idList) {
			if(!(anId.equals("1011") || anId.equals("1022"))) {
				Assert.fail("PrincipalIds searched with both workareas should be either '1011' or '1022', not " + anId);
			}
		}
		
		workAreaList = new ArrayList<String>();
		workAreaList.add("1111");
		idList = TkServiceLocator.getTimeApproveService()
					.getTimePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);		
		Assert.assertTrue("There should be 1 principal ids for workArea '1111', not " + idList.size(), idList.size() == 1);
		Assert.assertTrue("Principal id for workArea '1111' should be principalA, not " + idList.get(0), idList.get(0).equals("1011"));
		
		workAreaList = new ArrayList<String>();
		workAreaList.add("2222");
		idList = TkServiceLocator.getTimeApproveService()
						.getTimePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);	
		Assert.assertTrue("There should be 1 principal ids for workArea '2222', not " + idList.size(), idList.size() == 1);
		Assert.assertTrue("Principal id for workArea '2222' should be principalB, not " + idList.get(0), idList.get(0).equals("1022"));
	}

    @Test
    public void testGetApprovalSummaryRows() throws Exception {
        //DateTime payBeginDate = new DateTime();
        //DateTime payEndDate = new DateTime();
        String calGroup = "";
        List<String> principalIds = new ArrayList<String>();
        principalIds.add("admin");
        List<String> payCalendarLabels = new ArrayList<String>();
        CalendarEntry pce = HrServiceLocator.getCalendarEntryService().getCalendarEntry("55");

    }
}
