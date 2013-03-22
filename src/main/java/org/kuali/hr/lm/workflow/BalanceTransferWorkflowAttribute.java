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


import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.AbstractRoleAttribute;
import org.kuali.rice.kew.rule.ResolvedQualifiedRole;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BalanceTransferWorkflowAttribute extends AbstractRoleAttribute {
    private static final Logger LOG = Logger.getLogger(BalanceTransferWorkflowAttribute.class);

    @Override
    public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
        List<String> roles = new ArrayList<String>();
        String documentNumber = documentContent.getRouteContext().getDocument().getDocumentId();
        MaintenanceDocument document = null;
		try {
			document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentNumber);
		} catch (WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BalanceTransfer balanceTransfer = null;
		if(ObjectUtils.isNotNull(document))
			balanceTransfer = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();

        if (balanceTransfer != null) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(balanceTransfer.getPrincipalId(), balanceTransfer.getEffectiveDate());
            for(Assignment assignment : assignments) {
                String roleStr = roleName+"_"+assignment.getWorkArea();
                if(!roles.contains(roleStr))
                    roles.add(roleStr);
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
        MaintenanceDocument document = null;
		try {
			document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(routeHeaderId);
		} catch (WorkflowException e) {
			LOG.error("unable to retrieve the Maintenance Document with route hearder id: " + routeHeaderId);
			e.printStackTrace();
		}
        TkRoleService roleService = TkServiceLocator.getTkRoleService();
        BalanceTransfer balanceTransfer = null;
        if(ObjectUtils.isNotNull(document)) {
        	balanceTransfer = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();
        }
        if(ObjectUtils.isNotNull(balanceTransfer)) {
	        WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(workAreaNumber, balanceTransfer.getEffectiveDate());
	
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
	                List<Job> lstJobsForPosition = TkServiceLocator.getJobService().getActiveJobsForPosition(positionNumber, balanceTransfer.getEffectiveDate());
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
	
	        if (principals.size() == 0)  {
	            throw new RuntimeException("No principals to route to. Push to exception routing.");
            }
	        rqr.setRecipients(principals);
	        rqr.setAnnotation("Dept: "+ workArea.getDept()+", Work Area: "+workArea.getWorkArea());
	        return rqr;
        }
        else {
        	throw new RuntimeException("no business object could be retreived");
        }
    }

    @Override
    public List<RoleName> getRoleNames() {
        return Collections.EMPTY_LIST;
    }

    private CalendarEntry getCalendarEntry(LeaveBlock leaveBlock) {
        return TkServiceLocator.getCalendarEntryService().getCalendarEntry(leaveBlock.getCalendarId());
    }
}
