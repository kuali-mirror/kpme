package org.kuali.hr.tklm.time.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.GenericRoleAttribute;
import org.kuali.rice.kew.rule.QualifiedRoleName;
import org.kuali.rice.kim.api.role.RoleMember;

@SuppressWarnings("unchecked")
public class TimesheetRoleAttribute extends GenericRoleAttribute {

	private static final long serialVersionUID = 6133675090144279606L;
	
	private static final Logger LOG = Logger.getLogger(TimesheetRoleAttribute.class);

	@Override
	public List<RoleName> getRoleNames() {
		List<RoleName> roleNames = new ArrayList<RoleName>();
		
		roleNames.add(RoleName.Builder.create(getClass().getName(), KPMERole.APPROVER.getRoleName(), KPMERole.APPROVER.getRoleName()).build());
		roleNames.add(RoleName.Builder.create(getClass().getName(), KPMERole.APPROVER_DELEGATE.getRoleName(), KPMERole.APPROVER_DELEGATE.getRoleName()).build());

		return roleNames;
	}
	
	@Override
    protected List<String> getRoleNameQualifiers(String roleName, DocumentContent documentContent) {
		Set<String> roleNameQualifiers = new HashSet<String>();
		
		Long routeHeaderId = new Long(documentContent.getRouteContext().getDocument().getDocumentId());
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(routeHeaderId.toString());

		if (timesheetDocument != null) {
			List<Assignment> assignments = timesheetDocument.getAssignments();
			for (Assignment assignment : assignments) {
				roleNameQualifiers.add(String.valueOf(assignment.getWorkArea()));
			}
		}
		
		return new ArrayList<String>(roleNameQualifiers);
    }
	
	@Override
    protected List<Id> resolveRecipients(RouteContext routeContext, QualifiedRoleName qualifiedRoleName) {
		List<Id> recipients = new ArrayList<Id>();
		
		String roleName = qualifiedRoleName.getBaseRoleName();
		String qualifier = qualifiedRoleName.getQualifier();
		
		if (StringUtils.isNotBlank(roleName) && NumberUtils.isNumber(qualifier)) {
			Long workArea = Long.valueOf(qualifier);
	
			List<RoleMember> roleMembers = TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(roleName, workArea, new DateTime(), true);
			
	        for (RoleMember roleMember : roleMembers) {
	        	recipients.add(new PrincipalId(roleMember.getMemberId()));
		    }
		} else {
			LOG.error("Could not route to role " + roleName + " with work area " + qualifier);
		}
		
		return recipients;
    }

	@Override
	public Map<String, String> getProperties() {
		return null;
	}

}