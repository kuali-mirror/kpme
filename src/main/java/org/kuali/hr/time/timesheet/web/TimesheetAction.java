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
package org.kuali.hr.time.timesheet.web;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimesheetAction extends TkAction {

	private static final Logger LOG = Logger.getLogger(TimesheetAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();

        if (!roles.isDocumentReadable(doc)) {
            throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "TimesheetAction: docid: " + (doc == null ? "" : doc.getDocumentId()), "");
        }
    }

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimesheetActionForm taForm = (TimesheetActionForm)form;
		TKUser user = TKContext.getUser();
		String documentId = taForm.getDocumentId();

        LOG.debug("DOCID: " + documentId);

        // Here - viewPrincipal will be the principal of the user we intend to
        // view, be it target user, backdoor or otherwise.
        String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
		CalendarEntries payCalendarEntries;
		TimesheetDocument td;
		TimesheetDocumentHeader tsdh;

        // By handling the prev/next in the execute method, we are saving one
        // fetch/construction of a TimesheetDocument. If it were broken out into
        // methods, we would first fetch the current document, and then fetch
        // the next one instead of doing it in the single action.
        if (StringUtils.isNotBlank(documentId)) {
            td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
        } else {
            // Default to whatever is active for "today".
            Date currentDate = TKUtils.getTimelessDate(null);
            payCalendarEntries = TkServiceLocator.getCalendarService().getCurrentCalendarDates(viewPrincipal,  currentDate);
            if(payCalendarEntries == null){
                throw new RuntimeException("No Calendar Entry setup for "+viewPrincipal);
            }
            td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, payCalendarEntries);
        }

        // Set the TKContext for the current timesheet document id.
        if (td != null) {
           setupDocumentOnFormContext(taForm, td);
        } else {
            LOG.error("Null timesheet document in TimesheetAction.");
        }

        // Do this at the end, so we load the document first,
        // then check security permissions via the superclass execution chain.
		return super.execute(mapping, form, request, response);
	}
    
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.equals(request.getParameter("command"), "displayDocSearchView")
        		|| StringUtils.equals(request.getParameter("command"), "displayActionListView") ) {
        	final String docId = (String)request.getParameter("docId");
        	TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
        	final String principalName = KimApiServiceLocator.getPersonService().getPerson(td.getPrincipalId()).getPrincipalName();
        	
        	return new ActionRedirect("/changeTargetPerson.do?methodToCall=changeTargetPerson&documentId" + docId + "&principalName=" + principalName + "&targetUrl=TimeDetail.do%3FdocmentId=" + docId + "&returnUrl=TimeApproval.do");
        }
    	
    	return mapping.findForward("basic");
    }

    protected void setupDocumentOnFormContext(TimesheetActionForm taForm, TimesheetDocument td){
    	String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
    	TKContext.setCurrentTimesheetDocumentId(td.getDocumentId());
        TKContext.setCurrentTimesheetDocument(td);
	    taForm.setTimesheetDocument(td);
	    taForm.setDocumentId(td.getDocumentId());
        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(TkConstants.PREV_TIMESHEET, viewPrincipal);
        TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(TkConstants.NEXT_TIMESHEET, viewPrincipal);
       
        taForm.setPrevDocumentId(prevTdh != null ? prevTdh.getDocumentId() : null);
        taForm.setNextDocumentId(nextTdh != null ? nextTdh.getDocumentId() : null);
      
        taForm.setPayCalendarDates(td.getCalendarEntry());
        taForm.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(taForm.getPayCalendarDates()));
    }

}
