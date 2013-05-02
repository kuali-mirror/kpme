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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TKContext {

    private static final String TDOC_OBJ_KEY = "_TDOC_O_KEY";
    private static final String TDOC_KEY = "_TDOC_ID_KEY";
    private static final String LDOC_OBJ_KEY = "_LDOC_O_KEY";
    private static final String LDOC_KEY = "_LDOC_ID_KEY";

	private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Collections.synchronizedMap(new HashMap<String, Object>());
		}
	};
	
	public static boolean isLocationAdmin() {
        return HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime())
        		|| HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetLocationAdmin() {
        return HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime())
        		|| HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isDepartmentAdmin() {
    	return HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime())
    			|| HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetDepartmentAdmin() {
    	return HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime())
    			|| HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isDepartmentViewOnly() {
    	return HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime())
    			|| HrServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetDepartmentViewOnly() {
    	return HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime())
    			|| HrServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime());
	}
	
	public static String getPrincipalId() {
		return GlobalVariables.getUserSession().getPrincipalId();
	}

    public static String getTargetPrincipalId() {
        String principalId = (String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_PERSON);
        if (principalId == null) {
        	principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();
        }
        return principalId;
    }
    
    public static String getTargetName() {
    	return KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(getTargetPrincipalId()).getDefaultName().getCompositeName();
    }
    
	public static void setTargetPrincipalId(String principalId) {
		GlobalVariables.getUserSession().addObject(TkConstants.TK_TARGET_USER_PERSON, principalId);
	}
    
	public static boolean isTargetInUse() {
		return GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_PERSON) != null;
	}
    
	public static void clearTargetUser() {
		GlobalVariables.getUserSession().removeObject(TkConstants.TK_TARGET_USER_PERSON);
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
	
	public static boolean isSynchronous() {
    	boolean isSynchronous = false;
    	
    	List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), LocalDate.now());
    	
    	for (Assignment assignment : assignments) {
            if (assignment.isSynchronous()) {
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
            if (assignment.isSynchronous()) {
            	isSynchronous = true;
            	break;
            }
        }
    	
        return isSynchronous;
    }

    public static TimesheetDocument getCurrentTimesheetDocument() {
        return (TimesheetDocument)TKContext.getStorageMap().get(TDOC_OBJ_KEY);
    }

    public static void setCurrentTimesheetDocument(TimesheetDocument tdoc) {
        TKContext.getStorageMap().put(TDOC_OBJ_KEY, tdoc);
    }

    public static String getCurrentTimesheetDocumentId() {
        return (String)TKContext.getStorageMap().get(TDOC_KEY);
    }

    public static void setCurrentTimesheetDocumentId(String timesheetDocumentId) {
        TKContext.getStorageMap().put(TDOC_KEY, timesheetDocumentId);
    }

    public static LeaveCalendarDocument getCurrentLeaveCalendarDocument() {
        return  (LeaveCalendarDocument)TKContext.getStorageMap().get(LDOC_OBJ_KEY);
    }

    public static void setCurrentLeaveCalendarDocument(LeaveCalendarDocument ldoc) {
        TKContext.getStorageMap().put(LDOC_OBJ_KEY, ldoc);
    }

    public static String getCurrentLeaveCalendarDocumentId() {
        return (String)TKContext.getStorageMap().get(LDOC_KEY);
    }

    public static void setCurrentLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
        TKContext.getStorageMap().put(LDOC_KEY, leaveCalendarDocumentId);
    }	

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getStorageMap().get("REQUEST");
	}

	public static void setHttpServletRequest(HttpServletRequest request) {
		getStorageMap().put("REQUEST", request);
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