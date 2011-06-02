package org.kuali.hr.time.roles.dao;

import org.kuali.hr.time.roles.TkRole;

import java.sql.Date;
import java.util.List;

public interface TkRoleDao {

	/**
	 * Returns a list of roles matching the specified criteria. Nulls are valid
	 * as parameters, see parameter comments.
	 *
     * @param principalId (optional - null is allowed)
     * @param asOfDate The effective date (required)
     * @param roleName (optional - null is allowed)
     * @param workArea (optional - null is allowed)
     * @param department (optional - null is allowed)
     * @param chart (optional - null is allowed)
     * @return A List<TkRole> of roles matching the specified parameters.
	 */
	public List<TkRole> findRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart);
	/**
	 * 
	 * @param positionNumber
	 * @param asOfDate
	 * @param roleName
	 * @param workArea
	 * @param department
	 * @param chart
	 * @return
	 */
	public List<TkRole> findPositionRoles(Long positionNumber, Date asOfDate, String roleName, Long workArea, String department, String chart);
	/**
	 * Returns a list of inactive roles matching the specified criteria. Nulls are valid
	 * as parameters, see parameter comments.
	 *
	 * @param principalId (optional - null is allowed)
	 * @param asOfDate The effective date (required)
	 * @param roleName (optional - null is allowed)
	 * @param workArea (optional - null is allowed)
	 * @param department (optional - null is allowed)
	 * @param chart (optional - null is allowed)
	 * @return A List<TkRole> of roles matching the specified parameters.
	 */
	public List<TkRole> findInActiveRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart);

	/**
	 * A role to update/save
	 * @param role
	 */
	public void saveOrUpdateRole(TkRole role);

	/**
	 * A list of roles to save.
	 * @param roles
	 * @return
	 */
	public void saveOrUpdateRoles(List<TkRole> roles);
}
