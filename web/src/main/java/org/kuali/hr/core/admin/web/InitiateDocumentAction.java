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
package org.kuali.hr.core.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.core.bo.calendar.Calendar;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.web.HrAction;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class InitiateDocumentAction extends HrAction {

    public ActionForward initiateDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	InitiateDocumentForm initiateDocumentForm = (InitiateDocumentForm) form;
    	String principalId = initiateDocumentForm.getPrincipalId();
    	String hrCalendarEntryId = initiateDocumentForm.getHrCalendarEntryId();
    	
    	if (StringUtils.isNotBlank(principalId) && StringUtils.isNotBlank(hrCalendarEntryId)) {
    		Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
    		CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
    		
    		if (principal != null && calendarEntry != null) {
    			Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
    			
    			if (calendar != null) {
    				if (StringUtils.equals(calendar.getCalendarTypes(), TkConstants.CALENDAR_TYPE_PAY)) {
    					TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
    				} else if (StringUtils.equals(calendar.getCalendarTypes(), TkConstants.CALENDAR_TYPE_LEAVE)) {
    					LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, calendarEntry);
    				}
    			}
    		}
    	}
    	
    	return mapping.findForward("basic");
    }
    
}