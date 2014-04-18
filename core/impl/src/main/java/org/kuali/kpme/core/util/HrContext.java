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
package org.kuali.kpme.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
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
		return isApprover() || isApproverDelegate();
	}

    public static boolean isAnyPayrollProcessor() {
        return isPayrollProcessor() || isPayrollProcessorDelegate();
    }
	
	public static boolean isTargetAnyApprover() {
		return isTargetApprover() || isTargetApproverDelegate();
	}
	
	public static boolean isUserOrTargetAnyApprover() {
		return isAnyApprover() || isTargetAnyApprover();
	}
	
	public static boolean isUserOrTargetAnyPayrollProcessor() {
		return isAnyPayrollProcessor() || isTargetAnyPayrollProcessor();
	}
	
	public static boolean isUserOrTargetReviewer() {
		return isReviewer() || isTargetReviewer();
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
    
    //KPME-3400
    public static boolean isUserOrTargetKOHRInstitutionAdmin(){
    	return isUserKOHRInstitutionAdmin() || isTargetKOHRInstitutionAdmin();
    }

    public static boolean isUserKOHRInstitutionAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isTargetKOHRInstitutionAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetKOHRAcademicHrAdmin(){
    	return isUserKOHRAcademicHrAdmin() || isTargetKOHRAcademicHrAdmin();
    }
    public static boolean isUserKOHRAcademicHrAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ACADEMIC_HR_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isTargetKOHRAcademicHrAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ACADEMIC_HR_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetKOHRInstitutionViewOnly(){
    	return isUserKOHRInstitutionViewOnly() || isTargetKOHRInstitutionViewOnly();
    }
    public static boolean isUserKOHRInstitutionViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isTargetKOHRInstitutionViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetKOHRLocationViewOnly(){
    	return isUserKOHRLocationViewOnly() || isTargetKOHRLocationViewOnly();
    }
    public static boolean isUserKOHRLocationViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isTargetKOHRLocationViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_INSTITUTION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetKOHRLocationAdmin(){
    	return isUserKOHRLocationAdmin() || isTargetKOHRLocationAdmin();
    }
    public static boolean isUserKOHRLocationAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_LOCATION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isTargetKOHRLocationAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_LOCATION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetKOHROrgAdmin(){
    	return isUserKOHROrgAdmin() || isTargetKOHROrgAdmin();
    }
    public static boolean isUserKOHROrgAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ORG_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isTargetKOHROrgAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ORG_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetKOHROrgViewOnly(){
    	return isUserKOHROrgViewOnly() || isTargetKOHROrgViewOnly();
    }
    public static boolean isTargetKOHROrgViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ORG_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserKOHROrgViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.KOHR_ORG_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetHRDepartmentAdmin(){
    	return isUserHRDepartmentAdmin() || isTargetHRDepartmentAdmin();
    }
    public static boolean isTargetHRDepartmentAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_DEPARTMENT_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserHRDepartmentAdmin(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_DEPARTMENT_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetHRDepartmentViewOnly(){
    	return isUserHRDepartmentViewOnly() || isTargetHRDepartmentViewOnly();
    }
    public static boolean isTargetHRDepartmentViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_DEPARTMENT_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserHRDepartmentViewOnly(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_DEPARTMENT_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetHRInstitutionApprover(){
    	return isUserHRInstitutionApprover() || isTargetHRInstitutionApprover();
    }
    public static boolean isTargetHRInstitutionApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_INSTITUTION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserHRInstitutionApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_INSTITUTION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetAcademicHRInstitutionApprover(){
    	return isUserAcademicHRInstitutionApprover() || isTargetAcademicHRInstitutionApprover();
    }
    public static boolean isTargetAcademicHRInstitutionApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.ACADEMIC_HR_INSTITUTION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserAcademicHRInstitutionApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.ACADEMIC_HR_INSTITUTION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetBudgetApprover(){
    	return isUserBudgetApprover() || isTargetBudgetApprover();
    }
    public static boolean isTargetBudgetApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.BUDGET_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserBudgetApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.BUDGET_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetPayrollApprover(){
    	return isUserPayrollApprover() || isTargetPayrollApprover();
    }
    public static boolean isTargetPayrollApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserPayrollApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetHRLocationApprover(){
    	return isUserHRLocationApprover() || isTargetHRLocationApprover();
    }
    public static boolean isTargetHRLocationApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_LOCATION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserHRLocationApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_LOCATION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetAcademicHRLocationApprover(){
    	return isUserAcademicHRLocationApprover() || isTargetAcademicHRLocationApprover();
    }
    public static boolean isTargetAcademicHRLocationApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.ACADEMIC_HR_LOCATION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserAcademicHRLocationApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.ACADEMIC_HR_LOCATION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetFiscalLocationApprover(){
    	return isUserFiscalLocationApprover() || isTargetFiscalLocationApprover();
    }
    public static boolean isTargetFiscalLocationApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_LOCATION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserFiscalLocationApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_LOCATION_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetHROrgApprover(){
    	return isUserHROrgApprover() || isTargetHROrgApprover();
    }
    public static boolean isTargetHROrgApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_ORG_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserHROrgApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.HR_ORG_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetFiscalOrgApprover(){
    	return isUserFiscalOrgApprover() || isTargetFiscalOrgApprover();
    }
    public static boolean isTargetFiscalOrgApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_ORG_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserFiscalOrgApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_ORG_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetDepartmentApprover(){
    	return isUserDepartmentApprover() || isTargetDepartmentApprover();
    }
    public static boolean isTargetDepartmentApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.DEPARTMENT_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserDepartmentApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.DEPARTMENT_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserOrTargetFiscalDepartmentApprover(){
    	return isUserFiscalDepartmentApprover() || isTargetFiscalDepartmentApprover();
    }
    public static boolean isTargetFiscalDepartmentApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_DEPARTMENT_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }

    public static boolean isUserFiscalDepartmentApprover(){
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.FISCAL_DEPARTMENT_APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
    }
    
    public static boolean isPositionModuleEnabled() {
    	String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.module.status");
    	if(StringUtils.equals(status, "On")) {
    		return true;
    	} else {
    		return false;
    	}
    }
}