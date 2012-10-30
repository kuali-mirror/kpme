/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.roles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.document.calendar.CalendarDocumentContract;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * TkUserRoles encapsulates the concept of roles for a single user and provides
 * lookup methods for quick Role checking. This object will be stored in TkUser.
 */
public class TkUserRoles implements UserRoles {
    private boolean synchronousAssignments = false;
    private TkRole globalViewOnly;
    private TkRole systemAdmin;
    private String principalId;

    private Map<String, TkRole> orgAdminRolesDept = new HashMap<String, TkRole>();
    private Map<String, TkRole> orgAdminRolesChart = new HashMap<String, TkRole>();
    private Map<Long, TkRole> approverRoles = new HashMap<Long, TkRole>();
    private Map<Long, TkRole> approverDelegateRoles = new HashMap<Long, TkRole>();
    private Map<Long, TkRole> reviewerRoles = new HashMap<Long, TkRole>();

    private Map<String, TkRole> deptViewOnlyRoles = new HashMap<String, TkRole>();
    private Set<String> activeAssignmentIds = new HashSet<String>();
    
    public static TkUserRoles getUserRoles(String principalId) {
    	List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(principalId, TKUtils.getCurrentDate());
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getCurrentDate());
		
		return new TkUserRoles(principalId, roles, assignments);
    }

    /**
     * Does not keep reference to the assignment objects.  We just need the IDs,
     * so in future refactoring, if we have a lighter weight call to obtain
     * assignments, we could use that and modify this code.
     *
     * @param roles
     * @param assignments
     */
    private TkUserRoles(String principalId, List<TkRole> roles, List<Assignment> assignments) {
        this.principalId = principalId;
        setRoles(roles);
        setAssignments(assignments);
    }

    @Override
    public boolean isLocationAdmin() {
        return CollectionUtils.isNotEmpty(getOrgAdminCharts());
    }

    @Override
    public boolean isDepartmentAdmin() {
        return CollectionUtils.isNotEmpty(getOrgAdminDepartments());
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    @Override
    public Set<Long> getApproverWorkAreas() {
        Set<Long> workAreas = new HashSet<Long>();
        workAreas.addAll(approverRoles.keySet());
        workAreas.addAll(approverDelegateRoles.keySet());
        return workAreas;
    }

    @Override
    public Set<Long> getReviewerWorkAreas() {
        return reviewerRoles.keySet();
    }

    @Override
    public Set<String> getOrgAdminDepartments() {
        return orgAdminRolesDept.keySet();
    }

    @Override
    public Set<String> getOrgAdminCharts() {
        return orgAdminRolesChart.keySet();
    }

    @Override
    public Set<String> getDepartmentViewOnlyDepartments() {
        return deptViewOnlyRoles.keySet();
    }

    /**
     * Accessor method to obtain the Set of active Assignment ids for the
     * current employee.
     *
     * @return a Set of active assignment IDs
     */
    @Override
    public Set<String> getActiveAssignmentIds() {
        return activeAssignmentIds;
    }

    @Override
    public boolean isSystemAdmin() {
        return systemAdmin != null;
    }

    @Override
    public boolean isGlobalViewOnly() {
        return globalViewOnly != null;
    }

    public boolean isDeptViewOnly() {
        return MapUtils.isNotEmpty(deptViewOnlyRoles);
    }

    @Override
    public boolean isSynchronous() {
        return synchronousAssignments;
    }
    
	public boolean isReviewer() {
		return CollectionUtils.isNotEmpty(TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getReviewerWorkAreas());
	}

	public boolean isApprover() {
		return CollectionUtils.isNotEmpty(TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas());
	}

    /**
     * Place the TkRole objects in the provided List into their appropriate
     * buckets for fast lookup.
     *
     * @param roles A List of TkRole objects for the current user.
     */
    public void setRoles(List<TkRole> roles) {
        for (TkRole role : roles) {
            if (role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER)) {
                approverRoles.put(role.getWorkArea(), role);
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER_DELEGATE)) {
                approverDelegateRoles.put(role.getWorkArea(), role);
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_LOCATION_ADMIN) ||
            			role.getRoleName().equals(TkConstants.ROLE_LV_DEPT_ADMIN)) {
                if (!StringUtils.isEmpty(role.getChart())) {
                    orgAdminRolesChart.put(role.getChart(), role);
                    List<Department> ds = TkServiceLocator.getDepartmentService().getDepartments(role.getChart(), TKUtils.getCurrentDate());
                    for (Department d : ds) {
                        orgAdminRolesDept.put(d.getDept(), role);
                    }
                }
            } else if (StringUtils.equals(role.getRoleName(), TkConstants.ROLE_TK_LOCATION_VO)) {
                if (!StringUtils.isEmpty(role.getChart())) {
                    List<Department> ds = TkServiceLocator.getDepartmentService().getDepartments(role.getChart(), TKUtils.getCurrentDate());
                    for (Department dept : ds) {
                        deptViewOnlyRoles.put(dept.getDept(), role);
                    }

                }
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_SYS_ADMIN)) {
                systemAdmin = role;
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_DEPT_VO)) {
                deptViewOnlyRoles.put(role.getDepartment(), role);
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_DEPT_ADMIN)) {
                orgAdminRolesDept.put(role.getDepartment(), role);
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_GLOBAL_VO)) {
                globalViewOnly = role;
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_REVIEWER)) {
                reviewerRoles.put(role.getWorkArea(), role);
            }
        }
    }

    public void setAssignments(List<Assignment> assignments) {
        for (Assignment a : assignments) {
            activeAssignmentIds.add(a.getTkAssignmentId());
            if (a.isSynchronous())
                synchronousAssignments = true;
        }
    }

    @Override
    public boolean isActiveEmployee() {
        return CollectionUtils.isNotEmpty(this.activeAssignmentIds);
    }

    @Override
    public boolean isTimesheetApprover() {
        return MapUtils.isNotEmpty(this.approverRoles) || MapUtils.isNotEmpty(this.approverDelegateRoles);
    }

    public boolean isTimesheetReviewer() {
        return CollectionUtils.isNotEmpty(this.getReviewerWorkAreas());
    }

    @Override
    public boolean isAnyApproverActive() {
        return MapUtils.isNotEmpty(this.approverRoles) || MapUtils.isNotEmpty(this.approverDelegateRoles);
    }


    @Override
    public boolean isApproverForTimesheet(CalendarDocumentContract doc) {
        boolean approver = false;

        if (this.isSystemAdmin()) {
            return true;
        }

        List<Assignment> assignments = doc.getAssignments();
        for (Assignment assignment : assignments) {
            if (this.approverRoles.containsKey(assignment.getWorkArea()) || this.approverDelegateRoles.containsKey(assignment.getWorkArea())) {
                return true;
            }
        }

        return approver;
    }

    @Override
    public boolean isApproverForTimesheet(String docId) {
        boolean approver = false;

        TimesheetDocument doc = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
        if (doc != null)
            approver = isApproverForTimesheet(doc);

        return approver;
    }

    @Override
    public boolean isDocumentWritable(CalendarDocumentContract document) {
        boolean writable = false;

        // Quick escape.
        if (document == null)
            return writable;

        // Sysadmin
        writable = this.isSystemAdmin();
        // Owner (and not enroute/final)
        writable |= (StringUtils.equals(this.principalId, document.getDocumentHeader().getPrincipalId())
                && (StringUtils.equals(TkConstants.ROUTE_STATUS.INITIATED, document.getDocumentHeader().getDocumentStatus()) ||
                StringUtils.equals(TkConstants.ROUTE_STATUS.SAVED, document.getDocumentHeader().getDocumentStatus()) ||
                (StringUtils.equals(TkConstants.ROUTE_STATUS.ENROUTE, document.getDocumentHeader().getDocumentStatus()))));


        if (!writable) {
            // Departmental View Only? || Reviewer || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();
                // Dept admins should not have write access
                //writable |= this.orgAdminRolesDept.containsKey(dept);
                writable |= this.approverRoles.containsKey(wa);
                writable |= this.approverDelegateRoles.containsKey(wa);
                writable |= this.reviewerRoles.containsKey(wa);
            }
        }

        return writable;
    }

    @Override
    public boolean isDocumentWritable(String documentId) {
        return isDocumentWritable(TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId));
    }

    @Override
    public boolean isDocumentReadable(String documentId) {
        boolean readable = false;

        if (documentId != null) {
            return isDocumentReadable(TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId));
        }

        return readable;
    }

    @Override
    public boolean isDocumentReadable(CalendarDocumentContract document) {
        boolean readable = false;

        // Quick escape.
        if (document == null)
            return readable;

        // Sysadmin
        readable = this.isSystemAdmin();
        // Owner
        readable |= StringUtils.equals(this.principalId, document.getDocumentHeader().getPrincipalId());
        // Global VO
        readable |= this.isGlobalViewOnly();

        if (!readable) {
            // Departmental View Only? || Reviewer || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();

                readable |= this.orgAdminRolesDept.containsKey(dept);
                readable |= this.approverRoles.containsKey(wa);
                readable |= this.approverDelegateRoles.containsKey(wa);
                readable |= this.reviewerRoles.containsKey(wa);
                readable |= this.deptViewOnlyRoles.containsKey(dept);
                readable |= this.reviewerRoles.containsKey(wa);
            }
        }

        return readable;
    }

    private boolean isLocationAdmin(CalendarDocumentContract doc) {
        for (Assignment assign : doc.getAssignments()) {
            String location = assign.getJob().getLocation();
            return this.orgAdminRolesChart.containsKey(location);
        }
        return false;
    }

    private boolean isDepartmentAdmin(CalendarDocumentContract doc) {
        for (Assignment assign : doc.getAssignments()) {
            String dept = assign.getDept();
            return this.orgAdminRolesDept.containsKey(dept);
        }
        return false;
    }

    @Override
    public boolean canSubmitTimesheet(CalendarDocumentContract doc) {
        if (StringUtils.equals(TKContext.getPrincipalId(), doc.getDocumentHeader().getPrincipalId())) {
            return true;
        }

        if (this.isApproverForTimesheet(doc)) {
            return true;
        }

        //System admins can route the document as well as the employee
        if (this.isSystemAdmin()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canSubmitTimesheet(String docId) {
        TimesheetDocument doc = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
        return canSubmitTimesheet(doc);
    }

    @Override
    public boolean isApproverForPerson(String principalId) {
        List<Assignment> lstAssignment = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getCurrentDate());

        for (Assignment assignment : lstAssignment) {
            if (getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas().contains(assignment.getWorkArea())) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean isDepartmentAdminForPerson(String principalId) {
        UserRoles userRoles = getUserRoles(GlobalVariables.getUserSession().getPrincipalId());

        // Department admin
        // Department view only
        if (userRoles.isDepartmentAdmin() || userRoles.isDeptViewOnly()) {
        	List<Job> jobs = TkServiceLocator.getJobService().getJobs(principalId,TKUtils.getCurrentDate());
            for (Job job : jobs) {
                if (getOrgAdminDepartments().contains(job.getDept())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isDeptViewOnlyForPerson(String principalId) {
        return isDepartmentAdminForPerson(principalId);
    }

    @Override
    public boolean isLocationAdminForPerson(String principalId) {
        List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(principalId, TKUtils.getCurrentDate());

        if (CollectionUtils.isNotEmpty(roles)) {
            for (TkRole role : roles) {
                if (this.getOrgAdminCharts().contains(role.getChart())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isTimesheetReviewerForPerson(String principalId) {
        List<Assignment> lstAssignment = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getCurrentDate());

        for (Assignment assignment : lstAssignment) {
            if (getReviewerWorkAreas().contains(assignment.getWorkArea())) {
                return true;
            }
        }
        return false;
    }
}