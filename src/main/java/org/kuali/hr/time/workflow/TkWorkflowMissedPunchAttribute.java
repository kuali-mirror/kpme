package org.kuali.hr.time.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.identity.Id;
import org.kuali.rice.kew.identity.PrincipalId;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.kew.rule.Role;
import org.kuali.rice.kew.rule.RoleAttribute;

public class TkWorkflowMissedPunchAttribute implements RoleAttribute {

    private static final Logger LOG = Logger.getLogger(TkWorkflowMissedPunchAttribute.class);

    // Root of the XPath expression needed to retrieve relevant data

    public static final String XP_BO_ROOT = "/documentContent/applicationContent/org.kuali.rice.kns.workflow.KualiDocumentXmlMaterializer/document/newMaintainableObject/businessObject";
    // Attributes on the MissedPunch object we require to determine route recipients.
    public static final String XP_MD_A_ASSIGN = "/assignment/text()";
    public static final String XP_MD_A_TDOCID = "/timesheetDocumentId/text()";

	@Override
	public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
		List<String> roles = new ArrayList<String>();
		roles.add(roleName);
		return roles;
	}

	@Override
	public List<Role> getRoleNames() {
		// This is a list of "RoleNames" that this RoleAttribute supports -
		// we can use this to have branching logic in the resolveQualifiedRole()
		// method for different types of "Roles"
		// and have different principal Resolution.
		// ... Now whether or not we need this is another question
		throw new UnsupportedOperationException("Not supported in TkWorkflowMissedPunchAttribute");
	}

	@Override
	/**
	 * Role name is passed in in the routing rule.
	 */
	public ResolvedQualifiedRole resolveQualifiedRole(RouteContext routeContext, String roleName, String qualifiedRole) {
		ResolvedQualifiedRole rqr = new ResolvedQualifiedRole();
		List<Id> principals = new ArrayList<Id>();
		Long routeHeaderId = routeContext.getDocument().getRouteHeaderId();

		TkRoleService roleService = TkServiceLocator.getTkRoleService();
        MissedPunchDocument missedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(routeHeaderId.toString());

        String assign_string = missedPunch.getAssignment();
        String tsDocIdString = missedPunch.getTimesheetDocumentId();

        if (tsDocIdString != null && assign_string != null) {
            TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsDocIdString);
            if (tdoc != null) {
                Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdoc, assign_string);
                if (assignment != null) {
                    List<String> users = roleService.getResponsibleParties(assignment, roleName, tdoc.getAsOfDate());
                    if(users.isEmpty()){
                    	throw new RuntimeException("No responsible people for work area" + assignment.getWorkArea());
                    }
                    for (String user : users) {
                        PrincipalId pid = new PrincipalId(user);
                        if (!principals.contains(pid)) {
                            principals.add(pid);
                        }
                    }
                } else {
                    throw new RuntimeException("Could not obtain Assignment.");
                }
            } else {
                throw new RuntimeException("Could not obtain TimesheetDocument.");
            }
        } else {
            throw new RuntimeException("Could not obtain Timesheet Document ID or Assignment ID");
        }


		if (principals.size() == 0)
			throw new RuntimeException("No principals to route to. Push to exception routing.");

		rqr.setRecipients(principals);
		return rqr;
	}
}
