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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import com.google.common.collect.Multimap;

/**
 * This class houses the concept of a user in the Timekeeping system.  It
 * is essentially a lightweight wrapper around multiple KIM Person objects.
 *
 * One for the actual ACTUAL person
 *
 * One for the user the ACTUAL person is backdooring as: Back Door user is like
 * doing 'su - <username>' in unix. You "become" that person, assume all of their
 * roles, etc.
 *
 * One for the user the ACTUAL person is targeting: Targeting a user is being
 * granted read/write access to the users data.
 *
 * See Javadocs for:
 *
 * getCurrentTargetPerson(), getCurrentPerson(), getActualPerson(),
 * getBackdoorPerson(), getTargetPerson().
 *
 * the getCurrent*() methods are most likely what you should be using in any
 * end user display logic. The methods get[ABT]*() can return null.
 *
 */
public class TKUser {

	public static void setTargetPerson(String principalId) {
		GlobalVariables.getUserSession().addObject(TkConstants.TK_TARGET_USER_PERSON, principalId);
	}
	
	public static boolean isTargetInUse() {
		return GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_PERSON) != null;
	}

	public static void clearTargetUser() {
		GlobalVariables.getUserSession().removeObject(TkConstants.TK_TARGET_USER_PERSON);
	}
    
    /**
     * Returns a principal id for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A principalId: target > backdoor > actual.
     */
    public static String getCurrentTargetPersonId() {
        String p = (String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_PERSON);
        if (p == null) {
            p = GlobalVariables.getUserSession().getPerson().getPrincipalId();
        }
        return p;
    }

    /**
     * Returns a Person object for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A Person object: target > backdoor > actual.
     */
    public static Person getCurrentTargetPerson() {
        Person p;
        String principalId = (String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_PERSON);
        if (principalId == null) {
            p = GlobalVariables.getUserSession().getPerson();
        } else {
            p = KimApiServiceLocator.getPersonService().getPerson(principalId);
        }
        return p;
    }

    /**
     * Returns a UserRoles object for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A UserRoles object: target > backdoor > actual.
     */
    public static UserRoles getCurrentTargetRoles() {
    	return TkUserRoles.getUserRoles(getCurrentTargetPersonId());
    }
    
    //public static TKUser getUser(String targetUserId, Date asOfDate) {
    //    TKUser.setTargetPerson(targetUserId);
    //
    //    return new TKUser();
    //}

	public static boolean isSystemAdmin() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin();
	}

	public static boolean isLocationAdmin() {
		return TKUser.getLocationAdminAreas().size() > 0;
	}

	public static boolean isDepartmentAdmin() {
		return TKUser.getDepartmentAdminAreas().size() > 0;
	}

	public static boolean isGlobalViewOnly() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isGlobalViewOnly();
	}

	public static boolean isDeptViewOnly() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isDeptViewOnly();
	}
	
	public static boolean isActiveEmployee() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isActiveEmployee();
	}
	
	public static boolean isSynchronous() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isSynchronous();
	}

	public static boolean isReviewer() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isReviewer();
	}

	public static boolean isApprover() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isApprover();
	}
	
	public static boolean isTimesheetReviewer() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isTimesheetReviewer();
	}
	
	public static boolean isTimesheetApprover() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isTimesheetApprover();
	}
	
	public static boolean isAnyApproverActive() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isAnyApproverActive();
	}
	
	public static boolean isApproverForTimesheet(String docId) {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isApproverForTimesheet(docId);
	}
	
	public static boolean isDocumentReadable(String documentId) {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isDocumentReadable(documentId);
	}
	
	public static boolean isDocumentWritable(String documentId) {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isDocumentWritable(documentId);
	}
	
	public static Multimap<String, Long> getReportingApprovalDepartments(){
		UserRoles userRoles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        Set<Long> workAreas = new HashSet<Long>();
        workAreas.addAll(userRoles.getApproverWorkAreas());
        workAreas.addAll(userRoles.getReviewerWorkAreas());
        // see the comment in the getDeptWorkAreasByWorkAreas() for the explanation of Multimap
        Multimap<String, Long> reportingApprovalDepartments = TkServiceLocator.getTimeApproveService().getDeptWorkAreasByWorkAreas(workAreas);

        //KPME-1338
		/*Set<String> depts = new HashSet<String>();
		depts.addAll(userRoles.getDepartmentViewOnlyDepartments());
		depts.addAll(userRoles.getOrgAdminDepartments());
        if (depts.size() > 0) {
            reportingApprovalDepartments.putAll(TkServiceLocator.getTimeApproveService().getDeptWorkAreasByDepts(depts));
        }*/
		
		return reportingApprovalDepartments;
	}
	
	public static Set<Long> getReportingWorkAreas(){
		UserRoles userRoles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
		Set<Long> reportingWorkAreas = new HashSet<Long>();
		List<String> depts = new ArrayList<String>();
		
		reportingWorkAreas.addAll(userRoles.getApproverWorkAreas());
		for(Long workArea : userRoles.getApproverWorkAreas()){
			if(!reportingWorkAreas.contains(workArea)){
				reportingWorkAreas.add(workArea);
			}
		}
		
		for(Long workArea : userRoles.getReviewerWorkAreas()){
			if(!reportingWorkAreas.contains(workArea)){
				reportingWorkAreas.add(workArea);
			}
		}
		
		reportingWorkAreas.addAll(userRoles.getReviewerWorkAreas());
		
		depts.addAll(userRoles.getDepartmentViewOnlyDepartments());
		depts.addAll(userRoles.getOrgAdminDepartments());
		
		for(String dept : depts){
			List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(dept, TKUtils.getCurrentDate());
			for(WorkArea workArea : workAreas){
				if(!reportingWorkAreas.contains(workArea.getWorkArea())){
					reportingWorkAreas.add(workArea.getWorkArea());
				}
			}
		}
		
		
		return reportingWorkAreas;
	}
	
	public static Set<Long> getApproverWorkAreas() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();
	}

	public static Set<Long> getReviewerWorkAreas() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getReviewerWorkAreas();
	}

	public static Set<String> getLocationAdminAreas() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getOrgAdminCharts();
	}

	public static Set<String> getDepartmentAdminAreas() {
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getOrgAdminDepartments();
	}

    public static SortedSet<Long> getWorkAreasFromUserRoles() {
    	UserRoles userRoles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        SortedSet<Long> workAreas = new TreeSet<Long>();
        workAreas.addAll(userRoles.getApproverWorkAreas());
        workAreas.addAll(userRoles.getReviewerWorkAreas());
        
        if(userRoles.isDepartmentAdmin()){
        	Set<String> deptAdminDepts = userRoles.getOrgAdminDepartments();
        	for(String dept : deptAdminDepts){
        		List<WorkArea> was = TkServiceLocator.getWorkAreaService().getWorkAreas(dept, TKUtils.getCurrentDate());
        		for(WorkArea wa : was){
        			workAreas.add(wa.getWorkArea());
        		}
        	}
        }

        return workAreas;
    }

}