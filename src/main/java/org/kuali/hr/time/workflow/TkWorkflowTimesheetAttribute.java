package org.kuali.hr.time.workflow;

import java.util.ArrayList;
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
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.identity.Id;
import org.kuali.rice.kew.identity.PrincipalId;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.kew.rule.Role;
import org.kuali.rice.kew.rule.RoleAttribute;

public class TkWorkflowTimesheetAttribute implements RoleAttribute {

    private static final Logger LOG = Logger.getLogger(TkWorkflowTimesheetAttribute.class);

	@Override
	public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
		List<String> roles = new ArrayList<String>();
		Long routeHeaderId = documentContent.getRouteContext().getDocument().getRouteHeaderId();
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

	@Override
	public List<Role> getRoleNames() {
		// This is a list of "RoleNames" that this RoleAttribute supports -
		// we can use this to have branching logic in the resolveQualifiedRole()
		// method for different types of "Roles"
		// and have different principal Resolution.
		// ... Now whether or not we need this is another question
		throw new UnsupportedOperationException("Not supported in TkWorkflowTimesheetAttribute");
	}

	@Override
	/**
	 * Role name is passed in in the routing rule.
	 */
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
		Long routeHeaderId = routeContext.getDocument().getRouteHeaderId();
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
				List<Job> lstJobsForPosition = TkServiceLocator.getJobService().getActiveJobsForPosition(positionNumber, timesheetDocument.getPayCalendarEntry().getEndPeriodDateTime());
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

}
