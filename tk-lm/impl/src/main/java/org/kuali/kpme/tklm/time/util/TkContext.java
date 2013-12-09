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
package org.kuali.kpme.tklm.time.util;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TkContext {
	
	public static String getPrincipalId() {
		return GlobalVariables.getUserSession().getPrincipalId();
	}

	public static boolean isLocationAdmin() {
        return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
        		|| HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetLocationAdmin() {
        return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
        		|| HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isLocationViewOnly() {
        return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
        		|| HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetLocationViewOnly() {
        return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
        		|| HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isDepartmentAdmin() {
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
    			|| HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetDepartmentAdmin() {
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
    			|| HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isDepartmentViewOnly() {
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
    			|| HrServiceLocator.getKPMERoleService().principalHasRole(getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
	public static boolean isTargetDepartmentViewOnly() {
    	return HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
    			|| HrServiceLocator.getKPMERoleService().principalHasRole(getTargetPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());
	}
	
    public static String getTargetPrincipalId() {
        String principalId = (String) GlobalVariables.getUserSession().retrieveObject(HrConstants.TK_TARGET_USER_PERSON);
        if (principalId == null) {
        	principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();
        }
        return principalId;
    }
    
	public static boolean isSynchronous() {
    	boolean isSynchronous = false;
    	
    	List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), LocalDate.now());
    	
    	for (Assignment assignment : assignments) {
    		TimeCollectionRule tcr = null;
    		if(assignment.getJob() != null)
    			tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), LocalDate.now());
            if (tcr == null || tcr.isClockUserFl()) {
            	isSynchronous = true;
            	break;
            }
        }
    	
        return isSynchronous;
	}
	
    public static boolean isTargetSynchronous() {
    	boolean isSynchronous = false;
    	
    	List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), LocalDate.now());
    	
    	for (Assignment assignment : assignments) {
    		TimeCollectionRule tcr = null;
    		if(assignment.getJob() != null)
    			tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), LocalDate.now());
            if (tcr == null || tcr.isClockUserFl()) {
            	isSynchronous = true;
            	break;
            }
        }
    	
        return isSynchronous;
    }

}