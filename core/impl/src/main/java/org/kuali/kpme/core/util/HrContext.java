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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
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
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(getPrincipalId(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetSystemAdmin() {
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(getTargetPrincipalId(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isGlobalViewOnly() {
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(getPrincipalId(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetGlobalViewOnly() {
		return HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(getTargetPrincipalId(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isAnyApprover() {
		return isApprover() || isApproverDelegate() || isAnyPositionApprover() || isAnyPositionApproverDelegate();
	}

	public static boolean isAnyPositionApprover() {
		return isAnyPositionAuthorizedUser(getPrincipalId(), KPMERole.APPROVER.getRoleName());
	}
	
	public static boolean isTargetAnyPositionApprover() {
		return isAnyPositionAuthorizedUser(getTargetPrincipalId(), KPMERole.APPROVER.getRoleName());
	}
	
	public static boolean isAnyPositionApproverDelegate() {
		return isAnyPositionAuthorizedUser(getPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName());
	}
	
	public static boolean isTargetAnyPositionApproverDelegate() {
		return isAnyPositionAuthorizedUser(getTargetPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName());
	}
	
	public static boolean isAnyPositionReviewer() {
		return isAnyPositionAuthorizedUser(getPrincipalId(), KPMERole.REVIEWER.getRoleName());
	}
	
	public static boolean isTargetAnyPositionReviewer() {
		return isAnyPositionAuthorizedUser(getTargetPrincipalId(), KPMERole.REVIEWER.getRoleName());
	}
	
	public static boolean isAnyPositionAuthorizedUser(String principalId, String roleName){
		JobContract jobObj = HrServiceLocator.getJobService().getPrimaryJob(principalId, LocalDate.now());
		Map<String, String> qualification = new HashMap<String, String>();
		if(jobObj != null) {
			qualification.put(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName(), jobObj.getPositionNumber());
		}
		return HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), roleName, qualification, new DateTime());
	}
	
    public static boolean isAnyPayrollProcessor() {
        return isPayrollProcessor() || isPayrollProcessorDelegate();
    }
	
	public static boolean isTargetAnyApprover() {
		return isTargetApprover() || isTargetApproverDelegate() || isTargetAnyPositionApprover() || isTargetAnyPositionApproverDelegate();
	}
	
	public static boolean isUserOrTargetAnyApprover() {
		return isAnyApprover() || isTargetAnyApprover() ;
	}
	
	public static boolean isUserOrTargetAnyPayrollProcessor() {
		return isAnyPayrollProcessor() || isTargetAnyPayrollProcessor();
	}
	
	public static boolean isUserOrTargetReviewer() {
		return isReviewer() || isTargetReviewer() || isAnyPositionReviewer() || isTargetAnyPositionReviewer();
	}
	
	public static boolean isTargetAnyPayrollProcessor() {
		return isTargetPayrollProcessor() || isTargetPayrollProcessorDelegate();
	}

    public static boolean isAnyAdmin() {
        String principalId = GlobalVariables.getUserSession().getPrincipalId();
        DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
        boolean isSysAdmin = HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(principalId, new DateTime());
        boolean isTimeLocationAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), date);
        boolean isTimeSysAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_SYSTEM_ADMINISTRATOR.getRoleName(), date);
        boolean isLeaveLocationAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), date);
        boolean isLeaveSysAdmin = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_SYSTEM_ADMINISTRATOR.getRoleName(), date);

        return isSysAdmin || isLeaveLocationAdmin || isLeaveSysAdmin || isTimeLocationAdmin || isTimeSysAdmin;
    }

	public static boolean isApprover() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetApprover() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isApproverDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetApproverDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isReviewer() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetReviewer() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isActiveEmployee() {
    	return CollectionUtils.isNotEmpty(HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), LocalDate.now()));
	}
    
	public static boolean isTargetActiveEmployee() {
		return CollectionUtils.isNotEmpty(HrServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), LocalDate.now()));
	}
	
	// KPME-2532
	public static boolean isPayrollProcessor() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetPayrollProcessor() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isPayrollProcessorDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetPayrollProcessorDelegate() {
		return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}

    //KPME-2699
    public static boolean canEditInactiveRecords() {
        return isAnyAdmin();
    }

}