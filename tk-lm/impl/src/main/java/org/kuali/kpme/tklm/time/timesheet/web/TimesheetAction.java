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
package org.kuali.kpme.tklm.time.timesheet.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class TimesheetAction extends KPMEAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
		TimesheetActionForm timesheetActionForm = (TimesheetActionForm) form;

    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
    	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetActionForm.getDocumentId());
        if (!HrServiceLocator.getHRPermissionService().canViewCalendarDocument(principalId, timesheetDocument)) {
            throw new AuthorizationException(principalId, "TimesheetAction: docid: " + timesheetDocument.getDocumentId(), "");
        }
    }
    
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = mapping.findForward("basic");
    	String command = request.getParameter("command");
    	
    	if (StringUtils.equals(command, "displayDocSearchView") || StringUtils.equals(command, "displayActionListView")) {
        	String documentId = (String) request.getParameter("docId");
        	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
        	String timesheetPrincipalName = KimApiServiceLocator.getPersonService().getPerson(timesheetDocument.getPrincipalId()).getPrincipalName();
        	
        	String principalId = HrContext.getTargetPrincipalId();
        	String principalName = KimApiServiceLocator.getPersonService().getPerson(principalId).getPrincipalName();
        	
        	StringBuilder builder = new StringBuilder();
        	if (!StringUtils.equals(principalName, timesheetPrincipalName)) {
            	if (StringUtils.equals(command, "displayDocSearchView")) {
            		builder.append("changeTargetPerson.do?methodToCall=changeTargetPerson");
            		builder.append("&documentId=");
            		builder.append(documentId);
            		builder.append("&principalName=");
            		builder.append(timesheetPrincipalName);
            		builder.append("&targetUrl=TimeDetail.do");
            		builder.append("?documentId=" + documentId);
            		builder.append("&returnUrl=TimeApproval.do");
            	} else {
            		builder.append("TimeApproval.do");
            	}
        	} else {
        		builder.append("TimeDetail.do");
        		builder.append("?documentId=" + documentId);
        	}

        	forward = new ActionRedirect(builder.toString());
        }
    	
    	return forward;
    }

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimesheetActionForm timesheetActionForm = (TimesheetActionForm) form;
		String documentId = timesheetActionForm.getDocumentId();
		String hrCalendarEntryId = timesheetActionForm.getHrCalendarEntryId();
        String principalId = HrContext.getTargetPrincipalId();
        
		CalendarEntry calendarEntry = null;
		TimesheetDocument timesheetDocument;
        if (StringUtils.isNotBlank(documentId)) {
            timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
        } else {
        	if (StringUtils.isNotBlank(hrCalendarEntryId)) {
        		calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
        	} else {
        		calendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates(principalId, new LocalDate().toDateTimeAtStartOfDay());
        	}
        	
        	timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
        }

        if (timesheetDocument != null) {
            calendarEntry = timesheetDocument.getCalendarEntry();
            setupDocumentOnFormContext(timesheetActionForm, timesheetDocument);
        } else {
        	EntityNamePrincipalName entityNamePrincipalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "clock.error.missing.payCalendar", entityNamePrincipalName.getPrincipalName());
        }

		return super.execute(mapping, form, request, response);
	}

    protected void setupDocumentOnFormContext(TimesheetActionForm taForm, TimesheetDocument td) throws Exception{
    	String viewPrincipal = HrContext.getTargetPrincipalId();
	    taForm.setTimesheetDocument(td);
	    taForm.setDocumentId(td.getDocumentId());
        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(viewPrincipal, td.getAsOfDate().toDateTimeAtStartOfDay());
        TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getNextDocumentHeader(viewPrincipal, td.getDocEndDate().toDateTimeAtStartOfDay());

        taForm.setPrevDocumentId(prevTdh != null ? prevTdh.getDocumentId() : null);
        taForm.setNextDocumentId(nextTdh != null ? nextTdh.getDocumentId() : null);
      
        taForm.setCalendarEntry(td.getCalendarEntry());        
    }

}