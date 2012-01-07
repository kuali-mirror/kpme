package org.kuali.hr.time.roles;

import java.util.Set;

import org.kuali.hr.time.timesheet.TimesheetDocument;

/**
 * Class to set the contract between Users and Roles.
 */
public interface UserRoles {

    public boolean isLocationAdmin();

    public boolean isDepartmentAdmin();

    public String getPrincipalId();

    public boolean isTimesheetReviewer();
    
    /**
     * Is the current user a system administrator?
     * @return true if yes, false otherwise
     */
    public boolean isSystemAdmin();

    /**
     * Does the current user have any active assignments?
     * @return true if yes, false otherwise.
     */
    public boolean isActiveEmployee();

    /**
     * Does the current user have any active approver roles?
     *
     * Approver roles attach at the WorkArea level.
     *
     * @return true if yes, false otherwise.
     */
    public boolean isAnyApproverActive();


    /**
     * Does the current user have any role that grants permission to approve
     * timesheets?
     * @return true if yes, false otherwise.
     */
    public boolean isTimesheetApprover();

    /**
     * Does the current user have the Global View Only role?
     *
     * @return true if yes, false otherwise.
     */
    public boolean isGlobalViewOnly();

    /**
     * Does the current user have an assignment that is marked synchronous?
     *
     * @return true if yes, false otherwise.
     */
    public boolean isSynchronous();

    /**
     * Provides a set of WorkArea names that this user is an approver for.
     * @return a Set<Long> of workarea names.
     */
    public Set<Long> getApproverWorkAreas();

    /**
     * Provides a Set of WorkArea names that this user is a reviewer for.
     * @return a Set<Long> of workarea names.
     */
    public Set<Long> getReviewerWorkAreas();

    /**
     * Provides a Set of Assignment ids that this user is active under.
     * @return a Set<Long> of assignment object ids.
     */
    public Set<String> getActiveAssignmentIds();

    /**
     * Provides a Set<String> of Department names that this user is a
     * departmental view only for.
     * @return a Set<String> of Department names.
     */
    public Set<String> getDepartmentViewOnlyDepartments();

    /**
     * Provides a Set<String> of Department names that this user is a chart
     * administrator for.
     * @return a Set<String> of Department names.
     */
    public Set<String> getOrgAdminDepartments();

    /**
     * Provides a Set<String> of chart names that this user is a chart
     * administrator for.
     * @return a Set<String> of Chart names.
     */
    public Set<String> getOrgAdminCharts();
    
    public boolean isDeptViewOnly();

    /**
     * Indicates whether or not the current can approve the provided timesheet.
     * @param doc The TimesheetDocument in question.
     * @return true if the doc can be approved by the current user.
     */
    public boolean isApproverForTimesheet(TimesheetDocument doc);
    public boolean isApproverForTimesheet(String docId);

    public boolean canSubmitTimesheet(TimesheetDocument doc);
    public boolean canSubmitTimesheet(String docId);

    public boolean isDocumentReadable(TimesheetDocument document);
    public boolean isDocumentReadable(String documentId);

    public boolean isDocumentWritable(TimesheetDocument document);
    public boolean isDocumentWritable(String documentId);
    
    public boolean isApproverForPerson(String principalId);
}
