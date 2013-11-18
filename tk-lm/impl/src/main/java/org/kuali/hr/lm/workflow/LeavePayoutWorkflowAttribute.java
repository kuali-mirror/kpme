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
package org.kuali.hr.lm.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.AbstractRoleAttribute;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

@Deprecated
public class LeavePayoutWorkflowAttribute extends AbstractRoleAttribute {

	private static final long serialVersionUID = 540529528227045564L;
	
	private static final Logger LOG = Logger.getLogger(LeavePayoutWorkflowAttribute.class);

    @Override
    public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
        List<String> roles = new ArrayList<String>();
        String documentNumber = documentContent.getRouteContext().getDocument().getDocumentId();
        MaintenanceDocument document = null;
		try {
			document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentNumber);
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		LeavePayout leavePayout = null;
		if (document != null && document.getNewMaintainableObject() != null) {
			leavePayout = (LeavePayout) document.getNewMaintainableObject().getDataObject();
		}
		
        if (leavePayout != null) {
            List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(leavePayout.getPrincipalId(), leavePayout.getEffectiveLocalDate());
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
        MaintenanceDocument document = null;
		try {
			document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(routeHeaderId);
		} catch (WorkflowException e) {
			LOG.error("unable to retrieve the Maintenance Document with route header id: " + routeHeaderId);
			e.printStackTrace();
		}
		
        LeavePayout leavePayout = null;
        if (document != null && document.getNewMaintainableObject() != null) {
        	leavePayout = (LeavePayout) document.getNewMaintainableObject().getDataObject();
        }
        
        if (leavePayout != null) {
	        WorkAreaContract workArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(workAreaNumber, leavePayout.getEffectiveLocalDate());
	
	        List<RoleMember> roleMembers = new ArrayList<RoleMember>();
	        
	        if (TkConstants.ROLE_TK_APPROVER.equals(roleName)) {
		        roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workAreaNumber, new DateTime(), true));
		        roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workAreaNumber, new DateTime(), true));
			}
			
	        for (RoleMember roleMember : roleMembers) {
	        	principals.add(new PrincipalId(roleMember.getMemberId()));
		    }
	
	        if (principals.size() == 0)  {
	            throw new RuntimeException("No principals to route to. Push to exception routing.");
            }
	        
	        rqr.setRecipients(principals);
	        rqr.setAnnotation("Dept: "+ workArea.getDept()+", Work Area: "+workArea.getWorkArea());
	        
	        return rqr;
        } else {
        	throw new RuntimeException("no business object could be retreived");
        }
    }

    @Override
    public List<RoleName> getRoleNames() {
        return Collections.emptyList();
    }

}
