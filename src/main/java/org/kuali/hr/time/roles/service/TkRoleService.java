package org.kuali.hr.time.roles.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface TkRoleService {
	/**
	 * Fetch all roles for a given Principal ID as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getRoles(String principalId, Date asOfDate);
	
	/**
	 * Fetch all inactive roles for a given Principal ID as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getInActiveRoles(String principalId, Date asOfDate);
	/**
	 * Fetch all roles for a given Principal ID and Role Name as of a particular date
	 * @param principalId
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getRoles(String principalId, String roleName, Date asOfDate);
	/**
	 * Fetch all Roles for a given work area and role name as of a particular date
	 * @param workArea
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getWorkAreaRoles(Long workArea, String roleName, Date asOfDate);
	/**
	 * Fetch all Inactive Roles for a given work area and role name as of a particular date
	 * @param workArea
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getInActiveWorkAreaRoles(Long workArea, String roleName, Date asOfDate);
	/**
	 * Fetch all Roles for a given Work Area as of a particular date
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getWorkAreaRoles(Long workArea, Date asOfDate);
	/**
	 * Fetch all Roles for a given Department and Role Name as of a particular date
	 * @param department
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getDepartmentRoles(String department, String roleName, Date asOfDate);
	/**
	 * Fetch all Roles for a given Department of a particular date
	 * @param department
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getDepartmentRoles(String department, Date asOfDate);
	/**
	 * Save or Update a given TkRole
	 * @param role
	 */
	public void saveOrUpdate(TkRole role);
	/**
	 * Save or Update a List of TkRole objects
	 * @param roles
	 */
	public void saveOrUpdate(List<TkRole> roles);

    /**
     * Gets the list of principal IDs responsible for the provided assignment /
     * role name combination.
     *
     * @param assignment the assignment to query
     * @param roleName The role we are interested in.
     * @param asOfDate effective date
     * @return
     */
    public List<String> getResponsibleParties(Assignment assignment, String roleName, Date asOfDate);

    /**
     * Provides a unique set of work areas that this user is an approver for.
     * @param principalId The principal to retrieve roles for.
     * @param asOfDate effective date
     * @return A Set of Long work area numbers.
     */
    public Set<Long> getWorkAreasForApprover(String principalId, Date asOfDate);

    /**
     * Provides a unique set of principal ids that have active assignments in the
     * given work areas.
     * @param workAreas A set of work area numbers.
     * @param asOfDate effective date
     * @return A Set of String principal IDs.
     */
    public Set<String> getActivePrinciaplsForWorkAreas(Set<Long> workAreas, Date asOfDate);
    
    /**
     * Fetches Role by primary key
     */
    public TkRole getRole(Long tkRoleId);
    
    /**
     * Fetches Role by primary key
     */
    public TkRole getRolesByPosition(String positionNumber);
    
    public TkRole getInactiveRolesByPosition(String positionNumber);

    public List<TkRole> getPositionRolesForWorkArea(Long workArea, Date asOfDate);
}
