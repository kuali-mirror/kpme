package org.kuali.hr.time.workflow.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;

public class WorkflowTagSupport {

    public String getDocumentId() {
        return TKContext.getCurrentTimesheetDocumentId();
    }

    public boolean isDisplayingRouteButton() {
      UserRoles roles = TKContext.getUser().getCurrentTargetRoles();
      TimesheetDocument doc = TKContext.getCurrentTimesheetDoucment();
      TimesheetDocumentHeader tdh = doc.getDocumentHeader();
      if(tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.INITIATED) || tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.SAVED)){
    	  return roles.canSubmitTimesheet(doc);
      }
      return false;
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteButtonEnabled() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDoucment();
        TimesheetDocumentHeader tdh = doc.getDocumentHeader();
        return (tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.INITIATED) || tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.SAVED));
    }

    public boolean isDisplayingApprovalButtons() {
        UserRoles roles = TKContext.getUser().getCurrentTargetRoles();
        TimesheetDocument doc = TKContext.getCurrentTimesheetDoucment();
        return roles.isApproverForTimesheet(doc) && !StringUtils.equals(doc.getDocumentHeader().getDocumentStatus(), TkConstants.ROUTE_STATUS.FINAL);
    }

    public boolean isApprovalButtonsEnabled() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDoucment();
        TimesheetDocumentHeader tdh = doc.getDocumentHeader();
        boolean isEnroute = tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE);
        if(isEnroute){
        	DocumentRouteHeaderValue routeHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(Long.parseLong(doc.getDocumentId()));
        	boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(TKContext.getUserSession(), routeHeader, new SecuritySession(TKContext.getUserSession()));
        	return authorized;
        }
        return false;
    }

    public String getRouteAction() { return TkConstants.TIMESHEET_ACTIONS.ROUTE; }
    public String getApproveAction() { return TkConstants.TIMESHEET_ACTIONS.APPROVE; }
    public String getDisapproveAction() { return TkConstants.TIMESHEET_ACTIONS.DISAPPROVE; }
}
