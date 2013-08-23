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
import org.kuali.kpme.core.KPMENamespace;
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
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(getPrincipalId(), DateTime.now());
	}
	
	public static boolean isTargetSystemAdmin() {
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(getTargetPrincipalId(), DateTime.now());
	}
	
	public static boolean isGlobalViewOnly() {
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(getPrincipalId(), DateTime.now());
	}
	
	public static boolean isTargetGlobalViewOnly() {
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(getTargetPrincipalId(), DateTime.now());
	}
	
	public static boolean isAnyApprover() {
		return isApprover() || isApproverDelegate();
	}

    public static boolean isAnyPayrollProcessor() {
        return isPayrollProcessor() || isPayrollProcessorDelegate();
    }
	
	public static boolean isTargetAnyApprover() {
		return isTargetApprover() || isTargetApproverDelegate();
	}
	
	public static boolean isApprover() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), DateTime.now());
	}
	
	public static boolean isTargetApprover() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), DateTime.now());
	}
	
	public static boolean isApproverDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), DateTime.now());
	}
	
	public static boolean isTargetApproverDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), DateTime.now());
	}
	
	public static boolean isReviewer() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), DateTime.now());
	}
	
	public static boolean isTargetReviewer() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), DateTime.now());
	}
	
	public static boolean isActiveEmployee() {
    	return CollectionUtils.isNotEmpty(HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), LocalDate.now()));
	}
    
	public static boolean isTargetActiveEmployee() {
		return CollectionUtils.isNotEmpty(HrServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), LocalDate.now()));
	}
	
	// KPME-2532
	public static boolean isPayrollProcessor() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), DateTime.now());
	}
	
	public static boolean isTargetPayrollProcessor() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), DateTime.now());
	}
	
	public static boolean isPayrollProcessorDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), DateTime.now());
	}
	
	public static boolean isTargetPayrollProcessorDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), DateTime.now());
	}
    //KPME-2699
    public static boolean canEditInactiveRecords() {

        String principalId = GlobalVariables.getUserSession().getPrincipalId();
        boolean isSysAdmin = HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(principalId, new DateTime());
        boolean isTimeLocationAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
        boolean isTimeSysAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_SYSTEM_ADMINISTRATOR.getRoleName(), new DateTime());
        boolean isLeaveLocationAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
        boolean isLeaveSysAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_SYSTEM_ADMINISTRATOR.getRoleName(), new DateTime());

        return isSysAdmin || isLeaveLocationAdmin || isLeaveSysAdmin || isTimeLocationAdmin || isTimeSysAdmin;
    }

}