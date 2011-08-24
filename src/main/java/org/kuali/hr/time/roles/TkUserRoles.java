package org.kuali.hr.time.roles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

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
    private Map<Long, TkRole> reviewerRoles = new HashMap<Long,TkRole>();;
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
			} else if (role.getRoleName().equals(TkConstants.ROLE_TK_LOCATION_ADMIN)) {
                if (!StringUtils.isEmpty(role.getChart())) {
                    orgAdminRolesChart.put(role.getChart(), role);
                    List<Department> ds = TkServiceLocator.getDepartmentService().getDepartments(role.getChart(), TKUtils.getCurrentDate());
                    for (Department d : ds) {
                        orgAdminRolesDept.put(d.getDept(), role);
                    }
                }
			} else if(StringUtils.equals(role.getRoleName(), TkConstants.ROLE_TK_LOCATION_VO)){
				if (!StringUtils.isEmpty(role.getChart())) {
					List<Department> ds = TkServiceLocator.getDepartmentService().getDepartments(role.getChart(), TKUtils.getCurrentDate());
					for(Department dept : ds){
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
            }  else {
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
        return this.isSystemAdmin() || this.approverRoles.size() > 0 || this.orgAdminRolesDept.size() > 0;
    }

    @Override
    public boolean isAnyApproverActive() {
        return this.approverRoles.size() > 0;
    }


    @Override
    public boolean isApproverForTimesheet(TimesheetDocument doc) {
        boolean approver = false;

        if (this.isSystemAdmin()) {
            return true;
        }

        List<Assignment> assignments = doc.getAssignments();
        for (Assignment assignment : assignments) {
            if (this.approverRoles.containsKey(assignment.getWorkArea())) {
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
                && (StringUtils.equals(TkConstants.ROUTE_STATUS.INITIATED, document.getDocumentHeader().getDocumentStatus()) ||
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
            // Departmental View Only? || Reviewer || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();

                readable |= this.orgAdminRolesDept.containsKey(dept);
                readable |= this.approverRoles.containsKey(wa);
                readable |= this.reviewerRoles.containsKey(wa);
                readable |= this.deptViewOnlyRoles.containsKey(dept);
            }
        }

        return readable;
    }

    private boolean isLocationAdmin(TimesheetDocument doc){
    	for(Assignment assign : doc.getAssignments()){
    		String location = assign.getJob().getLocation();
    		return this.orgAdminRolesChart.containsKey(location);
    	}
    	return false;
    }

    private boolean isDepartmentAdmin(TimesheetDocument doc){
    	for(Assignment assign : doc.getAssignments()){
    		String dept = assign.getDept();
    		return this.orgAdminRolesDept.containsKey(dept);
    	}
    	return false;
    }

	@Override
	public boolean canSubmitTimesheet(TimesheetDocument doc) {
		if(StringUtils.equals(TKContext.getPrincipalId(),doc.getPrincipalId())){
			return true;
		}

		if(this.isApproverForTimesheet(doc)){
			return true;
		}

		//System admins can route the document as well as the employee
		if(this.isSystemAdmin()){
			return true;
		}
		return false;
	}

	@Override
	public boolean canSubmitTimesheet(String docId) {
		TimesheetDocument doc = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
		return canSubmitTimesheet(doc);
	}
}