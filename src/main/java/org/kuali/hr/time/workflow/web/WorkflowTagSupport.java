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
package org.kuali.hr.time.workflow.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.kuali.hr.core.document.CalendarDocumentHeaderContract;
import org.kuali.hr.core.document.calendar.CalendarDocumentContract;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class WorkflowTagSupport {

    public String getTimesheetDocumentId() {
        return TKContext.getCurrentTimesheetDocumentId();
    }

    public String getLeaveCalendarDocumentId() {
        return TKContext.getCurrentLeaveCalendarDocumentId();
    }

    public boolean isDisplayingTimesheetRouteButton() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
        return isDisplayingRouteButton(doc);
    }

    public boolean isDisplayingLeaveRouteButton() {
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
        return isDisplayingRouteButton(doc);
    }
    
    public boolean isDisplayingCurrentPeriodRouteButtonWithNoDelinquencies() {
    	LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
    	if(TKUtils.getCurrentDate().after(DateUtils.addMilliseconds(doc.getCalendarEntry().getBeginPeriodDate(),1)) &&
    			TKUtils.getCurrentDate().before(DateUtils.addMilliseconds(doc.getCalendarEntry().getEndPeriodDate(), -1))) {
    		if(!isDelinquent(doc) && isDisplayingRouteButton(doc))
    			return true;
    		else
    			return false;
    	}
    	else
    		return false;
    }
    
    public boolean isDisplayingCurrentPeriodTimesheetRouteButtonWithNoDelinquencies() {
    	TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
    	if(TKUtils.getCurrentDate().after(DateUtils.addMilliseconds(doc.getCalendarEntry().getBeginPeriodDate(),1)) &&
    			TKUtils.getCurrentDate().before(DateUtils.addMilliseconds(doc.getCalendarEntry().getEndPeriodDate(), -1))) {
    		if(isDisplayingRouteButton(doc))
    			return true;
    		else
    			return false;
    	}
    	else
    		return false;
    }

    private boolean isDisplayingRouteButton(CalendarDocumentContract doc) {
        TkUserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        CalendarDocumentHeaderContract docHeader = doc.getDocumentHeader();
        if(StringUtils.isNotBlank(docHeader.getDocumentStatus())
            && (docHeader.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.SAVED)
                || docHeader.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.INITIATED))){
            return roles.canSubmitTimesheet(doc);
        }
        return false;
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteTimesheetButtonEnabled() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
        return isRouteButtonEnabled(doc);
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteLeaveButtonEnabled() {
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
        Date asOfDate = TKUtils.getTimelessDate(null);
        return isRouteButtonEnabled(doc) && !isDelinquent(doc) 
        		&& (TkServiceLocator.getPermissionsService().canViewLeaveTabsWithEStatus() && asOfDate.compareTo(doc.getDocumentHeader().getEndDate()) > 0);
    }

    private boolean isRouteButtonEnabled(CalendarDocumentContract doc) {
        CalendarDocumentHeaderContract dh = doc.getDocumentHeader();
        return dh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.INITIATED)
                || dh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.SAVED);
    }

    /**
     * checks for delinquncies in target ee's calendar.
     * @param doc
     * @return true if there are previous non-routed or non-final documents
     */
    private boolean isDelinquent(LeaveCalendarDocument doc) {
        String principalId = doc.getDocumentHeader().getPrincipalId();
        List<LeaveCalendarDocumentHeader> lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getSubmissionDelinquentDocumentHeaders(principalId, DateUtils.addSeconds(doc.getAsOfDate(),1));
        if (lcdh.isEmpty()){
            return false;        // no delinquncy
        } else
            return true;        // all previous leave document are final or enroute.
    }
    
    public boolean isDisplayingTimesheetApprovalButtons() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
        return isDisplayingApprovalButtons(doc);
    }

    public boolean isDisplayingLeaveApprovalButtons() {
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
        return isDisplayingApprovalButtons(doc);
    }

    private boolean isDisplayingApprovalButtons(CalendarDocumentContract doc) {
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(TKContext.getPrincipalId(), doc.getDocumentHeader().getDocumentId());
        return !tookActionAlready
                && roles.isApproverForTimesheet(doc)
                && !StringUtils.equals(doc.getDocumentHeader().getDocumentStatus(), TkConstants.ROUTE_STATUS.FINAL);
    }

    public boolean isApprovalTimesheetButtonsEnabled() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
        return isApprovalButtonsEnabled(doc);
    }

    public boolean isApprovalLeaveButtonsEnabled() {
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
        return isApprovalButtonsEnabled(doc) && TkServiceLocator.getLeaveCalendarService().isReadyToApprove(doc);
    }

    private boolean isApprovalButtonsEnabled(CalendarDocumentContract doc) {
        CalendarDocumentHeaderContract dh = doc.getDocumentHeader();
        boolean isEnroute = StringUtils.isNotBlank(dh.getDocumentStatus()) && dh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE);
        if(isEnroute){
            DocumentRouteHeaderValue routeHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(dh.getDocumentId());
            boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(TKContext.getPrincipalId(), routeHeader, new SecuritySession(TKContext.getPrincipalId()));
            boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(TKContext.getPrincipalId(), dh.getDocumentId());
            return !tookActionAlready && authorized;
        }
        return false;
    }

    public String getRouteAction() { return TkConstants.DOCUMENT_ACTIONS.ROUTE; }
    public String getApproveAction() { return TkConstants.DOCUMENT_ACTIONS.APPROVE; }
    public String getDisapproveAction() { return TkConstants.DOCUMENT_ACTIONS.DISAPPROVE; }
}
