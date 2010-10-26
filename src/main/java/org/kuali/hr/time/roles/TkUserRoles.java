package org.kuali.hr.time.roles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.util.TkConstants;

/**
 * TkUserRoles encapsulates the concept of roles for a single user and provides
 * lookup methods for quick Role checking. This object will be stored in TkUser.
 */
public class TkUserRoles {

	private TkRole systemAdmin;
	/** Department -> TkRole */
	private Map<String, TkRole> orgAdminRoles = new HashMap<String,TkRole>();
	/** Work Area -> TkRole */
	private Map<Long, TkRole> approverRoles = new HashMap<Long,TkRole>();
	/** Set of TkAssignmentIds */
	private Set<Long> tkEmployeeRoles = new HashSet<Long>();
	
	public TkUserRoles() {
	}
	
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

	public boolean isSystemAdmin() {
		return systemAdmin != null;
	}

	public boolean isTkEmployee(Long tkAssignmentId) {
		return tkEmployeeRoles.contains(tkAssignmentId);
	}

	public boolean isApprover(Long workArea) {
		return approverRoles.containsKey(workArea);
	}

	public boolean isOrgAdmin(String department) {
		return orgAdminRoles.containsKey(department);
	}
	
	public void setRoles(List<TkRole> roles) {
		for (TkRole role : roles) {
			if (role.getRoleName().equals(TkConstants.ROLE_TK_APPROVER)) {
				approverRoles.put(role.getWorkArea(), role);
			} else if (role.getRoleName().equals(TkConstants.ROLE_TK_ORG_ADMIN)) {
				orgAdminRoles.put(role.getDepartment(), role);
			} else if (role.getRoleName().equals(TkConstants.ROLE_TK_SYS_ADMIN)) {
				systemAdmin = role;
			} else {
				// TODO : A Quick Fail while we're in development, probably do
				// not want to throw this exception for ever.  Maybe just log
				// a message and continue on would be more appropriate.
				throw new RuntimeException("Invalid Role.");
			}
		}		
	}
	
	public void setAssignments(List<Assignment> assignments) {
		for (Assignment a : assignments) {
			tkEmployeeRoles.add(a.getTkAssignmentId());
		}
	}
	
}
