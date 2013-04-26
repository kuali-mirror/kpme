package org.kuali.hr.tklm.leave.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.core.bo.assignment.Assignment;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.GenericRoleAttribute;
import org.kuali.rice.kew.rule.QualifiedRoleName;
import org.kuali.rice.kim.api.role.RoleMember;

@SuppressWarnings("unchecked")
public class LeaveRequestRoleAttribute extends GenericRoleAttribute {

	private static final long serialVersionUID = -5633136464524032279L;
	
	private static final Logger LOG = Logger.getLogger(LeaveRequestRoleAttribute.class);

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
        LeaveRequestDocument leaveRequestDocument = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(routeHeaderId.toString());
		
        if (leaveRequestDocument != null) {
            LeaveBlock leaveBlock = leaveRequestDocument.getLeaveBlock();
            CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(leaveBlock.getCalendarId());
            List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(leaveBlock.getPrincipalId(), calendarEntry);
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