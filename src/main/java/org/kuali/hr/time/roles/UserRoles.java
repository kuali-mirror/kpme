package org.kuali.hr.time.roles;

import java.util.Set;

/**
 * Class to set the contract between Users and Roles. TKUser is the target
 * implementing class.
 */
public interface UserRoles {

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

    public Set<Long> getApproverWorkAreas();

    public Set<Long> getReviewerWorkAreas();

    public Set<String> getChartAdminDepartments();
    public Set<String> getChartAdminCharts();

    public Set<Long> getActiveAssignmentIds();

    public Set<Long> getProcessorWorkAreas();
    public Set<String> getProcessorDepartments();
    public Set<String> getDepartmentViewOnlyDepartments();
}
