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
package org.kuali.hr.time.workflow;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.AbstractRoleAttribute;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;

import java.util.*;

public class TkWorkflowMissedPunchAttribute extends AbstractRoleAttribute {

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

	/**
	 * Role name is passed in in the routing rule.
	 */
	@Override
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
	public List<RoleName> getRoleNames() {
        return Collections.EMPTY_LIST;
	}
}
