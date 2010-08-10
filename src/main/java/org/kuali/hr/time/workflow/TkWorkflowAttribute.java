package org.kuali.hr.time.workflow;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.identity.Id;
import org.kuali.rice.kew.identity.PrincipalId;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.kew.rule.Role;
import org.kuali.rice.kew.rule.RoleAttribute;

public class TkWorkflowAttribute implements RoleAttribute{

	@Override
	public List<String> getQualifiedRoleNames(String roleName,
			DocumentContent documentContent) {
		List<String> roles = new ArrayList<String>();
		roles.add(roleName);
		return roles;
	}

	@Override
	public List<Role> getRoleNames() {
		throw new UnsupportedOperationException("Not supported in TkWorkflowAttribute");
	}

	@Override
	public ResolvedQualifiedRole resolveQualifiedRole(
			RouteContext routeContext, String roleName, String qualifiedRole) {
		ResolvedQualifiedRole rqr = new ResolvedQualifiedRole();
		List<Id> principals = new ArrayList<Id>();
		String docId = routeContext.getDocument().getAppDocId();
		//TODO set the people up as necessary from the role setup
		//TODO find the work areas associated with this document 
		//and route accordingly
		//TODO confirm default delegate setup is used
		principals.add(new PrincipalId("admin"));
		rqr.setRecipients(principals);
		return rqr;
	}

}
