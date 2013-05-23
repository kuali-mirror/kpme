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
package org.kuali.kpme.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class HrContext {
	
	public static String getPrincipalId() {
		return GlobalVariables.getUserSession().getPrincipalId();
	}

    public static String getTargetPrincipalId() {
        String principalId = (String) GlobalVariables.getUserSession().retrieveObject(HrConstants.TK_TARGET_USER_PERSON);
        if (principalId == null) {
        	principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();
        }
        return principalId;
    }
    
    public static String getTargetName() {
    	return KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(getTargetPrincipalId()).getDefaultName().getCompositeName();
    }
    
	public static void setTargetPrincipalId(String principalId) {
		GlobalVariables.getUserSession().addObject(HrConstants.TK_TARGET_USER_PERSON, principalId);
	}
    
	public static boolean isTargetInUse() {
		return GlobalVariables.getUserSession().retrieveObject(HrConstants.TK_TARGET_USER_PERSON) != null;
	}
    
	public static void clearTargetUser() {
		GlobalVariables.getUserSession().removeObject(HrConstants.TK_TARGET_USER_PERSON);
	}
	
	public static boolean isSystemAdmin() {
		return HrServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(getPrincipalId(), new DateTime());
	}
	
	public static boolean isTargetSystemAdmin() {
		return HrServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(getTargetPrincipalId(), new DateTime());
	}
	
	public static boolean isGlobalViewOnly() {
		return HrServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(getPrincipalId(), new DateTime());
	}
	
	public static boolean isTargetGlobalViewOnly() {
		return HrServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(getTargetPrincipalId(), new DateTime());
	}
	
	public static boolean isAnyApprover() {
		return isApprover() || isApproverDelegate();
	}
	
	public static boolean isTargetAnyApprover() {
		return isTargetApprover() || isTargetApproverDelegate();
	}
	
	public static boolean isApprover() {
		return HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.APPROVER.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetApprover() {
		return HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.APPROVER.getRoleName(), new DateTime());
	}
	
	public static boolean isApproverDelegate() {
		return HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetApproverDelegate() {
		return HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime());
	}
	
	public static boolean isReviewer() {
		return HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.REVIEWER.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetReviewer() {
		return HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.REVIEWER.getRoleName(), new DateTime());
	}
	
	public static boolean isActiveEmployee() {
    	return CollectionUtils.isNotEmpty(HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), LocalDate.now()));
	}
    
	public static boolean isTargetActiveEmployee() {
		return CollectionUtils.isNotEmpty(HrServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), LocalDate.now()));
	}
	
}