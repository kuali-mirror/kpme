package org.kuali.hr.time.roles;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.util.*;

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
    ;
    private Map<String, TkRole> deptViewOnlyRoles = new HashMap<String, TkRole>();
    private Set<String> activeAssignmentIds = new HashSet<String>();

    /**
     * Constructor that takes a list of all roles that will be encapsulated
     * by this object.
     *
     * @param roles
     */
    public TkUserRoles(String principalId, List<TkRole> roles) {
        this.principalId = principalId;
        setRoles(roles);
    }

    /**
     * Does not keep reference to the assignment objects.  We just need the IDs,
     * so in future refactoring, if we have a lighter weight call to obtain
     * assignments, we could use that and modify this code.
     *
     * @param roles
     * @param assignments
     */
    public TkUserRoles(String principalId, List<TkRole> roles, List<Assignment> assignments) {
        this.principalId = principalId;
        setRoles(roles);
        setAssignments(assignments);
    }

    @Override
    public boolean isLocationAdmin() {
        return getOrgAdminCharts().size() > 0;
    }

    @Override
    public boolean isDepartmentAdmin() {
        return getOrgAdminDepartments().size() > 0;
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
        return deptViewOnlyRoles.size() > 0;
    }

    @Override
    public boolean isSynchronous() {
        return synchronousAssignments;
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
            } else {
                throw new RuntimeException("Invalid Role."); // TODO: Maybe we want to just ignore this exception.
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
        return this.activeAssignmentIds.size() > 0;
    }

    @Override
    public boolean isTimesheetApprover() {
        return this.isSystemAdmin() || this.approverRoles.size() > 0 || this.approverDelegateRoles.size() > 0 || this.orgAdminRolesDept.size() > 0;
    }

    public boolean isTimesheetReviewer() {
        return this.getReviewerWorkAreas().size() > 0;
    }

    @Override
    public boolean isAnyApproverActive() {
        return this.approverRoles.size() > 0 || this.approverDelegateRoles.size() > 0;
    }


    @Override
    public boolean isApproverForTimesheet(TimesheetDocument doc) {
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
    public boolean isDocumentWritable(TimesheetDocument document) {
        boolean writable = false;

        // Quick escape.
        if (document == null)
            return writable;

        // Sysadmin
        writable = this.isSystemAdmin();
        // Owner (and not enroute/final)
        writable |= (StringUtils.equals(this.principalId, document.getPrincipalId())
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

                writable |= this.orgAdminRolesDept.containsKey(dept);
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
    public boolean isDocumentReadable(TimesheetDocument document) {
        boolean readable = false;

        // Quick escape.
        if (document == null)
            return readable;

        // Sysadmin
        readable = this.isSystemAdmin();
        // Owner
        readable |= StringUtils.equals(this.principalId, document.getPrincipalId());
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

    private boolean isLocationAdmin(TimesheetDocument doc) {
        for (Assignment assign : doc.getAssignments()) {
            String location = assign.getJob().getLocation();
            return this.orgAdminRolesChart.containsKey(location);
        }
        return false;
    }

    private boolean isDepartmentAdmin(TimesheetDocument doc) {
        for (Assignment assign : doc.getAssignments()) {
            String dept = assign.getDept();
            return this.orgAdminRolesDept.containsKey(dept);
        }
        return false;
    }

    @Override
    public boolean canSubmitTimesheet(TimesheetDocument doc) {
        if (StringUtils.equals(TKContext.getPrincipalId(), doc.getPrincipalId())) {
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
            if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(assignment.getWorkArea())) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean isDepartmentAdminForPerson(String principalId) {
        UserRoles userRoles = TKContext.getUser().getCurrentRoles();
        TKUser targetUser = TkServiceLocator.getUserService().buildTkUser(principalId, TKUtils.getCurrentDate());

        // Department admin
        // Department view only
        if (userRoles.isDepartmentAdmin() || userRoles.isDeptViewOnly()) {

            for (String dept : targetUser.getDepartments()) {
                if (getOrgAdminDepartments().contains(dept)) {
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

        if (roles.size() > 0) {
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
        List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(principalId, TKUtils.getCurrentDate());

        if (roles.size() > 0) {
            for (TkRole role : roles) {
                if (this.getReviewerWorkAreas().contains(role.getWorkArea())) {
                    return true;
                }
            }
        }

        return false;
    }
}