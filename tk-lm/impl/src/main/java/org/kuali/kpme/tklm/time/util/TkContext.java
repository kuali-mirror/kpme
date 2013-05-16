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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TkContext {

	private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Collections.synchronizedMap(new HashMap<String, Object>());
		}
	};
	
	public static String getPrincipalId() {
		return GlobalVariables.getUserSession().getPrincipalId();
	}

	public static boolean isLocationAdmin() {
        return TkServiceLocator.getTKRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime())
        		|| LmServiceLocator.getLMRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetLocationAdmin() {
        return TkServiceLocator.getTKRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime())
        		|| LmServiceLocator.getLMRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isDepartmentAdmin() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime())
    			|| LmServiceLocator.getLMRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetDepartmentAdmin() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime())
    			|| LmServiceLocator.getLMRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isDepartmentViewOnly() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime())
    			|| LmServiceLocator.getLMRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetDepartmentViewOnly() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime())
    			|| LmServiceLocator.getLMRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime());
	}
	
    public static String getTargetPrincipalId() {
        String principalId = (String) GlobalVariables.getUserSession().retrieveObject(HrConstants.TK_TARGET_USER_PERSON);
        if (principalId == null) {
        	principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();
        }
        return principalId;
    }

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getStorageMap().get("REQUEST");
	}

	public static void setHttpServletRequest(HttpServletRequest request) {
		getStorageMap().put("REQUEST", request);
	}
    
	public static boolean isSynchronous() {
    	boolean isSynchronous = false;
    	
    	List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), LocalDate.now());
    	
    	for (Assignment assignment : assignments) {
    		TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), LocalDate.now());
            if (tcr == null || tcr.isClockUserFl()) {
            	isSynchronous = true;
            	break;
            }
        }
    	
        return isSynchronous;
	}
	
    public static boolean isTargetSynchronous() {
    	boolean isSynchronous = false;
    	
    	List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), LocalDate.now());
    	
    	for (Assignment assignment : assignments) {
    		TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), LocalDate.now());
            if (tcr == null || tcr.isClockUserFl()) {
            	isSynchronous = true;
            	break;
            }
        }
    	
        return isSynchronous;
    }
    
	public static Map<String, Object> getStorageMap() {
		return STORAGE_MAP.get();
	}

	public static void resetStorageMap() {
		STORAGE_MAP.remove();
	}

	public static void clear() {
		resetStorageMap();
	}
}
