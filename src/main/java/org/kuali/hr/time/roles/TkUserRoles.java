package org.kuali.hr.time.roles;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
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

	private Map<String, TkRole> chartAdminRolesDept = new HashMap<String,TkRole>();
    private Map<String, TkRole> chartAdminRolesChart = new HashMap<String,TkRole>();
	private Map<Long, TkRole> approverRoles = new HashMap<Long,TkRole>();
    private Map<Long, TkRole> reviewerRoles = new HashMap<Long,TkRole>();
    private Map<Long, TkRole> processorRolesWorkArea = new HashMap<Long,TkRole>();
    private Map<String, TkRole> processorRolesDept = new HashMap<String,TkRole>();
    private Map<String, TkRole> deptViewOnlyRoles = new HashMap<String, TkRole>();
	private Set<Long> activeAssignmentIds = new HashSet<Long>();

	/**
	 * Constructor that takes a list of all roles that will be encapsulated
	 * by this object.
	 *
	 * @param roles
	 */
	public TkUserRoles(List<TkRole> roles) {
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
	public TkUserRoles(List<TkRole> roles, List<Assignment> assignments) {
		setRoles(roles);
		setAssignments(assignments);
	}

    @Override
    public Set<Long> getApproverWorkAreas() {
        return approverRoles.keySet();
    }

    @Override
    public Set<Long> getReviewerWorkAreas() {
        return reviewerRoles.keySet();
    }

    @Override
    public Set<String> getChartAdminDepartments() {
        return chartAdminRolesDept.keySet();
    }

    @Override
    public Set<String> getChartAdminCharts() {
        return chartAdminRolesChart.keySet();
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
	public Set<Long> getActiveAssignmentIds() {
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
			} else if (role.getRoleName().equals(TkConstants.ROLE_TK_CHART_ADMIN)) {
				chartAdminRolesDept.put(role.getDepartment(), role);
			} else if (role.getRoleName().equals(TkConstants.ROLE_TK_SYS_ADMIN)) {
				systemAdmin = role;
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_DEPT_VO)) {
                deptViewOnlyRoles.put(role.getDepartment(), role);
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_GLOBAL_VO)) {
                globalViewOnly = role;
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_REVIEWER)) {
                reviewerRoles.put(role.getWorkArea(), role);
            } else if (role.getRoleName().equals(TkConstants.ROLE_TK_PROCESSOR)) {
                if (role.getWorkArea() != null) {
                    processorRolesWorkArea.put(role.getWorkArea(), role);
                } else if (!StringUtils.isEmpty(role.getDepartment())) {
                    processorRolesDept.put(role.getDepartment(), role);
                }
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
    public boolean isAnyApproverActive() {
        return this.approverRoles.size() > 0;
    }

    @Override
    public Set<Long> getProcessorWorkAreas() {
        return this.processorRolesWorkArea.keySet();
    }

    @Override
    public Set<String> getProcessorDepartments() {
        return this.processorRolesDept.keySet();
    }
}