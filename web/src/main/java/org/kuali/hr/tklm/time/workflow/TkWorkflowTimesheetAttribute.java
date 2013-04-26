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
package org.kuali.hr.tklm.time.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.core.bo.assignment.Assignment;
import org.kuali.hr.core.bo.workarea.WorkArea;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.AbstractRoleAttribute;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.kim.api.role.RoleMember;

@Deprecated
public class TkWorkflowTimesheetAttribute extends AbstractRoleAttribute {

	private static final long serialVersionUID = -1745965455586658502L;
	
	private static final Logger LOG = Logger.getLogger(TkWorkflowTimesheetAttribute.class);

	@Override
	public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
		List<String> roles = new ArrayList<String>();
		Long routeHeaderId = new Long(documentContent.getRouteContext().getDocument().getDocumentId());
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(routeHeaderId.toString());

		if (timesheetDocument != null) {
			List<Assignment> assignments = timesheetDocument.getAssignments();
			for (Assignment assignment : assignments) {
				String roleStr = roleName + "_" + assignment.getWorkArea();
				if (!roles.contains(roleStr)) {
					roles.add(roleStr);
				}
			}
		}
		
		return roles;
	}

	@Override
	public ResolvedQualifiedRole resolveQualifiedRole(RouteContext routeContext, String roleName, String qualifiedRole) {
		ResolvedQualifiedRole rqr = new ResolvedQualifiedRole();
        Long workAreaNumber = null;

        try {
            int pos = qualifiedRole.lastIndexOf("_");
            if (pos > -1) {
                String subs = qualifiedRole.substring(pos+1, qualifiedRole.length());
                workAreaNumber = Long.parseLong(subs);
            }
        } catch (NumberFormatException nfe) {
            LOG.error("qualifiedRole did not contain numeric data for work area.");
        }

        if (workAreaNumber == null) {
            throw new RuntimeException("Unable to resolve work area during routing.");
        }

		List<Id> principals = new ArrayList<Id>();
		String routeHeaderId = routeContext.getDocument().getDocumentId();
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(routeHeaderId.toString());
		WorkArea workArea = HrServiceLocator.getWorkAreaService().getWorkArea(workAreaNumber, timesheetDocument.getAsOfDate());

		List<RoleMember> roleMembers = new ArrayList<RoleMember>();
		
		if (TkConstants.ROLE_TK_APPROVER.equals(roleName)) {
	        roleMembers.addAll(TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(KPMERole.APPROVER.getRoleName(), workAreaNumber, new DateTime(), true));
	        roleMembers.addAll(TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(KPMERole.APPROVER_DELEGATE.getRoleName(), workAreaNumber, new DateTime(), true));
		}
		
        for (RoleMember roleMember : roleMembers) {
        	principals.add(new PrincipalId(roleMember.getMemberId()));
	    }

		if (principals.size() == 0) {
			throw new RuntimeException("No principals to route to. Push to exception routing.");
		}
		
		rqr.setRecipients(principals);
		rqr.setAnnotation("Dept: "+ workArea.getDept()+", Work Area: "+workArea.getWorkArea());

		return rqr;
	}

	@Override
	public List<RoleName> getRoleNames() {
        return Collections.emptyList();
	}

}