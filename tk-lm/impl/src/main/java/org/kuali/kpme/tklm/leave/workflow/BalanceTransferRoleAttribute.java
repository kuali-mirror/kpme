/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.GenericRoleAttribute;
import org.kuali.rice.kew.rule.QualifiedRoleName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

@SuppressWarnings("unchecked")
public class BalanceTransferRoleAttribute extends GenericRoleAttribute {

	private static final long serialVersionUID = 919441150612810324L;
	
	private static final Logger LOG = Logger.getLogger(BalanceTransferRoleAttribute.class);

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
        MaintenanceDocument document = null;
		try {
			document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(routeHeaderId.toString());
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		
		BalanceTransfer balanceTransfer = null;
		if (document != null && document.getNewMaintainableObject() != null) {
			balanceTransfer = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();
		}
		
        if (balanceTransfer != null) {
            List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(balanceTransfer.getPrincipalId(), balanceTransfer.getEffectiveLocalDate());
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
	
			List<RoleMember> roleMembers = HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), roleName, workArea, LocalDate.now().toDateTimeAtStartOfDay(), true);
			
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
