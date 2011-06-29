package org.kuali.hr.time.roles;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

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

	private Map<String, TkRole> orgAdminRolesDept = new HashMap<String,TkRole>();
    private Map<String, TkRole> orgAdminRolesChart = new HashMap<String,TkRole>();
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

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
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
			} else if (role.getRoleName().equals(TkConstants.ROLE_TK_ORG_ADMIN)) {
                if (StringUtils.isEmpty(role.getChart())) {
                    orgAdminRolesDept.put(role.getDepartment(), role);
                } else {
                    orgAdminRolesChart.put(role.getChart(), role);
                    // TODO : Not sure what date makes most sense here as the effdt...
                    List<Department> ds = TkServiceLocator.getDepartmentService().getDepartments(role.getChart(), TKUtils.getCurrentDate());
                    for (Department d : ds) {
                        orgAdminRolesDept.put(d.getDept(), role);
                    }
                }
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
                    // Pull work areas that belong to department for transitive
                    // role relationship
                    // TODO : Not sure what date makes most sense here as the effdt...
                    List<WorkArea> was = TkServiceLocator.getWorkAreaService().getWorkAreas(role.getDepartment(), TKUtils.getCurrentDate());
                    for (WorkArea w : was) {
                        processorRolesWorkArea.put(w.getWorkArea(), role);
                    }
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

    @Override
    public boolean isApproverForTimesheet(TimesheetDocument doc) {
        boolean approver = false;

        if (this.isSystemAdmin()) {
            return true;
        }

        List<Assignment> assignments = doc.getAssignments();
        for (Assignment assignment : assignments) {
            if (this.processorRolesWorkArea.containsKey(assignment.getWorkArea())) {
                return true;
            } else if (this.approverRoles.containsKey(assignment.getWorkArea())) {
                return true;
            }
        }

        return approver;
    }

    @Override
    public boolean isApproverForTimesheet(String docId) {
        boolean approver = false;

        TimesheetDocument doc =  TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
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
        writable |= ( StringUtils.equals(this.principalId, document.getPrincipalId())
                && StringUtils.equals(TkConstants.ROUTE_STATUS.INITIATED, document.getDocumentHeader().getDocumentStatus()) );

        if (!writable) {
            // Departmental View Only? || Reviewer || Processor || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();

                // processor (dept and wa covered)
                writable |= this.processorRolesWorkArea.containsKey(wa);
                writable |= this.orgAdminRolesDept.containsKey(dept);
                writable |= this.approverRoles.containsKey(wa);
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
        return isDocumentReadable(TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId));
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
            // Departmental View Only? || Reviewer || Processor || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();

                // processor (dept and wa covered)
                readable |= this.processorRolesWorkArea.containsKey(wa);
                readable |= this.orgAdminRolesDept.containsKey(dept);
                readable |= this.approverRoles.containsKey(wa);
                readable |= this.reviewerRoles.containsKey(wa);
                readable |= this.deptViewOnlyRoles.containsKey(dept);
            }
        }

        return readable;
    }
}