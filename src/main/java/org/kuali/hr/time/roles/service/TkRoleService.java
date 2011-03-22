package org.kuali.hr.time.roles.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.roles.TkRole;

public interface TkRoleService {
	/**
	 * Fetch all roles for a given Principal ID as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<TkRole> getRoles(String principalId, Date asOfDate);
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
}
