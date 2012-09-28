/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.calendar.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.CalendarEntryPeriodType;
import org.kuali.hr.time.calendar.service.CalendarEntriesService;
import org.kuali.hr.time.service.base.TkServiceLocator;

import org.kuali.rice.krad.util.GlobalVariables;

public class CalendarEntryAction extends TkAction {

	public ActionForward createCalendarEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CalendarEntryActionForm ceaf = (CalendarEntryActionForm) form;
		if (ceaf.getNoOfPeriods() == null || ceaf.getNoOfPeriods() < 1) {
			GlobalVariables.getMessageMap().putError("document.noOfPeriods",
					"periods.greater.than.one");
			return mapping.findForward("basic");
		} else if (ceaf.getHrPyCalendarEntryId() == null) {
			GlobalVariables.getMessageMap().putError(
					"document.hrPyCalendarEntryId",
					"error.calendar.not.available");
			return mapping.findForward("basic");
		}
        CalendarEntryPeriodType periodType = ceaf.getCalendarEntryPeriodType() == null ?
                                                CalendarEntryPeriodType.BI_WEEKLY :
                                                CalendarEntryPeriodType.fromCode(ceaf.getCalendarEntryPeriodType());
        CalendarEntriesService calendarEntriesService = TkServiceLocator.getCalendarEntriesService();
		CalendarEntries calendarEntries = calendarEntriesService.getCalendarEntries(
						ceaf.getHrPyCalendarEntryId().toString());
		if (calendarEntries == null) {
			GlobalVariables.getMessageMap().putError(
					"document.hrPyCalendarEntryId",
					"error.calendar.not.available");
		} else {
				for (int i = 0; i < ceaf.getNoOfPeriods(); i++) {
					CalendarEntries nextCalendarEntries = calendarEntriesService.getNextCalendarEntriesByCalendarId(
									calendarEntries.getHrCalendarId(), calendarEntries);
					if (nextCalendarEntries == null) {
                        calendarEntries = calendarEntriesService.createNextCalendarEntry(calendarEntries, periodType);
					}
				}
				ceaf.setMessage("Calendar entry sucessfully created.");
		}
		return mapping.findForward("basic");
	}
	
}
