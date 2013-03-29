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
package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import com.google.common.collect.Multimap;

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
		return TkServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(getPrincipalId(), new DateTime());
	}
	
	public static boolean isTargetSystemAdmin() {
		return TkServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(getTargetPrincipalId(), new DateTime());
	}
	
	public static boolean isGlobalViewOnly() {
		return TkServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(getPrincipalId(), new DateTime());
	}
	
	public static boolean isTargetGlobalViewOnly() {
		return TkServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(getTargetPrincipalId(), new DateTime());
	}
	
	public static boolean isLocationAdmin() {
        return TkServiceLocator.getTKRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime())
        		|| TkServiceLocator.getLMRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetLocationAdmin() {
        return TkServiceLocator.getTKRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime())
        		|| TkServiceLocator.getLMRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isDepartmentAdmin() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime())
    			|| TkServiceLocator.getLMRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetDepartmentAdmin() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime())
    			|| TkServiceLocator.getLMRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime());
	}
	
	public static boolean isDepartmentViewOnly() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getPrincipalId(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime())
    			|| TkServiceLocator.getLMRoleService().principalHasRole(getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetDepartmentViewOnly() {
    	return TkServiceLocator.getTKRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime())
    			|| TkServiceLocator.getLMRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime());
	}
	
	public static boolean isAnyApprover() {
		return isApprover() || isApproverDelegate();
	}
	
	public static boolean isTargetAnyApprover() {
		return isTargetApprover() || isTargetApproverDelegate();
	}
	
	public static boolean isApprover() {
		return TkServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.APPROVER.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetApprover() {
		return TkServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.APPROVER.getRoleName(), new DateTime());
	}
	
	public static boolean isApproverDelegate() {
		return TkServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetApproverDelegate() {
		return TkServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime());
	}
	
	public static boolean isReviewer() {
		return TkServiceLocator.getHRRoleService().principalHasRole(getPrincipalId(), KPMERole.REVIEWER.getRoleName(), new DateTime());
	}
	
	public static boolean isTargetReviewer() {
		return TkServiceLocator.getHRRoleService().principalHasRole(getTargetPrincipalId(), KPMERole.REVIEWER.getRoleName(), new DateTime());
	}
	
	public static boolean isActiveEmployee() {
    	return CollectionUtils.isNotEmpty(TkServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), TKUtils.getCurrentDate()));
	}
    
	public static boolean isTargetActiveEmployee() {
		return CollectionUtils.isNotEmpty(TkServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), TKUtils.getCurrentDate()));
	}
	
	public static boolean isSynchronous() {
    	boolean isSynchronous = false;
    	
    	List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), TKUtils.getCurrentDate());
    	
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
    	
    	List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(getTargetPrincipalId(), TKUtils.getCurrentDate());
    	
    	for (Assignment assignment : assignments) {
            if (assignment.isSynchronous()) {
            	isSynchronous = true;
            	break;
            }
        }
    	
        return isSynchronous;
    }
    
	public static Multimap<String, Long> getReportingApprovalDepartments() {
		String principalId = GlobalVariables.getUserSession().getPrincipalId();
        Set<Long> workAreas = new HashSet<Long>();
        workAreas.addAll(TkServiceLocator.getWorkAreaService().getApproverAndApproverDelegateWorkAreas(principalId));
        workAreas.addAll(TkServiceLocator.getWorkAreaService().getReviewerWorkAreas(principalId));
        // see the comment in the getDeptWorkAreasByWorkAreas() for the explanation of Multimap
        Multimap<String, Long> reportingApprovalDepartments = TkServiceLocator.getTimeApproveService().getDeptWorkAreasByWorkAreas(workAreas);
		
		return reportingApprovalDepartments;
	}
	
	public static Set<Long> getReportingWorkAreas() {
		String principalId = GlobalVariables.getUserSession().getPrincipalId();

		Set<Long> reportingWorkAreas = new HashSet<Long>();
		
		reportingWorkAreas.addAll(TkServiceLocator.getWorkAreaService().getApproverAndApproverDelegateWorkAreas(principalId));
		reportingWorkAreas.addAll(TkServiceLocator.getWorkAreaService().getReviewerWorkAreas(principalId));
		
		List<String> departments = new ArrayList<String>();
		departments.addAll(TkServiceLocator.getDepartmentService().getViewOnlyDepartments(principalId));
		departments.addAll(TkServiceLocator.getDepartmentService().getAdministratorDepartments(principalId));
		for(String department : departments) {
			List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(department, TKUtils.getCurrentDate());
			for (WorkArea workArea : workAreas) {
				reportingWorkAreas.add(workArea.getWorkArea());
			}
		}
		
		return reportingWorkAreas;
	}
	
	public static List<Long> getReviewerWorkAreas() {
		return TkServiceLocator.getWorkAreaService().getReviewerWorkAreas(GlobalVariables.getUserSession().getPrincipalId());
	}
	
	public static List<Long> getApproverWorkAreas() {
		return TkServiceLocator.getWorkAreaService().getApproverAndApproverDelegateWorkAreas(GlobalVariables.getUserSession().getPrincipalId());
	}
	
	public static List<String> getDepartmentAdminAreas() {
		return TkServiceLocator.getDepartmentService().getAdministratorDepartments(GlobalVariables.getUserSession().getPrincipalId());
	}
	
	public static List<String> getLocationAdminAreas() {
		return TkServiceLocator.getLocationService().getAdministratorLocations(GlobalVariables.getUserSession().getPrincipalId());
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