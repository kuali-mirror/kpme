package org.kuali.hr.time.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.uif.RemotableAttributeError;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.api.rule.RuleExtension;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.kew.rule.RoleAttribute;
import org.kuali.rice.kew.rule.RuleExtensionValue;
import org.kuali.rice.kns.web.ui.Row;

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
	/**
	 * Role name is passed in in the routing rule.
	 */
	public ResolvedQualifiedRole resolveQualifiedRole(RouteContext routeContext, String roleName, String qualifiedRole) {
		ResolvedQualifiedRole rqr = new ResolvedQualifiedRole();
		List<Id> principals = new ArrayList<Id>();
		Long routeHeaderId = new Long(routeContext.getDocument().getDocumentId());

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
                   
                    // add approver delegates to users
                    Long workAreaNumber = assignment.getWorkArea();
                    List<TkRole> approvers = roleService.getWorkAreaRoles(workAreaNumber, roleName, TKUtils.getCurrentDate());
                    List<TkRole> approverDelegates = roleService.getWorkAreaRoles(workAreaNumber, TkConstants.ROLE_TK_APPROVER_DELEGATE, TKUtils.getCurrentDate());
                    Set<TkRole> roles = new HashSet<TkRole>();
                    roles.addAll(approvers);
                    roles.addAll(approverDelegates);
                    for(TkRole aRole : roles) {
                    	users.add(aRole.getPrincipalId());
                    }
                    
                    if(users.isEmpty()){
                    	throw new RuntimeException("No responsible people for work area" + assignment.getWorkArea());
                    }
                    for (String user : users) {
                    	if(user != null) {
	                        PrincipalId pid = new PrincipalId(user);
	                        if (!principals.contains(pid)) {
	                            principals.add(pid);
	                        }
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

	@Override
	public boolean isMatch(DocumentContent docContent,
			List<RuleExtension> ruleExtensions) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Row> getRuleRows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Row> getRoutingDataRows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RuleExtensionValue> getRuleExtensionValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RemotableAttributeError> validateRoutingData(
			Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RemotableAttributeError> validateRuleData(
			Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoleName> getRoleNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
