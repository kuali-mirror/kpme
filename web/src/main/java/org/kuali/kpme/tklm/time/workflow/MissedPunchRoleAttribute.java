package org.kuali.kpme.tklm.time.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.GenericRoleAttribute;
import org.kuali.rice.kew.rule.QualifiedRoleName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

@SuppressWarnings("unchecked")
public class MissedPunchRoleAttribute extends GenericRoleAttribute {

	private static final long serialVersionUID = -7188190168685823002L;
	
	private static final Logger LOG = Logger.getLogger(MissedPunchRoleAttribute.class);

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
		
		try {
			String documentId = documentContent.getRouteContext().getDocument().getDocumentId();
			MissedPunchDocument missedPunchDocument = (MissedPunchDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
			MissedPunch missedPunch = missedPunchDocument.getMissedPunch();
	        roleNameQualifiers.add(String.valueOf(missedPunch.getWorkArea()));
		} catch (WorkflowException we) {
			we.printStackTrace();
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
	
			List<RoleMember> roleMembers = HrServiceLocator.getHRRoleService().getRoleMembersInWorkArea(roleName, workArea, new DateTime(), true);
			
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