package org.kuali.hr.time.workflow.web;

import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class WorkflowTagSupport {

    public String getDocumentId() {
        return TKContext.getCurrentTimesheetDocumentId();
    }

    public boolean isDisplayingRouteButton() {
        return true;
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteButtonEnabled() {
        String docId = TKContext.getCurrentTimesheetDocumentId();
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(docId);
        return (tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.INITIATED));
    }

    public boolean isDisplayingApprovalButtons() {

        UserRoles roles = TKContext.getUser().getCurrentTargetRoles();

        String docId = TKContext.getCurrentTimesheetDocumentId();
        TimesheetDocument doc = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
        return roles.isApproverForTimesheet(doc);
    }

    public boolean isApprovalButtonsEnabled() {
        String docId = TKContext.getCurrentTimesheetDocumentId();
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(docId);
        return (tdh.getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE));
    }

    public String getRouteAction() { return TkConstants.TIMESHEET_ACTIONS.ROUTE; }
    public String getApproveAction() { return TkConstants.TIMESHEET_ACTIONS.APPROVE; }
    public String getDisapproveAction() { return TkConstants.TIMESHEET_ACTIONS.DISAPPROVE; }
}
