package org.kuali.hr.time.roles;

import java.util.Set;

/**
 * Class to set the contract between Users and Roles.
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
     * Provides a Set of WorkArea names that this user is a processor for.
     * @return a Set<Long> of workarea names.
     */
    public Set<Long> getProcessorWorkAreas();

    /**
     * Provides a Set of Assignment ids that this user is active under.
     * @return a Set<Long> of assignment object ids.
     */
    public Set<Long> getActiveAssignmentIds();

    /**
     * Provides a Set<String> of Department names that this user is a processor
     * for.
     * @return a Set<String> of Department names.
     */
    public Set<String> getProcessorDepartments();

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
    public Set<String> getChartAdminDepartments();

    /**
     * Provides a Set<String> of chart names that this user is a chart
     * administrator for.
     * @return a Set<String> of Chart names.
     */
    public Set<String> getChartAdminCharts();

}
