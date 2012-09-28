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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.AbstractRoleAttribute;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;

public class TkWorkflowTimesheetAttribute extends AbstractRoleAttribute {

    private static final Logger LOG = Logger.getLogger(TkWorkflowTimesheetAttribute.class);

	@Override
	public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
		List<String> roles = new ArrayList<String>();
		Long routeHeaderId = new Long(documentContent.getRouteContext().getDocument().getDocumentId());
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(routeHeaderId.toString());

		if (timesheetDocument != null) {
			List<Assignment> assignments = timesheetDocument.getAssignments();
			for (Assignment assignment : assignments) {
				String roleStr = roleName + "_" +assignment.getWorkArea();
				if(!roles.contains(roleStr)){
					roles.add(roleStr);
				}
			}
		}
		return roles;
	}

	/**
	 * Role name is passed in in the routing rule.
	 */
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
		TkRoleService roleService = TkServiceLocator.getTkRoleService();
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(routeHeaderId.toString());
		WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(workAreaNumber, timesheetDocument.getAsOfDate());

        // KPME-1071
        List<TkRole> approvers = roleService.getWorkAreaRoles(workAreaNumber, roleName, TKUtils.getCurrentDate());
        List<TkRole> approverDelegates = roleService.getWorkAreaRoles(workAreaNumber, TkConstants.ROLE_TK_APPROVER_DELEGATE, TKUtils.getCurrentDate());
		List<TkRole> roles = new ArrayList<TkRole>();
        roles.addAll(approvers);
        roles.addAll(approverDelegates);

		for (TkRole role : roles) {
			//Position routing
			if(StringUtils.isEmpty(role.getPrincipalId())){
				String positionNumber = role.getPositionNumber();
				List<Job> lstJobsForPosition = TkServiceLocator.getJobService().getActiveJobsForPosition(positionNumber, timesheetDocument.getCalendarEntry().getEndPeriodDateTime());
				for(Job job : lstJobsForPosition){
					PrincipalId pid = new PrincipalId(job.getPrincipalId());
					if (!principals.contains(pid)) {
						principals.add(pid);
					}
				}
			} else {
				PrincipalId pid = new PrincipalId(role.getPrincipalId());
					if (!principals.contains(pid)) {
						principals.add(pid);
					}
			}
		}

		if (principals.size() == 0)
			throw new RuntimeException("No principals to route to. Push to exception routing.");

		rqr.setRecipients(principals);
		rqr.setAnnotation("Dept: "+ workArea.getDept()+", Work Area: "+workArea.getWorkArea());

		return rqr;
	}

	@Override
	public List<RoleName> getRoleNames() {
        return Collections.EMPTY_LIST;
	}

}
